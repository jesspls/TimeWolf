package GameServer.Users;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;


public class jUnitFeedback {
	
	private User userOne;
	private User userTwo;
	private LogIn login;
	
	@Before
	public void setUp() throws Exception {
		userOne = new User();
		userOne = login.logIn("ssimmons", "password");
		
		userTwo = new User();
		userTwo = login.logIn("test1", "testpw");
	}

	@Test
	public void leaveFeedback() throws RemoteException, Exception {
		int currSize = userOne.Feedback.size();
		login.insertFeedback(userOne.getID(), userTwo.getID(), true, "She is the greatest");
		login.getLiveUpdate(userOne);
		assertEquals(currSize + 1, userOne.Feedback.size());
	}
	
	/**
	 * Because of the nature of these tests, the below code will only work the first time
	 * Database checks were easier to simulate
	 */
	
//	@Test
//	public void checkFeedbackDescription()
//	{
//		assertEquals("She is the greatest", userOne.Feedback.get(0).getDescription());
//	}
//	
//	@Test
//	public void checkFeedbackUserID()
//	{
//		assertEquals(userOne.getID(), userOne.Feedback.get(0).getUserID());
//	}
//	
//	@Test
//	public void checkFeedbackLeftByUserID()
//	{
//		assertEquals(userTwo.getID(), userOne.Feedback.get(0).getByUserID());
//	}
	
}
