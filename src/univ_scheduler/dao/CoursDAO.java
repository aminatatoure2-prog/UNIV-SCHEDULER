package univ_scheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import univ_scheduler.model.Cours;

/**
 * Classe d'acces aux donnees des groupes.
 * @author Votre Nom
 * @version 1.0
 */

public class CoursDAO {

    public List<Cours> listerTous() {
        List<Cours> liste = new ArrayList<>();

        String sql = "SELECT * FROM cours";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Cours(
                    rs.getInt("id_cours"),
                    rs.getInt("semestre"),
                    rs.getInt("id_matiere"),
                    rs.getInt("id_enseignant"),
                    rs.getInt("id_class"),
                    rs.getInt("id_groupe")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return liste;
    }

    public Cours trouverParId(int id) {
        Cours c = null;

        String sql = "SELECT * FROM cours WHERE id_cours = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = new Cours(
                        rs.getInt("id_cours"),
                        rs.getInt("semestre"),
                        rs.getInt("id_matiere"),
                        rs.getInt("id_enseignant"),
                        rs.getInt("id_class"),
                        rs.getInt("id_groupe")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return c;
    }

    public List<Cours> listerParEnseignant(int idEnseignant) {
        List<Cours> liste = new ArrayList<>();

        String sql = "SELECT * FROM cours WHERE id_enseignant = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEnseignant);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    liste.add(new Cours(
                        rs.getInt("id_cours"),
                        rs.getInt("semestre"),
                        rs.getInt("id_matiere"),
                        rs.getInt("id_enseignant"),
                        rs.getInt("id_class"),
                        rs.getInt("id_groupe")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return liste;
    }

    public void ajouter(Cours c) {
        String sql = "INSERT INTO cours (id_cours, semestre, id_matiere, id_enseignant, id_class, id_groupe) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getId_Cours());
            ps.setInt(2, c.getSemestre());
            ps.setInt(3, c.getId_Matiere());
            ps.setInt(4, c.getId_Enseignant());
            ps.setInt(5, c.getId_Class());
            ps.setInt(6, c.getId_Groupe());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void modifier(Cours c) {
        String sql = "UPDATE cours SET semestre=?, id_matiere=?, id_enseignant=?, id_class=?, id_groupe=? WHERE id_cours=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getSemestre());
            ps.setInt(2, c.getId_Matiere());
            ps.setInt(3, c.getId_Enseignant());
            ps.setInt(4, c.getId_Class());
            ps.setInt(5, c.getId_Groupe());
            ps.setInt(6, c.getId_Cours());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM cours WHERE id_cours = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}