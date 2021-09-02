package brillant.soft.bean;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import brillant.soft.util.DateUtil;
import brillant.soft.util.LocalDateAdapter;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Flux {

	private IntegerProperty idFlux;
	private ObjectProperty<LocalDate> date;
	private FloatProperty montant;
	private StringProperty typeFlux;
	private StringProperty typePaiement;
	private StringProperty description;
	
	public Flux() {
		
	}
	
	public Flux(String date, String typeFlux) {
		this.date = new SimpleObjectProperty<LocalDate>(LocalDate.of(2020, 8, 27));
		this.typeFlux = new SimpleStringProperty(typeFlux);
	}
///////////////////////////////////////// DATE //////////////////////////////////////////////////
	
	@XmlJavaTypeAdapter(value = LocalDateAdapter.class)
	public LocalDate getDate() {
		return date.get();
	}

	public void setDate(LocalDate date) {
		this.date = new SimpleObjectProperty<LocalDate>(date);
	}
	
	public ObjectProperty<LocalDate> dateProperty() {
		return date;
	}
///////////////////////////////////////// MONTANT /////////////////////////////////////////////////
	
	public float getMontant() {
		return montant.get();
	}

	public void setMontant(float montant) {
		this.montant = new SimpleFloatProperty(montant);
	}
	
	public FloatProperty montantProperty() {
		return montant;
	}
//////////////////////////////////////// TYPE DE FLUX //////////////////////////////////////////////
	
	public String getTypeFlux() {
		return typeFlux.get();
	}

	public void setTypeFlux(String typeFlux) {
		System.out.println("Type de flux :"+typeFlux);
		this.typeFlux = new SimpleStringProperty(typeFlux);
	}

	public StringProperty typeFluxProperty() {
		return typeFlux;
	}
/////////////////////////////////////// TYPE PAIEMENT //////////////////////////////////////////////	
	
	public String getTypePaiement() {
		return typePaiement.get();
	}

	public void setTypePaiement(String typePaiement) {
		this.typePaiement = new SimpleStringProperty(typePaiement);
	}
	
	public StringProperty typePaiementProperty() {
		return typePaiement;
	}
//////////////////////////////////// Description ///////////////////////////////////////////////////
	
	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description = new SimpleStringProperty(description);
	}
	
	public StringProperty descriptionProperty() {
		return description;
	}
//////////////////////////////////// ID /////////////////////////////////////////////////////////////
	public Integer getIdFlux() {
		return idFlux.get();
	}
	
	
	
	
}
