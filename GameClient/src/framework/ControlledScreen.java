/**
 * @author Anish Kunduru
 * 
 * This interface needs to implemented by all the screen controllers so that we can set multiple screens seamlessly.
 */

package framework;

public interface ControlledScreen
{
   /**
    * This method will allow us to pull the parent screen. (Allow injection of the Parent type).
    * 
    * @param screenController The parent controller of our custom abstract type.
    */
   public void setScreenParent(AbstractScreenController screenController);
}