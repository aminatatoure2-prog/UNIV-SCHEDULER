package univ_scheduler.model;
/**
 * Classe representant une salle universitaire.
 * @author Votre Nom
 * @version 1.0
 */


public class Salle {
	/**
     * Constructeur principal
     */
	    public enum typeSalle {
	        TD, TP, AMPHI;

	        public String getlibelle() {
	            switch (this) {
	                case TD:return "Salle de TD";
	                case TP: return  "Salle de TP";
	                case AMPHI :return "Amphi";
	                default: return"";
	            }
	        }
	    }

	    private int       id_Salle;
	    private String    numero;
	    private int       capacite;
	    private typeSalle type;
	    private int       id_Batiment;

	    public Salle() {}
	    
	    /**
	     * Vérifie si la salle peut accueillir des personnes
	     */

	    public Salle(int id_Salle, String numero, int capacite,
	                 typeSalle type, int id_Batiment) {
	        this.id_Salle    = id_Salle;
	        this.numero      = numero;
	        this.capacite    = capacite;
	        this.type        = type;
	        this.id_Batiment = id_Batiment;
	    }

	    public boolean peutAccueillir(int nbpersonne) {
	    	return capacite >= nbpersonne; }
	    public boolean estAmphi() {
	    	return type == typeSalle.AMPHI; }
	    public boolean estTD()    {
	    	return type == typeSalle.TD; }
	    public boolean estTP()    {
	    	return type == typeSalle.TP; }

	    @Override
	    public String toString() {
	        return "Salle[id=" + id_Salle + ", numero=" + numero +
	               ", capacite=" + capacite + ", type=" + type +
	               ", id_Batiment=" + id_Batiment + "]";
	    }

		public int getId_Salle() {
			return id_Salle;
		}

		public void setId_Salle(int id_Salle) {
			this.id_Salle = id_Salle;
		}

		public String getNumero() {
			return numero;
		}

		public void setNumero(String numero) {
			this.numero = numero;
		}

		public int getCapacite() {
			return capacite;
		}

		public void setCapacite(int capacite) {
			this.capacite = capacite;
		}

		public typeSalle getType() {
			return type;
		}

		public void setType(typeSalle type) {
			this.type = type;
		}

		public int getId_Batiment() {
			return id_Batiment;
		}

		public void setId_Batiment(int id_Batiment) {
			this.id_Batiment = id_Batiment;
		}

	    
	
}
	