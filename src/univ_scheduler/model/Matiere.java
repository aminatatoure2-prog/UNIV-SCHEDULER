package univ_scheduler.model;

/**
 * Classe representant une matiere enseignee.
 * @author Votre Nom
 * @version 1.0
 */

public class Matiere {
	private int id_Matiere;
	private String nom;
	private String code;
	private int volumeHoraire;
	
	public Matiere(int id_Matiere,String nom,String code,int volumeHoraire) {
		this.id_Matiere=id_Matiere;
		this.nom=nom;
		this.code=code;
		this.volumeHoraire=volumeHoraire;
	}
	public String getInfoComplete() {
        return code + " — " + nom + " (" + volumeHoraire + "h)";
    }
	 @Override
	    public String toString() {
	        return getInfoComplete();
	    }

	public int getId_Matiere() {
		return id_Matiere;
	}

	public void setId_Matiere(int id_Matiere) {
		this.id_Matiere = id_Matiere;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getVolumeHoraire() {
		return volumeHoraire;
	}

	public void setVolumeHoraire(int volumeHoraire) {
		this.volumeHoraire = volumeHoraire;
	}
	

}
