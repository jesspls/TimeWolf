package GameServer.GameEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import GameServer.Users.User;


public class GameEngine extends UnicastRemoteObject implements Runnable, GameEngineRemote {
	
	
	private ArrayList<Player> players;
	private int totalNumOfPlayers;
	private int currentNumOfPlayers;
	private int currentPlayerIndex = 0;
	private int id = 0;
	private boolean chatEnabled;
	
	private String rmiRegistryName;
	private String name;
	private boolean isRunning = false;
	private boolean isFinished = false;
	
	
	private Deck startingDeck;
	
	private Deck mainDeck;
	private DiscardPile mainDiscard = new DiscardPile();
	private Hand mainPlayAreaCards = new Hand(5);
	private static final Card defaultAttack = new Card("Not So Important Historical Figure", "You think you may have read about this guy once.", "cards/notSoImportantHistoricalFigure.png", "Action", 0, 2, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false);
	private static final Card defaultBuyStealth = new Card("Prowl", "Gain 2 stealth when played.", "cards/prowl.png", "Action", 3, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false);;
	private static final Card defaultBuyAttack = new Card("Bite", "Gain 2 attack when played.", "cards/bite.png", "Action", 3, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false);;
	
	


	public GameEngine(int numOfPlayers, String name, Deck startingDeck, Deck mainDeck, int id) throws RemoteException {

		
		//Create the array of players and initialize info about the number of players.
		this.totalNumOfPlayers = numOfPlayers;
		this.currentNumOfPlayers = 0;
		this.players = new ArrayList<Player>();
		this.id = id;
		
		this.mainDeck = (Deck) mainDeck.clone();
		this.startingDeck = startingDeck;
		this.name = name;
		
		//Initialize the main play area cards.
		for(int i = 0; i < 5; i++) this.mainPlayAreaCards.addCard(this.mainDeck.draw(this.mainDiscard));
		
		
	}
	
	public boolean getChatEnabled()
	{
		return chatEnabled;
	}
	
	public void setChatEnabled(boolean enabled)
	{
		chatEnabled = enabled;
	}
	
	public int getID(){
		return id;
	}
	
