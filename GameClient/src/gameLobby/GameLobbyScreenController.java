/**
 * @author Anish Kunduru
 * 
 * This program is our handler for GameLobbyScreen.fxml.
 */

package gameLobby;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import chat.Chat;
import framework.AbstractScreenController;
import framework.ControlledScreen;
import framework.Destroyable;
import GameServer.GameInfo;
import GameServer.IGameManagement;
import singleton.MainModel;
import userListing.UserRow;
import view.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GameLobbyScreenController implements ControlledScreen, Destroyable {
	// PUBLIC CONSTANTS THAT WILL NEED TO BE UPDATED WHEN SERVER FIELDS CHANGE.
	public final String SERVER_ADDRESS = "localhost";
	// public final String SERVER_ADDRESS = "10.25.68.24";

	// Table components.
	private ArrayList<GameInfo> games;
	private ObservableList<LobbyRow> tableData;

	@FXML
	private TableView<LobbyRow> gamesTable;
	@FXML
	private TableColumn nameColumn;
	@FXML
	private TableColumn numPlayersColumn;
	@FXML
	private TableColumn chatColumn;
	@FXML
	private TableColumn privateColumn;

	@FXML
	private Button reloadTableButton;
	@FXML
	private Button joinButton;
	@FXML
	private Button searchButton;
	@FXML
	private Button createButton;

	@FXML
	private TextArea chatBoxTextArea;
	@FXML
	private TextArea chatMessageTextArea;

	@FXML
	private Button searchUsersButton;
	@FXML
	private TextField searchUsersTextField;
	@FXML
	private Label userNotFoundLabel;

	@FXML
	private Button reportButton;

	@FXML
	private Label reportLabel;

	// So we can set the screen's parent later on.
	MainController parentController;

	// So that we can call it from different event listeners.
	private Chat chat;
	private IGameManagement gameManagement;

	// Join game logic.
	private int gameID;
	private int numPlayers;
	private boolean chatEnabled;

	/**
	 * Initializes the controller class. Automatically called after the FXML
	 * file has been loaded. Calls remote game management object and from that
	 * object it obtains a list of games. It also initializes a chat server.
	 */
	@FXML
	public void initialize() {
		// Hide error Label for now
		userNotFoundLabel.setVisible(false);

		// Initialize gameManagement.
		try {
			gameManagement = (IGameManagement) Naming.lookup("//" + SERVER_ADDRESS + "/game");
			MainModel.getModel().currentGameLobbyData().setGameManager(gameManagement);
		} catch (Exception e) {
			// DEBUG
			System.out.println("Error initializing remote game management object.");
			e.printStackTrace();
		}

		reportButton.setOnAction(event -> {
			String log = chatBoxTextArea.getText();
			if (!log.equals("")) {
				MainModel.getModel().currentLoginData().getLogInConnection().insertReport(log);
				reportLabel.setText("Report sent.");
			}
		});

		// Initialize table
		loadGameTable();

		// TO-DO: REDIRECT LOGIC.
		// Store the information that game table might need in the GameLobbyData
		// singleton... unless you are supposed to pass something to the server,
		// up to you
		// guys...
		// The singleton has already been created, and linked, you just need to
		// define whatever you need/want to store.

		// Event handling for selected row on gamesTable.
		gamesTable.setOnMouseClicked(event -> {
			// Check to make sure something is selected.
				if (gamesTable.getSelectionModel().getSelectedIndex() != -1) {
					gameID = gamesTable.getSelectionModel().getSelectedItem().id.get();
					numPlayers = gamesTable.getSelectionModel().getSelectedItem().getNumPlayers();
					chatEnabled = gamesTable.getSelectionModel().getSelectedItem().getChat();
				}
			});

		// Initialize chat.
		chat = new Chat(Chat.GAME_LOBBY_SCREEN, MainModel.getModel().currentLoginData().getUsername(), -1);

		reloadTableButton.setOnAction(event -> {
			loadGameTable(); // Reload game table.
			});

		joinButton.setOnAction(event -> {
			// Check to make sure the user has clicked on a game in the table.
				if (numPlayers == 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Joining Game");
					alert.setHeaderText("You didn't join a valid game!");
					alert.setContentText("Select a game in the game table.");

					alert.showAndWait();
				}
				// TODO: Else if logic if the game is now full...
				/*
				 * { Alert alert = new Alert(AlertType.ERROR);
				 * alert.setTitle("Error Joining Game");
				 * alert.setHeaderText("The game is now full.");
				 * alert.setContentText
				 * ("Select a different game in the game table.");
				 * 
				 * loadGameTable(); // Reload the table.
				 * 
				 * alert.showAndWait(); }
				 */
				else {
					MainModel.getModel().currentGameLobbyData().setID(gameID);
					MainModel.getModel().currentGameLobbyData().setNumPlayers(numPlayers);
					MainModel.getModel().currentGameLobbyData().setChatEnabled(chatEnabled);
					parentController.goToGameTableScreen();
				}
			});

		searchButton.setOnAction(event -> {
			parentController.goToSearchGameScreen();
		});

		createButton.setOnAction(event -> {
			parentController.goToCreateGameScreen();
		});

		searchUsersButton.setOnAction(event -> {

			String username = searchUsersTextField.getText();
			try {
				MainModel.getModel().profileData().setRedirectToClicked(true);
				MainModel.getModel().profileData().setClickedUsername(username);
				parentController.goToProfileScreen();
			} catch (Exception e) {
				userNotFoundLabel.setVisible(true);
			}
		});
	}

	/**
	 * To be called by the chat's "Send message" button.
	 */
	public void sendMessage() {
		String outgoingMsg = chatMessageTextArea.getText();

		// Make sure the user didn't just press the button without anything in
		// the text field.
		if (!outgoingMsg.equals(""))
			chat.bufferMessage(outgoingMsg);

		chatMessageTextArea.clear();

		// DEBUG
		System.out.println("Outgoing: " + outgoingMsg);
	}

	/**
	 * A helper method that is called on initialize(), or whenever the user
	 * presses the refreshTableButton.
	 */
	private void loadGameTable() {
		try {
			games = gameManagement.listJoinableGames();

			// Bind table elements to their appropriate values.
			nameColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("name"));
			numPlayersColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("numberPlayers"));
			chatColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("chat"));
			//privateColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("privateLobby"));

			// Bind the table values.
			tableData = FXCollections.observableArrayList();
			gamesTable.setItems(tableData);

			if (MainModel.getModel().currentGameLobbyData().isSearching()) {
				System.out.println("Games size: " + games.size());
				for (int i = 0; i < games.size(); i++) {

					System.out.println(games.get(i).getName());
					System.out.println(games.get(i).getChat() + " != " + MainModel.getModel().currentGameLobbyData().getChatEnabled());
					System.out.println(games.get(i).getNumPlayers() + " != " + MainModel.getModel().currentGameLobbyData().getNumPlayers());
					if (games.get(i).getChat() != MainModel.getModel().currentGameLobbyData().getChatEnabled()
							|| games.get(i).getNumPlayers() != MainModel.getModel().currentGameLobbyData().getNumPlayers()
							|| games.get(i).getPrivacy() != MainModel.getModel().currentGameLobbyData().isPrivate()
							|| ((!games.get(i).getName().equals(MainModel.getModel().currentGameLobbyData().getGameName())) && !MainModel.getModel()
									.currentGameLobbyData().getGameName().trim().equals(""))) {
						games.remove(games.get(i));
						i--;
					}

				}
				MainModel.getModel().currentGameLobbyData().setSearch(false);
			}

			if (games != null && !games.isEmpty()) {
				// Populate the table.
				for (GameInfo currentGame : games) {
					LobbyRow currentEntry = new LobbyRow(); // new row.

					// Get number of players currently playing.
					int curPlayers = currentGame.getPlayers().size();
					// Get the total number of players.
					int maxPlayers = currentGame.getNumPlayers();

					// Combine number of players.
					String numberPlayers = curPlayers + "/" + maxPlayers;

					currentEntry.name.set(currentGame.getName()); // Set
																	// game
																	// name.
					currentEntry.numberPlayers.set(numberPlayers); // Set
																	// numberPlayers

					// The following are features we can add later if time
					// permits:
					currentEntry.chat.set(currentGame.getChat()); // Chat will
																	// be
																	// enabled
																	// for
					// all games for now.
					//currentEntry.privateLobby.set(false); // All lobbies
															// will be
															// public for
															// now.

					currentEntry.id.set(currentGame.getID());
					currentEntry.numPlayers.set(currentGame.getNumPlayers());

					// Add to observableArrayList.
					tableData.add(currentEntry);
				}

			}
			// Note that this won't work at initial launch because of the
			// way the controller is created... This would be fixed with my
			// revised controller.
			else
				gamesTable.setPlaceholder(new Label("There are no games to join. Create one!"));
		} catch (RemoteException e) {
			System.out.println("There was an error in trying to create the table.");
		}

	}

	/**
	 * Append a message to the chatBoxTextArea that is visible to the user.
	 * 
	 * @param message
	 *            The message that you wish appended to the chat box.
	 */
	public void appendToChatBox(String message) {
		String append = message + "\n";
		chatBoxTextArea.appendText(append);
	}

	/**
	 * This method will allow for the injection of each screen's parent.
	 */
	@Override
	public void setScreenParent(AbstractScreenController screenParent) {
		parentController = (MainController) screenParent;
	}

	/**
	 * This method will allow us to safely close thread elements when we are
	 * ready to leave the page.
	 */
	@Override
	public void onDestroy() {
		// DEBUG
		System.out.println("Destroying the game lobby screen...");

		// Close out chat.
		chat.end();
	}
}
