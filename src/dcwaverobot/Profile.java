package dcwaverobot;

import com.google.wave.api.ProfileServlet;

public class Profile extends ProfileServlet {
  @Override
  public String getRobotName() {
    return "My Robot Name";
  }
 
  @Override
  public String getRobotAvatarUrl() {
    return "My Robot Image URL";
  }
 
}
