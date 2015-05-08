/**
 * @author Anish Kunduru
 * 
 * The purpose of this program is to store a reference to the current controller so that other threads can set public state data.
 */

package singleton;

import profile.ProfileScreenController;
import registration.RegistrationScreenController;
import searchGame.SearchGameScreenController;
import createGame.CreateGameScreenController;
import forgotPassword.ForgotPasswordScreenController;
import gameLobby.GameLobbyScreenController;
import gameRules.GameRulesScreenController;
import gameTable.GameTableScreenController;
import afterGame.AfterGameScreenController;
import userListing.UserListingScreenController;
import view.MainController;
import leaderboards.LeaderboardsScreenController;
import login.LoginScreenController;
import moderatorReports.ModeratorReportsScreenController;

public class ControllerData
{
   // Check current controller.
   String currentController;
   
   // All possible controllers.
   LoginScreenController loginScreenController;
   AfterGameScreenController afterGameScreenController;
   CreateGameScreenController createGameScreenController;
   ForgotPasswordScreenController forgotPasswordScreenController;
   GameLobbyScreenController gameLobbyScreenController;
   GameRulesScreenController gameRulesScreenController;
   GameTableScreenController gameTableScreenController;
   LeaderboardsScreenController leaderboardsScreenController;
   ProfileScreenController profileScreenController;
   RegistrationScreenController registrationScreenController;
   SearchGameScreenController searchGameScreenController;
   UserListingScreenController userListingScreenController;
   ModeratorReportsScreenController moderatorReportsScreenController;
   
   /**
    * Default constructor to use in singleton.
    */
   public ControllerData()
   {
   }
   
   /**
    * Set the current controller so that it can be accessed by other pages.
    * 
    * @param currentController The currently active controller.
    */
   public void setCurrentController(String currentController)
   {
      this.currentController = currentController;
   }
   
   /**
    * Lets you know what the current controller is as set by the MainController.
    * 
    * @return A String representing the current controller.
    */
   public String getCurrentController()
   {
      return currentController;
   }
   
   /**
    * Allows you to set a LoginScreenControllerController.
    * 
    * @param controller The controller that you want to assign.
    * @return True if the passed controller was set; false if it was determined not to be a LoginScreenController.
    */
   public boolean setLoginScreenController(LoginScreenController controller)
   {
      if (controller instanceof LoginScreenController)
      {
         loginScreenController = controller;
         return true;
      }
      
      // Implied else.
      return false;
   }
   
   /**
    * @return LoginScreenController if it is the currently active screen. Otherwise you will get a null.
    */
   public LoginScreenController getLoginScreenController()
   {
      if (currentController.equals(MainController.LOGIN_SCREEN))
         return loginScreenController;
      else
         return null;
   }
   
   /**
    * Allows you to set an AfterGameScreenController.
    * 
    * @param controller The controller that you want to assign.
    * @return True if the passed controller was set; false if it was determined not to be an AfterGameScreenController.
    */
   public boolean setAfterGameScreenController(AfterGameScreenController controller)
   {
      if (controller instanceof AfterGameScreenController)
      {
         afterGameScreenController = controller;
         return true;
      }
      
      // Implied else.
      return false;
   }
   
   /**
    * @return AfterGameScreenController if it is the currently active screen. Otherwise you will get a null.
    */
   public AfterGameScreenController getAfterGameScreenController()
   {
      if (currentController.equals(MainController.AFTER_GAME_SCREEN))
         return afterGameScreenController;
      else
         return null;
   }
   
   /**
    * Allows you to set a CreateGameScreenController.
    * 
    * @param controller The controller that you want to assign.
    * @return True if the passed controller was set; false if it was determined not to be a CreateGameScreenController.
    */
   public boolean setCreateGameScreenController(CreateGameScreenController controller)
   {
      if (controller instanceof CreateGameScreenController)
      {
         createGameScreenController = controller;
         return true;
      }
      
      // Implied else.
      return false;
   }
   
