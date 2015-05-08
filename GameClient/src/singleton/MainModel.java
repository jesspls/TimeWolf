/**
 * @author Anish Kunduru
 *
 * The purpose of this program is to have one central model for all our user's game data.
 * This utilizes a software design pattern called the singleton pattern.
 * We separate each controller's game data into a new submodel that will be implemented in this class.
 */

package singleton;

public class MainModel
{
   // Singleton.
   private final static MainModel model = new MainModel();

   /**
    * @return The current instance of MainModel.
    */
   public static MainModel getModel()
   {
      return model;
   }

   // Instantiate data classes to store information.
   private LoginData loginData = new LoginData();
   private MainData mainData = new MainData();
   private GameLobbyData gameLobbyData = new GameLobbyData();
   private ProfileData profileData = new ProfileData();
   private ControllerData controllerData = new ControllerData();
   private GameTableData gameTableData = new GameTableData();
   
   /**
    * @return The current instance of GameTableData.
    */
   public GameTableData currentGameTableData()
   {
      return gameTableData;
   }
   
   /**
    * @return The current instance of LoginData.
    */
   public LoginData currentLoginData()
   {
      return loginData;
   }
   
   /**
    * @return the current instance of ControllerData.
    */
   public ControllerData currentControllerData()
   {
      return controllerData;
   }

   /**
    * @return The current instance of MainData.
    */
   public MainData currentMainData()
   {
      return mainData;
   }

   /**
    * @return The current instance of GameLobbyData.
    */
   public GameLobbyData currentGameLobbyData()
   {
      return gameLobbyData;
   }

   /**
    * @return The current instance of ProfileData.
    */
   public ProfileData profileData()
   {
      return profileData;
   }
}
