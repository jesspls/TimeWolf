package GameServer.GameEngine;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import GameServer.DBHelper;

public class Deck {
	
   private static final long serialVersionUID = 1L;
   
	private ArrayList<Card> cards = new ArrayList<Card>();
	private Random random = new Random();
	
	/**
	 * Adds a card num number of times to the deck.
	 * @param c adds card c to the deck
	 * @param number the number of times the card is added.
	 */
	public void addCard(Card c, int num) {
		//We must have a card and a valid number of cards
		if(c == null) throw new NullPointerException();
		if(num < 1) throw new IllegalArgumentException();
		
		//Add the card to the deck num times.
		for(int i = 0; i < num; i++) {
			this.cards.add(c);
		}
		
	}
	
	/**
	 * Add a single card to the draw deck
	 * @param c Card
	 */
	public void addCard(Card c) {
		this.addCard(c, 1); //Have the full version do the work
	}
	
	/**
	 * Draw a card from the deck.
	 * @return the card drawn
	 */
	public Card draw(DiscardPile pile) {
		int index;
		
		if(this.cards.size() == 0) throw new IllegalStateException();
		
		if(this.cards.size() == 1) { //If there is only one card, then it isn't so random
			index = 0;
		} else { 
			//Determine a random card to return
			index = this.random.nextInt(this.cards.size() - 1);
		}
		
		//Then return and remove it from the deck
		Card c = this.cards.get(index);
		this.cards.remove(index);
		
		//Draw deck back up if we run out of cards
		if(this.cards.size() == 0) {
			pile.addToDeck(this);
		}
		
		return(c);
		
	}
	
	/**
	 * Draw the default 5 cards from the deck to add to hand
	 * @param hand
	 * @return Hand with 5 cards
	 */
	public void draw(Hand hand, DiscardPile pile){
		
		for(int i = 0; i < 5; i++){
			hand.addCard(this.draw(pile));
		}

	}
	
	/**
	 * Draw the number of cards given from the deck and add to hand
	 * @param hand
	 * @param toAdd
	 * @return Hand with given number of cards
	 */
	public Hand draw(Hand hand, int toAdd, DiscardPile pile){
		
		for(int i = 0; i < toAdd; i++){
			hand.addCard(this.draw(pile));
		}
		
		return hand;
	}
	
	/**
	 * Gets the size of the deck.
	 * @return the number of cards remaining in the deck
	 */
	public int size() {
		return(this.cards.size());
	}
	
	/**
	 * Accesses the database to create the main playing deck
	 * @return the main deck
	 * @throws SQLException
	 */
	
	public static Deck getMainDeck() throws SQLException{
		

		
		Deck main = new Deck();
		
		DBHelper dbh = new DBHelper();
		String query = "SELECT * FROM Cards WHERE Deck='Main'";
		java.sql.ResultSet rs = dbh.executeQuery(query);
		
		while(rs.next()){
			String name = rs.getString("Name");
			String description = rs.getString("Description");
			String imagePath = rs.getString("ImagePath");
			String cardType = rs.getString("CardType");
			int costBuy = rs.getInt("CostBuy");
			int costAttack = rs.getInt("CostAttack");
			int vp = rs.getInt("VP");
			int power = rs.getInt("Attack");
			int money = rs.getInt("Stealth");
			int preturnDiscard = rs.getInt("PreturnDiscard");
			int postturnDiscard = rs.getInt("PostturnDiscard");
			int drawCards = rs.getInt("DrawCards");
			int othersDrawCards = rs.getInt("OthersDrawCards");
			int trashCardsMandatory = rs.getInt("TrashCardsMandatory");
			int trashCardsOptional = rs.getInt("TrashCardsOptional");
			int trashForAttack = rs.getInt("TrashForAttack");
			int trashForStealth = rs.getInt("TrashForStealth");
			int removeFromPlayArea = rs.getInt("RemoveFromPlayArea");
			int othersDiscard = rs.getInt("OthersDiscard");
			int giveCurseCards = rs.getInt("OthersLoseVP");
			boolean takeAnotherTurn = rs.getBoolean("TakeAnotherTurn");
			boolean refreshPlayArea = rs.getBoolean("RefreshPlayArea");
			boolean trashAfterUse = rs.getBoolean("TrashAfterUse");
			int NumInDeck = rs.getInt("NumInDeck");
			
			Card toAdd = new Card(name, description, imagePath, cardType, costBuy, costAttack, vp, power, money, preturnDiscard, postturnDiscard,
					drawCards, othersDrawCards, trashCardsMandatory, trashCardsOptional, trashForAttack, trashForStealth, removeFromPlayArea,
					othersDiscard, giveCurseCards, takeAnotherTurn, refreshPlayArea, trashAfterUse);
			
			main.addCard(toAdd, NumInDeck);
		}
		
		return main;
	}	
	
	/**
	 * Accesses the database to create the starter deck
	 * @return the starter deck
	 * @throws SQLException
	 */
	
	public static Deck getStarterDeck() throws SQLException{
		
		Deck starter = new Deck();
		
		DBHelper dbh = new DBHelper();
		String query = "SELECT * FROM Cards WHERE Deck='Starter'";
		java.sql.ResultSet rs = dbh.executeQuery(query);
		
		while(rs.next()){
			String name = rs.getString("Name");
			String description = rs.getString("Description");
			String imagePath = rs.getString("ImagePath");
			String cardType = rs.getString("CardType");
			int costBuy = rs.getInt("CostBuy");
			int costAttack = rs.getInt("CostAttack");
			int vp = rs.getInt("VP");
			int power = rs.getInt("Attack");
			int money = rs.getInt("Stealth");
			int preturnDiscard = rs.getInt("PreturnDiscard");
			int postturnDiscard = rs.getInt("PostturnDiscard");
			int drawCards = rs.getInt("DrawCards");
			int othersDrawCards = rs.getInt("OthersDrawCards");
			int trashCardsMandatory = rs.getInt("TrashCardsMandatory");
			int trashCardsOptional = rs.getInt("TrashCardsOptional");
			int trashForAttack = rs.getInt("TrashForAttack");
			int trashForStealth = rs.getInt("TrashForStealth");
			int removeFromPlayArea = rs.getInt("RemoveFromPlayArea");
			int othersDiscard = rs.getInt("OthersDiscard");
			int giveCurseCards = rs.getInt("OthersLoseVP");
			boolean takeAnotherTurn = rs.getBoolean("TakeAnotherTurn");
			boolean refreshPlayArea = rs.getBoolean("RefreshPlayArea");
			boolean trashAfterUse = rs.getBoolean("TrashAfterUse");
			int numInDeck = rs.getInt("NumInDeck");
			
			Card toAdd = new Card(name, description, imagePath, cardType, costBuy, costAttack, vp, power, money, preturnDiscard, postturnDiscard,
					drawCards, othersDrawCards, trashCardsMandatory, trashCardsOptional, trashForAttack, trashForStealth, removeFromPlayArea,
					othersDiscard, giveCurseCards, takeAnotherTurn, refreshPlayArea, trashAfterUse);
			
			starter.addCard(toAdd, numInDeck);
		}

		return starter;
	}
	
	/**
	 * Copies a deck into a new deck
	 */

	@Override
	protected Object clone() {
		Deck newDeck = new Deck();
		
		//Copy each card from this deck into the new deck.
		for(int i = 0; i < this.cards.size(); i++) {
			newDeck.addCard(this.cards.get(i));
		}

		//Return the new deck
		return newDeck;
	}


	
}
