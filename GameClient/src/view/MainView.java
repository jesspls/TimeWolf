/**
 * Our main GUI program displays our application.
 * 
 * @author Anish Kunduru
 */

package view;

import singleton.MainModel;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends Application
{   
   @Override
   public void start(Stage primaryStage)
   {
      // Initialize mainController.
      MainController mainController = new MainController();

      // Add the controller to the singleton.
      MainModel.getModel().currentMainData().setMainController(mainController);

      // Initialize display components.
      Group root = new Group();
      Scene scene = new Scene(root, 1280, 720);

      // Add mainController.
      root.getChildren().addAll(mainController);

      // Pin the root to scene and display it.
      primaryStage.setScene(scene);
      primaryStage.show();
      
      // Properly terminate the application if the user presses the "X" window button.
      primaryStage.setOnCloseRequest(event ->
      {
         mainController.closeApplication();
      });

      // Set the title and make the application a fixed size.
      primaryStage.setTitle("Time Wolf");
      primaryStage.setResizable(false);
      primaryStage.sizeToScene();

      // Add the stage to the singleton.
      MainModel.getModel().currentMainData().setMainStage(primaryStage);
      
      // Go to the first screen.
      mainController.goToLoginScreen();
   }

   /**
    * This method is actually not used in a correctly deployed JavaFX application. Instead, the start method above is called. This main serves as a fallback in
    * case of improper configuration.
    */
   public static void main(String[] args)
   {
      launch(args);
   }
}
