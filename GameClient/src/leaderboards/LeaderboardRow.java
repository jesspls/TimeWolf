package leaderboards;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LeaderboardRow {

	public SimpleIntegerProperty rank = new SimpleIntegerProperty();
	public SimpleStringProperty username = new SimpleStringProperty();
	public SimpleIntegerProperty totalPoints = new SimpleIntegerProperty();
	public SimpleIntegerProperty avgPoints = new SimpleIntegerProperty();
	public SimpleIntegerProperty gamesPlayed = new SimpleIntegerProperty();
	public SimpleIntegerProperty gamesWon = new SimpleIntegerProperty();
	public SimpleStringProperty ratio = new SimpleStringProperty();
	public SimpleStringProperty karma = new SimpleStringProperty();
	
	/**
	 * @return rank
	 */
	public int getRank(){
		return rank.get();
	}
	
	/**
	 * @return username
	 */
	public String getUsername(){
		return username.get();
	}
	
	/**
	 * @return total points
	 */
	public Integer getTotalPoints(){
		return totalPoints.get();
	}
	
	/**
	 * @return average points
	 */
	public Integer getAvgPoints(){
		return avgPoints.get();
	}
	
	/**
	 * @return total number of games played
	 */
	public int getGamesPlayed(){
		return gamesPlayed.get();
	}
	
	/**
	 * @return total number of games won
	 */
	public int getGamesWon(){
		return gamesWon.get();
	}
	
	/**
	 * @return ratio of games won vs games loss
	 */
	public String getRatio(){
		return ratio.get();
	}
	
	/**
	 * @return player's karma
	 */
	public String getKarma(){
		return karma.get();
	}
}


