/**
 * This program is our handler for AfterGameScreen.fxml.
 */

package afterGame;

import java.rmi.RemoteException;

import chat.Chat;
import framework.AbstractScreenController;
import framework.ControlledScreen;
import framework.Destroyable;
import GameServer.Users.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import singleton.MainModel;
import view.MainController;

public class AfterGameScreenController implements ControlledScreen, Destroyable
{
   // Reference to Parent type (to be set by setScreenParent).
   MainController parentController;

   @FXML
   private Label rankPlayerOne;
   @FXML
   private Label rankPlayerTwo;
   @FXML
   private Label rankPlayerThree;
   @FXML
   private Label rankPlayerFour;

   @FXML
   private Label namePlayerOne;
   @FXML
   private Label namePlayerTwo;
   @FXML
   private Label namePlayerThree;
   @FXML
   private Label namePlayerFour;

   @FXML
   private Label vpPlayerOne;
   @FXML
   private Label vpPlayerTwo;
   @FXML
   private Label vpPlayerThree;
   @FXML
   private Label vpPlayerFour;

   @FXML
   private Label deckPlayerOne;
   @FXML
   private Label deckPlayerTwo;
   @FXML
   private Label deckPlayerThree;
   @FXML
   private Label deckPlayerFour;

   @FXML
   private Label[] rankList;
   @FXML
   private Label[] nameList;
   @FXML
   private Label[] vpList;
   @FXML
   private Label[] deckList;

   @FXML
   private Button likePlayerOne;
   @FXML
   private Button likePlayerTwo;
   @FXML
   private Button likePlayerThree;
   @FXML
   private Button likePlayerFour;

   @FXML
   private Button dislikePlayerOne;
   @FXML
   private Button dislikePlayerTwo;
   @FXML
   private Button dislikePlayerThree;
   @FXML
   private Button dislikePlayerFour;

   @FXML
   private Button[] likeButtons;
   @FXML
   private Button[] dislikeButtons;

   private boolean[] canPress;
   private int count;

   @FXML
   private CheckBox reasonOne;
   @FXML
   private CheckBox reasonTwo;
   @FXML
   private CheckBox reasonThree;
   @FXML
   private CheckBox reasonFour;

   @FXML
   private Label feedbackLabel;

   @FXML
   private Button submit;

   // Chat fields.
   @FXML
   private Button sendMessageButton;
   @FXML
   private Button reportButton;
   @FXML
   private TextArea chatBoxTextArea;
   @FXML
   private TextField chatMessageTextField;

   // So that we can call it from different event listeners.
   private Chat chat;

