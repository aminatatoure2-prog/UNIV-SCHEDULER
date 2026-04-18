package univ_scheduler.model;

/**
 * Classe representant un cours universitaire.
 * @author Votre Nom
 * @version 1.0
 */

public class Creneau {

    public enum Jour {
        LUNDI, MARDI, MERCREDI, JEUDI, VENDREDI, SAMEDI;
    }

    private int  id_Creneau;
    private Jour jour;
    private int  heure_Debut;
    private int  duree;
    private int  id_Cours;
    private int  id_Salle;

    public Creneau() {}

    public Creneau(int id_Creneau, String jour, int heure_Debut,
                   int duree, int id_Cours, int id_Salle) {
        this.id_Creneau  = id_Creneau;
        this.jour        = Jour.valueOf(jour.toUpperCase());
        this.heure_Debut = heure_Debut;
        this.duree       = duree;
        this.id_Cours    = id_Cours;
        this.id_Salle    = id_Salle;
    }

    public boolean estLundi()    { 
    	return jour == Jour.LUNDI; 
    }
    
    public boolean estMardi()    {
    	return jour == Jour.MARDI;
    }
    public boolean estMercredi() {
    	return jour == Jour.MERCREDI;
    }
  
    public boolean estJeudi()    { 
    	return jour == Jour.JEUDI;
    }
    public boolean estVendredi() {
    	return jour == Jour.VENDREDI;
    }

    public boolean chevauche(Creneau autre) {
        if (this.id_Salle != autre.id_Salle) return false;
        if (this.jour     != autre.jour)     return false;
        return this.heure_Debut < autre.heure_Debut + autre.duree &&
               autre.heure_Debut < this.heure_Debut + this.duree;
    }

    @Override
    public String toString() {
        return "Creneau[id=" + id_Creneau + ", jour=" + jour +
               ", heure_Debut=" + heure_Debut +
               ", duree=" + duree +
               ", id_Cours=" + id_Cours +
               ", id_Salle=" + id_Salle + "]";
    }

	public int getId_Creneau() {
		return id_Creneau;
	}

	public void setId_Creneau(int id_Creneau) {
		this.id_Creneau = id_Creneau;
	}

	public Jour getJour() {
		return jour;
	}

	public void setJour(Jour jour) {
		this.jour = jour;
	}

	public int getHeure_Debut() {
		return heure_Debut;
	}

	public void setHeure_Debut(int heure_Debut) {
		this.heure_Debut = heure_Debut;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public int getId_Cours() {
		return id_Cours;
	}

	public void setId_Cours(int id_Cours) {
		this.id_Cours = id_Cours;
	}

	public int getId_Salle() {
		return id_Salle;
	}

	public void setId_Salle(int id_Salle) {
		this.id_Salle = id_Salle;
	}

}