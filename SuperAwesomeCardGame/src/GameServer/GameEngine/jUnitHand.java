package GameServer.GameEngine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class jUnitHand {
	
	private Hand hand;
	private Card beginStealth;
	private Card beginAttack;

	@Before
	public void setUp() throws Exception {
		
		hand = new Hand(5);
		
		//Six of these will go in the player's beginning hand, otherwise unable to buy, they are worth 1 stealth each
		beginStealth = new Card("Prowl");
		
		//Four of these will go in the player's beginning hand, otherwise unable to buy, they are worth 1 attack each
		beginAttack = new Card("Claw");
	}

	@Test
	public void testHandSizeAfterInitialization() {
		assertEquals(0, hand.size());
	}
	
	@Test
	public void testHandSizeAfterAddCard() {
		hand.addCard(beginAttack);
		hand.addCard(beginAttack);
		hand.addCard(beginAttack);
		hand.addCard(beginStealth);
		hand.addCard(beginStealth);
		assertEquals(5, hand.size());
	}
	
	@Test
	public void testHandSizeAfterAddCardRemove() {
		hand.addCard(beginAttack);
		hand.addCard(beginAttack);
		hand.addCard(beginAttack);
		hand.addCard(beginStealth);
		hand.addCard(beginStealth);
		hand.remove(3);
		assertEquals(4, hand.size());
	}
	
	@Test
	public void testHandSizeAfterAddCardRemove2() {
		hand.addCard(beginAttack);
		hand.remove(0);
		assertEquals(0, hand.size());
	}
	
	@Test
	public void testGetCard() {
		hand.addCard(beginAttack);
		hand.addCard(beginAttack);
		hand.addCard(beginAttack);
		hand.addCard(beginStealth);
		hand.addCard(beginStealth);
		assertEquals(beginStealth, hand.get(4));
	}
	
	@Test
	public void testSizeAfterAddingMoreThanDefault() {
		hand.addCard(beginAttack);
		hand.addCard(beginAttack);
		hand.addCard(beginAttack);
		hand.addCard(beginStealth);
		hand.addCard(beginStealth);
		hand.addCard(beginStealth);
		assertEquals(6, hand.size());
	}


}
