package univ_scheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import univ_scheduler.model.Notification;

/**
 * Classe d'acces aux donnees des notifications.
 * @author Votre Nom
 * @version 1.0
 */
public class NotificationDAO {

    public List<Notification> listerTous() {
        List<Notification> liste = new ArrayList<Notification>();
        String sql = "SELECT * FROM notifications";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Notification(
                    rs.getInt("id_notification"),
                    rs.getString("type"),
                    rs.getInt("id_utilisateur")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return liste;
    }

    public Notification trouverParId(int id) {
        Notification n = null;
        String sql = "SELECT * FROM notifications WHERE id_notification = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    n = new Notification(
                        rs.getInt("id_notification"),
                        rs.getString("type"),
                        rs.getInt("id_utilisateur")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return n;
    }

    public List<Notification> listerParUtilisateur(int idUtilisateur) {
        List<Notification> liste = new ArrayList<Notification>();
        String sql = "SELECT * FROM notifications WHERE id_utilisateur = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUtilisateur);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    liste.add(new Notification(
                        rs.getInt("id_notification"),
                        rs.getString("type"),
                        rs.getInt("id_utilisateur")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return liste;
    }

    public void ajouter(Notification n) {
        String sql = "INSERT INTO notifications (type, id_utilisateur) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, n.getType().name());
            ps.setInt(2, n.getId_Utilisateur());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void modifier(Notification n) {
        String sql = "UPDATE notifications SET type=?, id_utilisateur=? WHERE id_notification=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, n.getType().name());
            ps.setInt(2, n.getId_Utilisateur());
            ps.setInt(3, n.getId_Notification());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM notifications WHERE id_notification = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}