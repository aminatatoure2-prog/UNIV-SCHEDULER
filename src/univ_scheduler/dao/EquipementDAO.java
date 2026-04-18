package univ_scheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import univ_scheduler.model.Equipement;

/**
 * Classe d'acces aux donnees des equipements.
 * @author Votre Nom
 * @version 1.0
 */
public class EquipementDAO {

    public List<Equipement> listerTous() {
        List<Equipement> liste = new ArrayList<>();

        String sql = "SELECT * FROM equipements";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Equipement(
                    rs.getInt("id_equipement"),
                    rs.getString("nom"),
                    rs.getString("type")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return liste;
    }

    public Equipement trouverParId(int id) {
        Equipement e = null;

        String sql = "SELECT * FROM equipements WHERE id_equipement = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    e = new Equipement(
                        rs.getInt("id_equipement"),
                        rs.getString("nom"),
                        rs.getString("type")
                    );
                }
            }

        } catch (SQLException e2) {
            System.out.println("Erreur : " + e2.getMessage());
        }

        return e;
    }

    public void ajouter(Equipement e) {

        String sql = "INSERT INTO equipements (id_equipement, nom, type) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, e.getId_Equipement());
            ps.setString(2, e.getNom());
            ps.setString(3, e.getType());

            ps.executeUpdate();

        } catch (SQLException e2) {
            System.out.println("Erreur : " + e2.getMessage());
        }
    }

    public void modifier(Equipement e) {

        String sql = "UPDATE equipements SET nom=?, type=? WHERE id_equipement=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getNom());
            ps.setString(2, e.getType());
            ps.setInt(3, e.getId_Equipement());

            ps.executeUpdate();

        } catch (SQLException e2) {
            System.out.println("Erreur : " + e2.getMessage());
        }
    }

    public void supprimer(int id) {

        String sql = "DELETE FROM equipements WHERE id_equipement = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}