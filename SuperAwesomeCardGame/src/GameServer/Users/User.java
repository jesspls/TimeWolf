package GameServer.Users;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import javafx.scene.image.Image;
import GameServer.DBHelper;

/**
 * User class object to hold all valuable data for a given user over
 * any period of time
 * @author Shelbie
 *
 */

public class User implements Serializable {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private int ID;
   private String username;
   private String email;
   private String password;
   private String imgPath;
   private String securityQuestion;
   private String securityAnswer;
   public UserStats Statistics;
   private boolean isBanned;
   private String bannedReason;
   private int role;
   public ArrayList<Feedback> Feedback;
   private byte[] imageBytes;
   private String location;
   private boolean paranoia;
   private boolean isFlagged;

   /**
    * Creates a new user object with empty fields
    */
   public User() {
      this.ID = 0;
      this.username = "";
      this.email = "";
      this.password = "";
      this.imgPath = "";
      securityQuestion = "";
      securityAnswer = "";
      this.Statistics = null;
      this.isBanned = false;
      this.role = 0;
      this.imageBytes = null;
      this.location = "";
      this.paranoia = true;
      this.isFlagged = false;
      //this.Feedback = new ArrayList<Feedback>();
   }
   
   public boolean isFlagged()
   {
	   return isFlagged;
   }
   
   public void setFlag(boolean raised)
   {
	   isFlagged = raised;
   }
   
   public boolean isParanoid()
   {
	   return paranoia;
   }
   
   public void setParanoid(boolean isParanoid)
   {
	   paranoia = isParanoid;
   }
   
   public String getLocation()
   {
	   return location;
   }
   
   public void setLocation(String loc)
   {
	   location = loc;
   }
   
   /**
    * Initiates the UserStats object associated with this user
    * All users should always have an associated stats object
    */
   public void initStats()
   {
      //this.Statistics = new UserStats(ID);
   
   }
   
   /**
    * Returns the user id
    * @return ID
    */
   public int getID()
   {
      return ID;
   }
   
   /**
    * Sets the users id to the given parameter
    * @param id - new id
    */
   public void setID(int id)
   {
      ID = id;
   }

   /**
    * Returns users email
    * @return email
    */
   public String getEmail() {
      return email;
   }
   
   /**
    * Sets users email to the email passed via parameter
    * @param email - new email to be set
    */
   public void setEmail(String email)
   {
      this.email = email;
   }
   
   public void setImageBytes(byte[] bs)
   {
	   this.imageBytes = bs;
   }
   
   public byte[] getImageBytes()
   {
	   return imageBytes;
   }
   
   /**
    * Returns the security question of the user.
    * @return securityQuestion
    */
   
   public String getSecurityQuestion(){
	   return securityQuestion;
   }
   
   /**
    * Returns the private list of all feedback for the given user
    * @return
    */
   public ArrayList<Feedback> listFeedback()
   {
	   return Feedback;
   }
   
   /**
    * Sets the security question of the user.
    * @param securityQuestion
    */
   
   public void setSecurityQuestion(String securityQuestion){
	   this.securityQuestion = securityQuestion;
   }
   
   /**
    * Gets the answer to the security question of the user.
    * @return securityAnswer
    */
   public String getSecurityAnswer(){
	   return securityAnswer;
   }
   
   /**
    * Sets the answer to the security question of the user.
    * @param securityAnswer
    */
   
   public void setSecurityAnswer(String securityAnswer){
	   this.securityAnswer = securityAnswer;
   }

   /**
    * Returns users username
    * @return username
    */
   public String getUsername() {
      return username;
   }
   
   /**
    * Sets users username to the given new username
    * @param username - new username for user
    */
   public void setUsername(String username)
   {
      this.username = username;
   }
   
   
   /**
    * Returns password for user
    * @return password
    */
   public String getPassword() {
      return password;
   }
   
   /**
    * returns the users role as an int
    * 0 - Normal user
    * 1 - Moderator
    * 2 - Admin
    * @return role
    */
   public int getRole()
   {
      return role;
   }
   
   /**
    * Sets password to given password
    * Mostly used for the LoginClass
    * NOT A SUBSTITUTE FOR RESETPASSWORD
    * @param pw - new password
    */
   public void setPassword(String pw) {
      password = pw;
   }

   /**
    * Returns image path for the user on the server
    * @return imgPath
    */
   public String getImagePath() {
      return imgPath;
      // get image from server
   }
   
   
   /**
    * Deletes image from server and sets imgPath to empty string
    */
   public void deleteImage()
   {
      //do server stuff to delete from server
      imageBytes = null;
   }

   /**
    * Returns true if user is banned, false if user is not banned
    * @return isBanned
    */
   public boolean isBanned() {
      return isBanned;
   }
   
   /**
    * Sets isBanned to the passed boolean value
    * @param banned - new banned status
    */
   public void setBannedStatus(boolean banned)
   {
      this.isBanned = banned;
   }
   
