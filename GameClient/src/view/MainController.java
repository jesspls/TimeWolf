/**
 * This is the controller of controllers.
 * Its job is to seamlessly manage handoffs between deifferent FXML pages and their respective controllers.
 * 
 * We create this by simply implementing the AbstractScreenController we created earlier (easy enough).
 */

package view;

import profile.ProfileScreenController;
import createGame.CreateGameScreenController;
import afterGame.AfterGameScreenController;
import registration.RegistrationScreenController;
import searchGame.SearchGameScreenController;
import singleton.MainModel;
import userListing.UserListingScreenController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Parent;
import javafx.util.Duration;
import leaderboards.LeaderboardsScreenController;
import login.LoginScreenController;
import moderatorReports.ModeratorReportsScreenController;
import forgotPassword.ForgotPasswordScreenController;
import framework.AbstractScreenController;
import gameLobby.GameLobbyScreenController;
import gameRules.GameRulesScreenController;
import gameTable.GameTableScreenController;

public class MainController extends AbstractScreenController
{
   // Constants that represent the screen names and locations in the workspace.
   public static final String LOGIN_SCREEN = "login";
   public static final String LOGIN_SCREEN_FXML = "/login/LoginScreen.fxml";

   public static final String FORGOT_PASSWORD_SCREEN = "forgotPassword";
   public static final String FORGOT_PASSWORD_SCREEN_FXML = "/forgotPassword/ForgotPasswordScreen.fxml";

   public static final String REGISTRATION_SCREEN = "registration";
   public static final String REGISTRATION_SCREEN_FXML = "/registration/RegistrationScreen.fxml";

   public static final String GAME_LOBBY_SCREEN = "gameLobby";
   public static final String GAME_LOBBY_SCREEN_FXML = "/gameLobby/GameLobbyScreen.fxml";

   public static final String GAME_TABLE_SCREEN = "gameTable";
   public static final String GAME_TABLE_SCREEN_FXML = "/gameTable/GameTableScreen.fxml";

   public static final String CREATE_GAME_SCREEN = "createGame";
   public static final String CREATE_GAME_SCREEN_FXML = "/createGame/CreateGameScreen.fxml";

   public static final String SEARCH_GAME_SCREEN = "searchGame";
   public static final String SEARCH_GAME_SCREEN_FXML = "/searchGame/SearchGameScreen.fxml";

   public static final String GAME_RULES_SCREEN = "gameRules";
   public static final String GAME_RULES_SCREEN_FXML = "/gameRules/GameRulesScreen.fxml";

   public static final String USER_LISTING_SCREEN = "userListing";
   public static final String USER_LISTING_SCREEN_FXML = "/userListing/UserListingScreen.fxml";

   public static final String PROFILE_SCREEN = "profileScreen";
   public static final String PROFILE_SCREEN_FXML = "/profile/ProfileScreen.fxml";

   public static final String AFTER_GAME_SCREEN = "afterGameScreen";
   public static final String AFTER_GAME_SCREEN_FXML = "/afterGame/AfterGameScreen.fxml";

   public static final String LEADERBOARDS_SCREEN = "leaderboards";
   public static final String LEADERBOARDS_SCREEN_FXML = "/leaderboards/LeaderboardsScreen.fxml";
   
   public static final String MODERATOR_REPORTS_SCREEN = "moderatorReports";
   public static final String MODERATOR_REPORTS_SCREEN_FXML = "/moderatorReports/ModeratorReportsScreen.fxml";
   
   public void goToModeratorReportsScreen()
   {
      try
      {
         ModeratorReportsScreenController controller = (ModeratorReportsScreenController) loadScreen(MODERATOR_REPORTS_SCREEN_FXML);
         MainModel.getModel().currentControllerData().setCurrentController(MODERATOR_REPORTS_SCREEN);
         MainModel.getModel().currentControllerData().setModeratorReportsScreenController(controller);
      }
      catch (Exception e)
      {
         // DEBUG
         System.out.println("Error trying to load the moderator screen. This is likely an issue with its controller AND/OR FXML dependencies.\n" + e.getMessage());
         e.printStackTrace();
      }
   }
   
   /**
    * Method so that we can dynamically access the login screen at runtime.
    */
   public void goToLoginScreen()
   {
      try
      {
         LoginScreenController controller = (LoginScreenController) loadScreen(LOGIN_SCREEN_FXML);
         MainModel.getModel().currentControllerData().setCurrentController(LOGIN_SCREEN);
         MainModel.getModel().currentControllerData().setLoginScreenController(controller);
      }
      catch (Exception e)
      {
         // DEBUG
         System.out.println("Error trying to load the after game screen. This is likely an issue with its controller AND/OR FXML dependencies.\n" + e.getMessage());
         e.printStackTrace();
      }
   }
   
