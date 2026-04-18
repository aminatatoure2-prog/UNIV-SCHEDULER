package univ_scheduler.model;

//Classe.java
/**
* Classe representant une classe d'etudiants.
* @author Votre Nom
* @version 1.0
*/

public class Groupe {

	    private int    id_Groupe;
	    private String nom;
	    private int    id_Class;

	    public Groupe(int id_Groupe, String nom, int id_Class) {
	        this.id_Groupe = id_Groupe;
	        this.nom       = nom;
	        this.id_Class  = id_Class;
	    }

	    public boolean estGroupeTD() {
	    	return nom.contains("TD"); }
	    public boolean estGroupeTP() { 
	    	return nom.contains("TP"); }

	    @Override
	    public String toString() {
	        return "Groupe[id=" + id_Groupe + ", nom=" + nom +
	               ", id_Class=" + id_Class + "]";
	    }

		public int getId_Groupe() {
			return id_Groupe;
		}

		public void setId_Groupe(int id_Groupe) {
			this.id_Groupe = id_Groupe;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public int getId_Class() {
			return id_Class;
		}

		public void setId_Class(int id_Class) {
			this.id_Class = id_Class;
		}

}
