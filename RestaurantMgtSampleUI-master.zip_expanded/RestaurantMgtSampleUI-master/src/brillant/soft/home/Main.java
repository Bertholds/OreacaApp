package brillant.soft.home;

import java.io.IOException;

import brillant.soft.controller.HomeController;
import brillant.soft.controller.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double x, y;
    
    private Stage primaryStage;
    private BorderPane root;
    private RootController rootController; 
    private HomeController homeController;

    @Override
    public void start(Stage primaryStage) throws Exception {
    	iniRootLayout();
    	showFluxOverview();
       /* Parent root = FXMLLoader.load(getClass().getResource("../vue/Home.fxml"));
        this.primaryStage = primaryStage;
        this.primaryStage.setScene(new Scene(root));
        //set stage borderless
        this.primaryStage.initStyle(StageStyle.UNDECORATED);

        //drag it here
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {

        	this.primaryStage.setX(event.getScreenX() - x);
        	this.primaryStage.setY(event.getScreenY() - y); 

        });
        injectMainClass();
        this.primaryStage.show();
        */
    }
    
    private void iniRootLayout() {
    	try {
    		FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(Main.class.getResource("../vue/Root.fxml"));
        	root = loader.load();
        	
    		RootController rootController = loader.getController();
    		this.rootController = rootController;
        	
        	Scene scene = new Scene(root);
        	 this.primaryStage = new Stage();
             this.primaryStage.setScene(scene);
             //set stage borderless
             this.primaryStage.initStyle(StageStyle.UNDECORATED);

             //drag it here
             root.setOnMousePressed(event -> {
                 x = event.getSceneX();
                 y = event.getSceneY();
             });
             root.setOnMouseDragged(event -> {

             	this.primaryStage.setX(event.getScreenX() - x);
             	this.primaryStage.setY(event.getScreenY() - y); 

             });
             
             this.primaryStage.show();
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Shows the flux overview inside the root layout.
     */
    public void showFluxOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../vue/Home.fxml"));
            AnchorPane fluxOverview = (AnchorPane) loader.load();
            
            //injection de dépendance
            homeController = loader.getController();
			homeController.setMain(Main.this);
			homeController.injectRootController(rootController);
			this.rootController.sethomeApp(homeController);
            
            // Set person overview into the center of root layout.
            root.setCenter(fluxOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
    public Stage getPrimaryStage() {
		return primaryStage;
	}
    
    public static void main(String[] args) {
        launch(args);
    }
}
