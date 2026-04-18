package univ_scheduler.model;
/**
 * Classe representant un cours universitaire.
 * @author Votre Nom
 * @version 1.0
 */


	public class Cours {

	    private int     id_Cours;
	    private int     semestre;
	    private int     id_Matiere;
	    private int     id_Enseignant;
	    private int     id_Class;
	    private int     id_Groupe;

	    public Cours(int idCours, int semestre, int idMatiere, 
	                 int idEnseignant, int idClass, int idGroupe) {
	        this.id_Cours      = idCours;
	        this.semestre     = semestre;
	        this.id_Matiere    = idMatiere;
	        this.id_Enseignant = idEnseignant;
	        this.id_Class      = idClass;
	        this.id_Groupe     = idGroupe;
	    }

	    public boolean estPremierSemestre()   { 
	    	return semestre == 1; }
	    public boolean estDeuxiemeSemestre()  { 
	    	return semestre == 2; }
	    public boolean estPourTouteLaClasse() { 
	    	return id_Groupe == 0; }

	    @Override
	    public String toString() {
	        return "Cours[id=" + id_Cours + ", semestre=" + semestre +
	               ", id_Matiere=" + id_Matiere +
	               ", id_Enseignant=" + id_Enseignant +
	               ", id_Class=" + id_Class +
	               ", id_Groupe=" + id_Groupe + "]";
	    }

		public int getId_Cours() {
			return id_Cours;
		}

		public void setId_Cours(int id_Cours) {
			this.id_Cours = id_Cours;
		}

		public int getSemestre() {
			return semestre;
		}

		public void setSemestre(int semestre) {
			this.semestre = semestre;
		}

		public int getId_Matiere() {
			return id_Matiere;
		}

		public void setId_Matiere(int id_Matiere) {
			this.id_Matiere = id_Matiere;
		}

		public int getId_Enseignant() {
			return id_Enseignant;
		}

		public void setId_Enseignant(int id_Enseignant) {
			this.id_Enseignant = id_Enseignant;
		}

		public int getId_Class() {
			return id_Class;
		}

		public void setId_Class(int id_Class) {
			this.id_Class = id_Class;
		}

		public int getId_Groupe() {
			return id_Groupe;
		}

		public void setId_Groupe(int id_Groupe) {
			this.id_Groupe = id_Groupe;
		}

}
