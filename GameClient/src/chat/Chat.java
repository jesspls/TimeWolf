
/**
 * @author Anish Kunduru
 *
 * This is the client side chat class that is called by a screen.
 */

package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import view.MainController;
import GameServer.ChatMessage;

public class Chat
{
   // PUBLIC CONSTANTS THAT WILL NEED TO BE UPDATED WHEN SERVER FIELDS CHANGE.
   //public final String SERVER_ADDRESS = "localhost";
   public final String SERVER_ADDRESS = "10.25.68.24";
   public final int SERVER_PORT = 1444;
   
   // PUBLIC CONSTANTS THE REPRESENT THE LOBBIES THAT CHAT CAN BE DEPLOYED IN.
   public static final String GAME_LOBBY_SCREEN = MainController.GAME_LOBBY_SCREEN;
   public static final String GAME_TABLE_SCREEN = MainController.GAME_TABLE_SCREEN;
   public static final String AFTER_GAME_SCREEN = MainController.AFTER_GAME_SCREEN;
   
   // What we will listen on.
   private Socket socket;

   // For our ChatMessage objects.
   private ObjectInputStream input;
   private ObjectOutputStream output;

   private ChatListener listener; // Listening thread waits for server message.

   /**
    * Constructor creates a new Chat object.
    * 
    * @param screen String that represents the lobbies that chat can be deployed in (check the constants at the top of this file).
    * @param username The username of the player that will be chatting.
    * @param chatroomID The chatroomID (as given to the client by the server as the gameID).
    */
   public Chat(String screen, String username, int chatroomID)
   {
      // Try and connect to the server.
      try
      {
         socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
      }
      catch (IOException ioe)
      {
         System.out.println("There was an error connecting to the server: " + ioe);
      }

      // Initialize streams.
      try
      {
         output = new ObjectOutputStream(socket.getOutputStream());
         input = new ObjectInputStream(socket.getInputStream());
      }
      catch (IOException ioe)
      {
         System.out.println("Exception creating I/O streams: " + ioe);
      }
      
      // Initialize the thread to listen from the server and start it.
      listener = new ChatListener(screen, input);
      listener.start();

      // Initialize server side calls to set username and chatroomID.
      try
      {
         output.writeObject(username);
         output.writeInt(chatroomID);
         
         // Let everyone know that you have connected.
         bufferMessage("has joined the room.");
      }
      catch (IOException ioe)
      {
         System.out.println("Error adding username and chatroomID to server: " + ioe);
         close(); // Shut it down, there is nothing else we can do. This will also shut down the chat listener thread.
      }
   }

   /**
    * Closes out the I/O streams, socket, and sets the ChatListener run boolean to false by calling its end() method.
    */
   private void close()
   {
      try
      {
         socket.close();
         input.close();
         output.close();
      }
      catch (IOException ioe)
      {
         System.out.println("Error closing I/O streams: " + ioe);
      }

      listener.end();
   }

   /**
    * Write a message to the object buffer (socket stream).
    * 
    * @param message The message that you wish to send to the buffer.
    * @return true if the message was successfully sent, false if something failed.
    */
   public boolean bufferMessage(String message)
   {
      // Check if client is still connected.
      if (!socket.isConnected())
      {
         close();
         return false;
      }

      // Write to buffer.
      try
      {
         ChatMessage chatMessage = new ChatMessage(ChatMessage.Type.MESSAGE, message);

         output.writeObject(chatMessage);
      }
      catch (IOException ioe)
      {
         System.out.println("I/O error while attempting to write to the buffer: " + ioe);
      }

      // Implied else.
      return true;
   }

   /**
    * Send a logout type ChatMessage to the buffer, close out the streams, and end the listener thread.
    */
   public void end()
   {
      // Send logout message.
      try
      {
         output.writeObject(new ChatMessage(ChatMessage.Type.LOGOUT, ""));
      }
      catch (IOException ioe)
      {
         System.out.println("I/O error while sending logout request to server: " + ioe);
      }

      close();
   }
}