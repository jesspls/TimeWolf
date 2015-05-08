package GameServer.GameEngine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class jUnitDiscardPile {
	
	private Deck deckOne;
	private Deck deckTwo;
	private Deck deckStart;
	private DiscardPile discard;
	private Card paradox;
	private Card scavenge;
	private Card bury;
	private Card wormhole;
	private Card beginStealth;
	private Card buyStealth;
	private Card beginAttack;

	@Before
	public void setUp() throws Exception {
		
		deckOne = new Deck();
		deckTwo = new Deck();
		
		discard = new DiscardPile();
		
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
		
		//Card that is always available to buy, similiar to the mystic in ascension
		buyStealth = new Card("Lurk");
		
		//Four of these will go in the player's beginning hand, otherwise unable to buy, they are worth 1 attack each
		beginAttack = new Card("Claw");

	    //Deck one represents the main deck pile cards are drawn from
		deckOne.addCard(paradox, 4);
		deckOne.addCard(scavenge, 4);
		deckOne.addCard(bury, 4);
		deckOne.addCard(wormhole, 4);
		
		//deckStart represents a beginning deck for individual player
		deckStart = new Deck();
		deckStart.addCard(beginStealth, 6);
		deckStart.addCard(beginAttack, 4);
	}

	@Test
	public void testDiscardSizeAfterDiscards() {
		discard.discard(beginAttack);
		discard.discard(scavenge);
		discard.discard(buyStealth);
		assertEquals(3, discard.size());
	}
	
	@Test
	public void testDiscardSizeAfterAddToDeck() {
		discard.discard(beginAttack);
		discard.discard(scavenge);
		discard.discard(buyStealth);
		discard.addToDeck(deckTwo);
		assertEquals(0, discard.size());
	}
	
	@Test
	public void testDeckSizeeAfterAddToDeck() {
		discard.discard(beginAttack);
		discard.discard(scavenge);
		discard.discard(buyStealth);
		discard.addToDeck(deckTwo);
		assertEquals(3, deckTwo.size());
	}
}
