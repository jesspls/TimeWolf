/**
 * @author Anish Kunduru
 *
 * The purpose of this program is to store login state data.
 * We don't have to worry about checking input before setting, because that is the job of the controller that will call this class.
 */

package singleton;

import GameServer.Users.LogIn;

public class LoginData
{
   // User information.
   private String usernameString;
   private int userID;
   private boolean isAdmin, isModerator;

   // Login connection.
   private LogIn logInConnection;

   /**
    * Default constructor to use in singleton.
    */
   public LoginData()
   {
   }

   /**
    * Sets the userNameString.
    * 
    * @param userNameString A string representing the userName of the current player.
    */
   public void setUsername(String usernameString)
   {
      this.usernameString = usernameString;
   }

   /**
    * Sets the userID.
    * 
    * @param userID An int representing the userID of the current player.
    */
   public void setUserID(int userID)
   {
      this.userID = userID;
   }

   /**
    * Sets if the user is an admin or not.
    * 
    * @param isAdmin True if the user is an administrator, false if he isn't.
    */
   public void setIsAdmin(boolean isAdmin)
   {
      this.isAdmin = isAdmin;
   }
   
   /**
    * Sets if the user is a moderator or not.
    * 
    * @param isModerator True if the user is a moderator, false if he isn't.
    */
   public void setIsModerator(boolean isModerator)
   {
      this.isModerator = isModerator;
   }
   
   /**
    * @return True if the user is an admin, false if he isn't.
    */
   public boolean getIsModerator()
   {
      return isModerator;
   }

   /**
    * Sets the loginConnection.
    * 
    * @param loginConnection An valid LogInConnection that can be accessed and used by other screens.
    */
   public void setLogInConnection(LogIn loginConnection)
   {
      this.logInConnection = loginConnection;
   }

   /**
    * @return The valid username of the current player.
    */
   public String getUsername()
   {
      return usernameString;
   }

   /**
    * @return The userID of the current player.
    */
   public int getUserID()
   {
      return userID;
   }

   /**
    * @return True if the user is an administrator, false if he isn't.
    */
   public boolean getIsAdmin()
   {
      return isAdmin;
   }

   /**
    * @return The LogIn object created when the application was launched.
    */
   public LogIn getLogInConnection()
   {
      return logInConnection;
   }
}
