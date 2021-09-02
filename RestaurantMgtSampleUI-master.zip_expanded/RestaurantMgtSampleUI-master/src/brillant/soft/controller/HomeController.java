package brillant.soft.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;

import brillant.soft.bean.Flux;
import brillant.soft.bean.FluxListWrapper;
import brillant.soft.home.Main;
import brillant.soft.util.DateUtil;
import brillant.soft.util.MethodUtilitaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeController implements Initializable {
	@FXML
	private Button btnDashboard;
	@FXML
	private Button btnFlux;
	@FXML
	private Button btnRecapitulatif;
	@FXML
	private Button btnRapport;
	@FXML
	private Button btnSauvegarde;
	@FXML
	private Button btnReglage;
	@FXML
	private Button btnDeconnection;
	@FXML
	private Pane paneRapport;
	@FXML
	private Pane paneRecapitulatif;
	@FXML
	private Pane paneDashboard;
	@FXML
	private VBox pnItems;
	@FXML
	private Pane paneFlux;
	@FXML
	private TableView<Flux> tableFlux;
	@FXML
	private TableColumn<Flux, LocalDate> tableColumDateFlux;
	@FXML
	private TableColumn<Flux, String> tableColumTypeFlux;
	@FXML
	private TableColumn<Flux, Float> tableColumMontantFlux;
	@FXML
	private TextField textFieldSearchFlux;
	@FXML
	private Label idFluxLabel;
	@FXML
	private Label dateFluxLabel;
	@FXML
	private Label typeFluxLabel;
	@FXML
	private Label typePaiementFluxLabel;
	@FXML
	private Label montantLabel;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Label dashboardDate;
	@FXML
	private Label totalCaisse;
	@FXML
	private Label totalCb;
	@FXML
	private Label totalOm;
	@FXML
	private Label total;
	@FXML
	private Label depense;
	@FXML
	private Label solde;

	private Main main;
	private RootController rootController;
	private ObservableList<Flux> fluxData = FXCollections.observableArrayList();
	private ObservableList<Flux> tempFluxData = FXCollections.observableArrayList();
	String[] months = DateFormatSymbols.getInstance(Locale.FRANCE).getMonths();
	
	LocalDate today = LocalDate.now();
	int month = today.getMonthValue();
	List<Flux> monthFlux;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setMain(main);

		tableFlux.setItems(fluxData);

		setFluxColumProperty();
		injectRootController(rootController);
        filterData();
	}
	
