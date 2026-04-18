package univ_scheduler.model;
/**
 * Classe representant une salle universitaire.
 * @author Votre Nom
 * @version 1.0
 */

public class Equipement {

	    private int    id_Equipement;
	    private String nom;
	    private String type;

	    public Equipement() {}

	    public Equipement(int id_Equipement, String nom, String type) {
	        this.id_Equipement = id_Equipement;
	        this.nom           = nom;
	        this.type          = type;
	    }

	    public boolean estAudiovisuel()  {
	    	return "Audiovisuel".equals(type); }
	    public boolean estInformatique() { 
	    	return "Informatique".equals(type); }
	    public boolean estReseau()  {
	    	return "Reseau".equals(type); }
	    public boolean estConfort()      {
	    	return "Confort".equals(type); }
	    public boolean estMobilier() {
	    	return "Mobilier".equals(type); }

	    @Override
	    public String toString() {
	        return "Equipement[id=" + id_Equipement +
	               ", nom=" + nom + ", type=" + type + "]";
	    }

		public int getId_Equipement() {
			return id_Equipement;
		}

		public void setId_Equipement(int id_Equipement) {
			this.id_Equipement = id_Equipement;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

	  
	}



