package univ_scheduler.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import univ_scheduler.model.Signalement;

public class SignalementDAO {

    public List<Signalement> listerTous() {
        List<Signalement> liste = new ArrayList<>();

        String sql = "SELECT * FROM signalement";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Signalement(
                    rs.getInt("id_signalement"),
                    rs.getString("statut"),
                    rs.getString("date_signalement"),
                    rs.getString("date_resolution"),
                    rs.getInt("id_salle"),
                    rs.getInt("id_utilisateur")
                ));
            }

        } catch (SQLException e) {
            System.err.println("SignalementDAO.listerTous : " + e.getMessage());
        }

        return liste;
    }

    public Signalement trouverParId(int id) {
        Signalement s = null;

        String sql = "SELECT * FROM signalement WHERE id_signalement = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s = new Signalement(
                        rs.getInt("id_signalement"),
                        rs.getString("statut"),
                        rs.getString("date_signalement"),
                        rs.getString("date_resolution"),
                        rs.getInt("id_salle"),
                        rs.getInt("id_utilisateur")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("SignalementDAO.trouverParId : " + e.getMessage());
        }

        return s;
    }

    public void ajouter(Signalement s) {
        String sql = "INSERT INTO signalement (statut, date_signalement, date_resolution, id_salle, id_utilisateur) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getStatut().name());
            ps.setString(2, s.getDate_Signalement());
            ps.setString(3, s.getDate_Resolution());
            ps.setInt(4, s.getId_Salle());
            ps.setInt(5, s.getId_Utilisateur());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SignalementDAO.ajouter : " + e.getMessage());
        }
    }

    public void modifier(Signalement s) {
        String sql = "UPDATE signalement SET statut=?, date_signalement=?, date_resolution=?, id_salle=?, id_utilisateur=? WHERE id_signalement=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getStatut().name());
            ps.setString(2, s.getDate_Signalement());
            ps.setString(3, s.getDate_Resolution());
            ps.setInt(4, s.getId_Salle());
            ps.setInt(5, s.getId_Utilisateur());
            ps.setInt(6, s.getId_Signalement());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SignalementDAO.modifier : " + e.getMessage());
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM signalement WHERE id_signalement = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SignalementDAO.supprimer : " + e.getMessage());
        }
    }
}