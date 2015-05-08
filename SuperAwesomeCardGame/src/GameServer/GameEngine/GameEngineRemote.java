package GameServer.GameEngine;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameEngineRemote extends Remote {

	/**
	 * Get the name of the game.
	 * @return the name of the game
	 */
	public String getName() throws RemoteException;
	
	
	/**
	 * Is the game full?
	 * @return true if full
	 */
	public abstract boolean isFull() throws RemoteException;

	/**
	 * Is the game running?
	 * @return true if game is running
	 */
	public abstract boolean isRunning() throws RemoteException;

	/**
	 * Is the game finished?
	 * @return true if the game is finished
	 */
	public abstract boolean isFinished() throws RemoteException;

	/**
	 * Has the game started?
	 * @return true if the game started
	 */
	public abstract boolean hasStarted() throws RemoteException;

	
	/**
	 * The current player ends their turn. 
	 * @throws RemoteException
	 */
	public void endTurn() throws RemoteException;
	
	
	/**
	 * Aquire a card
	 * @param a the action the user's running
	 * @return true if successful
	 */
	public boolean aquireCard(Action a) throws RemoteException;
	
	/**
	 * 
	 * @param a
	 * @return
	 */
	public boolean playCard(Action a) throws RemoteException;
	
	/**
	 * Discards a card from hand.
	 * @param a
	 * @throws RemoteException
	 */
	public void discardCard(Action a) throws RemoteException;
	
	public void trashCard(Action a) throws RemoteException;
}