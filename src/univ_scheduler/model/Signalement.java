package univ_scheduler.model;

/**
 * Classe representant un signalement de probleme.
 * @author Votre Nom
 * @version 1.0
 */

public class Signalement {

	    public enum Statut {
	        OUVERT, EN_COURS, RESOLU;
	    }

	    private int  id_Signalement;
	    private Statut statut;
	    private String date_Signalement;
	    private String date_Resolution;
	    private int   id_Salle;
	    private int   id_Utilisateur;


	    public Signalement(int id_Signalement, String statut,
	                       String date_Signalement, String date_Resolution,
	                       int id_Salle, int id_Utilisateur) {
	        this.id_Signalement   = id_Signalement;
	        this.statut           = Statut.valueOf(statut.toUpperCase());
	        this.date_Signalement = date_Signalement;
	        this.date_Resolution  = date_Resolution;
	        this.id_Salle         = id_Salle;
	        this.id_Utilisateur   = id_Utilisateur;
	    }

	    public boolean estOuvert()  {
	    	return statut == Statut.OUVERT; }
	    public boolean estEnCours() { 
	    	return statut == Statut.EN_COURS; }
	    public boolean estResolu()  { 
	    	return statut == Statut.RESOLU; }

	    @Override
	    public String toString() {
	        return "Signalement[id=" + id_Signalement +
	               ", statut=" + statut +
	               ", date=" + date_Signalement +
	               ", id_Salle=" + id_Salle +
	               ", id_Utilisateur=" + id_Utilisateur + "]";
	    }

		public int getId_Signalement() {
			return id_Signalement;
		}

		public void setId_Signalement(int id_Signalement) {
			this.id_Signalement = id_Signalement;
		}

		public Statut getStatut() {
			return statut;
		}

		public void setStatut(Statut statut) {
			this.statut = statut;
		}

		public String getDate_Signalement() {
			return date_Signalement;
		}

		public void setDate_Signalement(String date_Signalement) {
			this.date_Signalement = date_Signalement;
		}

		public String getDate_Resolution() {
			return date_Resolution;
		}

		public void setDate_Resolution(String date_Resolution) {
			this.date_Resolution = date_Resolution;
		}

		public int getId_Salle() {
			return id_Salle;
		}

		public void setId_Salle(int id_Salle) {
			this.id_Salle = id_Salle;
		}

		public int getId_Utilisateur() {
			return id_Utilisateur;
		}

		public void setId_Utilisateur(int id_Utilisateur) {
			this.id_Utilisateur = id_Utilisateur;
		}

	       
	

}
