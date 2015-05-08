/**
 * @author Anish Kunduru
 * 
 * This program is our handler for RegistrationScreen.fxml.
 */

package registration;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import GameServer.Users.LogIn;
import framework.AbstractScreenController;
import framework.ControlledScreen;
import singleton.MainModel;
import view.MainController;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class RegistrationScreenController implements ControlledScreen
{

   // So we can set the screen's parent later on.
   MainController parentController;

   @FXML
   private Button registerButton;
   @FXML
   private Button cancelButton;
   @FXML
   private Button browseButton;

   @FXML
   private TextField usernameTextField;
   @FXML
   private TextField profilePictureTextField;
   @FXML
   private TextField emailTextField;
   @FXML
   private TextField checkEmailTextField;
   @FXML
   private TextField securityAnswerTextField;
   @FXML
   private TextField securityQuestionTextField;

   @FXML
   private CheckBox over13CheckBox;
   @FXML
   private CheckBox usernameAvailableCheckBox;
   @FXML
   private CheckBox validEmailCheckBox;
   @FXML
   private CheckBox emailMatchCheckBox;
   @FXML
   private CheckBox passwordMatchCheckBox;

   @FXML
   private ImageView userImage;

   @FXML
   private PasswordField passwordField;
   @FXML
   private PasswordField checkPasswordField;

   @FXML
   private Label errorLabel;

   // Validity checks.
   private boolean validUsername = false;
   private boolean validEmail = false;
   private boolean validPassword = false;
   private boolean setSecurityQuestion = false;
   private boolean setSecurityAnswer = false;

   // If user uploads a profile picture.
   private Image profilePictureImage;
   private File profilePictureFile;

   /**
    * Initializes the controller class. Automatically called after the FXML
    * file has been loaded.
    */
   @FXML
   public void initialize()
   {
	   
      // Open file chooser so that user can select a photo to upload.
      browseButton.setOnAction(event ->
      {
         // Initialize and setup fileChooser.
         FileChooser fileChooser = new FileChooser();
         fileChooser.setTitle("Open Profile Picture");

         // Get the file from the fileChooser.
         profilePictureFile = fileChooser.showOpenDialog(MainModel.getModel().currentMainData().getMainStage());

         // So we don't throw an exception.
         if (profilePictureFile != null)
         {
            // Check if it is a supported image format.
            if (isValidImage(profilePictureFile))
            {
               // Set the image.
               profilePictureImage = new Image("file:/" + profilePictureFile.getAbsolutePath());

               // Display the image.
               profilePictureTextField.setText(profilePictureFile.getAbsolutePath());
               userImage.setImage(profilePictureImage);
            }
            else
               errorLabel.setText("Not a supported image format. Please upload a valid BMP, JPEG, PNG, or GIF.");
         }
      });

      // Check if username is already taken.
      usernameTextField.setOnKeyReleased(event ->
      {
         try
         {
            if (MainModel.getModel().currentLoginData().getLogInConnection().doesUsernameExist(usernameTextField.getText()))
            {
               usernameAvailableCheckBox.setSelected(false);
               validUsername = false;
            }
            else
            {
               usernameAvailableCheckBox.setSelected(true);
               validUsername = true;
            }
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      });

      // Check if e-mail address is valid.
      emailTextField.setOnKeyReleased(event ->
      {
         if (isValidEmail(emailTextField.getText()))
            validEmailCheckBox.setSelected(true);
         else
            validEmailCheckBox.setSelected(false);

         if (emailTextField.getText().equals(checkEmailTextField.getText()))
         {
            emailMatchCheckBox.setSelected(true);
            validEmail = true;
         }
         else
         {
            emailMatchCheckBox.setSelected(false);
            validEmail = false;
         }
      });

      // Check if both e-mail fields match.
      checkEmailTextField.setOnKeyReleased(event ->
      {
         if (emailTextField.getText().equals(checkEmailTextField.getText()))
         {
            emailMatchCheckBox.setSelected(true);
            validEmail = true;
         }
         else
         {
            emailMatchCheckBox.setSelected(false);
            validEmail = false;
         }
      });

      // Check if passwords match.
      passwordField.setOnKeyReleased(event ->
      {
         if (passwordField.getText().equals(checkPasswordField.getText()))
         {
            passwordMatchCheckBox.setSelected(true);
            validPassword = true;
         }
         else
         {
            passwordMatchCheckBox.setSelected(false);
            validPassword = false;
         }
      });

      checkPasswordField.setOnKeyReleased(event ->
      {
         if (passwordField.getText().equals(checkPasswordField.getText()))
         {
            passwordMatchCheckBox.setSelected(true);
            validPassword = true;
         }
         else
         {
            passwordMatchCheckBox.setSelected(false);
            validPassword = false;
         }
      });

      // Check to make sure user sets something to the security question fields (changes something) in these fields.
      securityQuestionTextField.setOnKeyReleased(event -> setSecurityQuestion = true);
      securityAnswerTextField.setOnKeyReleased(event -> setSecurityAnswer = true);

      // Send the user back to the login screen.
      cancelButton.setOnAction(event ->
      {
         parentController.goToLoginScreen();
      });

      // Check if all fields valid and register if okay.
      registerButton.setOnAction(event ->
      {
         if (!validUsername)
            errorLabel.setText("That username is not valid.");
         else if (!validEmail)
            errorLabel.setText("That e-mail address is not valid.");
         else if (!validPassword)
            errorLabel.setText("Passwords do not match.");
         else if (!setSecurityQuestion)
            errorLabel.setText("You must type a password reset question.");
         else if (!setSecurityAnswer)
            errorLabel.setText("You must answer your password reset question.");
         else if (!over13CheckBox.isSelected())
            errorLabel.setText("You are not over 13.");
         else
         {
            try
            {
               // Check if user selected a profile picture.
               if (profilePictureImage != null)
               {
                  // TO-DO: Call overloaded register function that allows me to pass an image.
                  // Call normal registration until that is ready.
                  MainModel.getModel()
                           .currentLoginData()
                           .getLogInConnection()
                           .register(usernameTextField.getText(), checkEmailTextField.getText(), checkPasswordField.getText(),
                                     securityQuestionTextField.getText(), securityAnswerTextField.getText(), profilePictureFile);
               }
               else
               {
                  MainModel.getModel()
                           .currentLoginData()
                           .getLogInConnection()
                           .register(usernameTextField.getText(), checkEmailTextField.getText(), checkPasswordField.getText(),
                                     securityQuestionTextField.getText(), securityAnswerTextField.getText());
               }

               // Timeline action event.
               errorLabel.setText("Registration sucessful! Redirecting to the login screen in 5 seconds.");

               Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5000), action ->
               {
                  parentController.goToLoginScreen();
               }));

               timeline.play();
            }
            catch (Exception e)
            {
               errorLabel.setText("There was an error registering your account. Please contact support.");
            }
         } // End final else.
      }); // End cancelButton lambda.
   } // End #initialize.

   /**
    * This private helper method checks if the passed image is a valid one. It can be broken by intentionally passing a corrupted file.
    * 
    * @param file The file you think is an image.
    * @return true if it is an image file; false otherwise.
    */
   private boolean isValidImage(File file)
   {
      String fileName = file.getName();
      String fileExtension = "";

      // Loop over fileName in reverse order.
      for (int index = (fileName.length() - 1); index > 0; index--)
      {
         // Tack on index to beginning.
         fileExtension = fileName.charAt(index) + fileExtension;

         // Found it!
         if (fileExtension.charAt(0) == '.')
            break; // ////////////////////////////////////////////////////////////////////////////////
      }

      // Check if BMP, JPEG, PNG, or GIF.
      if (fileExtension.equalsIgnoreCase(".png") || fileExtension.equalsIgnoreCase(".bmp") || fileExtension.equalsIgnoreCase(".gif")
          || fileExtension.equalsIgnoreCase(".jpeg") || fileExtension.equalsIgnoreCase(".jpg"))
      {
         return true;
      }

      // Implied else
      return false;
   }

   /**
    * This private helper method validates e-mail in a VERY weak way. If we wanted to strongly authenticate e-mails we would have many more checks, force the
    * user to verify through a confirmation e-mail, and likely use regex for sanity.
    * 
    * @param email The e-mail address you wish to validate.
    * @return true If valid e-mail format, false otherwise.
    */
   private boolean isValidEmail(String email)
   {
      if (email.contains("@") & email.contains("."))
         return true;

      // Implied else.
      return false;
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
