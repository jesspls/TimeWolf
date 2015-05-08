/**
 * @author Anish Kunduru
 *
 * This program is our handler for UserListingScreen.fxml.
 */

package userListing;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import framework.AbstractScreenController;
import framework.ControlledScreen;
import GameServer.Users.LogIn;
import GameServer.Users.User;
import singleton.MainModel;
import view.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserListingScreenController implements ControlledScreen
{

   // So we can set the screen's parent later on.
   private MainController parentController;

   // Table components.
   private ArrayList<User> users;
   private ObservableList<UserRow> tableData;

   @FXML
   private TableView<UserRow> userTable;
   @FXML
   private TableColumn usernameColumn;
   @FXML
   private TableColumn emailColumn;
   @FXML
   private TableColumn bannedColumn;
   @FXML
   private TableColumn roleColumn;
   @FXML
   private TableColumn flaggedColumn;

   // User components.
   @FXML
   private TextField usernameTextField;
   @FXML
   private TextField emailTextField;
   @FXML
   private CheckBox bannedCheckBox;
   @FXML
   private CheckBox flaggedCheckBox;
   @FXML
   private TextArea bannedReasonTextArea;
   @FXML
   private CheckBox administratorRoleCheckBox;
   @FXML
   private CheckBox moderatorRoleCheckBox;
   @FXML
   private CheckBox userRoleCheckBox;
   @FXML
   private Button resetPasswordButton;

   @FXML
   private ImageView profilePictureImageView;
   @FXML
   private Button removePhotoButton;

   // Save/Cancel components.
   @FXML
   private Label errorLabel;

   @FXML
   private Button saveChangesButton;
   @FXML
   private Button cancelButton;

   private LogIn login;

   /**
    * Initializes the controller class. Automatically called after the FXML file has been loaded. Uses the login remote object to create a list of users and
    * their information which can then be changed by an admin.
    */
   @FXML
   public void initialize()
   {
      // Get list of users.
      // NOTE: THIS CODE ISN'T VERY EFFICIENT AND USES A TON OF MEMORY.
      // WE KNOW THIS, BUT THIS SHOULD SUFFICE FOR THE PURPOSES OF THIS DEMO.

      login = MainModel.getModel().currentLoginData().getLogInConnection();

      try
      {
         users = login.getUserList();
      }
      catch (RemoteException | SQLException e1)
      {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }

      // Bind table elements to their appropriate values.
      usernameColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("username"));
      emailColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("email"));
      bannedColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("isBanned"));
      roleColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("role"));
      flaggedColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("isFlagged"));

      // Bind the table values.
      tableData = FXCollections.observableArrayList();
      userTable.setItems(tableData);

      // Populate the table.
      for (int i = 0; i < users.size(); i++)
      {
         UserRow currentEntry = new UserRow(); // new row.

         User currentUser = users.get(i); // Get index in ArrayList.

         currentEntry.username.set(currentUser.getUsername()); // Set
         // username.
         currentEntry.email.set(currentUser.getEmail()); // Set e-mail.
         currentEntry.isBanned.set(currentUser.isBanned()); // Set banned.
         currentEntry.isFlagged.set(currentUser.isFlagged());

         currentEntry.bannedReason.set(currentUser.getBannedReason());

         // Set role.
         if (currentUser.isAdmin())
            currentEntry.role.set("Administrator");
         else if (currentUser.isModerator())
            currentEntry.role.set("Moderator");
         else
            currentEntry.role.set("User");

         // Add to observableArrayList.
         tableData.add(currentEntry);
      }

      // Event handling for selected row on userTable.
      userTable.setOnMouseClicked(event ->
      {
         // Check to make sure something is selected.
         if (userTable.getSelectionModel().getSelectedIndex() != -1)
         {
            usernameTextField.setText(userTable.getSelectionModel().getSelectedItem().username.get());
            emailTextField.setText(userTable.getSelectionModel().getSelectedItem().email.get());

            bannedCheckBox.setSelected(userTable.getSelectionModel().getSelectedItem().isBanned.get());
            flaggedCheckBox.setSelected(userTable.getSelectionModel().getSelectedItem().isFlagged.get());
            checkFlaggedCheckBoxText();
            checkBannedCheckBoxText();

            bannedReasonTextArea.setText(userTable.getSelectionModel().getSelectedItem().bannedReason.get());

            String role = userTable.getSelectionModel().getSelectedItem().role.get();
            if (role.equals("Administrator"))
            {
               administratorRoleCheckBox.setSelected(true);
               moderatorRoleCheckBox.setSelected(false);
               userRoleCheckBox.setSelected(false);
            }
            else if (role.equals("Moderator"))
            {
               moderatorRoleCheckBox.setSelected(true);
               administratorRoleCheckBox.setSelected(false);
               userRoleCheckBox.setSelected(false);
            }
            else
            {
               userRoleCheckBox.setSelected(true);
               administratorRoleCheckBox.setSelected(false);
               moderatorRoleCheckBox.setSelected(false);
            }
            
            /*
             * User now has byte[] of image (must be stored this way to be serializable) getImageBytes() Get file by doing the following: FileOutputStream fos =
             * new FileOutputStream("pathname"); fos.write(myByteArray); fos.close(); Then convert file to image and display
             */
            try
            {

               User u = login.getUser(userTable.getSelectionModel().getSelectedItem().username.get());
               byte[] imgBytes = u.getImageBytes();
               if (imgBytes != null)
               {
                  InputStream is = new ByteArrayInputStream(imgBytes);
                  Image img = new Image(is);
                  profilePictureImageView.setImage(img);
               }
               else
               {
                  profilePictureImageView.setImage(null);
               }
            }
            catch (Exception e)
            {
               System.out.println("Image could not be loaded for selected user.");
               e.printStackTrace();
            }
         }
      });

      // Remove profile picture.
      removePhotoButton.setOnAction(event ->
      {
         // Check if a user is selected.
         if (userTable.getSelectionModel().getSelectedIndex() != -1)
         {
            String username = userTable.getSelectionModel().getSelectedItem().username.get();

            try
            {
               login.removeAvatar(username);
               profilePictureImageView.setImage(null);

            }
            catch (Exception e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      });

      // Remove password.
      resetPasswordButton.setOnAction(event ->
      {
         // Check if a user is selected.
         if (userTable.getSelectionModel().getSelectedIndex() != -1)
         {
            String username = userTable.getSelectionModel().getSelectedItem().username.get();
            Random rnd = new Random();
            try
            {
               login.resetPassword(username, Integer.toString(rnd.nextInt()));
            }
            catch (Exception e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      });

      // Call helper method when user toggles this switch.
      bannedCheckBox.setOnAction(event ->
      {
         checkBannedCheckBoxText();
      });

      flaggedCheckBox.setOnAction(event ->
      {
         checkFlaggedCheckBoxText();
      });

      // Check roles switching logic handling by calling helper method.
      administratorRoleCheckBox.setOnAction(event ->
      {
         moderatorRoleCheckBox.setSelected(false);
         userRoleCheckBox.setSelected(false);
      });

      moderatorRoleCheckBox.setOnAction(event ->
      {
         administratorRoleCheckBox.setSelected(false);
         userRoleCheckBox.setSelected(false);
      });

      userRoleCheckBox.setOnAction(event ->
      {
         administratorRoleCheckBox.setSelected(false);
         moderatorRoleCheckBox.setSelected(false);
      });

      // Send to game lobby screen.
      cancelButton.setOnAction(event ->
      {
         parentController.goToGameLobbyScreen();
      });

      // Save changes.
      saveChangesButton.setOnAction(event ->
      {
         String username = usernameTextField.getText();

         String email = emailTextField.getText();

         int role;
         if (administratorRoleCheckBox.isSelected())
            role = 2;
         else if (moderatorRoleCheckBox.isSelected())
            role = 1;
         else
            role = 0;

         boolean banned = bannedCheckBox.isSelected();

         String bannedReason = bannedReasonTextArea.getText();

         try
         {
            User u = login.getUser(username);
            u.setEmail(email);
            u.setRole(role);
            u.setBannedStatus(banned);
            u.setBannedReason(bannedReason);
            login.save(u);
            if (!flaggedCheckBox.isSelected())
            {
               login.controlFlag(username, "", false);
            }
            else
            {
               login.controlFlag(username, bannedReasonTextArea.getText(), true);
            }
            if (banned) // if they are banned, lower the flag since they are being punished
               login.controlFlag(username, bannedReason, false);
            bannedReasonTextArea.clear();
         }
         catch (Exception e)
         {
            System.out.println("Could not retrieve remote object.");
            e.printStackTrace();
         }
         // Since the table isn't bound to the database (for now), we will
         // need to re-initialize the page for the settings to be visible to
         // the user.
         errorLabel.setText("Changes saved.");
         initialize();
      });
   }

   /**
    * Private helper method to set the text value of bannedCheckBox.
    */
   private void checkBannedCheckBoxText()
   {
      if (bannedCheckBox.isSelected())
         bannedCheckBox.setText("Yes");
      else
         bannedCheckBox.setText("No");
   }

   /**
    * Private helper method to set the text value of flaggedCheckBox.
    */
   private void checkFlaggedCheckBoxText()
   {
      if (flaggedCheckBox.isSelected())
         flaggedCheckBox.setText("Yes");
      else
         flaggedCheckBox.setText("No");
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