////////////////////////////// CHARGEMENT DE CHAQUE HISTORIQUE EN TERME DE FLUX POUR CHAQUE JOUR ////////
	
	public void loadItem() {
		
		float caisse = 0;
		float carteBancaire = 0;
		float orangeMoney = 0;
		float total = 0;
		float depense = 0;
		float solde = 0;
		
		// Recuperer le nombre de jour du mois courant
		Calendar calendar = Calendar.getInstance();
		int numDays = calendar.getActualMaximum(Calendar.DATE);
		
		System.out.println("Nombre de jour : "+numDays);
		Node[] nodes = new Node[numDays];
		
		
		for (int i = 0; i < nodes.length; i++) {
			try {

				final int j = i;
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../vue/Item.fxml"));
				nodes[i] = loader.load();
				ItemController itemController = loader.getController();

				for (Flux flux : fluxData) {
					
					if(flux.getDate().getDayOfMonth() == i) {
						
						System.out.println("Dans le if");
						
						if(flux.getTypeFlux().equals("Entré")) {
							if(flux.getTypePaiement().equals("Caisse")) {
								caisse += flux.getMontant();
							}else if (flux.getTypePaiement().equals("Orange money")) {
								orangeMoney += flux.getMontant();
							}else
								carteBancaire += flux.getMontant();
						}else {
							depense += flux.getMontant();
						}
						
					}
					
					// set value on item
					itemController.setValue(caisse, carteBancaire, orangeMoney, total, depense, solde, DateUtil.parse("10.10.2020"));
					
					// give the items some effect

					nodes[i].setOnMouseEntered(event -> {
						nodes[j].setStyle("-fx-background-color : #0A0E3F");
					});
					nodes[i].setOnMouseExited(event -> {
						nodes[j].setStyle("-fx-background-color : #02030A");
					});
					pnItems.getChildren().add(nodes[i]);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public List<Flux> getFluxData(){
		return fluxData;
	}

	public void injectRootController(RootController rootController) {
		
		this.rootController = rootController;
	}

	public void setMain(Main main) {
		this.main = main;
	}
	
	public Main getMain() {
		return main;
	}

	private void setFluxColumProperty() {

		tableColumDateFlux.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		tableColumTypeFlux.setCellValueFactory(cellData -> cellData.getValue().typeFluxProperty());
		// tableColumMontantFlux.setCellValueFactory(cellData ->
		// cellData.getValue().montantProperty());

		showFluxDetails(null);

		// Listen for selection changes and show the person details when changed.
		tableFlux.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showFluxDetails(newValue));
		
	}
	
	public void filterData() {
		
		dashboardDate.setText(months[month].toUpperCase()+" "+today.getYear());
		
		float dataCaisse = 0 ;
		float dataCb = 0;
		float dataOm = 0;
		float totalDepense = 0;
		
		monthFlux = new ArrayList<Flux>();
		
		for (Flux flux : getFluxData()) {
			if(flux.getDate().getMonthValue() == month) {
				System.out.println("Mois numéro : "+month);
				monthFlux.add(flux);
				if(flux.getTypeFlux().equals("Entré")) {
					if(flux.getTypePaiement().equals("Caisse")) {
						dataCaisse += flux.getMontant();
					}else if (flux.getTypePaiement().equals("Orange money")) {
						dataOm += flux.getMontant();
					}else
						dataCb += flux.getMontant();
				}else {
					totalDepense += flux.getMontant();
				}
			}
		}
		
		totalCaisse.setText(String.valueOf(dataCaisse));
		totalOm.setText(String.valueOf(dataOm));
		totalCb.setText(String.valueOf(dataCb));
		total.setText(String.valueOf(dataOm + dataCb + dataCaisse));
		depense.setText(String.valueOf(totalDepense));
		solde.setText(String.valueOf((dataOm + dataCb + dataCaisse) - totalDepense));
	}
	

	// Event Listener on Button[#btnDashboard].onAction
	@FXML
	public void handleClicks(ActionEvent actionEvent) {
		if (actionEvent.getSource() == btnRapport) {
			paneRapport.setStyle("-fx-background-color : red");
			paneRapport.toFront();
		}
		if (actionEvent.getSource() == btnRecapitulatif) {
			paneRecapitulatif.setStyle("-fx-background-color : #53639F");
			paneRecapitulatif.toFront();
		}
		if (actionEvent.getSource() == btnDashboard) {
			paneDashboard.setStyle("-fx-background-color : #02030A");
			paneDashboard.toFront();
		}
		if (actionEvent.getSource() == btnFlux) {
			paneFlux.setStyle("-fx-background-color : #464F67");
			paneFlux.toFront();
		}
	}

	// Event Listener on TextField[#textFieldSearchFlux].onKeyReleased
	@FXML
	public void handleFilteredFlux(KeyEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleAddFlux(ActionEvent event) {
		Flux flux = new Flux();

		boolean okClicked = showPersonEditDialog(flux, false);
		if (okClicked) {
			fluxData.add(flux);
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleEditFlux(ActionEvent event) {
		Flux selectedFlux = tableFlux.getSelectionModel().getSelectedItem();
		if (selectedFlux != null) {
			boolean okClicked = showPersonEditDialog(selectedFlux, true);
			if (okClicked) {
				System.out.println("Edit successful");
				tempFluxData.clear();
				tempFluxData.addAll(fluxData);
				fluxData.clear();
				fluxData.addAll(tempFluxData);
				tempFluxData.clear();
				showFluxDetails(selectedFlux);
			}
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Aucune sélection", "Aucune sélection",
					"Sélectionner un enregistrement dans le tableau et éssayer de nouveau");
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleDelFlux(ActionEvent event) {
		int selectedIndex = tableFlux.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			tableFlux.getItems().remove(selectedIndex);
		} else {
			MethodUtilitaire.deleteNoPersonSelectedAlert("Aucune selection", "Aucun flux n'a été séléctionné",
					"S'il vous plait sélectionné un flux et éssayer de nouveau ! ");
		}
	}

///////////////////////////////////////////////// FLUX DÉTAILS //////////////////////////////////////////////

	private void showFluxDetails(Flux flux) {
		if (flux != null) {
			// Fill the labels with info from the person object.
			// idFluxLabel.setText(flux.getIdFlux().toString());
			typeFluxLabel.setText(flux.getTypeFlux());
			typePaiementFluxLabel.setText(flux.getTypePaiement());
			montantLabel.setText(Float.toString(flux.getMontant()));
			descriptionLabel.setText(flux.getDescription());

			// TODO: We need a way to convert the birthday into a String!
			dateFluxLabel.setText(DateUtil.format(flux.getDate()));
		} else {
			// Si flux est null, retirer tout le text.
			// idFluxLabel.setText("");
			typeFluxLabel.setText("");
			typePaiementFluxLabel.setText("");
			montantLabel.setText("");
			descriptionLabel.setText("");
			dateFluxLabel.setText("");
		}
	}

/////////////////////////////////////////// METHODE DE D'OUVERTURE DE LA DIALOG D'AJOUT OU D'ÉDITION  DE FLUX////////

	/**
	 * Opens a dialog to edit details for the specified person. If the user clicks
	 * OK, the changes are saved into the provided person object and true is
	 * returned.
	 *
	 * @param person the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showPersonEditDialog(Flux flux, boolean newOrEdit) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(HomeController.class.getResource("../vue/FluxEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(main.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			FluxEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			// Pour determiner si il a cliquer sur ajouter ou modifier
			controller.setNewOrEdit(newOrEdit);
			controller.setFlux(flux);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// ************************************ GESTION DE LA PERXISTANCE DES DONNÉES EN
	// XML ***************************//

/////////////////////// ENREGISTREMENT DES PRÉFÉRENCES UTILISATEURS /////////////////////////////////////////////

	/**
	 * Returns the person file preference, i.e. the file that was last opened. The
	 * preference is read from the OS specific registry. If no such preference can
	 * be found, null is returned.
	 * 
	 * @return
	 */
	public File getFluxFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(HomeController.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			System.out.println("getPreference"+filePath);
			return new File(filePath);
		} else {
			return null;
		}
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in the
	 * OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	public void setFluxFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(HomeController.class);
		if (file != null) {
			System.out.println("setPreference"+file.getPath());
			prefs.put("filePath", file.getPath());

// Update the stage title.
			main.getPrimaryStage().setTitle("OréacaApp - " + file.getName());

		} else {
			prefs.remove("filePath");

// Update the stage title.
			main.getPrimaryStage().setTitle("OréacaApp");
		}
	}

////////////////////////////////////LIRE ET ECRIRE LES DONNÉES AVEC JAXB /////////////////////////////////

	/**
	 * Loads person data from the specified file. The current person data will be
	 * replaced.
	 * 
	 * @param file
	 */
	public void loadFluxDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(FluxListWrapper.class);
			javax.xml.bind.Unmarshaller um = context.createUnmarshaller();

// Reading XML from the file and unmarshalling.
			FluxListWrapper wrapper = (FluxListWrapper) um.unmarshal(file);

			fluxData.clear();
			fluxData.addAll(wrapper.getFlux());

// Save the file path to the registry.
			setFluxFilePath(file);

		} catch (Exception e) { 
			e.printStackTrace();// catches ANY exception
			MethodUtilitaire.errorMessageAlert("Erreur", "Impossible de charger les données", "Impossible de charger les données depuis "+file.getPath());
		}
	}

	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public void saveFluxDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(FluxListWrapper.class);
			javax.xml.bind.Marshaller m = context.createMarshaller();
			m.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);

// Wrapping our person data.
			FluxListWrapper wrapper = new FluxListWrapper();
			wrapper.setFlux(fluxData);

// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);

// Save the file path to the registry.
			setFluxFilePath(file);
		} catch (Exception e) {
			e.printStackTrace();// catches ANY exception
			MethodUtilitaire.errorMessageAlert("Erreur", "Impossible de sauvegarder les données", "Impossible de sauvegarder les données dans "+file.getPath());
		}
	}

}
