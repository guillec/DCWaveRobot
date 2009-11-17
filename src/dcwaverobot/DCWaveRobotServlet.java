package dcwaverobot;

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
    	    
    	    URL yahoo;
			try {
				yahoo = new URL("http://www.wmata.com/rider_tools/metro_service_status/feeds/rail.xml");
				URLConnection yc = yahoo.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
				String inputLine;

            while ((inputLine = in.readLine()) != null) 
            	blip.createChild().getDocument().append(inputLine);
            	in.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	  }
      }
    }
  }
}
