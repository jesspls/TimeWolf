/**
 * @author Anish Kunduru
 * 
 * This class defines a row in the user listing table.
 */

package userListing;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserRow
{
   // Define our entry types.
   public SimpleStringProperty username = new SimpleStringProperty();
   public SimpleStringProperty email = new SimpleStringProperty();
   public SimpleBooleanProperty isBanned = new SimpleBooleanProperty();
   public SimpleBooleanProperty isFlagged = new SimpleBooleanProperty();
   public SimpleStringProperty bannedReason = new SimpleStringProperty();
   public SimpleStringProperty role = new SimpleStringProperty();

   // The following getters are called automatically when the table is loaded.
   // Even though we are using properties, these are required for the FXML to work automatically (unless you wan to bind each and every column).

   /**
    * @return The user's username.
    */
   public String getUsername()
   {
      return username.get();
   }

   /**
    * @return The e-mail address of a user.
    */
   public String getEmail()
   {
      return email.get();
   }
   
   /**
    * @return the boolean representation of a user's flagged status (true for flagged, false for not banned).
    */
   public boolean getIsFlagged(){
	   return isFlagged.get();
   }

   /**
    * @return The boolean representation of a user's ban status (true for banned, false for not banned).
    */
   public boolean getIsBanned()
   {
      return isBanned.get();
   }

   /**
    * @return The reason why the user was banned.
    */
   public String getBannedReason()
   {
      return bannedReason.get();
   }

   /**
    * @return The String representation of a user's role (administrator, moderator, user).
    */
   public String getRole()
   {
      return role.get();
   }

}
