package univ_scheduler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import univ_scheduler.model.Creneau;

/**
 * Classe d'acces aux donnees des creneaux.
 * @author Votre Nom
 * @version 1.0
 */
public class CreneauDAO {

    public List<Creneau> listerTous() {
        List<Creneau> liste = new ArrayList<>();

        String sql = "SELECT * FROM crenaux";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Creneau(
                    rs.getInt("id_crenequ"),
                    rs.getString("jour"),
                    rs.getInt("heure_debut"),
                    rs.getInt("duree"),
                    rs.getInt("id_cours"),
                    rs.getInt("id_salle")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return liste;
    }

    public Creneau trouverParId(int id) {
        Creneau c = null;

        String sql = "SELECT * FROM crenaux WHERE id_crenequ = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = new Creneau(
                        rs.getInt("id_crenequ"),
                        rs.getString("jour"),
                        rs.getInt("heure_debut"),
                        rs.getInt("duree"),
                        rs.getInt("id_cours"),
                        rs.getInt("id_salle")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return c;
    }

    public List<Creneau> listerParSalle(int idSalle) {
        List<Creneau> liste = new ArrayList<>();

        String sql = "SELECT * FROM crenaux WHERE id_salle = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSalle);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    liste.add(new Creneau(
                        rs.getInt("id_crenequ"),
                        rs.getString("jour"),
                        rs.getInt("heure_debut"),
                        rs.getInt("duree"),
                        rs.getInt("id_cours"),
                        rs.getInt("id_salle")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        return liste;
    }

    public void ajouter(Creneau c) {

        String sql = "INSERT INTO crenaux (id_crenequ, jour, heure_debut, duree, id_cours, id_salle) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getId_Creneau());
            ps.setString(2, c.getJour().name());
            ps.setInt(3, c.getHeure_Debut());
            ps.setInt(4, c.getDuree());
            ps.setInt(5, c.getId_Cours());
            ps.setInt(6, c.getId_Salle());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void modifier(Creneau c) {

        String sql = "UPDATE crenaux SET jour=?, heure_debut=?, duree=?, id_cours=?, id_salle=? WHERE id_crenequ=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getJour().name());
            ps.setInt(2, c.getHeure_Debut());
            ps.setInt(3, c.getDuree());
            ps.setInt(4, c.getId_Cours());
            ps.setInt(5, c.getId_Salle());
            ps.setInt(6, c.getId_Creneau());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void supprimer(int id) {

        String sql = "DELETE FROM crenaux WHERE id_crenequ = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}