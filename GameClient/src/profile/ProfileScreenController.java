/**
 * @author Anish Kunduru
 * 
 * This program is our handler for ProfileScreen.fxml.
 */

package profile;

import profile.KarmaRow;
import profile.RecentGameStatsRow;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import framework.AbstractScreenController;
import framework.ControlledScreen;
import GameServer.Users.Feedback;
import GameServer.Users.LogIn;
import GameServer.Users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import singleton.MainModel;
import userListing.UserRow;
import view.MainController;

public class ProfileScreenController implements ControlledScreen {
	// FXML components.
	@FXML
	private ObservableList<KarmaRow> tableData;
	@FXML
	private ObservableList<RecentGameStatsRow> tableData2;

	@FXML
	private TableView<KarmaRow> karmaTable;
	@FXML
	private TableColumn ratingColumn;
	@FXML
	private TableColumn playerColumn;
	@FXML
	private TableColumn commentColumn;

	@FXML
	private TableView<RecentGameStatsRow> statTable;
	@FXML
	private TableColumn gamesPlayedColumn;
	@FXML
	private TableColumn gamesWonColumn;
	@FXML
	private TableColumn ratioColumn;
	@FXML
	private TableColumn totalPointsColumn;
	@FXML
	private TableColumn avgPointsColumn;
	@FXML
	private TableColumn karmaColumn;

	@FXML
	private Label largeUsernameLabel;

	@FXML
	private ImageView profileImage;

	private File profileImageFile;
	
	@FXML
	private Label personalInfoLabel;
	@FXML
	private Label usernameLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label locationLabel;
	@FXML
	private Label paranoiaLabel;
	@FXML
	private Label userTypeLabel;

	@FXML
	private Button changeSettingsButton;
	@FXML
	private Button changePasswordButton;
	@FXML
	private Button removeImage;
	@FXML
	private Button browseButton;


	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField locationTextField;
	@FXML
	private CheckBox paranoiaChoiceBox;

	@FXML
	private PasswordField newPasswordTextField;
	@FXML
	private PasswordField checkPasswordTextField;

	@FXML
	private Label changePasswordLabel;
	@FXML
	private Text passwordLabel;
	@FXML
	private Text checkPasswordLabel;

	@FXML
	private Label errorLabel;

	// So we can set the screen's parent later on.
	MainController parentController;

	private User user;

	private LogIn login;

