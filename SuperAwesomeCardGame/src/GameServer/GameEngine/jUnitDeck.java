package GameServer.GameEngine;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class jUnitDeck {
	
	private Deck deckOne;
	private Deck deckTwo;
	private Card paradox;
	private Card scavenge;
	private Card bury;
	private Card wormhole;
	private Card beginStealth;

	@Before
	public void setUp() throws Exception {
		
		deckOne = new Deck();
		deckTwo = new Deck();
		
		//Card paradox causes all other players to go back 10 years by adding a "paradox" card to their deck
		paradox = new Card("Paradox");
		
		//Card scavenge allows the player to draw two additional cards when played from hand
		scavenge = new Card("Scavenge");
		
		//Card bury allows a player to discard two cards and pick up two new ones
		bury = new Card("Bury");
		
		//Card wormhole makes all other players discard 2 cards from hand
		wormhole = new Card("Wormhole");
		
		//Six of these will go in the player's beginning hand, otherwise unable to buy, they are worth 1 stealth each
		beginStealth = new Card("Prowl");

	    //Deck one represents the main deck pile cards are drawn from
		deckOne.addCard(paradox, 4);
		deckOne.addCard(scavenge, 4);
		deckOne.addCard(bury, 4);
		deckOne.addCard(wormhole, 4);
		
	}

	@Test
	public void testDeckSize() {
		assertEquals(16, deckOne.size());
	}
	
	@Test
	public void testDeckSizeAfterDraw() {
		deckOne.draw(null);
		assertEquals(15, deckOne.size());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testDrawWithNoCards() {
		deckTwo.draw(null);
	}
	
	@Test
	public void testDeckSizeDrawWithOneCard() {
		deckTwo.addCard(beginStealth);
		deckTwo.draw(null);
		assertEquals(0, deckTwo.size());
	}

	@Test
	public void testDeckSizeAfterAddMultipleCards() {
		deckTwo.addCard(beginStealth, 6);
		assertEquals(6, deckTwo.size());
	}
	
	@Test
	public void testGetMainDeck() throws SQLException {
		Deck deck = new Deck();
		deck = deck.getMainDeck();
		assertEquals(120, deck.size());
	}
	
	@Test
	public void testGetStarterDeck() throws SQLException {
		Deck deck = new Deck();
		deck = deck.getStarterDeck();
		assertEquals(10, deck.size());
	}

}
