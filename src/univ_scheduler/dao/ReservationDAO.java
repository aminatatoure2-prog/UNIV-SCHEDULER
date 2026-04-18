package univ_scheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import univ_scheduler.model.Reservation;

/**
 * Classe d'acces aux donnees des creneaux.
 * @author Votre Nom
 * @version 1.0
 */
public class ReservationDAO {

    public List<Reservation> listerTous() {
        List<Reservation> liste = new ArrayList<Reservation>();
        String sql = "SELECT * FROM reservations_ponctuelles";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Reservation(
                    rs.getInt("id_reservation"),
                    rs.getString("date_reservation"),
                    rs.getString("motif"),
                    rs.getString("statut"),
                    rs.getInt("id_salle"),
                    rs.getInt("id_utilisateur")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return liste;
    }

    public Reservation trouverParId(int id) {
        Reservation r = null;
        String sql = "SELECT * FROM reservations_ponctuelles WHERE id_reservation = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    r = new Reservation(
                        rs.getInt("id_reservation"),
                        rs.getString("date_reservation"),
                        rs.getString("motif"),
                        rs.getString("statut"),
                        rs.getInt("id_salle"),
                        rs.getInt("id_utilisateur")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return r;
    }

    public List<Reservation> listerParUtilisateur(int idUtilisateur) {
        List<Reservation> liste = new ArrayList<Reservation>();
        String sql = "SELECT * FROM reservations_ponctuelles WHERE id_utilisateur = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUtilisateur);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    liste.add(new Reservation(
                        rs.getInt("id_reservation"),
                        rs.getString("date_reservation"),
                        rs.getString("motif"),
                        rs.getString("statut"),
                        rs.getInt("id_salle"),
                        rs.getInt("id_utilisateur")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return liste;
    }

    public void ajouter(Reservation r) {
        String sql = "INSERT INTO reservations_ponctuelles " +
                     "(date_reservation, motif, statut, id_salle, id_utilisateur) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getDate_Reservation());
            ps.setString(2, r.getMotif());
            ps.setString(3, r.getStatut().name());
            ps.setInt(4, r.getId_Salle());
            ps.setInt(5, r.getId_Utilisateur());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void modifier(Reservation r) {
        String sql = "UPDATE reservations_ponctuelles SET " +
                     "date_reservation=?, motif=?, statut=?, id_salle=?, id_utilisateur=? " +
                     "WHERE id_reservation=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getDate_Reservation());
            ps.setString(2, r.getMotif());
            ps.setString(3, r.getStatut().name());
            ps.setInt(4, r.getId_Salle());
            ps.setInt(5, r.getId_Utilisateur());
            ps.setInt(6, r.getId_Reservation());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM reservations_ponctuelles WHERE id_reservation = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public List<Integer> getSallesOccupees(String date, String heureDebut, String heureFin) {
        List<Integer> sallesOccupees = new ArrayList<Integer>();

        String sql = "SELECT id_salle FROM reservations_ponctuelles " +
                     "WHERE date_reservation = ? AND (? < heure_fin AND ? > heure_debut)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, date);
            ps.setString(2, heureDebut);
            ps.setString(3, heureFin);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sallesOccupees.add(rs.getInt("id_salle"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return sallesOccupees;
    }
}