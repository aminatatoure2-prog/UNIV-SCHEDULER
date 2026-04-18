package univ_scheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import univ_scheduler.model.Matiere;

/**
 * Classe d'acces aux donnees des equipements.
 * @author Votre Nom
 * @version 1.0
 */
public class MatiereDAO {

    public List<Matiere> listerTous() {
        List<Matiere> liste = new ArrayList<Matiere>();
        String sql = "SELECT * FROM matiere";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Matiere(
                    rs.getInt("id_matiere"),
                    rs.getString("nom"),
                    rs.getString("code"),
                    rs.getInt("volume_horaire")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return liste;
    }

    public Matiere trouverParId(int id) {
        Matiere m = null;
        String sql = "SELECT * FROM matiere WHERE id_matiere = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    m = new Matiere(
                        rs.getInt("id_matiere"),
                        rs.getString("nom"),
                        rs.getString("code"),
                        rs.getInt("volume_horaire")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return m;
    }

    public void ajouter(Matiere m) {
        String sql = "INSERT INTO matiere (nom, code, volume_horaire) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getNom());
            ps.setString(2, m.getCode());
            ps.setInt(3, m.getVolumeHoraire());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void modifier(Matiere m) {
        String sql = "UPDATE matiere SET nom=?, code=?, volume_horaire=? WHERE id_matiere=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getNom());
            ps.setString(2, m.getCode());
            ps.setInt(3, m.getVolumeHoraire());
            ps.setInt(4, m.getId_Matiere());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM matiere WHERE id_matiere = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}