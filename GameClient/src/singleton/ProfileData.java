/**
 * @author Anish Kunduru
 *
 * The purpose of this program is to store profile screen state data (the username of the player we wish to redirect to).
 */

package singleton;

public class ProfileData
{
   // So we know which profile page to load.
   private boolean redirectToClicked;
   private String clickedUsername;

   /**
    * Default constructor to use in singleton.
    */
   public ProfileData()
   {
   }

   /**
    * Lets the profile screen screen know that the page should be redirect to the username stored in clickedUsername.
    * 
    * @param redirect True if we should redirect, false if we should go to the logged in (stored in the LoginData singleton) user's profile page (personal
    *        profile page).
    */
   public void setRedirectToClicked(boolean redirect)
   {
      redirectToClicked = redirect;
   }

   /**
    * @return The boolean to redirect or not.
    */
   public boolean getRedirectToClicked()
   {
      return redirectToClicked;
   }

   /**
    * Set the user name that the user should be redirected to. Must make sure redirectToClicked is set to true.
    * 
    * @param username The username of the player you want the profile page to be redirected to.
    */
   public void setClickedUsername(String username)
   {
      clickedUsername = username;
   }

   /**
    * @return The username of the player that the profile page is set to redirect to if redirectToClicked is true.
    */
   public String getClickedUsername()
   {
      return clickedUsername;
   }

   /**
    * Set the boolean redirect and username that the profile page should redirect to in one fell swoop.
    * 
    * @param redirect True if we should redirect, false if we should go to the logged in user's page.
    * @param username The username of the player that we should redirect to.
    */
   public void setRedirect(boolean redirect, String username)
   {
      setRedirectToClicked(redirect);
      setClickedUsername(username);
   }
}
