package univ_scheduler.model;
/**
 * Classe representant un batiment universitaire.
 * @author Votre Nom
 * @version 1.0
 */


public class Batiment {
	private int id_Batiment;
	private String nom;
	private String localisation;
	private int nb_etage;
	
	
	public Batiment(int id_Batiment,String nom,String localisation,int nb_etage) {
		this.id_Batiment=id_Batiment;
		this.nom=nom;
		this.localisation=localisation;
		this.nb_etage=nb_etage;	
	}
	public String getInfoComplete() {
        return nom + " — " + localisation + " (" + nb_etage + " etages)";
    }

    @Override
    public String toString() {
        return getInfoComplete();
    }


	public int getId_Batiment() {
		return id_Batiment;
	}


	public void setId_Batiment(int id_Batiment) {
		this.id_Batiment = id_Batiment;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getLocalisation() {
		return localisation;
	}


	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}


	public int getNb_etage() {
		return nb_etage;
	}


	public void setNb_etage(int nb_etage) {
		this.nb_etage = nb_etage;
	}
}
