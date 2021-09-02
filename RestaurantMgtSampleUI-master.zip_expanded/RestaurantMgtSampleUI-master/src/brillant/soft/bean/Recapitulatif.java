package brillant.soft.bean;

import java.util.Date;

public class Recapitulatif {
    
	public Recapitulatif() {
		
	}
	
	private int idRecapitulatif;
	private Date date;
	private float caisse;
	private float om;
	private float cb;
	private float total;
	private float depense;
	private float solde;
	private String motif;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getCaisse() {
		return caisse;
	}
	public void setCaisse(float caisse) {
		this.caisse = caisse;
	}
	public float getOm() {
		return om;
	}
	public void setOm(float om) {
		this.om = om;
	}
	public float getCb() {
		return cb;
	}
	public void setCb(float cb) {
		this.cb = cb;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public float getDepense() {
		return depense;
	}
	public void setDepense(float depense) {
		this.depense = depense;
	}
	public float getSolde() {
		return solde;
	}
	public void setSolde(float solde) {
		this.solde = solde;
	}
	public String getMotif() {
		return motif;
	}
	public void setMotif(String motif) {
		this.motif = motif;
	}
	public int getIdRecapitulatif() {
		return idRecapitulatif;
	}
	
	
}
