package GameServer.GameEngine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import GameServer.Users.User;

public class jUnitPlayer {
	
	private Player player;
	private User user;

	@Before
	public void setUp() throws Exception {

		user = new User();
		player = new Player(user);

	}
	
	@Test
	public void initializedValueAttack() {
		assertEquals(0, player.getAttack());
	}
	
	@Test
	public void initializedValueStealth() {
		assertEquals(0, player.getStealth());
	}
	
	@Test
	public void initializedValueHand() {
		assertEquals(5, player.getHand().size());
	}
	
	@Test
	public void initializedValueDeck() {
		assertEquals(5, player.getDeck().size());
	}
	

	@Test
	public void initializedValueIsTurn() {
		assertEquals(false, player.getIsTurn());
	}
	
	
	@Test
	public void getUser() {
		assertEquals(user, player.getUser());
	}
	
	@Test
	public void initializedValueDiscardPile() {
		assertEquals(0, player.getDiscardPile().size());
	}
	
	@Test
	public void addStealth() {
		player.addStealth(2);
		assertEquals(2, player.getStealth());
	}
	
	@Test
	public void addAttack() {
		player.addAttack(2);
		assertEquals(2, player.getAttack());
	}
	
	@Test
	public void addVP() {
		player.addVP(100);
		assertEquals(100, player.getVP());
	}
	
	@Test
	public void setIsTurn() {
		player.isTurn(true);
		assertEquals(true, player.getIsTurn());
	}
	
	@Test
	public void afterResetValueAttack() {
		player.addAttack(5);
		player.resetPlayer();
		assertEquals(0, player.getAttack());
	}
	
	@Test
	public void afterResetValueStealth() {
		player.addStealth(5);
		player.resetPlayer();
		assertEquals(0, player.getStealth());
	}
	
	@Test
	public void afterResetValueDeck() {
		player.resetPlayer();
		assertEquals(0, player.getDeck().size());
	}
	
	@Test
	public void afterResetValueDiscardPile() {
		player.resetPlayer();
		assertEquals(5, player.getDiscardPile().size());
	}
	
	@Test
	public void afterResetResetValueDeck() {
		player.resetPlayer();
		player.resetPlayer();
		assertEquals(5, player.getDeck().size());
	}

}