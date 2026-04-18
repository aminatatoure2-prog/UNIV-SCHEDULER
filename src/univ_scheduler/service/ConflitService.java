package univ_scheduler.service;

import univ_scheduler.dao.CreneauDAO;

import univ_scheduler.model.Creneau;
import java.util.List;

/**
 * Service de detection des conflits horaires.
 * @author Votre Nom
 * @version 1.0
 */
public class ConflitService {

    private CreneauDAO creneauDAO = new CreneauDAO();

    public boolean salleOccupee(int idSalle, String jour, int heure_Debut, int duree) {
        List<Creneau> creneaux = creneauDAO.listerParSalle(idSalle);
        Creneau nouveau = new Creneau(0, jour, heure_Debut, duree, 0, idSalle);
        for (Creneau c : creneaux) {
            if (c.chevauche(nouveau)) {
                return true;
            }
        }
        return false;
    }

    public boolean enseignantOccupe(int idEnseignant, String jour, int heure_Debut, int duree) {
        List<Creneau> tousLesCreneaux = creneauDAO.listerTous();
        Creneau nouveau = new Creneau(0, jour, heure_Debut, duree, 0, 0);
        for (Creneau c : tousLesCreneaux) {
            if (c.getId_Cours() == idEnseignant && c.chevauche(nouveau)) {
                return true;
            }
        }
        return false;
    }

    public boolean classeOccupee(int idClasse, String jour, int heure_Debut, int duree) {
        List<Creneau> tousLesCreneaux = creneauDAO.listerTous();
        Creneau nouveau = new Creneau(0, jour, heure_Debut, duree, 0, 0);
        for (Creneau c : tousLesCreneaux) {
            if (c.getId_Cours() == idClasse && c.chevauche(nouveau)) {
                return true;
            }
        }
        return false;
    }

    public boolean aucunConflit(int idSalle, int idEnseignant, int idClasse,
                                 String jour, int heure_Debut, int duree) {
        return !salleOccupee(idSalle, jour, heure_Debut, duree) &&
               !enseignantOccupe(idEnseignant, jour, heure_Debut, duree) &&
               !classeOccupee(idClasse, jour, heure_Debut, duree);
    }
}