package brillant.soft.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import brillant.soft.bean.Flux;
import brillant.soft.util.DateUtil;
import brillant.soft.util.MethodUtilitaire;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

public class FluxEditDialogController implements Initializable {
	@FXML
	private ComboBox<String> typeFlux;
	@FXML
	private ComboBox<String> typePaiement;
	@FXML
	private TextField date;
	@FXML
	private TextField montant;
	@FXML
	private TextField description;

	private Stage dialogStage;
	private Flux flux;
	private boolean okClicked = false;
	private boolean newOrEdit;

	// Event Listener on Button.onAction
	@FXML
	public void handleOkClick(ActionEvent event) {

		if (isInputValid()) {
			flux.setDate(DateUtil.parse(date.getText()));
			flux.setDescription(description.getText());
			flux.setMontant(Float.parseFloat(montant.getText()));
			flux.setTypeFlux(typeFlux.getSelectionModel().getSelectedItem());
			flux.setTypePaiement(typePaiement.getSelectionModel().getSelectedItem());

			okClicked = true;
			dialogStage.close();
		}

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setNewOrEdit(boolean value) {
		this.newOrEdit = value;
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleCancelClick(ActionEvent event) {
		dialogStage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		typePaiement.setItems(FXCollections.observableArrayList("Caisse", "Orange money", "Carte bancaire"));
		typeFlux.setItems(FXCollections.observableArrayList("Entré", "Sorti"));
	}

	public void setFlux(Flux flux) {

		this.flux = flux;

		if (newOrEdit) {
			typeFlux.getSelectionModel().select(flux.getTypeFlux());
			typePaiement.getSelectionModel().select(flux.getTypePaiement());
			description.setText(flux.getDescription());
			montant.setText(String.valueOf(flux.getMontant()));
			date.setText(DateUtil.format(flux.getDate()));
			date.setPromptText("dd.mm.yyyy");
		}
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (typeFlux.getSelectionModel().getSelectedItem() == null || typeFlux.getSelectionModel().getSelectedItem().length() == 0) {
			errorMessage += "Le champ type de flux est invalide\n";
		}
		if (typePaiement.getSelectionModel().getSelectedItem() == null || typePaiement.getSelectionModel().getSelectedItem().length() == 0) {
			errorMessage += "Le champ mode de paiement est invalide\n";
		}
		if (montant.getText() == null || montant.getText().length() == 0) {
			errorMessage += "Le champ montant est invalide!\n";
		}else {
			try{
				Float.parseFloat(montant.getText());
			}catch (Exception e) {
				errorMessage += "Le champ montant est invalide! Utiliser le format nombre\n";
			}
		}

		if (date.getText() == null || date.getText().length() == 0) {
			errorMessage += "Le champ date est invalide!\n";
		} else {
			if (!DateUtil.validDate(date.getText())) {
				errorMessage += "Le champ date est invalide!. Utiliser le format dd.mm.yyyy!\n";
			}

			if (description.getText() == null || description.getText().length() == 0) {
				errorMessage += "Le champ description est invalide!\n";
			}

			if (errorMessage.length() == 0) {
				return true;
			} else {
				MethodUtilitaire.errorMessageAlert("Formulaire invalide", "Veuillez remplir tous les champs",
						errorMessage);
			}

		}
		return false;
	}

}
