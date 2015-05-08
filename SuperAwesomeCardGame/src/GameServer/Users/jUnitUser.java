package GameServer.Users;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class jUnitUser {
	
	private User userOne;
	private User userTwo;
	private User userThree;
	private User userFour;
	private LogIn login;

	@Before
	public void setUp() throws Exception {
		
		userOne = new User();
		userOne = login.logIn("ssimmons", "password");
		
		userTwo = new User();
		userTwo = login.logIn("test2", "testpw");
		
		userThree = new User();
		userThree = login.logIn("test3", "test34");
		
		userFour = new User();
		userFour = login.logIn("test1", "testpw");
	}

	@Test
	public void getUsername() {
		assertEquals("ssimmons", userOne.getUsername());
	}
	
	@Test
	public void getEmail() {
		assertEquals("sebesmos@iastate.edu", userOne.getEmail());
	}
	
	@Test
	public void getPassword() {
		assertEquals("password", userOne.getPassword());
	}
	
	@Test
	public void isBannedTrue() {
		assertEquals(true, userTwo.isBanned());
	}
	
	@Test
	public void isBannedFalse() {
		assertEquals(false, userOne.isBanned());
	}
	
	@Test
	public void isAdminTrue() {
		assertEquals(true, userOne.isAdmin());
	}
	
	@Test
	public void isAdminFalse() {
		assertEquals(false, userTwo.isAdmin());
	}
	
	@Test
	public void isModeratorTrue() {
		assertEquals(true, userFour.isModerator());
	}
	
	@Test
	public void isModeratorFalse() {
		assertEquals(false, userThree.isModerator());
	}
	
	@Test
	public void getID() throws Exception {
		assertEquals(4, userThree.getID());
	}
	
	@Test
	public void Ban() throws Exception {
		userThree.setBannedStatus(true);
		login.save(userThree);
		assertEquals(true, userThree.isBanned());
	}
	
	@Test
	public void resetPassword() throws Exception {
		login.resetPassword("test3", "test34");
		assertEquals("test34", userThree.getPassword());
	}
	
	//Successfully creates a new user
	/*@Test
	public void createNewUser() throws Exception {
		User userNew = new User();
		userNew.saveUser("test4", "test4@test.com", "password");
	}*/

}
