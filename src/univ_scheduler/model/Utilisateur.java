package univ_scheduler.model;
/**
 * Classe representant un utilisateur de l'application.
 * @author Votre Nom
 * @version 1.0
 */


public class Utilisateur {

    public enum Role {
        ADMIN, GESTIONNAIRE, ENSEIGNANT, ETUDIANT;

        public String getlibelle() {
             switch (this) {
                case ADMIN: return "Administrateur";
                case GESTIONNAIRE: return"Gestionnaire EDT";
                case ENSEIGNANT: return "Enseignant";
                case ETUDIANT: return "Etudiant";
                default: return"";
            }
        }
    }

    private int    id_Utilisateur;
    private String nom;
    private String prenom;
    private String email;
    private Role   role;

    public Utilisateur() {}

    public Utilisateur(int id_Utilisateur, String nom,
                       String prenom, String email, String role) {
        this.id_Utilisateur = id_Utilisateur;
        this.nom            = nom;
        this.prenom         = prenom;
        this.email          = email;
        this.role           = Role.valueOf(role.toUpperCase());
    }

    public String  getNomComplet()  {
    	return prenom + " " + nom;
    }
    public boolean isADMIN(){ 
    	return role == Role.ADMIN;
    }
    public boolean isGESTIONNAIRE() { 
    	return role == Role.GESTIONNAIRE;
    }
    public boolean isENSEIGNANT() { 
    	return role == Role.ENSEIGNANT; 
   	}
    public boolean isETUDIANT() {
    	return role == Role.ETUDIANT; 
    }
    public boolean peutModifier(){ 
    	return role == Role.ADMIN || role == Role.GESTIONNAIRE;
    }

    @Override
    public String toString() {
        return getNomComplet() + " [" + role.getlibelle() + "]";
    }

	public int getId_Utilisateur() {
		return id_Utilisateur;
	}

	public void setId_Utilisateur(int id_Utilisateur) {
		this.id_Utilisateur = id_Utilisateur;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


}