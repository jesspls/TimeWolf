package GameServer.GameEngine;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class jUnitCard {
	
	private Card paradox;
	private Card scavenge;
	private Card bury;
	private Card wormhole;
	private Card beginStealth;
	private Card buyStealth;
	private Card beginAttack;
	private Card buyAttack;
	private Card dataBase;

	@Before
	public void setUp() throws Exception {
		
		//Card paradox causes all other players to go back 25 years by adding a "paradox" card to their deck
		paradox = new Card("Paradox");
				
		//Card scavenge allows the player to draw two additional cards when played from hand
		scavenge = new Card("Scavenge");
				
		//Card bury allows a player to discard two cards and pick up two new ones
		bury = new Card("Bury");
				
		//Card wormhole makes all other players discard 2 cards from hand
		wormhole = new Card("Wormhole");
				
		//Six of these will go in the player's beginning hand, otherwise unable to buy, they are worth 1 stealth each
		beginStealth = new Card("Prowl");
				
		//Card that is always available to buy, similiar to the mystic in ascension
		buyStealth = new Card("Lurk");
				
		//Four of these will go in the player's beginning hand, otherwise unable to buy, they are worth 1 attack each
		beginAttack = new Card("Claw");
				
		//Card that is always available to buy, similiar to the heavy infantry in ascension
		buyAttack = new Card("Bite");

	}

	@Test
	public void testGetName() {
		assertEquals("Scavenge", scavenge.getName());
	}
	
	@Test
	public void testGetType() {
		assertEquals("Action", scavenge.getCardType());
	}
	
	@Test
	public void testGetDescription() {
		assertEquals("Draw 2 additional cards.", scavenge.getDescription());
	}
	
	@Test
	public void testGetCostBuy() {
		assertEquals(3, buyStealth.getCostStealth());
	}
	
	@Test
	public void testGetVp() {
		assertEquals(0, scavenge.getVp());
	}
	
	@Test
	public void testGetCostAttack() {
		assertEquals(0, scavenge.getCostAttack());
	}
	
	@Test
	public void testGetPower() {
		assertEquals(2, buyAttack.getAttack());
	}
	
	@Test
	public void testGetMoney() {
		assertEquals(2, buyStealth.getStealth());
	}
	
	@Test
	public void testGetPreturnDiscard() {
		assertEquals(0, scavenge.getPreturnDiscard());
	}
	
	@Test
	public void testGetPostTurnDiscard() {
		assertEquals(0, scavenge.getPostturnDiscard());
	}
	
	@Test
	public void testGetDrawCards() {
		assertEquals(2, scavenge.getDrawCards());
	}
	
	@Test
	public void testGetOthersDrawCards() {
		assertEquals(0, scavenge.getOthersDrawCards());
	}
	
	@Test
	public void testGetTrashCardsMandatory() {
		assertEquals(0, scavenge.getTrashCardsMandatory());
	}
	
	@Test
	public void testGetTrashCardsOptional() {
		assertEquals(0, scavenge.getTrashCardsOptional());
	}
	
	@Test
	public void testGetTrashForPower() {
		assertEquals(0, scavenge.getTrashForAttack());
	}
	
	@Test
	public void testGetTrashForStealth() {
		assertEquals(0, scavenge.getTrashForStealth());
	}
	
	@Test
	public void testGetRemoveFromPlayArea() {
		assertEquals(0, scavenge.getRemoveFromPlayArea());
	}
	
	@Test
	public void testGetOthersDiscard() {
		assertEquals(0, scavenge.getOthersDiscard());
	}
	
	@Test
	public void testIsTakeAnotherTurn() {
		assertEquals(true, bury.isTakeAnotherTurn());
	}
	
	@Test
	public void testIsRefreshPlayArea() {
		assertEquals(false, scavenge.isRefreshPlayArea());
	}
	
	@Test
	public void getGiveCurseCards() {
		assertEquals(25, paradox.getOthersLoseVP());
	}
	
	@Test
	public void testIsTrashAfterUse() {
		assertEquals(false, paradox.isTrashAfterUse());
	}
	

}
