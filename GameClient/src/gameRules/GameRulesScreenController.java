/**
 * @author Anish Kunduru
 *
 * This program is our handler for GameRulesScreen.fxml.
 */

package gameRules;

import framework.AbstractScreenController;
import framework.ControlledScreen;
import view.MainController;
import javafx.fxml.FXML;

public class GameRulesScreenController implements ControlledScreen
{
   // So we can set the screen's parent later on.
   MainController parentController;

   /**
    * Initializes the controller class. Automatically called after the FXML file has been loaded.
    */
   @FXML
   public void initialize()
   {
   }

   /**
    * This method will allow for the injection of each screen's parent.
    */
   @Override
   public void setScreenParent(AbstractScreenController screenParent)
   {
      parentController = (MainController) screenParent;
   }
}
