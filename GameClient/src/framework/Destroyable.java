/**
 * @author Anish Kunduru
 * 
 * This is the interface that must be implemented if specific resources need to be trashed in the controller or internal window.
 */

package framework;

public interface Destroyable
{
   /**
    * Dispose resources.
    */
   public void onDestroy();
}