	/**
	 * Add player "p" to the game.
	 * @param p the player to add.
	 * @return True if the player has been added successfully.
	 * @throws SQLException 
	 */
	public boolean addPlayer(User u, String clientRegistryName) {
		System.out.println("GameENgine: Add Player: " + u.getEmail() + " Reg: " + clientRegistryName);
		
		//We can only have so many players in a game, and we can't add null players
		if(this.currentNumOfPlayers == this.totalNumOfPlayers || u == null) return false;
		
		//Create the player object using the user
		//Player p = new Player(u.getID(), false, 0, 0, new Hand(5), new DiscardPile(), (Deck)this.startingDeck.clone());
		Player p = null;
		try {
			p = new Player(u, (Deck)this.startingDeck.clone(), clientRegistryName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//Tell the game client how to talk to me
		try {
			p.setGameEngine(this.rmiRegistryName);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Add the player if there is room.
		this.players.add(p);
		this.currentNumOfPlayers++;
		
		String[] players = new String[this.players.size()];
		int counter = 0;
		for (Player player : this.players) {
			players[counter] = player.getUser().getUsername();
			counter++;
		}
		
		for (Player player : this.players) {
			player.initializeTable(player.getHand(), this.mainPlayAreaCards, players);
		}
		
		return true;
	}
	
	
	/**
	 * Start the game.
	 * @return true if the game thread started succesffully.
	 */
	public boolean start() {
		//Must not have already started and the game table must be full
		if(this.hasStarted() || !this.isFull()) return false;
		
		//Start the thread
		Thread t = new Thread(this);
		t.start();
		
		if(!t.isAlive()) return false;
		
		//This is set here and in the thread. Since with threading either can 
		//jump to running the new thread or the original thread
		this.isRunning = true; //Mark it as running
		
		return false;
	}

	
	@Override
	public String getName() {
		return name;
	}


	/**
	 * Get the total number of players allowed to play
	 * @return total number of players
	 */
	public int getTotalNumOfPlayers() {
		return totalNumOfPlayers;
	}

	/**
	 * Get the current number of players in the game.
	 * @return the current number of players
	 */
	public int getCurrentNumOfPlayers() {
		return currentNumOfPlayers;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	
	/* (non-Javadoc)
	 * @see GameServer.GameEngine.GameEngineRemote#isFull()
	 */
	@Override
	public boolean isFull() {
		return this.currentNumOfPlayers == this.totalNumOfPlayers;
	}
	
	
	/* (non-Javadoc)
	 * @see GameServer.GameEngine.GameEngineRemote#isRunning()
	 */
	@Override
	public boolean isRunning() {
		return isRunning;
	}

	/* (non-Javadoc)
	 * @see GameServer.GameEngine.GameEngineRemote#isFinished()
	 */
	@Override
	public boolean isFinished() {
		return isFinished;
	}

	/* (non-Javadoc)
	 * @see GameServer.GameEngine.GameEngineRemote#hasStarted()
	 */
	@Override
	public boolean hasStarted() {
		return isRunning || isFinished;
	}

	@Override
	public boolean playCard(Action a) {
		this.inProgressAction = a;
		Card c = a.getCard();
		Player p = this.players.get(this.currentPlayerIndex);
		
		System.out.println("Playing Card: " + c.getName() );
		this.ruleDiscard(p, c, true, a);
		
		
				
		return true;

	}
	
	private boolean playCardPt2(Action a) {
		Card c = a.getCard();
		Player p = this.players.get(this.currentPlayerIndex);
		
		this.ruleTrash(p, c, a);
		return true;
	
	}
	
	private boolean playCardPt3(Action a) {
		Card c = a.getCard();
		Player p = this.players.get(this.currentPlayerIndex);
		
		this.ruleAttack(p, c);
		this.ruleStealth(p, c);
		this.ruleExtraTurn(p, c);
		
		this.ruleDrawCards(p, c);
		this.ruleLoseVP(p, c);
		//this.ruleTakeAnotherTurn(p, c);
		this.ruleDiscard(p, c, false, a);
		
		return true;
	}
	
	private boolean playCardPt4(Action a) {
		
		Card c = a.getCard();
		Player p = this.players.get(this.currentPlayerIndex);
		
		//When we find the card played, discard it.
		for(int i = 0; i < p.getHand().size(); i++) {
			if(p.getHand().get(i).getName().equals(a.getCard().getName())) {
				if(!c.isTrashAfterUse()) p.getDiscardPile().discard(c);
				p.getHand().remove(i);
				break;
			}
		}
		
		
		//p.setPlayerHand();
		
		System.out.println("Player: " + p.getUser().getUsername() + 
				" Attack: " + p.getAttack() + " Stealth: " + p.getStealth());
		
		//Update player stats for everyone.
		String[] playerList = new String[this.players.size()];
		for(int i = 0; i < this.players.size(); i++) {
				playerList[i] = this.players.get(i).getUser().getUsername();
		}
		
		p.updatePlayerStats(playerList);
		
		for(int i = 0; i < this.players.size(); i++) {
			if(this.currentPlayerIndex != i) {
				this.players.get(i).updateOtherPlayersStats(p.getVP(), playerList, p.getUser().getUsername());
			}
		}
		
		
		Action forUpdate = new Action(0, 0, c, p.getUser().getUsername());
		
		for(Player toUpdate: this.players) {
			if(p != toUpdate) {
				try {
					toUpdate.playCard(forUpdate);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
		return true;
	}
	
	private void ruleLoseVP(Player current, Card c) {
		if(c.getOthersLoseVP() == 0) return;
		
		
		
		for(int i = 0; i < this.players.size(); i++) {
			if(this.currentPlayerIndex != i) {
				this.players.get(i).addVP(c.getOthersLoseVP() * -1);
			}
		}
		
		//Update player stats for everyone.
		String[] playerList = new String[this.players.size()];
		for(int i = 0; i < this.players.size(); i++) {
				boolean wormhole = false;
				
				for(Card cardFromHand : this.players.get(i).getHand()) {
					if(cardFromHand.getName().equals("Wormhole")) {
						wormhole = true;
					}
				}
				if(!wormhole) {
					playerList[i] = this.players.get(i).getUser().getUsername();
				}
		}
		
		current.updatePlayerStats(playerList);
		
		for(int i = 0; i < this.players.size(); i++) {
			if(this.currentPlayerIndex != i) {
				this.players.get(i).updateOtherPlayersStats(current.getVP(), playerList, current.getUser().getUsername());
			}
		}
		
		//c.getOthersLoseVP()
	}
	
	/**
	 * Parse the card for its stealth value.
	 * @param current the current player
	 * @param c the card
	 */
	private void  ruleStealth(Player current, Card c) {
		current.addStealth(c.getStealth());
	}
	
	/**
	 * Parse the card for its attack value.
	 * @param current the current player
	 * @param c the card
	 */
	private void ruleAttack(Player current, Card c) {
		current.addAttack(c.getAttack());
	}
	
	private void ruleExtraTurn(Player current, Card c) {
		if(c.isTakeAnotherTurn()) {
			System.out.println("Taking extra turn.");
			current.setExtraTurn(true);
		} else {
			System.out.println("Not taking extra turn.");
		}
		
	}
	
	private boolean isDiscardingPre = false;
	private boolean isDiscardingPost = false;
	private int discardCount;
	private int discardMax;
	private Action inProgressAction;

	
	public void discardCard(Action a) {
		if(a == null || a.getCard() == null) throw new NullPointerException();
		if(!this.isDiscardingPre && !this.isDiscardingPost) throw new IllegalStateException();
		
		System.out.println("Discarding on server.");
		
		Player p = this.players.get(this.currentPlayerIndex);
		boolean hasDiscarded = false;
		
		for(int i = 0; i < p.getHand().size(); i++) {
			if(p.getHand().get(i).getName().equals(a.getCard().getName())) {
				p.getDiscardPile().discard(p.getHand().get(i));
				p.getHand().remove(i);
				hasDiscarded = true;
				System.out.println("Found card for discard in hand.");
				break; //exit early so we don't discard a second copy of the same card
			}
		}
		
		//If they pass a card that isn't in the hand is invalid. 
		//Otherwise we've discarded one more card.
		if(hasDiscarded)  {
			this.discardCount++;
		} else {
			throw new IllegalStateException();
		}
		
		
		if(this.discardCount == this.discardMax) {
			if(this.isDiscardingPre) {
				this.playCardPt2(this.inProgressAction);
			} else if(this.isDiscardingPost) {
				this.playCardPt4(this.inProgressAction);
			}
		}
		
		
	}
	
	
	
	private void ruleDiscard(Player current, Card c, boolean isBefore, Action a) {
		
		int numOfCards;
		
		//We can either discard before or after
		if(isBefore) {
			numOfCards = c.getPreturnDiscard();
		} else {
			numOfCards = c.getPostturnDiscard();
		}
		
		
		//If there aren't enough cards left for a full discard.
		if(numOfCards > current.getHand().size()) {
			numOfCards = current.getHand().size();
		}
		
		if(numOfCards > 0) {
			this.discardMax = numOfCards;
			this.discardCount = 0;
			this.isDiscardingPre = true;
			this.inProgressAction = a;
			
				
			try {
				System.out.println("Calling client for discard");
				current.discardCard(new Action(Action.DISCARD, c));
				System.out.println("Calling client for discard");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} else {
			if(isBefore) {
				this.playCardPt2(this.inProgressAction);
			} else {
				this.playCardPt4(this.inProgressAction);
			}
			
		}
		
	}
	
	
private boolean isTrashForStealth = false;
private boolean isTrashForAttack = false;
private boolean isTrashing = false;
private int trashMax = 0;
private int trashCount = 0;

public void trashCard(Action a) {
	if(a == null || a.getCard() == null) throw new NullPointerException();
	if(!this.isTrashing) throw new IllegalStateException();
	
	System.out.println("Trashing on server.");
	
	Player p = this.players.get(this.currentPlayerIndex);
	boolean hasDiscarded = false;
	
	for(int i = 0; i < p.getHand().size(); i++) {
		if(p.getHand().get(i).getName().equals(a.getCard().getName())) {
			p.getHand().remove(i);
			hasDiscarded = true;
			System.out.println("Found card for trash in hand.");
			break; //exit early so we don't discard a second copy of the same card
		}
	}
	
	//If they pass a card that isn't in the hand is invalid. 
	//Otherwise we've discarded one more card.
	if(hasDiscarded)  {
		this.trashCount++;
		
		//If this is a trash for attack or trash for stealth then we add the purchase
		//value of the card to their attack or stealth.
		if(this.isTrashForAttack) {
			p.addAttack(a.getCard().getCostStealth());
		} else if(this.isTrashForStealth) {
			p.addStealth(a.getCard().getCostStealth());
		}
		
	} else {
		throw new IllegalStateException();
	}
	
	
	if(this.trashCount == this.trashMax) {
			this.isTrashing = false;
			this.playCardPt3(this.inProgressAction);
	}
	
	
}
	
private void ruleTrash(Player current, Card c, Action a) {
		
		
		this.trashCount = 0;
		this.trashMax = 0;
		
		if(c.getTrashForAttack() > 0) {
			this.isTrashForStealth = false;
			this.isTrashForAttack = true;
			this.trashMax = c.getTrashForAttack();
		} else if(c.getTrashForStealth() > 0) {
			this.isTrashForStealth = true;
			this.isTrashForAttack = false;
			this.trashMax = c.getTrashForStealth();
		} else if(c.getTrashCardsMandatory() > 0) {
			this.isTrashForStealth = false;
			this.isTrashForAttack = false;
			this.trashMax = c.getTrashCardsMandatory();
		}
		
		//If there aren't enough cards left for a full discard.
		if(this.trashMax > current.getHand().size()) {
			this.trashMax = current.getHand().size();
		}
		
		if(this.trashMax > 0) {
			this.inProgressAction = a;
			this.isTrashing = true;
				
			try {
				System.out.println("Calling client for Trash - before - Trash Max: " + this.trashMax);
				current.trashCard(new Action(Action.TRASH, c));
				System.out.println("Calling client for Trash - after");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} else {
			this.playCardPt3(a);
		}
		
	}
	
	
	/**
	 * Parse the card for its draw card value
	 * @param current the current player
	 * @param c the card
	 */
	private void ruleDrawCards(Player current, Card c) {
		
		Hand newCards = new Hand(0);
		
		for(int i = 0; i < c.getDrawCards(); i++) {
			Card temp = current.getDeck().draw(current.getDiscardPile());
			newCards.addCard(temp);
			current.getDeck().addCard(temp);
		}
		current.getDeck().draw(current.getHand(), c.getDrawCards(), current.getDiscardPile());
		
		try {
			current.drawCards(new Action(Action.DRAW, newCards));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void ruleTakeAnotherTurn(Player current, Card c) {
		
	}
	
	public static void main(String[] args) {

		Card c;
		Deck playerDeck;
		Deck maingame;
		try {
			playerDeck = Deck.getStarterDeck(); //The deck the player starts with
			maingame = Deck.getMainDeck(); //The deck for the center of the game.
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}  
		
		
		//  open up standard input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input; //place to put input from command line.

		
		//Starting the game engine
		
		int vp = 0;
		int stealth = 0;
		int attack = 0;
		int turnNum = 0;
		Hand playArea = new Hand(4);
		Hand playerHand = new Hand(5);
		DiscardPile discard = new DiscardPile();
		
		playerHand.addCard(playerDeck.draw(discard));
		playerHand.addCard(playerDeck.draw(discard));
		playerHand.addCard(playerDeck.draw(discard));
		playerHand.addCard(playerDeck.draw(discard));
		playerHand.addCard(playerDeck.draw(discard));
		
		
		//Initialize the first four cards in main game area
		playArea.addCard(maingame.draw(null));
		playArea.addCard(maingame.draw(null));
		playArea.addCard(maingame.draw(null));
		playArea.addCard(maingame.draw(null));
		
		
		System.out.println("Welcome to the Super Awsome Card Game Demo v1.0!!!!!!");
		System.out.println();
		System.out.println("How fast can you get to 200 years?");
		System.out.println();
		
		while(vp < 200) {
			turnNum++;
			System.out.println("Start Turn " + turnNum);
			
			while(true) {
				System.out.println("VP: " + vp + "\tStealth: " + stealth + "\tAttack: " + attack);
				System.out.println("Hand Size: " + playerHand.size() + "\tDeck Size: " + playerDeck.size() + "\tDiscard Size: " + discard.size());
				System.out.println("Choose:");
				System.out.println("\t(1) Play Card From Hand");
				System.out.println("\t(2) Purchase or Attack Card from Center.");
				System.out.println("\t(3) View Hand.");
				System.out.println("\t(4) View Cards in Center.");
				System.out.println("\t(5) End Turn");
				System.out.println("Enter Choice: ");
				
				try {
					input = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
				
				if(input.trim().equals("1")) { //play acard from hand.
					GameEngine.printHand(playerHand);
					System.out.println("Play card #: ");
					try {
						input = br.readLine();
					} catch (IOException e) {
						e.printStackTrace();
						continue;
					}
					
					c = playerHand.get(Integer.parseInt(input) -1);
					playerHand.remove(Integer.parseInt(input) - 1);
					
					if(c.getDrawCards() > 0) {
						//Draw X cards
						for(int i = 0; i < c.getDrawCards(); i++) {
							
							playerHand.addCard(playerDeck.draw(null));
							
							//If necessary reshuffle the discard pile into the deck
							if(playerDeck.size() == 0) {
								discard.addToDeck(playerDeck);
							}
							
						}
						
					}
					
					attack += c.getAttack();
					stealth += c.getStealth();
					
					discard.discard(c);
					
				} else if(input.trim().equals("2")) {
					GameEngine.printCenter(playArea);
					
					System.out.println("Buy or Attack Card #: ");
					try {
						input = br.readLine();
					} catch (IOException e) {
						e.printStackTrace();
						continue;
					}
					
					c = playArea.get(Integer.parseInt(input) -1);
					
					if(c.getCostAttack() <= attack && c.getCostStealth() <= stealth) {
						if(c.getCostAttack() > 0) {
							vp += c.getVp(); //otherwise we get vp
						} else {
							
							//If it isn't a card we attack, then add it to discard
							discard.discard(c);
						}
						
						stealth -= c.getCostStealth();
						attack -= c.getCostAttack();
						
						playArea.remove(Integer.parseInt(input) - 1);
						playArea.addCard(maingame.draw(null));
					} else {
						System.out.println("Sorry, but you cannot afford to buy or attack this card.");
					}
					
					
					
				} else if(input.trim().equals("3")) {
					GameEngine.printHand(playerHand);
				} else if(input.trim().equals("4")) {
					GameEngine.printCenter(playArea);
				} else if(input.trim().equals("5")) {
					
					
					//End of turn so discard hand and draw a new one.
					for(int i =  playerHand.size() -1; i >= 0; i--) {
						discard.discard(playerHand.get(i));
						playerHand.remove(i);
					}
					
					//Draw 5 cards
					for(int i = 0; i < 5; i++) {
						
						playerHand.addCard(playerDeck.draw(null));
						
						//If necessary reshuffle the discard pile into the deck
						if(playerDeck.size() == 0) {
							discard.addToDeck(playerDeck);
							
							System.out.println("Reshuffling deck. New deck size: " + playerDeck.size());
						}
						
					}
					
					//Reset the available currency to zero
					stealth = 0;
					attack = 0;									
					
					break;
				}
				
				
				
			}
			
		}
		
		System.out.println("Congratulations on winning!!!!");
		
	}
	
	private static void printHand(Hand h) {
		System.out.println("In your hand:");
		
		for(int i = 0; i < h.size(); i++) {
			System.out.println("(" + (i+1) + ") " + h.get(i).getName() + ": " + h.get(i).getDescription());
		}
		
	}
	
	private static void printCenter(Hand h) {
		
		System.out.println("In the center area:");
		
		for(int i = 0; i < h.size(); i++) {
			System.out.println("(" + (i+1) + ") " + h.get(i).getName() + ": " + h.get(i).getDescription());
			if(h.get(i).getCostAttack() > 0) System.out.println("\t\tCosts Attack: " + h.get(i).getCostAttack());
			if(h.get(i).getCostStealth() > 0) System.out.println("\t\tCosts Stealth: " + h.get(i).getCostStealth());
		}
		
	}


	/**
	 * Prompt the user for input on what they want to do.
	 * @return the action to take
	 */
	private Action promptUserForAction() {
		
		Action chosen;
		
		//  open up standard input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input; //place to put input from command line.
		
		Player p = this.players.get(this.currentPlayerIndex);
		
		System.out.println("\n\n\n");
		System.out.println("Player: " + p.getUser().getUsername());
		System.out.println("VP: " + p.getVP() + "\tStealth: " + p.getStealth() + "\tAttack: " + p.getAttack());
		System.out.println("Hand Size: " + p.getHand().size() + "\tDeck Size: " + p.getDeck().size() + "\tDiscard Size: " + p.getDiscardPile().size());
		System.out.println("Choose:");
		System.out.println("\t(1) Play Card From Hand");
		System.out.println("\t(2) Purchase or Attack Card from Center.");
		System.out.println("\t(3) View Hand.");
		System.out.println("\t(4) View Cards in Center.");
		System.out.println("\t(5) End Turn");
		System.out.println("Enter Choice: ");
		
		try {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return this.promptUserForAction();
		}
		
		if(input.equals("1")) {
			
			GameEngine.printHand(p.getHand());
			
			System.out.println("Play card #: ");
			try {
				input = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				input = "1";
			}
			
			int cardIndex = Integer.parseInt(input) -1;
			
			chosen = new Action(Action.PLAY_CARD, cardIndex, p.getHand().get(cardIndex));
			
		} else if(input.equals("2")) {
			
			GameEngine.printHand(this.mainPlayAreaCards);
			System.out.println("(A) Not So Important Historical Figure: You think you may have read about this guy once.");
			System.out.println("(B) Prowl: Gain 2 stealth when played.");
			System.out.println("(C) Bite: Gain 2 attack when played.");
			
			/*
			 * private static final Card defaultAttack = new Card("Not So Important Historical Figure", "You think you may have read about this guy once.", "cards/notSoImportantHistoricalFigure.png", "Action", 0, 2, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false);
	private static final Card defaultBuyStealth = new Card("Prowl", "Gain 2 stealth when played.", "cards/prowl.png", "Action", 3, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false);;
	private static final Card defaultBuyAttack = new Card("Bite", "Gain 2 attack when played.", "cards/bite.png", "Action", 3, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, false, false);;
	
			 */
			System.out.println("Atack/Buy card #: ");
			try {
				input = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				input = "1";
			}
			
			if(input.trim().equals("A")) {
				chosen = new Action(Action.AQUIRE_CARD, -1, this.defaultAttack); 
			} else if(input.trim().equals("B")) {
				chosen = new Action(Action.AQUIRE_CARD, -1, this.defaultBuyStealth);
				
			} else if(input.trim().equals("C")) {
				chosen = new Action(Action.AQUIRE_CARD, -1, this.defaultBuyAttack);
			} else {
				int cardIndex = Integer.parseInt(input) - 1;
				
				
				
				chosen = new Action(Action.AQUIRE_CARD, cardIndex, this.mainPlayAreaCards.get(cardIndex));
				
				
			}
			
			
		} else if(input.equals("3")) {
			GameEngine.printHand(p.getHand());
			chosen = this.promptUserForAction();
		} else if(input.equals("4")) {
			GameEngine.printHand(this.mainPlayAreaCards);
			chosen = this.promptUserForAction();
		} else if(input.equals("5")) {
			chosen = new Action(Action.END_TURN);			
		} else {
			chosen = this.promptUserForAction();
		}
		
		return chosen;
	}
	
	
	@Override
	public boolean aquireCard(Action a) {
		
		Card c = a.getCard();
		Player p = this.players.get(this.currentPlayerIndex);
		
		
		//TODO: When the tagging for historical figures vs action cards gets done, fix this code
		if(c.getCostAttack() == 0) { //Aquire an action card
			
			//Can't aquire cards we can't afford
			if(c.getCostStealth() > p.getStealth()) return false;
			
			p.addStealth(-1 * c.getCostStealth());
			
			p.getDiscardPile().discard(c);
			
			
			
		} else { //Aquire an historical figure
			
			//Can't aquire cards we can't afford
			if(c.getCostAttack() > p.getAttack()) return false;
			
			
			p.addAttack(-1 * c.getCostAttack());
			
			p.addVP(c.getVp());
			
			
			
		}
		
		//Discard a card from the center cards if it isn't one of the ever present cards
		//chosen
		if(!a.getCard().getName().equals("Bite") && 
				!a.getCard().getName().equals("Not So Important Historical Figure") &&
				!a.getCard().getName().equals("Lurk") ) {
			for(int i = 0; i < this.mainPlayAreaCards.size(); i ++) {
				if(a.getCard().getName().equals(this.mainPlayAreaCards.get(i).getName())) {
					if(true) {
						this.mainPlayAreaCards.remove(i);
						this.mainPlayAreaCards.addCard(this.mainDeck.draw(this.mainDiscard));
					}/* else { //For debugging cards.
						this.mainPlayAreaCards.remove(i);
						Card drew = this.mainDeck.draw(this.mainDiscard);
						while(!drew.getName().substring(0, 4).equals("Quan") && 
								!drew.getName().substring(0, 4).equals("Para")) {
							drew = this.mainDeck.draw(this.mainDiscard);
						}
						this.mainPlayAreaCards.addCard(drew);
					}*/
					break;
				}
			}
			
			
		}
		
		Action forUpdate = new Action(0, 0, c, p.getUser().getUsername());
		
		for(Player toUpdate: this.players) {
			if(p != toUpdate) {
				try {
					toUpdate.acquireCard(forUpdate);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//Update player stats for everyone.
		String[] playerList = new String[this.players.size()];
		for(int i = 0; i < this.players.size(); i++) {
				playerList[i] = this.players.get(i).getUser().getUsername();
		}
		
		p.updatePlayerStats(playerList);
		
		for(int i = 0; i < this.players.size(); i++) {
			if(this.currentPlayerIndex != i) {
				
				this.players.get(i).updateOtherPlayersStats(p.getVP(), playerList, p.getUser().getUsername());
				
			}
			
			try {
				this.players.get(i).setNewTableCards(this.mainPlayAreaCards);
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		//If the game is done.
		if(p.getVP() >= 2000) {
			
			Player[] endGame = new Player[this.players.size()];
			for(int i = 0; i < this.players.size(); i++) {
				endGame[i] = this.players.get(i);
			}
			
			Player temp;
			
			//Do a stupid sort to sort players by ranking
			for(int i = 0; i < endGame.length - 1; i++) {
				for(int j = endGame.length - 1; j != i; j--) {
					if(endGame[j].getVP() > endGame[j - 1].getVP()) {
						temp = endGame[j-1];
						endGame[j-1] = endGame[j];
						endGame[j] = temp;
					}
				}
			}
			
			int vp[] = new int[endGame.length];
			int cardsInDeck[] = new int[endGame.length];
			String playerNames[] = new String[endGame.length];
			
			for(int i = 0; i < endGame.length; i++) {
				vp[i] = endGame[i].getVP();
				cardsInDeck[i] = endGame[i].getDeck().size() + endGame[i].getHand().size() + endGame[i].getDiscardPile().size();
				playerNames[i] = endGame[i].getUser().getUsername();
			}
			
			for(Player ending : this.players) {
				try {
					ending.endGame(vp, cardsInDeck, playerNames);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			this.isFinished = true;
		}
		
		
		return true;
	}

	
		
	
	
	@Override
	public void run() {
		// TODO The main loop of logic goes here for the game engine.
		System.out.println("Game engine main.");
		Player currentPlayer;
		
		
		
		//This is set here and in the start() function. Since with threading processing
		//can jump to running the new thread or the original thread
		this.isRunning = true; 
		
		
		int endingVP = 3000;
		
		// TODO Initialize the state of the GameEngine
		
		
		// TODO Initialize the state of the game on the clients
		
		/*
		// TODO Main Game Loop
		while(true) {
			*/
			//set the current player
			currentPlayer = this.players.get(this.currentPlayerIndex);
			
			String[] playerList = new String[this.players.size()];
			for(int i = 0; i < this.players.size(); i++) {
					playerList[i] = this.players.get(i).getUser().getUsername();
			}
			
			
			for(int i = 0; i < this.players.size(); i++) {
				this.players.get(i).updatePlayerStats(playerList);
			}
			
			
			try {
				currentPlayer.startTurn();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i =0; i < this.players.size(); i++) {
				if(i != this.currentPlayerIndex) {
					this.players.get(i).setOtherPlayerTurn(currentPlayer.getUser().getUsername());
				}
			}
			
			//Stay true during turn
			/*
			while(true) {
				
				Action action = this.promptUserForAction();
				
				if(action.getAction() == Action.END_TURN) {
					break;
				} else if (action.getAction() == Action.PLAY_CARD) {
					this.playCard(action);
				} else if (action.getAction() == Action.AQUIRE_CARD) {
					this.aquireCard(action);
				}
				
			}
			*/
			
			
			
			/*
			 * Take care of end of turn information. This includes updates to 
			 * who the current player is, and resetting the player to the 
			 * the intial correct state.
			 */
			
			/*
			this.currentPlayerIndex++; //Choose the next player to take a turn
			if(this.currentPlayerIndex == this.totalNumOfPlayers) {
				this.currentPlayerIndex = 0;
			}
			*/
			
			
			//Now that we're done with the turn, reset the player
			//currentPlayer.resetPlayer();
			
			// TODO Update the current player's display and the other player's display.
			
			
		//}
		
		while(!isFinished)
		{
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(Player p : players)
			{
				try {
					p.checkOnline();
				} catch (RemoteException e) {
					Player[] endGame = new Player[this.players.size()];
					for(int i = 0; i < this.players.size(); i++) {
						endGame[i] = this.players.get(i);
					}
					
					Player temp;
					
					//Do a stupid sort to sort players by ranking
					for(int i = 0; i < endGame.length - 1; i++) {
						for(int j = endGame.length - 1; j != i; j--) {
							if(endGame[j].getVP() > endGame[j - 1].getVP()) {
								temp = endGame[j-1];
								endGame[j-1] = endGame[j];
								endGame[j] = temp;
							}
						}
					}
					
					int vp[] = new int[endGame.length];
					int cardsInDeck[] = new int[endGame.length];
					String playerNames[] = new String[endGame.length];
					
					for(int i = 0; i < endGame.length; i++) {
						vp[i] = endGame[i].getVP();
						cardsInDeck[i] = endGame[i].getDeck().size() + endGame[i].getHand().size() + endGame[i].getDiscardPile().size();
						playerNames[i] = endGame[i].getUser().getUsername();
					}
					
					for(Player ending : this.players) {
						try {
							ending.endGame(vp, cardsInDeck, playerNames);
						} catch (RemoteException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
					}
					
					this.isFinished = true;
				}
				
			}
		}
		
		
	}


	/**
	 * Store the RMI registry name of this object. This is what will be communicated
	 * to the client so it can talk to the server.
	 * @param rmiRegistryName
	 */
	public void setRmiRegistryName(String rmiRegistryName) {
		this.rmiRegistryName = rmiRegistryName;
	}
	
	@Override
	public void endTurn() {
		
		String[] playerList = new String[this.players.size()];
		for(int i = 0; i < this.players.size(); i++) {
				playerList[i] = this.players.get(i).getUser().getUsername();
		}
		
		
		Player currentPlayer = this.players.get(this.currentPlayerIndex);
		
		//If we are going to take an extra turn, set back the current player index.
		if(currentPlayer.isExtraTurn()) {
			System.out.println("Taking extra turn now.");
			this.currentPlayerIndex--;
		}
		
		currentPlayer.resetPlayer();
		currentPlayer.setPlayerHand();
		currentPlayer.updatePlayerStats(playerList);
		
		this.currentPlayerIndex++; //Choose the next player to take a turn
		
		if(this.currentPlayerIndex == this.totalNumOfPlayers) {
			this.currentPlayerIndex = 0;
		}
		
		//New current player
		currentPlayer = this.players.get(this.currentPlayerIndex);
		
		currentPlayer.isTurn(true); 
		
		try {
			currentPlayer.startTurn();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < this.players.size(); i++) {
			if(i != this.currentPlayerIndex) {
				this.players.get(i).setOtherPlayerTurn(currentPlayer.getUser().getUsername());
			}
		}
		
		
		
	}
	
	public String getRMIRegistryName() {
		return this.rmiRegistryName;
	}

}
