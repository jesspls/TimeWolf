/**
 * @author Anish Kunduru
 * 
 * This class defines a row in the profile feedback table.
 */

package profile;

import javafx.beans.property.SimpleStringProperty;

public class KarmaRow
{
   // Define our entry types.
   public SimpleStringProperty rating = new SimpleStringProperty();
   public SimpleStringProperty player = new SimpleStringProperty();
   public SimpleStringProperty reasonGiven = new SimpleStringProperty();

   // Define our auto load getters that will be called on initialize.

   /**
    * @return The rating that was left by the player.
    */
   public String getRating()
   {
      return rating.get();
   }

   /**
    * @return The name of the player that left the rating.
    */
   public String getPlayer()
   {
      return player.get();
   }

   /**
    * @return The reason the player left for his stated feedback.
    */
   public String getReasonGiven()
   {
      return reasonGiven.get();
   }
}
