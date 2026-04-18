package univ_scheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import univ_scheduler.model.Groupe;

/**
 * Classe d'acces aux donnees des groupes.
 * @author Votre Nom
 * @version 1.0
 */
public class GroupeDAO {

    public List<Groupe> listerTous() {
        List<Groupe> liste = new ArrayList<>();

        String sql = "SELECT * FROM groupes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Groupe(
                    rs.getInt("id_groupe"),
                    rs.getString("nom"),
                    rs.getInt("id_class")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return liste;
    }

    public Groupe trouverParId(int id) {
        Groupe g = null;

        String sql = "SELECT * FROM groupes WHERE id_groupe = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    g = new Groupe(
                        rs.getInt("id_groupe"),
                        rs.getString("nom"),
                        rs.getInt("id_class")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return g;
    }

    public void ajouter(Groupe g) {

        String sql = "INSERT INTO groupes (id_groupe, nom, id_class) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, g.getId_Groupe());
            ps.setString(2, g.getNom());
            ps.setInt(3, g.getId_Class());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void modifier(Groupe g) {

        String sql = "UPDATE groupes SET nom=?, id_class=? WHERE id_groupe=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, g.getNom());
            ps.setInt(2, g.getId_Class());
            ps.setInt(3, g.getId_Groupe());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void supprimer(int id) {

        String sql = "DELETE FROM groupes WHERE id_groupe = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}