package univ_scheduler.model;

/**
* Classe representant une classe d'etudiants.
* @author Votre Nom
* @version 1.0
*/


public class Classe {

	    private int    id_Class;
	    private String nom;
	    private String filiere;
	    private String niveau;


	    public Classe(int id_Class, String nom,
	                  String filiere, String niveau) {
	        this.id_Class = id_Class;
	        this.nom      = nom;
	        this.filiere  = filiere;
	        this.niveau   = niveau;
	    }

	    public boolean estLicence() { 
	    	return niveau.startsWith("L"); }
	    public boolean estMaster()  { 
	    	return niveau.startsWith("M"); }

	    public String getInfoComplete() {
	        return nom + " — " + filiere + " (" + niveau + ")";
	    }

	    @Override
	    public String toString() {
	        return getInfoComplete();
	    }

		public int getId_Class() {
			return id_Class;
		}

		public void setId_Class(int id_Class) {
			this.id_Class = id_Class;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getFiliere() {
			return filiere;
		}

		public void setFiliere(String filiere) {
			this.filiere = filiere;
		}

		public String getNiveau() {
			return niveau;
		}

		public void setNiveau(String niveau) {
			this.niveau = niveau;
		}

	    
}
