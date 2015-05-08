/**
 * @author Anish Kunduru
 *
 * This program is our handler for SearchGameScreen.fxml.
 */

package searchGame;

import framework.AbstractScreenController;
import framework.ControlledScreen;
import singleton.MainModel;
import view.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SearchGameScreenController implements ControlledScreen {
	// FXML Components.
	@FXML
	private Button searchButton;

	@FXML
	private ComboBox<Integer> numberPlayersComboBox;

	@FXML
	private CheckBox chatCheckBox;
	
	@FXML 
	private TextField gameNameTextField;

	// So we can set the screen's parent later on.
	MainController parentController;

	/**
	 * Initializes the controller class. Automatically called after the FXML
	 * file has been loaded.
	 */
	@FXML
	public void initialize() {
		numberPlayersComboBox.getItems().add(2);
		numberPlayersComboBox.getItems().add(3);
		numberPlayersComboBox.getItems().add(4);
		numberPlayersComboBox.getSelectionModel().select(0);

		// Set appropriate text if box is unchecked.
		chatCheckBox.setOnAction(event -> {
			if (chatCheckBox.isSelected())
				chatCheckBox.setText("On");
			else
				chatCheckBox.setText("Off");
		});

		
		searchButton.setOnAction(event -> {
			// TO-DO: CHECK IF EVERYTHING WAS SELECTED PROPERLY. (VERIFY INPUT)

				// TO-DO: SET SEARCH CRITERA IN GAME LOBBY SINGLETON.
				MainModel.getModel().currentGameLobbyData().setGameName(gameNameTextField.getText());
				MainModel.getModel().currentGameLobbyData().setChatEnabled(chatCheckBox.isSelected());
				MainModel.getModel().currentGameLobbyData().setNumPlayers(numberPlayersComboBox.getSelectionModel().getSelectedItem());
				MainModel.getModel().currentGameLobbyData().setSearch(true);
			
			
				// Display the lobby screen.
				parentController.goToGameLobbyScreen();
			});
	}

	/**
	 * This method will allow for the injection of each screen's parent.
	 */
	@Override
	public void setScreenParent(AbstractScreenController screenParent) {
		parentController = (MainController) screenParent;
	}
}
