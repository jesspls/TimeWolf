package GameServer.Users;

import java.io.Serializable;
import java.rmi.Remote;

public class Feedback implements Remote, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private int userID;
	private String description;
	private boolean isPositive;
	private int byUserID;
	
	/**
	 * ANY NEW FEEDBACK SHOULD ALWAYS HAVE AN ID OF 0
	 * IF NOT, THE SAVE WILL NOT WORK CORRECTLY
	 */
	public Feedback()
	{
		ID = 0;
		userID = 0;
		description = "";
		isPositive = false;
		byUserID = 0;
	}
	
	/**
	 * Constructs a feedback object
	 * @param userid user's id
	 * @param desc the description text of the feedback
	 * @param isPositive true if positive feedback, false if negative
	 * @param byUserID user id who left the feedback
	 */
	public Feedback(int userid, String desc, boolean isPositive, int byUserID)
	{
		this.ID = 0;
		this.userID = userid;
		this.description = desc;
		this.isPositive = isPositive;
		this.byUserID = byUserID;
	}
	
	/**
	 * Constructs a feedback object
	 * @param id feedback's id
	 * @param userid user's id
	 * @param desc the description text of the feedback
	 * @param isPositive true if positive feedback, false if negative
	 * @param byUserID user id who left the feedback
	 */
	
	public Feedback(int id, int userid, String desc, boolean isPositive, int byUserID)
	{
		this.ID = id;
		this.userID = userid;
		this.description = desc;
		this.isPositive = isPositive;
		this.byUserID = byUserID;
	}
	
	/**
	 * Returns ID of object
	 * @return
	 */
	public int getID()
	{
		return ID;
	}
	
	/**
	 * Returns FK UserID of the user it belongs to
	 * @return
	 */
	public int getUserID()
	{
		return userID;
	}
	
	/**
	 * Returns description of feedback
	 * @return
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Returns whether the feedback was positive or negative
	 * @return
	 */
	public boolean isPositive()
	{
		return isPositive;
	}
	
	/**
	 * Returns the ID of the user who left the feedback
	 * @return
	 */
	public int getByUserID()
	{
		return byUserID;
	}
}
