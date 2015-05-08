package GameServer.GameEngine;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;









import javafx.application.Platform;
//import view.GameTableScreenController;
import GameServer.Users.User;

/**An object that will have a user's ID for each player in a game to 
 *hold on to current games statistics.Making a separate object instead 
 *of tagging these properties onto User prevents a lot of unnecessary null
 *data floating around on user objects when a user is not in a game.
**/

public class Player implements Client {
	
	//private GameTableScreenController client;
	private User user;
	private boolean isTurn;
	private Hand hand;
	private DiscardPile discard;
	private Deck deck;
	private int stealth;
	private int attack;
	private int VP;
	private boolean extraTurn = false;
	public boolean isExtraTurn() {
		return extraTurn;
	}


	public void setExtraTurn(boolean extraTurn) {
		this.extraTurn = extraTurn;
	}


	private Client client;
	
	/**
	 * @param user The user who is the player.
	 * @throws SQLException 
	 */
	
	public Player(User user) throws SQLException {
		
		//Initliaize given values
		this.user = user;
		this.isTurn = false;
		
		//Initialize discard pile to an empty DiscardPile
		DiscardPile discardPile = new DiscardPile();
		this.discard = discardPile;
		
		//Initiliaze deck to the starter deck
		this.deck = new Deck();
		this.deck = Deck.getStarterDeck();

		//Initialize hand
		this.hand = new Hand(5);
		this.deck.draw(this.hand, this.discard);
		
		//Initialize int values to 0
		this.attack = 0;
		this.stealth = 0;
		this.VP = 1000;
		
	}
	
	
	/**
	 * Create a player.
	 * @param user The user who is the player.
	 * @param starter The starter deck the player is using.
	 */
	public Player(User user, Deck starter) {
		
		//Initliaize given values
		this.user = user;
		this.isTurn = false;
		
		//Initialize discard pile to an empty DiscardPile
		DiscardPile discardPile = new DiscardPile();
		this.discard = discardPile;
		
		//Initiliaze deck to the starter deck
		this.deck = (Deck)starter.clone();

		//Initialize hand
		this.hand = new Hand(5);
		this.deck.draw(this.hand, this.discard);
		
		//Initialize int values to 0
		this.attack = 0;
		this.stealth = 0;
		this.VP = 1000;
		
	}
	
	
	/**
	 * Create a player.
	 * @param user The user who is the player.
	 * @param starter The starter deck the player is using.
	 * @param clientRegistryName the Java RMI registry entry for the game
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	public Player(User user, Deck starter, String clientRegistryName) throws MalformedURLException, RemoteException, NotBoundException {
		
		//Initliaize given values
		this.user = user;
		this.isTurn = false;
		
		//Initialize discard pile to an empty DiscardPile
		DiscardPile discardPile = new DiscardPile();
		this.discard = discardPile;
		
		//Initiliaze deck to the starter deck
		this.deck = (Deck)starter.clone();

		//Initialize hand
		this.hand = new Hand(5);
		this.deck.draw(this.hand, this.discard);
		
		//Initialize int values to 0
		this.attack = 0;
		this.stealth = 0;
		this.VP = 1000;
		System.out.println(clientRegistryName);
		this.client = (Client) Naming.lookup(clientRegistryName);
		
	}
	
	/**
	 * Called after a player ends their turn. It initializes stealth and attack back to 0, discards the cards in hand 
	 * to the discard pile, and draws five new cards.
	 */
	
	public void resetPlayer(){
		stealth = 0;
		attack = 0;
		this.extraTurn = false;
		discard.discard(this.hand);
		
		while(hand.size() < 5){
			if(this.deck.size() == 0){
				this.discard.addToDeck(this.deck);
			}
			this.deck.draw(this.hand, this.discard);
		}
		
	}
	
	
	/**
	 * Returns the user's ID
	 * @return The user's ID
	 */
	
	public User getUser() {
		return user;
	}
	
	
	/**
	 * Returns the Hand object of the player
	 * @return Player's hand
	 */
	
	public Hand getHand() {
		return hand;
	}
	
	/**
	 * Returns the DiscardPile object of the player
	 * @return Player's discard pile
	 */
	
	public DiscardPile getDiscardPile() {
		return discard;
	}
	
	/**
	 * Returns the Deck object of the Player
	 * @return Player's deck
	 */
	
	public Deck getDeck() {
		return deck;
	}
	
	/**
	 * Gets the player's current available stealth
	 * @return Player's stealth
	 */
	public int getStealth() {
		return stealth;
	}
	
