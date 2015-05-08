/**
 * @author Anish Kunduru
 * 
 * This program is our handler for ForgotPasswordScreen.fxml.
 */

package forgotPassword;

import java.sql.SQLException;

import framework.AbstractScreenController;
import framework.ControlledScreen;
import singleton.MainModel;
import view.MainController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class ForgotPasswordScreenController implements ControlledScreen
{
   // So we can set the screen's parent later on.
   MainController parentController;

   @FXML
   private Button resetButton;
   @FXML
   private Button cancelButton;

   @FXML
   private TextField usernameTextField;
   @FXML
   private TextField emailTextField;
   @FXML
   private TextField securityQuestionTextField;
   @FXML
   private TextField securityAnswerTextField;

   @FXML
   private PasswordField passwordField;
   @FXML
   private PasswordField verifyPasswordField;

   @FXML
   private Label errorLabel;

   // Validity check.
   private boolean validEmail = false;
   private boolean validPassword = false;
   
   /**
    * Initializes the controller class. Automatically called after the FXML file has been loaded.
    */

   @FXML
   public void initialize()
   {
      // Display username when return register is hit.
      emailTextField.setOnAction(event ->
      {
         try
         {
            String username = MainModel.getModel().currentLoginData().getLogInConnection().findUsername(emailTextField.getText());

            usernameTextField.setText(username);
            usernameTextField.setVisible(true);
            passwordField.setVisible(true);
            verifyPasswordField.setVisible(true);

            validEmail = true;
         }
         catch (Exception e)
         {
            errorLabel.setText("Not a valid e-mail address.");
         }
      });

      // Send the user back to the login screen.
      cancelButton.setOnAction(event ->
      {
         parentController.goToLoginScreen();
      });

      // Display password reset question if passwordFields match (indicating that the user wishes to reset his password).
      passwordField.setOnKeyReleased(event ->
      {
         if (passwordField.getText().equals(verifyPasswordField.getText()) && validEmail)
         {
            try
            {
               securityQuestionTextField.setText(MainModel.getModel().currentLoginData().getLogInConnection().getSecurityQuestion(emailTextField.getText()));

               validPassword = true;
            }
            catch (Exception e)
            {
               errorLabel.setText("Not a valid e-mail address.");
            }
         }
         else
            validPassword = false;
      });

      verifyPasswordField.setOnKeyReleased(event ->
      {
         if (passwordField.getText().equals(verifyPasswordField.getText()) && validEmail)
         {
            try
            {
               securityQuestionTextField.setText(MainModel.getModel().currentLoginData().getLogInConnection().getSecurityQuestion(emailTextField.getText()));
               securityQuestionTextField.setVisible(true);
               securityAnswerTextField.setVisible(true);

               validPassword = true;
            }
            catch (Exception e)
            {
               errorLabel.setText("Not a valid e-mail address.");
            }
         }
         else
            validPassword = false;
      });

      // Check if all fields valid and reset if okay.
      resetButton.setOnAction(event ->
      {
         if (!validEmail)
            errorLabel.setText("Not a valid e-mail address.");
         else if (!validPassword)
            errorLabel.setText("Passwords do not match.");
         else if (!checkIfSecurityAnswerMatch(usernameTextField.getText(), securityAnswerTextField.getText()))
            errorLabel.setText("Your answer does not match the answer on file.");
         else
         {
            try
            {
               MainModel.getModel().currentLoginData().getLogInConnection().resetPassword(usernameTextField.getText(), verifyPasswordField.getText());

               // Timeline action event.
               errorLabel.setText("Reset sucessful! Redirecting to the login screen in 5 seconds.");

               Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), action ->
               {
                  parentController.goToLoginScreen();
               }));

               timeline.play();
            }
            catch (Exception e)
            {
               errorLabel.setText("There was an error resetting your account. Please contact support.");
            }
         } // End else.
      }); // End Lambda.
   } // End initalize.

   /**
    * Private helper method to check if security answer is a match.
    * 
    * @param username The username for which you want to check the securityAnswer.
    * @param securityAnswer The securityAnswer provided by the user.
    * @return true if is a match; false otherwise.
    */
   private boolean checkIfSecurityAnswerMatch(String username, String securityAnswer)
   {
      boolean check = false;

      try
      {
         check = MainModel.getModel().currentLoginData().getLogInConnection().checkSecurityQuestionAnswer(username, securityAnswer);
      }
      catch (SQLException e)
      {
         errorLabel.setText("That username does not exist!");
      }

      return check;
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
