/**
 * @author Anish Kunduru
 * 
 * This program is our handler for LoginScreen.fxml.
 */

package login;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.rmi.Naming;

import framework.AbstractScreenController;
import framework.ControlledScreen;
import singleton.MainModel;
import view.MainController;
import GameServer.Users.LogIn;
import GameServer.Users.User;

public class LoginScreenController implements ControlledScreen
{
   // PUBLIC CONSTANTS THAT WILL NEED TO BE UPDATED WHEN SERVER FIELDS CHANGE.
   //public final String SERVER_ADDRESS = "localhost";
   public final String SERVER_ADDRESS = "10.25.68.24";
   
   // Functional components.
   @FXML
   private Button loginButton;
   @FXML
   private Button registerButton;
   @FXML
   private Hyperlink forgotUsernamePasswordHyperlink;

   // To get input from user.
   @FXML
   private TextField usernameTextField;
   @FXML
   private PasswordField passwordField;

   @FXML
   private Label errorMessage;

   // So we can set the screen's parent later on.
   MainController parentController;

   // So that we can call it from different event listeners.
   private LogIn login;

   /**
    * Initializes the controller class. Automatically called after the FXML file has been loaded.
    */
   @FXML
   public void initialize()
   {
      // Initialize login and store in singleton.
      try
      {
         login = (LogIn) Naming.lookup("//" + SERVER_ADDRESS + "/auth");

         MainModel.getModel().currentLoginData().setLogInConnection(login);
      }
      catch (Exception e)
      {
         errorMessage.setText("The server is offline. Please try again later.");
      }

      // Event handlers for buttons.
      // The arrow means lambda expression in Java.
      // Lambda expressions allow you to create anonymous methods, which is perfect for eventHandling.
      loginButton.setOnAction(event ->
      {
         User user = new User();

         try
         {
            user = login.logIn(usernameTextField.getText(), passwordField.getText());
            // TEST LOGIN: username: ssimmons, password: password

            // This will only be called if an exception isn't thrown by the previous statement, so no need to worry about error handling.
            // Set the login information in our shared model so that we can access it from other controllers.
            MainModel.getModel().currentLoginData().setUsername(usernameTextField.getText());
            MainModel.getModel().currentLoginData().setUserID(user.getID());
            MainModel.getModel().currentLoginData().setIsAdmin(user.isAdmin());
            MainModel.getModel().currentLoginData().setIsModerator(user.isModerator());

            // Go to the next screen.
            parentController.goToGameLobbyScreen();
         }
         catch (Exception e)
         {
            errorMessage.setText(e.getMessage());
         }
      });

      // Attempt login upon hitting return register in passwordField.
      passwordField.setOnAction(event ->
      {
         loginButton.fire(); // Fire off a loginButton event.
      });

      // Go to registration screen.
      registerButton.setOnAction(event ->
      {
         parentController.goToRegistrationScreen();
      });

      // Go to forgot password screen.
      forgotUsernamePasswordHyperlink.setOnAction(event ->
      {
         parentController.goToForgotPasswordScreen();
      });
   }

   /**
    * This method will allow for the injection of each screen's parent.
    */
   @Override
   public void setScreenParent(AbstractScreenController screenParent)
   {
      parentController = (MainController) screenParent;
   }
}