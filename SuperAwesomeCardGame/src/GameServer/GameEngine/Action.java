package GameServer.GameEngine;

import java.io.Serializable;

/**
 * This class attempt to communicate an action that has been chosen,
 * taken, or a result.
 * @author John
 *
 */
public class Action implements Serializable {
	
	//The min and max action numbers
	private static final int min = 0;
	private static final int max = 4;
	
	//The possible actions
	public static final int PLAY_CARD = 0;
	public static final int AQUIRE_CARD = 1;
	public static final int END_TURN = 2;
	public static final int VIEW_CENTER = 3;	//Used for text based thing
	public static final int VIEW_HAND = 4;		//Used for text based thing
	public static final int DISCARD = 5;
	public static final int TRASH = 6;
	public static final int DRAW = 7;
	
	
	private int action;
	private int cardIndex;
	private Card c;
	private String username;
	private Hand h;
	
	/**
	 * Build a simple action object.
	 * @param action the action chosen
	 */
	public Action(int action) {
		this.action = action;
		this.cardIndex = -1;
		this.c = null;
	}
	
	
	/**
	 * Build a more complex action object. This action object passes
	 * the card itself.
	 * @param action the action chosen
	 * @param c the card
	 */
	public Action(int action, Card c) {
		this.action = action;
		this.cardIndex = -1;
		this.c = c;
	}
	
	
	/**
	 * Build a more complex action object. This action object passes
	 * a reference to the card as well as the card itself.
	 * @param action the action chosen
	 * @param cardIndex the index number of the card
	 * @param c the card
	 */
	public Action(int action, int cardIndex, Card c) {
		this.action = action;
		this.cardIndex = cardIndex;
		this.c = c;
		
		
	}
	
	/**
	 * Build a more complex action object. This action object passes a reference to the card, the card itself, and a username.
	 * @param action
	 * @param cardIndex
	 * @param c
	 * @param user
	 */
	
	public Action(int action, int cardIndex, Card c, String user)
	{
		this.action = action;
		this.cardIndex = cardIndex;
		this.c = c;
		this.username = user;
		
	}
	
	/**
	 * Builds a more complex action. This one passes a reference to the card, the card itself, and a hand object.
	 * @param action
	 * @param cardIndex
	 * @param c
	 * @param h
	 */
	
	public Action(int action, int cardIndex, Card c, Hand h)
	{

		this.action = action;
		this.cardIndex = cardIndex;
		this.c = c;
		this.h = h;
	}
	
	/**
	 * Builds an action. This one passes just a hand object.
	 * @param action
	 * @param h
	 */

	public Action(int action, Hand h)
	{

		this.action = action;
		this.h = h;
	}
	
	/**
	 * Get the player that is taking the action
	 * @return player's username
	 */
	
	public String getPlayerName()
	{
		return username;
	}


	/**
	 * Get the action being taken by the player.
	 * @return the action
	 */
	public int getAction() {
		return action;
	}

	/**
	 * Get the card index number.
	 * @return card index number
	 */
	public int getCardIndex() {
		return cardIndex;
	}


	/**
	 * Get the card involved with the game.
	 * @return the card
	 */
	public Card getCard() {
		return c;
	}
	
	/**
	 * Get the hand object involved with the action
	 * @return hand object
	 */
	
	public Hand getHand()
	{
		return h;
	}
	
	
	
}
