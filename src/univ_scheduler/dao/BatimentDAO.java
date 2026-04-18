package univ_scheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import univ_scheduler.model.Batiment;

/**
 * Classe d'accès aux données des bâtiments (version robuste).
 *
 * @author Votre Nom
 * @version 1.1
 */

public class BatimentDAO {

    public List<Batiment> listerTous() {
        List<Batiment> liste = new ArrayList<>();

        String sql = "SELECT * FROM batiments";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Batiment(
                    rs.getInt("id_batiment"),
                    rs.getString("nom"),
                    rs.getString("localisation"),
                    rs.getInt("nb_etage")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return liste;
    }

    public Batiment trouverParId(int id) {
        Batiment b = null;

        String sql = "SELECT * FROM batiments WHERE id_batiment = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    b = new Batiment(
                        rs.getInt("id_batiment"),
                        rs.getString("nom"),
                        rs.getString("localisation"),
                        rs.getInt("nb_etage")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return b;
    }

    public void ajouter(Batiment b) {
        String sql = "INSERT INTO batiments (nom, localisation, nb_etage) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getNom());
            ps.setString(2, b.getLocalisation());
            ps.setInt(3, b.getNb_etage());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void modifier(Batiment b) {
        String sql = "UPDATE batiments SET nom=?, localisation=?, nb_etage=? WHERE id_batiment=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getNom());
            ps.setString(2, b.getLocalisation());
            ps.setInt(3, b.getNb_etage());
            ps.setInt(4, b.getId_Batiment()); // ⚠️ vérifier nom exact

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM batiments WHERE id_batiment = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}