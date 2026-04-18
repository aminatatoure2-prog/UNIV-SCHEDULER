package univ_scheduler.service;


import univ_scheduler.dao.SalleDAO;

import univ_scheduler.model.Salle;
import univ_scheduler.model.Salle.typeSalle;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de detection des conflits horaires.
 * @author Votre Nom
 * @version 1.0
 */

public class RechercheSalleService {

    private SalleDAO     salleDAO      = new SalleDAO();
    private ConflitService conflitService = new ConflitService();

    public List<Salle> sallesDisponibles(String jour, int heure_Debut, int duree) {
        List<Salle> toutes     = salleDAO.listerTous();
        List<Salle> disponibles = new ArrayList<Salle>();
        for (Salle s : toutes) {
            if (!conflitService.salleOccupee(s.getId_Salle(), jour, heure_Debut, duree)) {
                disponibles.add(s);
            }
        }
        return disponibles;
    }

    public List<Salle> sallesDisponiblesParType(String jour, int heure_Debut,
                                                 int duree, typeSalle type) {
        List<Salle> disponibles      = sallesDisponibles(jour, heure_Debut, duree);
        List<Salle> disponiblesParType = new ArrayList<Salle>();
        for (Salle s : disponibles) {
            if (s.getType() == type) {
                disponiblesParType.add(s);
            }
        }
        return disponiblesParType;
    }

    public List<Salle> sallesDisponiblesParCapacite(String jour, int heure_Debut,
                                                     int duree, int capaciteMin) {
        List<Salle> disponibles         = sallesDisponibles(jour, heure_Debut, duree);
        List<Salle> disponiblesCapacite  = new ArrayList<Salle>();
        for (Salle s : disponibles) {
            if (s.peutAccueillir(capaciteMin)) {
                disponiblesCapacite.add(s);
            }
        }
        return disponiblesCapacite;
    }

    public List<Salle> rechercherSalle(String jour, int heure_Debut, int duree,
                                        typeSalle type, int capaciteMin) {
        List<Salle> disponibles = sallesDisponibles(jour, heure_Debut, duree);
        List<Salle> resultat    = new ArrayList<Salle>();
        for (Salle s : disponibles) {
            if (s.getType() == type && s.peutAccueillir(capaciteMin)) {
                resultat.add(s);
            }
        }
        return resultat;
    }
}