   /**
    * Method so that we can dynamically access the game rules screen at runtime.
    */
   public void goToGameRulesScreen()
   {
      try
      {
         GameRulesScreenController controller = (GameRulesScreenController) loadScreen(GAME_RULES_SCREEN_FXML);
         MainModel.getModel().currentControllerData().setCurrentController(GAME_RULES_SCREEN);
         MainModel.getModel().currentControllerData().setGameRulesScreenController(controller);
      }
      catch (Exception e)
      {
         // DEBUG
         System.out.println("Error trying to load the game rules screen. This is likely an issue with its controller AND/OR FXML dependencies.\n" + e.getMessage());
         e.printStackTrace();
      }
   }
   
   /**
    * Method so that we can dynamically access the after game screen at runtime.
    */
   public void goToAfterGameScreen()
   {
      try
      {
         AfterGameScreenController controller = (AfterGameScreenController) loadScreen(AFTER_GAME_SCREEN_FXML);
         MainModel.getModel().currentControllerData().setCurrentController(AFTER_GAME_SCREEN);
         MainModel.getModel().currentControllerData().setAfterGameScreenController(controller);
      }
      catch (Exception e)
      {
         // DEBUG
         System.out.println("Error trying to load the after game screen. This is likely an issue with its controller AND/OR FXML dependencies.\n" + e.getMessage());
         e.printStackTrace();
      }
   }
   
   /**
    * Method so that we can dynamically access the create game screen at runtime.
    */
   public void goToCreateGameScreen()
   {
      try
      {
         CreateGameScreenController controller = (CreateGameScreenController) loadScreen(CREATE_GAME_SCREEN_FXML);
         MainModel.getModel().currentControllerData().setCurrentController(CREATE_GAME_SCREEN);
         MainModel.getModel().currentControllerData().setCreateGameScreenController(controller);
      }
      catch (Exception e)
      {
         // DEBUG
         System.out.println("Error trying to load the creat game screen. This is likely an issue with its controller AND/OR FXML dependencies.\n" + e.getMessage());
         e.printStackTrace();
      }
   }
   
   /**
    * Method so that we can dynamically access the forgot password screen at runtime.
    */
   public void goToForgotPasswordScreen()
   {
      try
      {
         ForgotPasswordScreenController controller = (ForgotPasswordScreenController) loadScreen(FORGOT_PASSWORD_SCREEN_FXML);
         MainModel.getModel().currentControllerData().setCurrentController(FORGOT_PASSWORD_SCREEN);
         MainModel.getModel().currentControllerData().setForgotPasswordScreenController(controller);
      }
      catch (Exception e)
      {
         // DEBUG
         System.out.println("Error trying to load the forgot password screen. This is likely an issue with its controller AND/OR FXML dependencies.\n" + e.getMessage());
         e.printStackTrace();
      }
   }
   
   /**
    * Method so that we can dynamically access the game lobby screen at runtime.
    */
   public void goToGameLobbyScreen()
   {
      try
      {
         GameLobbyScreenController controller = (GameLobbyScreenController) loadScreen(GAME_LOBBY_SCREEN_FXML);
         MainModel.getModel().currentControllerData().setCurrentController(GAME_LOBBY_SCREEN);
         MainModel.getModel().currentControllerData().setGameLobbyScreenController(controller);
      }
      catch (Exception e)
      {
         // DEBUG
         System.out.println("Error trying to load the game lobby screen. This is likely an issue with its controller AND/OR FXML dependencies.\n" + e.getMessage());
         e.printStackTrace();
      }
   }
   
   /**
    * Method so that we can dynamically access the game table screen at runtime.
    */
   public void goToGameTableScreen()
   {
      try
      {
         GameTableScreenController controller = (GameTableScreenController) loadScreen(GAME_TABLE_SCREEN_FXML);
         MainModel.getModel().currentControllerData().setCurrentController(GAME_TABLE_SCREEN);
         MainModel.getModel().currentControllerData().setGameTableScreenController(controller);
      }
      catch (Exception e)
      {
         // DEBUG
         System.out.println("Error trying to load the game table screen. This is likely an issue with its controller AND/OR FXML dependencies.\n" + e.getMessage());
         e.printStackTrace();
      }
   }
   
