/**
 * @author Anish Kunduru
 * 
 * This program defines the different type of messages that will be exchanged between the clients and the server.
 * We are using an object because it helps us easily determine when a client logs off (just send a different type of message).
 */

package GameServer;

import java.io.Serializable;

public class ChatMessage implements Serializable
{
   // Implement serializable.
   private static final long serialVersionUID = -4060984163424431415L;

   /**
    * This enum represents the different type of possible messages.
    */
   public enum Type
   {
      LOGOUT, MESSAGE
   };

   // Object vars.
   private Type messageType;
   private String message;

   /**
    * Constructor that sets up the class variables.
    * 
    * @param type The type of message of ChatMessage.Type.
    * @param message The message that you wish to broadcast to the chatroom.
    */
   public ChatMessage(Type type, String message)
   {
      messageType = type;
      this.message = message;
   }

   /**
    * Returns the type of message this is.
    * 
    * @return A ChatMessage.Type enumeration.
    */
   public Type getMessageType()
   {
      return messageType;
   }

   /**
    * Get the message in the object.
    * 
    * @return A String representing the message that the user has sent.
    */
   public String getMessage()
   {
      return message;
   }
}