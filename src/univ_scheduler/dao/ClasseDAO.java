package univ_scheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import univ_scheduler.model.Classe;

/**
 * Classe d'acces aux donnees des classes.
 * @author Votre Nom
 * @version 1.0
 */

public class ClasseDAO {

    public List<Classe> listerTous() {
        List<Classe> liste = new ArrayList<>();

        String sql = "SELECT * FROM classes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Classe(
                    rs.getInt("id_class"),
                    rs.getString("nom"),
                    rs.getString("filiere"),
                    rs.getString("niveau")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return liste;
    }

    public Classe trouverParId(int id) {
        Classe c = null;

        String sql = "SELECT * FROM classes WHERE id_class = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = new Classe(
                        rs.getInt("id_class"),
                        rs.getString("nom"),
                        rs.getString("filiere"),
                        rs.getString("niveau")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return c;
    }

    public void ajouter(Classe c) {
        String sql = "INSERT INTO classes (id_class, nom, filiere, niveau) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getId_Class());
            ps.setString(2, c.getNom());
            ps.setString(3, c.getFiliere());
            ps.setString(4, c.getNiveau());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void modifier(Classe c) {
        String sql = "UPDATE classes SET nom=?, filiere=?, niveau=? WHERE id_class=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNom());
            ps.setString(2, c.getFiliere());
            ps.setString(3, c.getNiveau());
            ps.setInt(4, c.getId_Class());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM classes WHERE id_class = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}