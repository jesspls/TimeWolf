/**
 * @author Anish Kunduru
 *
 * The purpose of this program is to store MainView's application state data.
 */

package singleton;

import view.MainController;
import javafx.stage.Stage;

public class MainData
{
   // Store reference to our mainController for GUI logic.
   private MainController mainController;

   // Store a reference to our primaryStage for dialogs.
   private Stage primaryStage;

   /**
    * Default constructor to use in singleton.
    */
   public MainData()
   {
   }

   /**
    * Sets the main controller.
    * 
    * @param mainController The main controller.
    */
   public void setMainController(MainController mainController)
   {
      this.mainController = mainController;
   }

   /**
    * Gets the main controller
    * 
    * @return A valid MainController object.
    */
   public MainController getMainController()
   {
      return mainController;
   }

   /**
    * Sets the primary Stage.
    * 
    * @param primaryStage The primary stage of the application.
    */
   public void setMainStage(Stage primaryStage)
   {
      this.primaryStage = primaryStage;
   }

   /**
    * Gets the primaryStage.
    * 
    * @return The main stage for this application.
    */
   public Stage getMainStage()
   {
      return primaryStage;
   }
}
