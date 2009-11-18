package dcwaverobot;

import groovy.lang.GroovyShell;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;



import com.google.wave.api.*;

public class DCWaveRobotServlet extends AbstractRobotServlet {

  @Override
  public void processEvents(RobotMessageBundle bundle) {
    Wavelet wavelet = bundle.getWavelet();
              
    if (bundle.wasSelfAdded()) {
      Blip blip = wavelet.appendBlip();
      TextView textView = blip.getDocument();
      textView.append("Waaahooo! I am alive!");
    }
            
    for (Event e: bundle.getEvents()) {
      if (e.getType() == EventType.WAVELET_PARTICIPANTS_CHANGED) {    
        Blip blip = wavelet.appendBlip();
        TextView textView = blip.getDocument();
        textView.append("Welcome to the XMPP-DC Group!");
      }
      
      if (e.getType() == EventType.BLIP_SUBMITTED) {
    	  Blip blip = e.getBlip();
    	  String text = blip.getDocument().getText();
    	  
    	  if (text.contains("dcmetro")) {
				StringBuilder script = new StringBuilder();
				script.append("def rss = new XmlSlurper().parse('http://www.wmata.com/rider_tools/metro_service_status/feeds/rail.xml'); ");
				script.append("return rss.toString()");
				executeScript(blip, script.toString());
				
    	 } else if (text.contains("dcweather")) {
    			StringBuilder script = new StringBuilder();
 				script.append("def rss = new XmlSlurper().parse('http://weather.yahooapis.com/forecastrss?p=20001'); ");
 				script.append("def result = rss.channel.item.title; ");
 				script.append("result << ' ' + rss.channel.item.condition.@text; ");
 				script.append("return result");
 				executeScript(blip, script.toString());
    	 }
      }
    }
  }
  
  private void executeScript(Blip blip, String script) {  
		GroovyShell shell = new GroovyShell();  
		StringBuilder result = new StringBuilder();  
		try {  
		  result.append(shell.evaluate(script));  
		  blip.createChild().getDocument().append(result.toString());
		} catch (Exception e) {  
		  result.append(e.toString());  
		}  
  }
}
