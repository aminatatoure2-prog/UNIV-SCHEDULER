package univ_scheduler.service;

import univ_scheduler.dao.NotificationDAO;

import univ_scheduler.model.Notification;
import univ_scheduler.model.Notification.TypeNotif;
import java.util.List;

/**
 * Service de gestion des notifications.
 * @author Votre Nom
 * @version 1.0
 */


public class NotificationService {

    private NotificationDAO notificationDAO = new NotificationDAO();

    public void creerNotification(int idUtilisateur, String type) {
        Notification n = new Notification(0, type, idUtilisateur);
        notificationDAO.ajouter(n);
    }

    public void notifierConflit(int idUtilisateur) {
        creerNotification(idUtilisateur, TypeNotif.CONFLIT.name());
    }

    public void notifierChangementSalle(int idUtilisateur) {
        creerNotification(idUtilisateur, TypeNotif.CHANGEMENT_SALLE.name());
    }

    public void notifierRappel(int idUtilisateur) {
        creerNotification(idUtilisateur, TypeNotif.RAPPEL.name());
    }

    public void notifierInfo(int idUtilisateur) {
        creerNotification(idUtilisateur, TypeNotif.INFO.name());
    }

    public List<Notification> getNotificationsUtilisateur(int idUtilisateur) {
        return notificationDAO.listerParUtilisateur(idUtilisateur);
    }

    public void supprimerNotification(int idNotification) {
        notificationDAO.supprimer(idNotification);
    }
}