package GameServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import GameServer.GameEngine.GameEngine;
import GameServer.Users.User;

public interface IGameManagement extends Remote {
	
	
	/**
	 * Returns a list of games to join for the game lobby screen
	 * @return list of available games to join
	 */
	public ArrayList<GameInfo> listJoinableGames() throws RemoteException;
	

	public ArrayList<String> SearchGames(String name) throws RemoteException;
	
	public ArrayList<String> SearchGames(int players) throws RemoteException;
	
	public ArrayList<String> SearchGames(String name, int players) throws RemoteException;
	
	
	public int createGame(int numberOfPlayers, String gameName, boolean chatEnabled) throws RemoteException;
	
	public boolean addUserToGame(int game, User u, String clientRegistryName) throws RemoteException;

	public boolean startGame(int game) throws RemoteException;

}
