package GameServer.GameEngine;

import java.util.ArrayList;
import java.util.Iterator;

public class DiscardPile {
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	
	/**
	 * Discard a card to the discard pile.
	 * @param c the card to discard
	 */
	public void discard(Card c) {
		cards.add(c);
	}
	
	/**
	 * Discard the given hand
	 * @param hand
	 */
	
	public void discard(Hand hand){
		int i = 0;
		while(hand.size() > 0){
			this.discard(hand.remove(i));
		}
	}

	/**
	 * Add the contents of the discard pile to deck d
	 * @param d the deck
	 */
	public void addToDeck(Deck d) {
		
		//Add all the cards from the discard pile to the deck
		Iterator<Card> i = this.cards.iterator();
		while(i.hasNext()) {
			Card c = i.next();
			d.addCard(c);
		}
		
		//Empty the discard pile
		this.cards.clear();
		
	}

	/**
	 * The size of the discard pile
	 * @return size of the pile
	 */
	public int size() {
		return this.cards.size();
	}
}