   /**
    * Called automatically by the linked FXML at program launch via dependency injection.
    */
   @FXML
   public void initialize()
   {
      rankList = new Label[] { rankPlayerOne, rankPlayerTwo, rankPlayerThree, rankPlayerFour };
      nameList = new Label[] { namePlayerOne, namePlayerTwo, namePlayerThree, namePlayerFour };
      vpList = new Label[] { vpPlayerOne, vpPlayerTwo, vpPlayerThree, vpPlayerFour };
      deckList = new Label[] { deckPlayerOne, deckPlayerTwo, deckPlayerThree, deckPlayerFour };

      likeButtons = new Button[] { likePlayerOne, likePlayerTwo, likePlayerThree, likePlayerFour };
      dislikeButtons = new Button[] { dislikePlayerOne, dislikePlayerTwo, dislikePlayerThree, dislikePlayerFour };

      canPress = new boolean[] { true, true, true, true };

      reasonOne.setVisible(false);
      reasonTwo.setVisible(false);
      reasonThree.setVisible(false);
      reasonFour.setVisible(false);

      feedbackLabel.setVisible(false);
      submit.setVisible(false);

      // This part is for testing purposes only
      User userOne = null;
      try
      {
         userOne = MainModel.getModel().currentLoginData().getLogInConnection().logIn("jkhaynes", "password");
      }
      catch (RemoteException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch (Exception e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      User userTwo = null;
      try
      {
         userTwo = MainModel.getModel().currentLoginData().getLogInConnection().logIn("ssimmons", "password");
      }
      catch (Exception e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      // End of test code

      String playerUsername = MainModel.getModel().currentLoginData().getUsername();
      User user;
      try
      {
         user = MainModel.getModel().currentLoginData().getLogInConnection().getUser(playerUsername);
         String players[] = MainModel.getModel().currentGameTableData().getPlayerNames();
         int vp[] = MainModel.getModel().currentGameTableData().getVP();
         int numDeck[] = MainModel.getModel().currentGameTableData().getCardsInDeck();
         
         //players = new String[] {"jkhaynes", "ssimmons"};
         //vp = new int[] {200, 100};
         //numDeck = new int[] {50, 25};
         
         setAfterGameInfo(players, vp, numDeck);
         likeFeedbackEvent(players, user);
         disLikeFeedbackEvent(players, user);
         submitNegativeFeedbackEvent(players, user);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      
      // Enable chat if the game has chat enabled.
      if (MainModel.getModel().currentGameTableData().getChatEnabled())
      {
         // Initialize chat.
         chat = new Chat(Chat.AFTER_GAME_SCREEN, playerUsername, MainModel.getModel().currentGameTableData().getGameID());
         
         // Attempt to send message upon hitting return register in chatMessageTextField.
         chatMessageTextField.setOnAction(event ->
         {
            sendMessage();
         });
         
         reportButton.setOnAction(event ->
         {
            String log = chatBoxTextArea.getText();
            if (!log.equals(""))
            {
               MainModel.getModel().currentLoginData().getLogInConnection().insertReport(log);
               
               Alert alert = new Alert(AlertType.CONFIRMATION);
               alert.setTitle("Confirmation Dialog");
               alert.setHeaderText("Report sent.");
               alert.setContentText("A staff member will view your report shortly.");
               
               alert.showAndWait();
            }
         });
      }
      else
      {
         chatBoxTextArea.setVisible(false);
         chatMessageTextField.setVisible(false);
         sendMessageButton.setVisible(false);
         //reportButton.setVisible(false);
      }
      
   }
   
   /**
    * Helper method to set the labels on the after game screen
    * @param playerNames array of strings with player's usernames
    * @param vp array of ints with player's total vp
    * @param numDeck array of ints with player's number of cards in deck
    */

   public void setAfterGameInfo(String playerNames[], int vp[], int numDeck[])
   {

      int i =0;
      int count = 1;

      for (i = 0; i < playerNames.length; i++)
      {
         rankList[i].setText("" + count++);
         nameList[i].setText(playerNames[i]);
         vpList[i].setText("" + vp[i]);
         deckList[i].setText("" + numDeck[i]);
      }

      while (i < 4)
      {
         likeButtons[i].setVisible(false);
         dislikeButtons[i].setVisible(false);
         i++;
      }
   }
   
   /**
    * Helper method to handle functionality for leaving negative feedback.
    * @param players an array of strings of player names
    * @param user the current logged in user
    */

   private void submitNegativeFeedbackEvent(String players[], User user)
   {
      submit.setOnMouseClicked(event ->
      {
         if (reasonOne.isSelected())
         {
            try {
				MainModel.getModel().currentLoginData().getLogInConnection().insertFeedback(MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[count]).getID(), user.getID(), false, reasonOne.getText());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
         else if (reasonTwo.isSelected())
         {
            try {
				MainModel.getModel().currentLoginData().getLogInConnection().insertFeedback(MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[count]).getID(), user.getID(), false, reasonTwo.getText());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
         else if (reasonThree.isSelected())
         {
            try {
				MainModel.getModel().currentLoginData().getLogInConnection()
				         .insertFeedback(MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[count]).getID(), user.getID(), false, reasonThree.getText());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
         else if (reasonOne.isSelected())
         {
            try {
				MainModel.getModel().currentLoginData().getLogInConnection()
				         .insertFeedback(MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[count]).getID(), user.getID(), false, reasonThree.getText());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
         feedbackLabel.setText("Thank you for your feedback!");

         reasonOne.setVisible(false);
         reasonTwo.setVisible(false);
         reasonThree.setVisible(false);
         reasonFour.setVisible(false);

         submit.setVisible(false);

         canPress[count] = false;
      });
   }
   
   /**
    * A helper method that helps take care of actions needed to take place on dislike button click.
    * @param players array of strings of player's usernames
    * @param user current logged in user
    */

   private void disLikeFeedbackEvent(String players[], User user)
   {
      dislikeButtons[0].setOnMouseClicked(event ->
      {
         try {
			if (MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[0]).getID() == user.getID())
			 {
			    feedbackLabel.setText("You cannot rate yourself!");
			    feedbackLabel.setVisible(true);

			    reasonOne.setVisible(false);
			    reasonTwo.setVisible(false);
			    reasonThree.setVisible(false);
			    reasonFour.setVisible(false);
			    submit.setVisible(false);

			 }
			 else if (canPress[0])
			 {
			    reasonOne.setVisible(true);
			    reasonTwo.setVisible(true);
			    reasonThree.setVisible(true);
			    reasonFour.setVisible(true);

			    feedbackLabel.setVisible(true);
			    submit.setVisible(true);
			    feedbackLabel.setText("Please Select Your Reason For This Rating");
			    count = 0;
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      });

      dislikeButtons[1].setOnMouseClicked(event ->
      {
         try {
			if (MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[1]).getID() == user.getID())
			 {
			    feedbackLabel.setText("You cannot rate yourself!");
			    feedbackLabel.setVisible(true);
			    reasonOne.setVisible(false);
			    reasonTwo.setVisible(false);
			    reasonThree.setVisible(false);
			    reasonFour.setVisible(false);
			    submit.setVisible(false);
			 }
			 else if (canPress[1])
			 {
			    reasonOne.setVisible(true);
			    reasonTwo.setVisible(true);
			    reasonThree.setVisible(true);
			    reasonFour.setVisible(true);

			    feedbackLabel.setVisible(true);
			    submit.setVisible(true);
			    feedbackLabel.setText("Please Select Your Reason For This Rating");
			    count = 1;
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      });

      dislikeButtons[2].setOnMouseClicked(event ->
      {
         try {
			if (MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[2]).getID() == user.getID())
			 {
			    feedbackLabel.setText("You cannot rate yourself!");
			    feedbackLabel.setVisible(true);
			    reasonOne.setVisible(false);
			    reasonTwo.setVisible(false);
			    reasonThree.setVisible(false);
			    reasonFour.setVisible(false);
			    submit.setVisible(false);
			 }
			 else if (canPress[2])
			 {
			    reasonOne.setVisible(true);
			    reasonTwo.setVisible(true);
			    reasonThree.setVisible(true);
			    reasonFour.setVisible(true);

			    feedbackLabel.setVisible(true);
			    submit.setVisible(true);
			    feedbackLabel.setText("Please Select Your Reason For This Rating");
			    count = 2;
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      });

      dislikeButtons[3].setOnMouseClicked(event ->
      {
         try {
			if (MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[3]).getID() == user.getID())
			 {
			    feedbackLabel.setText("You cannot rate yourself!");
			    feedbackLabel.setVisible(true);

			    reasonOne.setVisible(false);
			    reasonTwo.setVisible(false);
			    reasonThree.setVisible(false);
			    reasonFour.setVisible(false);

			    submit.setVisible(false);
			 }
			 else if (canPress[3])
			 {
			    reasonOne.setVisible(true);
			    reasonTwo.setVisible(true);
			    reasonThree.setVisible(true);
			    reasonFour.setVisible(true);

			    feedbackLabel.setVisible(true);
			    submit.setVisible(true);
			    feedbackLabel.setText("Please Select Your Reason For This Rating");
			    count = 3;
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      });
   }
   
   /**
    * Helper method to handle what needs to happen when a like button is clicked.
    * @param players array of strings of player's usernames
    * @param user current logged in user
    */

   private void likeFeedbackEvent(String players[], User user)
   {
      likeButtons[0].setOnMouseClicked(event ->
      {
         try {
			if (MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[0]).getID() == user.getID())
			 {
			    feedbackLabel.setText("You cannot rate yourself!");
			    feedbackLabel.setVisible(true);
			    reasonOne.setVisible(false);
			    reasonTwo.setVisible(false);
			    reasonThree.setVisible(false);
			    reasonFour.setVisible(false);
			    submit.setVisible(false);
			 }
			 else if (canPress[0])
			 {
			    feedbackLabel.setText("Thank You For Your Feedback");
			    feedbackLabel.setVisible(true);
			    reasonOne.setVisible(false);
			    reasonTwo.setVisible(false);
			    reasonThree.setVisible(false);
			    reasonFour.setVisible(false);

			    submit.setVisible(false);
			    canPress[0] = false;

			    MainModel.getModel().currentLoginData().getLogInConnection().insertFeedback(MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[0]).getID(), user.getID(), true, "");
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      });

      likeButtons[1].setOnMouseClicked(event ->
      {
         try {
			if (MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[1]).getID() == user.getID())
			 {
			    feedbackLabel.setText("You cannot rate yourself!");
			    feedbackLabel.setVisible(true);
			    reasonOne.setVisible(false);
			    reasonTwo.setVisible(false);
			    reasonThree.setVisible(false);
			    reasonFour.setVisible(false);
			    submit.setVisible(false);
			 }
			 else if (canPress[1])
			 {
			    feedbackLabel.setText("Thank You For Your Feedback");
			    feedbackLabel.setVisible(true);
			    reasonOne.setVisible(false);
			    reasonTwo.setVisible(false);
			    reasonThree.setVisible(false);
			    reasonFour.setVisible(false);

			    submit.setVisible(false);
			    canPress[1] = false;
			    MainModel.getModel().currentLoginData().getLogInConnection().insertFeedback(MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[1]).getID(), user.getID(), true, "");
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      });

      likeButtons[2].setOnMouseClicked(event ->
      {
         try {
			if (MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[2]).getID() == user.getID())
			 {
			    feedbackLabel.setText("You cannot rate yourself!");
			    feedbackLabel.setVisible(true);
			    reasonOne.setVisible(false);
			    reasonTwo.setVisible(false);
			    reasonThree.setVisible(false);
			    reasonFour.setVisible(false);
			    submit.setVisible(false);
			 }
			 else if (canPress[2])
			 {
			    feedbackLabel.setText("Thank You For Your Feedback");
			    feedbackLabel.setVisible(true);
			    reasonOne.setVisible(false);
			    reasonTwo.setVisible(false);
			    reasonThree.setVisible(false);
			    reasonFour.setVisible(false);

			    submit.setVisible(false);
			    canPress[2] = false;
			    MainModel.getModel().currentLoginData().getLogInConnection().insertFeedback(MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[2]).getID(), user.getID(), true, "");
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      });

      likeButtons[3].setOnMouseClicked(event ->
      {
         try {
			if (MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[3]).getID() == user.getID())
			 {
			    feedbackLabel.setText("You cannot rate yourself!");
			    feedbackLabel.setVisible(true);
			    reasonOne.setVisible(false);
			    reasonTwo.setVisible(false);
			    reasonThree.setVisible(false);
			    reasonFour.setVisible(false);
			    submit.setVisible(false);
			 }
			 else if (canPress[3])
			 {
			    feedbackLabel.setText("Thank You For Your Feedback");
			    feedbackLabel.setVisible(true);
			    reasonOne.setVisible(false);
			    reasonTwo.setVisible(false);
			    reasonThree.setVisible(false);
			    reasonFour.setVisible(false);

			    submit.setVisible(false);
			    canPress[3] = false;
			    MainModel.getModel().currentLoginData().getLogInConnection().insertFeedback(MainModel.getModel().currentLoginData().getLogInConnection().getUser(players[3]).getID(), user.getID(), true, "");
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      });
   }

   /**
    * Append a message to the chatBoxTextArea that is visible to the user. (To be called by ChatListener.java)
    * 
    * @param message The message that you wish appended to the chat box.
    */
   public void appendToChatBox(String message)
   {
      String append = message + "\n";
      chatBoxTextArea.appendText(append);
   }

   /**
    * To be called by the chat's "Send message" button.
    */
   public void sendMessage()
   {
      String outgoingMsg = chatMessageTextField.getText();

      // Make sure the user didn't just press the button without anything in the text field.
      if (!outgoingMsg.equals(""))
         chat.bufferMessage(outgoingMsg);

      chatMessageTextField.clear();

      // DEBUG
      System.out.println("Outgoing: " + outgoingMsg);
   }

   /**
    * This method will allow for the injection of each screen's parent.
    */
   @Override
   public void setScreenParent(AbstractScreenController screenParent)
   {
      parentController = (MainController) screenParent;
   }

   /**
    * This method will allow us to safely close thread elements when we are ready to leave the page.
    */
   @Override
   public void onDestroy()
   {
      // DEBUG
      System.out.println("Destroying the after game screen...");
      
      if (MainModel.getModel().currentGameTableData().getChatEnabled())
      {
         // Close out chat.
         chat.end();
      }
   }
}
