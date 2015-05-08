package GameServer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

import GameServer.GameEngine.Client;
import GameServer.GameEngine.Deck;
import GameServer.GameEngine.FacadeClient;
import GameServer.GameEngine.GameEngine;
import GameServer.GameEngine.GameEngineRemote;
import GameServer.GameEngine.Player;
import GameServer.Users.User;


//public class GameManagement extends UnicastRemoteObject implements Runnable, Remote {
public class GameManagement extends UnicastRemoteObject implements Runnable, IGameManagement {
   
	private int gameID = 0;
	
	private ArrayList<GameEngine> games;
	private Deck startingDeck;
	private Deck mainDeck;
	
	/**
	 * Create the game managment class. 
	 * This is the primary class that manages all the game engines and such for 
	 * the entirety of the system.
	 * @throws SQLException
	 */
	public GameManagement() throws SQLException, RemoteException {
		
		this.games = new ArrayList<GameEngine>();
		
		//Load the decks we're going to use up
		try {
			this.startingDeck = Deck.getStarterDeck();
			this.mainDeck = Deck.getMainDeck();
		} catch (SQLException e) {	
			e.printStackTrace();
			throw(e);
		}
	}
	
	/**
	 * Returns a list of games to join for the game lobby screen
	 * @return list of available games to join
	 */
	public ArrayList<GameInfo> listJoinableGames()
	{
		
		ArrayList<GameInfo> available = new ArrayList<GameInfo>();
		
		/*
		for(int i = 0; i < games.size(); i++)
		{
			if(!games.get(i).isRunning() && !games.get(i).isFinished())
			{
				ArrayList<String> playerNames = new ArrayList<String>();
				for(int j = 0; i < games.get(i).getPlayers().size(); j++){
					playerNames.add(games.get(i).getPlayers().get(i).getUser().getUsername());
				}
				GameInfo gameInfo = new GameInfo(games.get(i).getName(), games.get(i).getTotalNumOfPlayers(), playerNames);
				available.add(gameInfo);
			}
		}
		*/
		
		// Loop over every game.
		for (GameEngine ge : games)
		{
		   // Make sure game isn't running and isn't finished.
		   // NOTE: WHY AREN'T WE REMOVING FINISHED GAMES?		   
		   if (!ge.isRunning() && !ge.isFinished()) 
		   {
		      // To hold the usernames of players.
		      ArrayList<String> playerNames = new ArrayList<String>();
		      
		      // Loop over list of players and extract username.
		      for (Player curPlayer : ge.getPlayers())
		         playerNames.add(curPlayer.getUser().getUsername());
		      // Create new game info.
		      GameInfo gameInfo = new GameInfo(ge.getName(), ge.getTotalNumOfPlayers(), playerNames, ge.getID(), ge.getChatEnabled());
		      
		      // Add to array.
		      available.add(gameInfo);
		   }
		}
		
		return available;
	}
	

	public ArrayList<String> SearchGames(String name)
	{
		ArrayList<String> games = SearchGames(name, 0);
		return games;
		
	}
	public ArrayList<String> SearchGames(int players)
	{
		ArrayList<String> games =  SearchGames("", players);
		return games;
	}
	public ArrayList<String> SearchGames(String name, int players)
	{
		boolean nameSearch = (name != "");
		boolean playerSearch = (players > 0);
		ArrayList<String> available = new ArrayList<String>();
		for(int i = 0; i < games.size(); i++)
		{
			if(!games.get(i).isRunning() && !games.get(i).isFinished())
			{
				if((games.get(i).getName().equals(name) || !nameSearch) && 
						(games.get(i).getTotalNumOfPlayers() == players || !playerSearch))
				{
					available.add(games.get(i).getName());
				}
			}
		}
		
		
		return available;
		
	}
	
	
	public int createGame(int numberOfPlayers, String gameName, boolean chatEnabled) {
		GameEngine ge;
		try {
			
			ge = new GameEngine(numberOfPlayers, gameName, this.startingDeck, this.mainDeck, gameID);
			ge.setChatEnabled(chatEnabled);
			
			String path = "//localhost/GameEngine-" + this.gameID;
			ge.setRmiRegistryName(path);
			Naming.rebind(path, (GameEngineRemote) ge);
			
			this.games.add(ge);
			this.gameID++;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gameID-1;
		
	}
	
	public boolean addUserToGame(int game, User u, String clientRegistryName) {
		System.out.println("initialize game table: GameManagement.    Registry Name: " + clientRegistryName);
		//User must be initialized
		if(u == null) {
			return false;
		}
		
		//We must have a valid game number
		if(game < 0 && game >= this.games.size()) {
			return false;
		}
		
		GameEngine ge = this.games.get(game);
		
		//Check to see if the game will allow additions
		//Can't add players to a running game or a full game
		if(ge.isRunning() || ge.isFull()) {
			return false;
		}
		
		
		ge.addPlayer(u, clientRegistryName);
		
		//Start the game once full
		if(ge.isFull()) ge.start();
		
		return true;
		
	}

	public boolean startGame(int game) {

		//If the game doesn't exist, we can't start it
		if(game < 0 || game >= this.games.size()) return false;
		
		GameEngine g = this.games.get(game);
		
		//The game can't have already have started and the game must be full
		if(g.hasStarted() || !g.isFull()) return false;
		
		//Return the status of the game engine startup
		return g.start();
	}



	@Override
	public void run() {
		/*
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = 0; i < this.games.size(); i++) {
				if(this.games.get(i).isFinished()) {
					try {
						Naming.unbind(this.games.get(i).getRMIRegistryName());
					} catch (RemoteException | MalformedURLException
							| NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				this.games.remove(i);
			}
			
		}
		*/
		
		
	}
	
	public static void main(String[] args) {
		
		try {
			GameManagement m = null;
			try {
				m = new GameManagement();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			User u1 = new User();
			User u2 = new User();
			
			u1.setUsername("Player 1");
			u2.setUsername("Player 2");
			
			m.createGame(2, "The Game", false);
			
			m.addUserToGame(0, u1, "//Path/ToClient");
			m.addUserToGame(0, u2, "//Path/ToClient");
			
			
			
			if(m.startGame(0)) {
				System.out.println("Good."); 
			} else {
				System.out.println("Bad.");
			}
			
			while(true)
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
