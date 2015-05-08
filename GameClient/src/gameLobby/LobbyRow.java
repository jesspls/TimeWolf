/**
 * @author Anish Kunduru
 *
 * This class defines a row in the game lobby listing table.
 */

package gameLobby;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LobbyRow
{
   // Define entry types.
   public SimpleStringProperty name = new SimpleStringProperty(); // Lobby name.
   public SimpleStringProperty numberPlayers = new SimpleStringProperty(); // Players in game / maxPlayers
   public SimpleBooleanProperty chat = new SimpleBooleanProperty(); // Chat enabled in game?
   //public SimpleBooleanProperty privateLobby = new SimpleBooleanProperty(); // Public lobby?
   public SimpleIntegerProperty id = new SimpleIntegerProperty();
   public SimpleIntegerProperty numPlayers = new SimpleIntegerProperty();

   // Auto load getters for "automagic" initialization of rows.
   
   /**
    * @return number of players allowed in game
    */
   public int getNumPlayers(){
	   return numPlayers.get();
   }
   
   /**
    * @return id of the game
    */
   public int getID(){
	   return id.get();
   }

   /**
    * @return The name of the game as declared by the user at game creation.
    */
   public String getName()
   {
      return name.get();
   }

   /**
    * @return Get the maximum number of allowed players for this game.
    */
   public String getNumberPlayers()
   {
      return numberPlayers.get();
   }

   /**
    * @return true if chat is enabled for this game, false if it has been disabled.
    */
   public boolean getChat()
   {
      return chat.get();
   }

   /**
    * @return true if a private lobby, false if a public lobby.
    */
   //public boolean getPrivateLobby()
   //{
      //return privateLobby.get();
   //}
}