   /**
    * Method so that we can dynamically access the leaderboards screen at runtime.
    */
   public void goToLeaderboardsScreen()
   {
      try
      {
         LeaderboardsScreenController controller = (LeaderboardsScreenController) loadScreen(LEADERBOARDS_SCREEN_FXML);
         MainModel.getModel().currentControllerData().setCurrentController(LEADERBOARDS_SCREEN);
         MainModel.getModel().currentControllerData().setLeaderboardsScreenController(controller);
      }
      catch (Exception e)
      {
         // DEBUG
         System.out.println("Error trying to load the leaderboards screen. This is likely an issue with its controller AND/OR FXML dependencies.\n" + e.getMessage());
         e.printStackTrace();
      }
   }
   
   /**
    * Method so that we can dynamically access the profile screen at runtime.
    */
   public void goToProfileScreen()
   {
      try
      {
         ProfileScreenController controller = (ProfileScreenController) loadScreen(PROFILE_SCREEN_FXML);
         MainModel.getModel().currentControllerData().setCurrentController(PROFILE_SCREEN);
         MainModel.getModel().currentControllerData().setProfileScreenController(controller);
      }
      catch (Exception e)
      {
         // DEBUG
         System.out.println("Error trying to load the profile screen. This is likely an issue with its controller AND/OR FXML dependencies.\n" + e.getMessage());
         e.printStackTrace();
      }
   }
   
   /**
    * Method so that we can dynamically access the registration screen at runtime.
    */
   public void goToRegistrationScreen()
   {
      try
      {
         RegistrationScreenController controller = (RegistrationScreenController) loadScreen(REGISTRATION_SCREEN_FXML);
         MainModel.getModel().currentControllerData().setCurrentController(REGISTRATION_SCREEN);
         MainModel.getModel().currentControllerData().setRegistrationScreenController(controller);
      }
      catch (Exception e)
      {
         // DEBUG
         System.out.println("Error trying to load the registration screen. This is likely an issue with its controller AND/OR FXML dependencies.\n" + e.getMessage());
         e.printStackTrace();
      }
   }
   
   /**
    * Method so that we can dynamically access the search game screen at runtime.
    */
   public void goToSearchGameScreen()
   {
      try
      {
         SearchGameScreenController controller = (SearchGameScreenController) loadScreen(SEARCH_GAME_SCREEN_FXML);
         MainModel.getModel().currentControllerData().setCurrentController(SEARCH_GAME_SCREEN);
         MainModel.getModel().currentControllerData().setSearchGameScreenController(controller);
      }
      catch (Exception e)
      {
         // DEBUG
         System.out.println("Error trying to load the search game screen. This is likely an issue with its controller AND/OR FXML dependencies.\n" + e.getMessage());
         e.printStackTrace();
      }
   }
   
   /**
    * Method so that we can dynamically access the user listing screen at runtime.
    */
   public void goToUserListingScreen()
   {
      try
      {
         UserListingScreenController controller = (UserListingScreenController) loadScreen(USER_LISTING_SCREEN_FXML);
         MainModel.getModel().currentControllerData().setCurrentController(USER_LISTING_SCREEN);
         MainModel.getModel().currentControllerData().setUserListingScreenController(controller);
      }
      catch (Exception e)
      {
         // DEBUG
         System.out.println("Error trying to load the user listing screen. This is likely an issue with its controller AND/OR FXML dependencies.\n" + e.getMessage());
         e.printStackTrace();
      }
   }
   
   /**
    * Make sure the current page gets to destroy its elements if it has something to destroy.
    * This will be called by the main application Stage.
    */
   public void closeApplication()
   {
      if (destroyableController != null)
         destroyableController.onDestroy();
   }
   
   /**
    * Replaces the page.
    * We will override this when we extend this class because we are fancy and want style (animations).
    * 
    * @param loadScreen The page that you wish to display.
    */
   @Override
   protected void displayPage(final Parent screen)
   {
      // For transition effects.
      final DoubleProperty opacity = opacityProperty();
      
      // Check if a screen is being displayed.
      if (!getChildren().isEmpty())
      {
         Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)), new KeyFrame(Duration.millis(275), action ->
         {
            // Remove the displayed screen.
            getChildren().remove(0);

            // Display the passed screen.
            getChildren().add(0, screen);
            
            Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)), new KeyFrame(Duration.millis(225), new KeyValue(opacity, 1.0)));
            
            fadeIn.play();
         }, new KeyValue(opacity, 0.0))); 
         
         fade.play();
         
      }
      else
      {
         setOpacity(0.0);
         
         // There is nothing being displayed, just show the passed screen.
         getChildren().add(screen);
         
         Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)), new KeyFrame(Duration.millis(1000), new KeyValue(opacity, 1.0)));
         
         fadeIn.play();
      }
   }
}