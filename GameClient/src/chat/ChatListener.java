/**
 * @author Anish Kunduru
 *
 * This class listens for an incoming message and sends it to the appropriate location.
 */

package chat;

import gameLobby.GameLobbyScreenController;
import gameTable.GameTableScreenController;

import java.io.IOException;
import java.io.ObjectInputStream;

import afterGame.AfterGameScreenController;
import singleton.MainModel;
import view.MainController;
import javafx.application.Platform;

public class ChatListener extends Thread
{   
   private ObjectInputStream input;

   private String screen; // The screen to output chat to.
   private boolean run; // To start and stop thread.

   /**
    * Constructor creates a new listener thread.
    * 
    * @param screen String that represents the lobbies that chat can be deployed in (check the constants at the top of the Chat.java file).
    * @param input The input stream of the socket the server is connected to.
    */
   public ChatListener(String screen, ObjectInputStream input)
   {
      // Initialize private state vars.
      this.screen = screen;
      this.input = input;
   }

   public void run()
   {
      run = true;

      // The following may or may not be necessary. I think it depends on how heavy the page we are to call is. TODO is there a better way to do this?
      // Put this thread to sleep to make sure we that MainController has enough time to update the current controller.
      try
      {
         Thread.sleep(1000);
      }
      catch (InterruptedException e)
      {
         // DEBUG
         System.out.println("There was an issue putting the thread to sleep.");
      }

      while (run)
      {
         try
         {
            String message = (String) input.readObject();

            if (screen.equals(MainController.GAME_LOBBY_SCREEN))
            {
               GameLobbyScreenController lobbyController = MainModel.getModel().currentControllerData().getGameLobbyScreenController();

               // For JavaFX thread safety:
               Platform.runLater(() ->
               {
                  lobbyController.appendToChatBox(message);
               });

               // DEBUG
               System.out.println("Incoming: " + message);
            }
            else if (screen.equals(MainController.GAME_TABLE_SCREEN))
            {
               GameTableScreenController tableController = MainModel.getModel().currentControllerData().getGameTableScreenController();

               // For JavaFX thread safety:
               Platform.runLater(() ->
               {
                  tableController.appendToChatBox(message);
               });

               // DEBUG
               System.out.println("Incoming: " + message);
            }
            else
            {
               AfterGameScreenController afterController = MainModel.getModel().currentControllerData().getAfterGameScreenController();

               // For JavaFX thread safety:
               Platform.runLater(() ->
               {
                  afterController.appendToChatBox(message);
               });

               // DEBUG
               System.out.println("Incoming: " + message);
            }
         }
         catch (IOException | ClassNotFoundException e)
         {
            System.out.println("The connection has been terminated: " + e.getMessage());
            run = false;
         }
      }
   }
   /**
    * Handles ending the chat
    */

   public void end()
   {
      run = false;
   }
}