	/**
	 * Gets the player's current available attack
	 * @return Player's attack
	 */
	public int getAttack(){
		return attack;
	}
	
	/**
	 * Gets the player's current Victory Points
	 * @return Player's Victory Points
	 */
	public int getVP(){
		return VP;
	}
	
	/**
	 * Add stealth to player's total stealth
	 * @param toAdd
	 */
	public void addStealth(int stealth) {
		this.stealth += stealth;
	}
	
	/**
	 * Add attack to the player's total attack
	 * @param toAdd
	 */
	public void addAttack(int attack) {
		this.attack += attack;
	}
	
	/**
	 * Add Victory Points to the player's total VPs
	 * @param toAdd
	 */
	public void addVP(int VP) {
		this.VP += VP;
	}
	
	/**
	 * Set true is it's the player's turn, false otherwise
	 * @param isTurn
	 */
	
	public void isTurn(boolean isTurn){
		this.isTurn = isTurn;
	}
	
	/**
	 * Returns true if it's the player's turn, false otherwise
	 * @return isTurn
	 */
	public boolean getIsTurn(){
		return isTurn;
	}


	@Override
	public void determineAction(Action a) {
		
		if(this.client == null) throw new IllegalStateException();
		
		try {
			this.client.determineAction(a);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void setGameEngine(String ge) throws RemoteException {
		if(this.client == null) throw new IllegalStateException();
		this.client.setGameEngine(ge);		
	}


	@Override
	public void initializeTable(Hand playerHand, Hand gameTableHand,
			String[] playerNames) {
		if(this.client == null) throw new IllegalStateException();
		System.out.println("initialize game table: Player " + this.user.getUsername());
		try {
			this.client.initializeTable(playerHand, gameTableHand, playerNames);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void startTurn() throws RemoteException {
		if(this.client == null) throw new IllegalStateException();
		System.out.println("initialize game table: Player " + this.user.getUsername());
		try {
			this.client.startTurn();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setOtherPlayerTurn(String player){
		if(this.client == null) throw new IllegalStateException();
		try {
			this.client.setOtherPlayerTurn(player);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	};
	
	public void updatePlayerStats(int st, int at, int vp, int numDeck, String players[]){
		if(this.client == null) throw new IllegalStateException();
		try {
			this.client.updatePlayerStats(st, at, vp, numDeck, players);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	};
	
	public void updatePlayerStats(String players[]){
		if(this.client == null) throw new IllegalStateException();
		System.out.println("Update Player VP: " + this.getVP());
		int numDeck = this.getDeck().size() + this.getDiscardPile().size() + this.getHand().size();
		this.updatePlayerStats(this.getStealth(), this.getAttack(), this.getVP(), numDeck, players);
	};
	
	public void updateOtherPlayersStats(int vp, String players[], String username){
		if(this.client == null) throw new IllegalStateException();
		try {
			this.client.updateOtherPlayersStats(vp, players,  username);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	};
	
	public void setPlayerHand(){
		if(this.client == null) throw new IllegalStateException();
		try {
			this.client.setPlayerHand(this.hand);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void setPlayerHand(Hand hand) throws RemoteException {
		if(this.client == null) throw new IllegalStateException();
		try {
			this.client.setPlayerHand(hand);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void drawCards(Action a) throws RemoteException {
		this.client.drawCards(a);		
	};
	
	/**
	 * Passes the draw cards perogative to the client.
	 * @throws RemoteException
	 */
	public void drawCards() throws RemoteException {
		this.client.drawCards(new Action(Action.DRAW, this.hand));		
	}


	@Override
	public void discardCard(Action a) throws RemoteException {
		this.client.discardCard(a);
	}


	@Override
	public void setNewTableCards(Hand hand) throws RemoteException {
		this.client.setNewTableCards(hand);
	}


	@Override
	public void endGame(int[] vp, int[] cardsInDeck, String[] playerNames)
			throws RemoteException {
		this.client.endGame(vp, cardsInDeck, playerNames);
	}


	@Override
	public void checkOnline() throws RemoteException {
		this.client.checkOnline();
	}


	@Override
	public void acquireCard(Action a) throws RemoteException {
		this.client.acquireCard(a);		
	}


	@Override
	public void playCard(Action a) throws RemoteException {
		this.client.playCard(a);			
	}


	@Override
	public void trashCard(Action a) throws RemoteException {
		this.client.trashCard(a);		
	};
	
	
	
}
