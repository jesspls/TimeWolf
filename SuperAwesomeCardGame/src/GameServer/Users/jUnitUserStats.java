package GameServer.Users;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class jUnitUserStats {

	@Before
	public void setUp() throws Exception {	
		
	}

	@Test
	public void testUserStatsGamesPlayed() {
		UserStats userStat = new UserStats(2);
		assertEquals(5, userStat.getGamesPlayed());
	}

	@Test
	public void testUserStatsGamesWon() {
		UserStats userStat = new UserStats(2);
		assertEquals(3, userStat.getGamesWon());
	}
	
	@Test
	public void testUserStatsWinLossRatio() {
		UserStats userStat = new UserStats(2);
		assertEquals(1.5, userStat.getWinLossRatio(), .0);
	}
	
	@Test
	public void testUserStatsTotalPoints() {
		UserStats userStat = new UserStats(2);
		assertEquals(10, userStat.getTotalPoints(), .0);
	}
	
	@Test
	public void testUserStatsAveragePoints() {
		UserStats userStat = new UserStats(2);
		assertEquals(2, userStat.getAveragePoints(), .0);
	}
	

}
