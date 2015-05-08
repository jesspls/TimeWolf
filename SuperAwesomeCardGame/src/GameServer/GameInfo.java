/**
 * This is a helper class created to encapsulate information held by GameManagement so that we can pass it to the client.
 */

package GameServer;

import java.io.Serializable;
import java.util.ArrayList;

public class GameInfo implements Serializable
{
   private static final long serialVersionUID = 1L;
   
   // Class vars.
   private String name;
   private int numPlayers;
   private boolean chat;
   private boolean privacy;
   private ArrayList<String> players;
   private int id;
   
   /**
    * Constructor creates a new GameInfo object.
    * 
    * @param name The name that represents the game table.
    * @param numPlayers The number of players in the game.
    * @param players An ArrayList<String> of the username of players in the game.
    */
   public GameInfo(String name, int numPlayers, ArrayList<String> players, int id, boolean isChatEnabled)
   {
      this.name = name;
      this.numPlayers = numPlayers;
      this.chat = isChatEnabled;
      // this.privacy = privacy;
      this.players = players;
      this.id = id;
   }
   
   /**
    * @return the id of the game engine
    */
   public int getID(){
	   return id;
   }
   
   /**
    * @return The name that represents the game table.
    */
   public String getName()
   {
      return name;
   }
   
   /**
    * @return The number of players in the game.
    */
   public int getNumPlayers()
   {
      return numPlayers;
   }
   
   /**
    * @return True if chat is enabled; false otherwise.
    */
   public boolean getChat()
   {
      return chat;
   }
   
   /**
    * @return True if it is a private game; false otherwise.
    */
   public boolean getPrivacy()
   {
      return privacy;
   }
   
   /**
    * @return The username of players participating in the game.
    */
   public ArrayList<String> getPlayers()
   {
      return players;
   }
   
   /**
    * Add a player to the game.
    * @param player The username of the player that you wish to add.
    */
   public void addPlayer(String player)
   {
      players.add(player);
   }

}
