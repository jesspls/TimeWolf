/**
 * @author Anish Kunduru
 * 
 * This is the controller for LeaderboardsScreen.fxml.
 */

package leaderboards;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import framework.AbstractScreenController;
import framework.ControlledScreen;
import GameServer.Users.User;
import profile.KarmaRow;
import profile.RecentGameStatsRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import singleton.MainModel;
import userListing.UserRow;
import view.MainController;

public class LeaderboardsScreenController implements ControlledScreen {
	private ObservableList<LeaderboardRow> tableData;
	@FXML
	private TableView<LeaderboardRow> leaderboard;

	@FXML
	private TableColumn rankColumn;
	@FXML
	private TableColumn usernameColumn;
	@FXML
	private TableColumn totalPointsColumn;
	@FXML
	private TableColumn avgPointsColumn;
	@FXML
	private TableColumn gamesPlayedColumn;
	@FXML
	private TableColumn gamesWonColumn;
	@FXML
	private TableColumn ratioColumn;
	@FXML
	private TableColumn karmaColumn;

	// So we can set the screen's parent later on.
	MainController parentController;

	@FXML
	private Button searchButton;

	private String username;

	/**
	 * Initializes the controller class. Automatically called after the FXML
	 * file has been loaded.
	 */
	@FXML
	public void initialize() {
		rankColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("rank"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("username"));
		totalPointsColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("totalPoints"));
		avgPointsColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("avgPoints"));
		gamesPlayedColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("gamesPlayed"));
		gamesWonColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("gamesWon"));
		ratioColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("ratio"));
		karmaColumn.setCellValueFactory(new PropertyValueFactory<UserRow, String>("karma"));

		tableData = FXCollections.observableArrayList();
		leaderboard.setItems(tableData);

		ArrayList<User> users = new ArrayList<User>();

		try {
			users = MainModel.getModel().currentLoginData().getLogInConnection().getLeaderboard();
			int count = 1;

			for (int i = 0; i < users.size(); i++) {
				DecimalFormat df = new DecimalFormat("#0.00");
				User user = users.get(i);
				if (!user.isBanned()) {
					LeaderboardRow currentRow = new LeaderboardRow();
					currentRow.rank.set(count++);
					currentRow.username.set(user.getUsername());
					currentRow.gamesPlayed.set(user.Statistics.getGamesPlayed());
					currentRow.gamesWon.set(user.Statistics.getGamesWon());
					currentRow.ratio.set(df.format(user.Statistics.getWinLossRatio()));
					currentRow.totalPoints.set((int) user.Statistics.getTotalPoints());
					currentRow.avgPoints.set((int) user.Statistics.getAveragePoints());
					currentRow.karma.set(df.format(user.Statistics.getKarma()));

					tableData.add(currentRow);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		leaderboard.setOnMouseClicked(event -> {
			// Check to make sure something is selected.
				if (leaderboard.getSelectionModel().getSelectedIndex() != -1) {
					username = leaderboard.getSelectionModel().getSelectedItem().username.get();
				}
			});

		searchButton.setOnMouseClicked(event -> {
			try {
				if (username != null) {
					MainModel.getModel().profileData().setClickedUsername(username);
					MainModel.getModel().profileData().setRedirectToClicked(true);
					parentController.goToProfileScreen();
				}
				else{
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
