package univ_scheduler.model;
/**
 * Classe representant une notification utilisateur.
 * @author Votre Nom
 * @version 1.0
 */


public class Notification {

	    public enum TypeNotif {
	        CONFLIT, CHANGEMENT_SALLE, RAPPEL, INFO;

	        public String getlibelle() {
	            switch (this) {
	                case CONFLIT :return   "Conflit detecte";
	                case CHANGEMENT_SALLE: return "Changement de salle";
	                case RAPPEL:return  "Rappel";
	                case INFO: return   "Information";
	                default: return"";
	            }
	        }
	    }

	    private int       id_Notification;
	    private TypeNotif type;
	    private int       id_Utilisateur;

	    public Notification() {}

	    public Notification(int id_Notification, String type,
	                        int id_Utilisateur) {
	        this.id_Notification = id_Notification;
	        this.id_Utilisateur  = id_Utilisateur;
	        try {
	        	 this.type = TypeNotif.valueOf(type.toUpperCase());
	        }catch(Exception e) {
	        	this.type=TypeNotif.INFO;
	        }
	    }

	    public boolean estConflit()         { 
	    	return type == TypeNotif.CONFLIT; }
	    public boolean estChangementSalle() { 
	    	return type == TypeNotif.CHANGEMENT_SALLE; }
	    public boolean estRappel()          {
	    	return type == TypeNotif.RAPPEL; }
	    public boolean estInfo()            {
	    	return type == TypeNotif.INFO; }

	    @Override
	    public String toString() {
	        return "Notification[id=" + id_Notification +
	               ", type=" + type.getlibelle() +
	               ", id_Utilisateur=" + id_Utilisateur + "]";
	    }

		public int getId_Notification() {
			return id_Notification;
		}

		public void setId_Notification(int id_Notification) {
			this.id_Notification = id_Notification;
		}

		public TypeNotif getType() {
			return type;
		}

		public void setType(TypeNotif type) {
			this.type = type;
		}

		public int getId_Utilisateur() {
			return id_Utilisateur;
		}

		public void setId_Utilisateur(int id_Utilisateur) {
			this.id_Utilisateur = id_Utilisateur;
		}

	   
}