	/**
	 * Initializes the controller class. Automatically called after the FXML
	 * file has been loaded.
	 */
	@FXML
	public void initialize() {

		login = MainModel.getModel().currentLoginData().getLogInConnection();
		if (MainModel.getModel().profileData().getRedirectToClicked() == true) {
			String username = MainModel.getModel().profileData().getClickedUsername();

			changeSettingsButton.setVisible(false);
			changePasswordButton.setVisible(false);
			usernameTextField.setVisible(false);
			emailTextField.setVisible(false);
			locationTextField.setVisible(false);
			paranoiaChoiceBox.setVisible(false);
			newPasswordTextField.setVisible(false);
			checkPasswordTextField.setVisible(false);
			changePasswordLabel.setVisible(false);
			passwordLabel.setVisible(false);
			checkPasswordLabel.setVisible(false);
			removeImage.setVisible(false);
			browseButton.setVisible(false);

			try {
				user = MainModel.getModel().currentLoginData().getLogInConnection().getUser(username);

				setInformation();
				loadKarmaTable();
				loadStatTable();
				
				if(user.isParanoid()){
					usernameLabel.setVisible(false);
					emailLabel.setVisible(false);
					locationLabel.setVisible(false);
					paranoiaLabel.setVisible(false);
					userTypeLabel.setVisible(false);
					personalInfoLabel.setVisible(false);
				}

				try {

					byte[] imgBytes = user.getImageBytes();
					if (imgBytes != null) {
						InputStream is = new ByteArrayInputStream(imgBytes);
						Image img = new Image(is);
						profileImage.setImage(img);
					} else {
						profileImage.setImage(null);
					}
				} catch (Exception e) {
					System.out.println("Image could not be loaded for selected user.");
					e.printStackTrace();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (MainModel.getModel().profileData().getRedirectToClicked() == false) {
			if (MainModel.getModel().currentLoginData().getUsername() != null) {
				String username = MainModel.getModel().currentLoginData().getUsername();
				try {
					if (login == null) {
						login = (LogIn) Naming.lookup("//localhost/auth");
					}
					User u = login.getUser(username);
					byte[] imgBytes = u.getImageBytes();
					if (imgBytes != null) {
						InputStream is = new ByteArrayInputStream(imgBytes);
						Image img = new Image(is);
						profileImage.setImage(img);
					} else {
						profileImage.setImage(null);
					}
				} catch (Exception e) {
					System.out.println("Image could not be loaded for selected user.");
					e.printStackTrace();
				}
				try {
					// Get user.
					user = MainModel.getModel().currentLoginData().getLogInConnection().getUser(username);

					setInformation();

					loadKarmaTable();
					loadStatTable();

					paranoiaChoiceBox.setOnAction(event -> {
						if (paranoiaChoiceBox.isSelected())
							paranoiaChoiceBox.setText("On");
						else
							paranoiaChoiceBox.setText("Off");
					});
					
					removeImage.setOnAction(event ->{
						login.removeAvatar(username);
						profileImage.setImage(null);
					});
					
					browseButton.setOnAction(event ->{
						// Initialize and setup fileChooser.
				         FileChooser fileChooser = new FileChooser();
				         fileChooser.setTitle("Open Profile Picture");

				         // Get the file from the fileChooser.
				         profileImageFile = fileChooser.showOpenDialog(MainModel.getModel().currentMainData().getMainStage());

				         // So we don't throw an exception.
				         if (profileImageFile != null)
				         {
				            // Check if it is a supported image format.
				            if (isValidImage(profileImageFile))
				            {
				               // Set the image.
				               Image img = new Image("file:/" + profileImageFile.getAbsolutePath());

				               profileImage.setImage(img);
				            }
				            else
				               errorLabel.setText("Not a supported image format. Please upload a valid BMP, JPEG, PNG, or GIF.");
				         }
					});

					changeSettingsButton.setOnAction(event -> {

						String username2 = usernameTextField.getText();
						String email = emailTextField.getText();
						String location = locationTextField.getText();
						Boolean paranoia = null;

						System.out.println("Test: " + username2);

						if (paranoiaChoiceBox.isSelected()) {
							paranoia = true;
						} else {
							paranoia = false;
						}

						if ((usernameTextField.getText() != null && !usernameTextField.getText().trim().isEmpty())) {
							user.setUsername(username2);
						}

						if ((emailTextField.getText() != null && !emailTextField.getText().trim().isEmpty())) {
							user.setEmail(email);
						}

						if (((locationTextField.getText() != null && !locationTextField.getText().trim().isEmpty()))) {
							user.setLocation(location);
						}

						if (paranoia != null) {
							user.setParanoid(paranoia);
						}

						try {
							MainModel.getModel().currentLoginData().getLogInConnection().updateSettings(user);
							MainModel.getModel().currentLoginData().getLogInConnection().updateImage(username, profileImageFile);
							errorLabel.setText("Settings update was successful.");
						} catch (Exception e) {
							errorLabel.setText("Settings update was unsuccessful.");
							e.printStackTrace();
						}

					});

				} catch (Exception e) {
					System.out.println("There was an error getting the User object. Is the server down?");
				}
			}

		}

		// Event handlers.

		changePasswordButton.setOnAction(event -> {
			String password = newPasswordTextField.getText();
			String checkPassword = checkPasswordTextField.getText();

			if (password.equals(checkPassword)) {
				int userID = MainModel.getModel().currentLoginData().getUserID();

				try {
					MainModel.getModel().currentLoginData().getLogInConnection().resetPassword(userID, password);
					errorLabel.setText("Password reset successful!");
				} catch (Exception e) {
					errorLabel.setText("There was an error changing your password.");
				}
			} else
				errorLabel.setText("Your new passwords do not match.");
		});
	}
	
	/**
	 * sets the player's information to the correct labels
	 */

	private void setInformation() {
		String email = "";
		String userType = "";
		String location = "";
		Boolean paranoia = false;

		// Set username
		String username = user.getUsername();

		// Set email address.
		email = user.getEmail();

		// Find admin type.
		if (user.isAdmin())
			userType = "admin";
		else if (user.isModerator())
			userType = "moderator";
		else
			userType = "player";

		// Set location
		location = user.getLocation();

		// Set paranoia
		paranoia = user.isParanoid();

		// Set personal information.
		largeUsernameLabel.setText("Profile - " + username);
		usernameLabel.setText("Username: " + username);
		emailLabel.setText("E-mail: " + email);
		userTypeLabel.setText("User Type: " + userType);
		locationLabel.setText("Location: " + location);

		if (paranoia) {
			paranoiaLabel.setText("Paranoia: on");
		} else {
			paranoiaLabel.setText("Paranoia: off");
		}
	}
	
	/**
	 * loads all of the player's karma feedback into the karma table
	 */

	private void loadKarmaTable() {

		// Bind table elements to their appropriate values.
		ratingColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("rating"));
		playerColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("player"));
		commentColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("reasonGiven"));

		// Bind the table values.
		tableData = FXCollections.observableArrayList();
		karmaTable.setItems(tableData);

		ArrayList<Feedback> feedback = new ArrayList<Feedback>();

		feedback = user.Feedback;

		for (int i = 0; i < feedback.size(); i++) {

			KarmaRow currentRow = new KarmaRow();
			if (feedback.get(i).isPositive()) {
				currentRow.rating.set("like");
			} else {
				currentRow.rating.set("dislike");
			}
			String userBy = MainModel.getModel().currentLoginData().getLogInConnection().getUsername(feedback.get(i).getByUserID());
			currentRow.player.set(userBy);

			currentRow.reasonGiven.set("" + feedback.get(i).getDescription());

			tableData.add(currentRow);
		}

	}
	
	/**
	 * load's the users stat information into the stat table
	 */

	private void loadStatTable() {
		gamesPlayedColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("gamesPlayed"));
		gamesWonColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("gamesWon"));
		ratioColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("winLossRatio"));
		totalPointsColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("totalPoints"));
		avgPointsColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("avgPoints"));
		karmaColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("karma"));

		// Bind the table values.
		tableData2 = FXCollections.observableArrayList();
		statTable.setItems(tableData2);
		DecimalFormat DF = new DecimalFormat("#0.00");
		RecentGameStatsRow currentRow = new RecentGameStatsRow();
		currentRow.gamesPlayed.set(user.Statistics.getGamesPlayed());
		currentRow.gamesWon.set(user.Statistics.getGamesWon());
		currentRow.winLossRatio.set(DF.format(user.Statistics.getWinLossRatio()));
		currentRow.totalPoints.set((int)user.Statistics.getTotalPoints());
		currentRow.avgPoints.set((int)user.Statistics.getAveragePoints());
		currentRow.karma.set(DF.format(user.Statistics.getKarma()));

		tableData2.add(currentRow);
	}

	/**
	 * This method will allow for the injection of each screen's parent.
	 */
	@Override
	public void setScreenParent(AbstractScreenController screenParent) {
		parentController = (MainController) screenParent;
	}
	
	/**
	 * Checks to see if the choosen file has a valid image extension
	 * @param file
	 * @return
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
}