   /**
    * Gets banned reason
    * @return bannedReason
    */
   public String getBannedReason()
   {
	   return bannedReason;
   }
   
   /**
    * sets banned reason
    */
   public void setBannedReason(String br)
   {
	   bannedReason = br;
   }
   
   /**
    * Boolean check if a user is an admin or not
    * Used for privilege checking
    * @return true if role > 1 else false
    */
   public boolean isAdmin() {
      if (role == 2)
         return true;
      else
         return false;
   }

   /**
    * Boolean check if user is moderator or not
    * Used for privilege checking
    * @return true if role > 0 else false
    */
   public boolean isModerator() {
      if (role > 0)
         return true;
      else
         return false;
   }
   
   /**
    * Sets users role to passed int role
    * @param role - new role for user
    */
   public void setRole(int role)
   {
      this.role = role;
   }
   
   
   /*
    * Pre-existing users
    */
//   public void saveUser() throws Exception
//   {
//      if(this.ID > 0)
//         saveUser(this.username, this.email, this.password);
//      else
//         throw new Exception("saveUser() cannot accept a call from a User with ID=0");
//   }
   
   
   
   /*
    * Creating a new user case
    */
//   public void saveUser(String username, String email, String password) throws Exception
//   {
//      if(username.equals("") || email.equals("") || password.equals(""))
//      {
//         throw new Exception("saveUser() cannot accept empty strings as arguments");
//      }
//   
//      DBHelper dbh = new DBHelper();
//      String query = "";
//      if(this.ID == 0) //insert query
//      {
//         query = "INSERT INTO User ";
//         query += "(Username, Email, Password, ImagePath, IsBanned, Role)";
//         query += "VALUES ('" + username + "','" + email + "','" + password + "','','0','0')";
//
//         try{
//            dbh.executeUpdate(query);
//         }
//         catch(Exception ex)
//         {
//            throw new Exception("This username already exists!");
//         }
//         
//         query = " SELECT * FROM User WHERE Username='" + username + "'";
//         ResultSet rs = dbh.executeQuery(query);
//         
//         if(rs.first())
//         {
//            this.ID = rs.getInt("ID");
//            this.username = username;
//            this.email = email;
//            this.password = password;
//            this.imgPath = "";
//            this.isBanned = false;
//            this.role = 0;
//            query = "INSERT INTO Statistics ";
//            query += "(UserID,TotalGames,TotalWins,TotalPoints)";
//            query += "VALUES ('" + this.ID + "','0','0','0')";
//            dbh.executeUpdate(query);
//         }
//         else
//         {
//            throw new Exception("Insert of new user failed in saveUser()!");
//         }
//         
//      }
//      else     //update query
//      {
//         query = "UPDATE User SET ";
//         query += "Username='" + username + "'";
//         query += ",Email='" + email + "'";
//         query += ",Password='" + password + "'";
//         query += ",ImagePath='" + this.imgPath + "'";
//         int bit = 0;
//         if(this.isBanned)
//            bit = 1;
//         query += ",IsBanned=" + bit;
//         query += ",Role=" + this.role;
//         query += " WHERE ID=" + this.ID;
//
//         dbh.executeUpdate(query);
//         //Stats are currently saved whenever games are incremented so there isn't a need to save here as well
//         
//         this.username = username;
//         this.email = email;
//         this.password = password;
//      }
//      
//      
//   }
//   
   /*
    * Resets current user's password and saves new password in database Emails
    * user new password - if we can get a SMTP setup
    */
//   public String resetPassword() {
//      if (this.ID > 0) {
//         // newPW is awful right now, can make better later
//         String newPW = username + ID;
//         DBHelper dbh = new DBHelper();
//         String query = "UPDATE User SET Password='" + newPW + "' WHERE ID="
//               + this.ID;
//         
//         dbh.executeUpdate(query);
//         
//         return newPW;
//      }
//      //if ID == 0 then no user is selected
//      return "";
//   }
//
//   public boolean logIn(String username, String password) throws SQLException {
//      DBHelper dbh = new DBHelper();
//      String query = "SELECT * FROM User WHERE Username='" + username + "' AND Password='"
//            + password + "'";
//      ResultSet rs = dbh.executeQuery(query);
//      if (rs.first()) {
//         this.ID = rs.getInt("ID");
//         this.username = username;
//         this.email = rs.getString("Email");
//         this.password = password;
//         this.imgPath = rs.getString("ImagePath");
//         this.isBanned = false;
//         int bannedBit = rs.getInt("IsBanned");
//         if (bannedBit > 0)
//            this.isBanned = true;
//         this.role = rs.getInt("Role");
//         this.Statistics = new UserStats(this.ID);
//
//         return true;
//      }
//
//      this.ID = 0;
//      return false;
//   }
   
      
      
   
}