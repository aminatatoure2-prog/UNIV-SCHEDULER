package univ_scheduler.model;

/**
 * Classe representant une reservation de salle.
 * @author Votre Nom
 * @version 1.0
 */

public class Reservation {
	
	    public enum Statut {
	        EN_ATTENTE, CONFIRMEE, ANNULEE;
	    }

	    private int    id_Reservation;
	    private String date_Reservation;
	    private String motif;
	    private Statut statut;
	    private int    id_Salle;
	    private int    id_Utilisateur;

	    public Reservation() {}

	    public Reservation(int id_Reservation, String date_Reservation,
	                       String motif, String statut,
	                       int id_Salle, int id_Utilisateur) {
	        this.id_Reservation   = id_Reservation;
	        this.date_Reservation = date_Reservation;
	        this.motif            = motif;
	        this.statut           = Statut.valueOf(statut.toUpperCase());
	        this.id_Salle         = id_Salle;
	        this.id_Utilisateur   = id_Utilisateur;
	        
	        if (statut == null || statut.trim().isEmpty()) {
	            this.statut = Statut.EN_ATTENTE;
	        } else {
	            switch (statut.toLowerCase().trim()) {
	                case "confirmee":
	                    this.statut = Statut.CONFIRMEE;
	                    break;
	                case "annule":
	                case "annulee":
	                    this.statut = Statut.ANNULEE;
	                    break;
	                case "en_attente":
	                case "en attente":
	                    this.statut = Statut.EN_ATTENTE;
	                    break;
	                default:
	                    this.statut = Statut.EN_ATTENTE;
	                    break;
	            }
	            }
	    }

	    public boolean estConfirmee() {
	    	return statut == Statut.CONFIRMEE; }
	    public boolean estAnnulee()   {
	    	return statut == Statut.ANNULEE; }
	    public boolean estEnAttente() {
	    	return statut == Statut.EN_ATTENTE; }

	    @Override
	    public String toString() {
	        return "Reservation[id=" + id_Reservation +
	               ", date=" + date_Reservation +
	               ", motif=" + motif +
	               ", statut=" + statut +
	               ", id_Salle=" + id_Salle +
	               ", id_Utilisateur=" + id_Utilisateur + "]";
	    }

		public int getId_Reservation() {
			return id_Reservation;
		}

		public void setId_Reservation(int id_Reservation) {
			this.id_Reservation = id_Reservation;
		}

		public String getDate_Reservation() {
			return date_Reservation;
		}

		public void setDate_Reservation(String date_Reservation) {
			this.date_Reservation = date_Reservation;
		}

		public String getMotif() {
			return motif;
		}

		public void setMotif(String motif) {
			this.motif = motif;
		}

		public Statut getStatut() {
			return statut;
		}

		public void setStatut(Statut statut) {
			this.statut = statut;
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
