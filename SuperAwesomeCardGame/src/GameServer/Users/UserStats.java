package GameServer.Users;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import GameServer.DBHelper;

/**
 * An object to interface with the database when viewing user statistics This
 * class will help calculate win ratios, karma values, and other helpful
 * statistics based on a users game interaction.
 * 
 * @author Shelbie
 *
 *
 *
 */



public class UserStats implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int gamesPlayed;
	private int gamesWon;
	private double totalPoints;
	private double karmaScore;
	private int userID;
	private int ID;

	public UserStats()
	{
		ID = 0;
	}
	
	/**
	 * no longer in use
	 * @param userID
	 */
	
	public UserStats(int userID)
	{
		//this method is discontinued
		//should not be used	
	}
	
	/**
	 * @param id id of stats
	 * @param uID id of user
	 * @param karma user's karma score
	 * @param points user's total points
	 * @param won user's total games won
	 * @param played user's total games played
	 */
	public UserStats(int id, int uID, double karma, double points, int won, int played)
	{
		this.ID = id;
		this.userID = uID;
		this.karmaScore = karma;
		this.totalPoints = points;
		this.gamesWon = won;
		this.gamesPlayed = played;
	
	}

	/**
	 * Total number of games played
	 * 
	 * @return gamesPlayed
	 */
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	/**
	 * Sets total number of games played
	 * @param gamesPlayed
	 */
	public void setGamesPlayed(int gamesPlayed)
	{
		this.gamesPlayed = gamesPlayed;
	}
	
	/**
	 * Total number of games won
	 * @return gamesWon
	 */
	
	public int getGamesWon() {
		return gamesWon;
	}

	/**
	 * Returns the win loss ratio
	 * 
	 * @return (# of games won) / ( # of games lost)
	 */
	public double getWinLossRatio() {
		if((double)gamesPlayed - (double)gamesWon == 0)
			return (double)gamesWon;
		else
			return (double)gamesWon / ((double)gamesPlayed - (double)gamesWon);
	}

	/**
	 * Returns total points accumulated over all games
	 * 
	 * @return totalPoints
	 */
	public double getTotalPoints() {
		return totalPoints;
	}

	/**
	 * Returns average number of points over all games
	 * 
	 * @return totalPoints/gamesPlayed
	 */
	public double getAveragePoints() {
		if(gamesPlayed == 0)
			return 0;
		
		return totalPoints / gamesPlayed;
	}

	/**
	 * TBD once we figure out how to use karma Will interface with the Feedback
	 * table in the database
	 * 
	 * @return
	 */
	public double getKarma() {
		return karmaScore;
	}

	/**
	 * Used after a game is played to increase statistics from that game
	 * @param wonGame if true, gamesWon counter increments by 1
	 * @param points increments total points value by this parameter
	 */
	public void incrementGamesPlayed(boolean wonGame, int points)
	{
		if(wonGame) gamesWon++;
		gamesPlayed++;
		totalPoints += points;
	}
	
	/**
	 * Retrieves stats from the database matching the given userID
	 * 
	 * @param userID
	 * @return corresponding user's statistic information
	 * @throws SQLException
	 */
//	public void getStats(int userID) {
//		try {
//
//			DBHelper dbh = new DBHelper();
//			String query = "SELECT * FROM Statistics WHERE UserID=" + userID;
//			ResultSet rs = dbh.executeQuery(query);
//			
//
//			if (rs.first()) // should only be one returned on table is incorrect
//			{
//				this.gamesPlayed = rs.getInt("TotalGames");
//				this.gamesWon = rs.getInt("TotalWins");
//				this.totalPoints = rs.getDouble("TotalPoints");
//				this.ID = rs.getInt("ID");
//				this.userID = userID;
//			} else {
//				// given userID is not valid
//				this.gamesPlayed = 0;
//				this.gamesWon = 0;
//				this.totalPoints = 0;
//				this.ID = 0;
//				this.userID = 0;
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//
//	}
//	
//	public void updateStatsDatabase()
//	{
//		DBHelper dbh = new DBHelper();
//		String query = "UPDATE Statistics SET TotalGames=" + gamesPlayed + ",TotalWins=" + gamesWon + ",TotalPoints=" + totalPoints;
//		query = query + " WHERE UserID=" + userID;
//		dbh.executeUpdate(query);
//		
//	}


	
}