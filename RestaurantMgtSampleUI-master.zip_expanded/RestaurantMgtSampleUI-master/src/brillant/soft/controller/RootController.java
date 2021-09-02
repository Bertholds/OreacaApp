package brillant.soft.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class RootController implements Initializable {
	
	// Reference to the main application
    private HomeController homeApp;
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param homeApp
     */
    public void sethomeApp(HomeController homeApp) {
        this.homeApp = homeApp;
    }

	// Event Listener on MenuItem.onAction
	@FXML
	public void handleOuvrirClick(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(homeApp.getMain().getPrimaryStage());

        if (file != null) {
        	homeApp.loadFluxDataFromFile(file);
        	homeApp.filterData();
        	homeApp.loadItem();
        }
	}
	// Event Listener on MenuItem.onAction
	@FXML
	public void handleNouveauClick(ActionEvent event) {
		homeApp.getFluxData().clear();
		homeApp.setFluxFilePath(null);
	}
	// Event Listener on MenuItem.onAction
	@FXML
	public void handleSaveClick(ActionEvent event) {
		 File fluxFile = homeApp.getFluxFilePath();
	        if (fluxFile != null) {
	        	homeApp.saveFluxDataToFile(fluxFile);
	        	homeApp.filterData();
	        	homeApp.loadItem();
	        } else {
	            handleSaveAsClick(event);
	        }
	}
	// Event Listener on MenuItem.onAction
	@FXML
	public void handleSaveAsClick(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(homeApp.getMain().getPrimaryStage());

		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			homeApp.saveFluxDataToFile(file);
			homeApp.filterData();
			homeApp.loadItem();
		}
	}
	// Event Listener on MenuItem.onAction
	@FXML
	public void handleExitClick(ActionEvent event) {
		System.exit(0);
	}
	// Event Listener on MenuItem.onAction
	@FXML
	public void handleAboutClick(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("OreacaApp");
    	alert.setHeaderText("A propos");
    	alert.setContentText("Auteur: Brillant Soft (+237) 691 518 113");

    	alert.showAndWait();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