   /**
    * @return CreateGameScreenController if it is the currently active screen. Otherwise you will get a null.
    */
   public CreateGameScreenController getCreateGameScreenController()
   {
      if (currentController.equals(MainController.CREATE_GAME_SCREEN))
         return createGameScreenController;
      else
         return null;
   }
   
   /**
    * Allows you to set a ForgotPasswordScreenController.
    * 
    * @param controller The controller that you want to assign.
    * @return True if the passed controller was set; false if it was determined not to be a ForgotPasswordScreenController.
    */
   public boolean setForgotPasswordScreenController(ForgotPasswordScreenController controller)
   {
      if (controller instanceof ForgotPasswordScreenController)
      {
         forgotPasswordScreenController = controller;
         return true;
      }
      
      // Implied else.
      return false;
   }
   
   /**
    * @return ForgotPasswordScreenController if it is the currently active screen. Otherwise you will get a null.
    */
   public ForgotPasswordScreenController getForgotPasswordScreenController()
   {
      if (currentController.equals(MainController.FORGOT_PASSWORD_SCREEN))
         return forgotPasswordScreenController;
      else
         return null;
   }
   
   /**
    * Allows you to set a GameLobbyScreenController.
    * 
    * @param controller The controller that you want to assign.
    * @return True if the passed controller was set; false if it was determined not to be a GameLobbyScreenController.
    */
   public boolean setGameLobbyScreenController(GameLobbyScreenController controller)
   {
      if (controller instanceof GameLobbyScreenController)
      {
         gameLobbyScreenController = controller;
         return true;
      }
      
      // Implied else.
      return false;
   }
   
   /**
    * @return GameLobbyScreenController if it is the currently active screen. Otherwise you will get a null.
    */
   public GameLobbyScreenController getGameLobbyScreenController()
   {
      if (currentController.equals(MainController.GAME_LOBBY_SCREEN))
         return gameLobbyScreenController;
      else
         return null;
   }
   
   /**
    * Allows you to set a GameRulesScreenController.
    * 
    * @param controller The controller that you want to assign.
    * @return True if the passed controller was set; false if it was determined not to be a GameRulesScreenController.
    */
   public boolean setGameRulesScreenController(GameRulesScreenController controller)
   {
      if (controller instanceof GameRulesScreenController)
      {
         gameRulesScreenController = controller;
         return true;
      }
      
      // Implied else.
      return false;
   }
   
   /**
    * @return GameRulesScreenController if it is the currently active screen. Otherwise you will get a null.
    */
   public GameRulesScreenController getGameRulesScreenController()
   {
      if (currentController.equals(MainController.GAME_RULES_SCREEN))
         return gameRulesScreenController;
      else
         return null;
   }
   
   /**
    * Allows you to set a GameTableScreenController.
    * 
    * @param controller The controller that you want to assign.
    * @return True if the passed controller was set; false if it was determined not to be a GameTableScreenController.
    */
   public boolean setGameTableScreenController(GameTableScreenController controller)
   {
      if (controller instanceof GameTableScreenController)
      {
         gameTableScreenController = controller;
         return true;
      }
      
      // Implied else.
      return false;
   }
   
   /**
    * @return GameTableScreenController if it is the currently active screen. Otherwise you will get a null.
    */
   public GameTableScreenController getGameTableScreenController()
   {
      if (currentController.equals(MainController.GAME_TABLE_SCREEN))
         return gameTableScreenController;
      else
         return null;
   }
   
   /**
    * Allows you to set a LeaderboardsScreenController.
    * 
    * @param controller The controller that you want to assign.
    * @return True if the passed controller was set; false if it was determined not to be a LeaderBoardsScreenController.
    */
   public boolean setLeaderboardsScreenController(LeaderboardsScreenController controller)
   {
      if (controller instanceof LeaderboardsScreenController)
      {
         leaderboardsScreenController = controller;
         return true;
      }
      
      // Implied else.
      return false;
   }
   
