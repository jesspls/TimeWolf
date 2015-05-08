/**
 * @author Anish Kunduru
 * 
 * This class represents a server-side thread for each client connected to the chat server.
 */

package GameServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread
{
   private Socket socket; // What we will listen on.

   // For our ChatMessage objects.
   private ObjectInputStream input;
   private ObjectOutputStream output;

   private int clientID; // To properly close the thread upon disconnect.
   private int chatroomID; // To know who to broadcast message to.

   private String username; // Username of the client.

   private ChatMessage chatMessage;

   private ChatServer parentServer; // Store reference of calling thread to remove myself upon logout.

   private boolean run; // To start and stop the thread.

   /**
    * Constructor creates a new client thread.
    * 
    * @param socket The socket that the client is operating on.
    * @param ID The unique ID number that represents the client.
    * @param server The parent server (calling class) that has initiated this new thread.
    */
   public ClientThread(Socket socket, int ID, ChatServer server)
   {
      // Set state vars.
      clientID = ID;
      this.socket = socket;
      parentServer = server;

      // DEBUG
      System.out.println("Creating I/O streams...");

      try
      {
         // Initialize streams.
         input = new ObjectInputStream(socket.getInputStream());
         output = new ObjectOutputStream(socket.getOutputStream());

         // Initialize username and chatroomID.
         username = (String) input.readObject();
         chatroomID = input.readInt();

         // DEBUG
         System.out.println(username + ": connected in room: " + chatroomID + ".");
      }
      catch (IOException ioe)
      {
         System.out.println("Exception creating I/O streams: " + ioe);
      }
      catch (ClassNotFoundException cnfe)
      {
         // NOTE: If this is thrown, there is an error getting the username.
         System.out.println("There was an issue reading the input stream: " + cnfe);
      }
   }

   /**
    * To start the thread.
    */
   public void run()
   {
      run = true;
      
      while (run)
      {
         try
         {
            chatMessage = (ChatMessage) input.readObject();
         }
         catch (IOException | ClassNotFoundException e)
         {
            System.out.println(clientID + " had an I/O stream exception: " + e);
            break; // //////////////////// So we don't lock up the thread. ////////////////////////////
         }

         // Extract the message.
         String message = chatMessage.getMessage();

         // Parse enum.
         switch (chatMessage.getMessageType())
         {
            case LOGOUT:
            {
               run = false; // Stop running.

               // DEBUG
               System.out.println(clientID + " has disconnected.");

               break;
            }
            case MESSAGE:
            {
               // Send the message to all the users in the server.
               String chatMsg = username + ": " + message;

               parentServer.broadcastMessage(chatMsg, chatroomID);

               break;
            }
         }
      }

      // Ya done! Leave the parent array.
      parentServer.removeClient(clientID);
      close();
   }

   /**
    * Private helper method to assit in closing the I/O streams and socket after client disconnect or fatal error.
    */
   public void close()
   {
      try
      {
         output.close();
         input.close();
         socket.close();
      }
      catch (IOException ioe)
      {
         System.out.println("There was an error closing the streams AND/OR socket: " + ioe);
      }
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
         output.writeObject(message);
      }
      catch (IOException ioe)
      {
         System.out.println(clientID + " had an error while attempting to write to the buffer: " + ioe);
      }

      // Implied else.
      return true;
   }

   /**
    * Getter for chatroomID.
    * 
    * @return The chatroomID associated with this client thread.
    */
   public int getChatroomID()
   {
      return chatroomID;
   }

   /**
    * Getter for clientID.
    * 
    * @return The clientID associated with this client thread (passed by the calling chatServer).
    */
   public int getClientID()
   {
      return clientID;
   }

   /**
    * Getter for username.
    * 
    * @return The username associated with this client thread.
    */
   public String getUsername()
   {
      return username;
   }
}
