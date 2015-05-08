/**
 * @author Anish Kunduru
 * 
 * This class defines a row in the feedback recent games stats table.
 */

package profile;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RecentGameStatsRow
{
   // Define our entry types.
   public SimpleIntegerProperty gamesPlayed = new SimpleIntegerProperty();
   public SimpleIntegerProperty gamesWon = new SimpleIntegerProperty();
   public SimpleStringProperty winLossRatio = new SimpleStringProperty();
   public SimpleIntegerProperty totalPoints = new SimpleIntegerProperty();
   public SimpleIntegerProperty avgPoints = new SimpleIntegerProperty();
   public SimpleStringProperty karma = new SimpleStringProperty();

   // Define auto getters to populate table on initialize.

   /**
    * @return number of games played
    */
   public int getGamesPlayed()
   {
      return gamesPlayed.get();
   }

   /**
    * @return number of games won
    */
   public int getGamesWon()
   {
      return gamesWon.get();
   }

   /**
    * @return the win/loss ratio
    */
   public String getWinLossRatio()
   {
      return winLossRatio.get();
   }

   /**
    * @return total points won
    */
   public int getTotalPoints()
   {
      return totalPoints.get();
   }

   /**
    * @return avg points won per game
    */
   public int getAvgPoints()
   {
      return avgPoints.get();
   }

   /**
    * @return user's karma score
    */
   public String getKarma()
   {
      return karma.get();
   }
}