   /**
    * @return LeaderboradsScreenController if it is the currently active screen. Otherwise you will get a null.
    */
   public LeaderboardsScreenController getLeaderboardsScreenController()
   {
      if (currentController.equals(MainController.LEADERBOARDS_SCREEN))
         return leaderboardsScreenController;
      else
         return null;
   }
   
   /**
    * Allows you to set a ProfileScreenController.
    * 
    * @param controller The controller that you want to assign.
    * @return True if the passed controller was set; false if it was determined not to be a ProfileScreenController.
    */
   public boolean setProfileScreenController(ProfileScreenController controller)
   {
      if (controller instanceof ProfileScreenController)
      {
         profileScreenController = controller;
         return true;
      }
      
      // Implied else.
      return false;
   }
   
   /**
    * @return ProfileScreenController if it is the currently active screen. Otherwise you will get a null.
    */
   public ProfileScreenController getProfileScreenController()
   {
      if (currentController.equals(MainController.PROFILE_SCREEN))
         return profileScreenController;
      else
         return null;
   }
   
   /**
    * Allows you to set a RegistrationScreenController.
    * 
    * @param controller The controller that you want to assign.
    * @return True if the passed controller was set; false if it was determined not to be a RegistrationScreenController.
    */
   public boolean setRegistrationScreenController(RegistrationScreenController controller)
   {
      if (controller instanceof RegistrationScreenController)
      {
         registrationScreenController = controller;
         return true;
      }
      
      // Implied else.
      return false;
   }
   
   /**
    * @return RegistrationScreenController if it is the currently active screen. Otherwise you will get a null.
    */
   public RegistrationScreenController getRegistrationSreenController()
   {
      if (currentController.equals(MainController.REGISTRATION_SCREEN))
         return registrationScreenController;
      else
         return null;
   }
   
   /**
    * Allows you to set a SearchGameScreenController.
    * 
    * @param controller The controller that you want to assign.
    * @return True if the passed controller was set; false if it was determined not to be a SearchGameScreenController.
    */
   public boolean setSearchGameScreenController(SearchGameScreenController controller)
   {
      if (controller instanceof SearchGameScreenController)
      {
         searchGameScreenController = controller;
         return true;
      }
      
      // Implied else.
      return false;
   }
   
   /**
    * @return SearchGameScreenController if it is the currently active screen. Otherwise you will get a null.
    */
   public SearchGameScreenController getSearchGameScreenController()
   {
      if (currentController.equals(MainController.SEARCH_GAME_SCREEN))
         return searchGameScreenController;
      else
         return null;
   }
   
   /**
    * Allows you to set a UserListingScreenController.
    * 
    * @param controller The controller that you want to assign.
    * @return True if the passed controller was set; false if it was determined not to be a UserListingScreenController.
    */
   public boolean setUserListingScreenController(UserListingScreenController controller)
   {
      if (controller instanceof UserListingScreenController)
      {
         userListingScreenController = controller;
         return true;
      }
      
      // Implied else.
      return false;
   }
   
   /**
    * @return UserListingScreenController if it is the currently active screen. Otherwise you will get a null.
    */
   public UserListingScreenController getUserListingScreenController()
   {
      if (currentController.equals(MainController.USER_LISTING_SCREEN))
         return userListingScreenController;
      else
         return null;
   }

   /**
    * Allows you to set a ModeratorReportsScreenController.
    * 
    * @param controller The controller that you want to assign.
    * @return True if the passed controller was set; false if it was determined not to be a ModeratorReportsScreenController.
    */
   public boolean setModeratorReportsScreenController(ModeratorReportsScreenController controller)
   {
      if (controller instanceof ModeratorReportsScreenController)
      {
         moderatorReportsScreenController = controller;
         return true;
      }
      
      // Implied else.
      return false;
   }
   
   /**
    * @return ModeratorReportsScreenController if it is the currently active screen. Otherwise you will get a null.
    */
   public ModeratorReportsScreenController getModeratorReportsScreenController()
   {
      if (currentController.equals(MainController.MODERATOR_REPORTS_SCREEN))
         return moderatorReportsScreenController;
      else
         return null;
   }
}
