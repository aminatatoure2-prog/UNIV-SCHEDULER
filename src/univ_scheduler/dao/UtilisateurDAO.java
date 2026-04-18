package univ_scheduler.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import univ_scheduler.model.Utilisateur;

public class UtilisateurDAO {

    public List<Utilisateur> listerTous() {
        List<Utilisateur> liste = new ArrayList<>();

        String sql = "SELECT * FROM utilisateurs";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Utilisateur(
                    rs.getInt("id_utilisateur"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("role")
                ));
            }

        } catch (SQLException e) {
            System.err.println("UtilisateurDAO.listerTous : " + e.getMessage());
        }

        return liste;
    }

    public Utilisateur trouverParId(int id) {
        Utilisateur u = null;

        String sql = "SELECT * FROM utilisateurs WHERE id_utilisateur = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Utilisateur(
                        rs.getInt("id_utilisateur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("role")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("UtilisateurDAO.trouverParId : " + e.getMessage());
        }

        return u;
    }

    public Utilisateur connecter(String email, String role) {
        Utilisateur u = null;

        String sql = "SELECT * FROM utilisateurs WHERE email = ? AND role = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, role);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Utilisateur(
                        rs.getInt("id_utilisateur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("role")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("UtilisateurDAO.connecter : " + e.getMessage());
        }

        return u;
    }

    public void ajouter(Utilisateur u) {
        String sql = "INSERT INTO utilisateurs (nom, prenom, email, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getRole().name());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("UtilisateurDAO.ajouter : " + e.getMessage());
        }
    }

    public void modifier(Utilisateur u) {
        String sql = "UPDATE utilisateurs SET nom=?, prenom=?, email=?, role=? WHERE id_utilisateur=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getRole().name());
            ps.setInt(5, u.getId_Utilisateur());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("UtilisateurDAO.modifier : " + e.getMessage());
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM utilisateurs WHERE id_utilisateur = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("UtilisateurDAO.supprimer : " + e.getMessage());
        }
    }
}