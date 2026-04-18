package univ_scheduler.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import univ_scheduler.model.Salle;
import univ_scheduler.model.Salle.typeSalle;

public class SalleDAO {

    public List<Salle> listerTous() {
        List<Salle> liste = new ArrayList<>();

        String sql = "SELECT * FROM salles";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                liste.add(new Salle(
                    rs.getInt("id_salle"),
                    rs.getString("numero"),
                    rs.getInt("capacite"),
                    typeSalle.valueOf(rs.getString("type").toUpperCase()),
                    rs.getInt("id_batiment")
                ));
            }

        } catch (SQLException e) {
            System.err.println("SalleDAO.listerTous : " + e.getMessage());
        }

        return liste;
    }

    public Salle trouverParId(int id) {
        Salle s = null;

        String sql = "SELECT * FROM salles WHERE id_salle = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    s = new Salle(
                        rs.getInt("id_salle"),
                        rs.getString("numero"),
                        rs.getInt("capacite"),
                        typeSalle.valueOf(rs.getString("type").toUpperCase()),
                        rs.getInt("id_batiment")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("SalleDAO.trouverParId : " + e.getMessage());
        }

        return s;
    }

    public List<Salle> listerParType(String type) {
        List<Salle> liste = new ArrayList<>();

        String sql = "SELECT * FROM salles WHERE type = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, type);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    liste.add(new Salle(
                        rs.getInt("id_salle"),
                        rs.getString("numero"),
                        rs.getInt("capacite"),
                        typeSalle.valueOf(rs.getString("type").toUpperCase()),
                        rs.getInt("id_batiment")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("SalleDAO.listerParType : " + e.getMessage());
        }

        return liste;
    }

    public void ajouter(Salle s) {
        String sql = "INSERT INTO salles (numero, capacite, type, id_batiment) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getNumero());
            ps.setInt(2, s.getCapacite());
            ps.setString(3, s.getType().name());
            ps.setInt(4, s.getId_Batiment());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SalleDAO.ajouter : " + e.getMessage());
        }
    }

    public void modifier(Salle s) {
        String sql = "UPDATE salles SET numero=?, capacite=?, type=?, id_batiment=? WHERE id_salle=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getNumero());
            ps.setInt(2, s.getCapacite());
            ps.setString(3, s.getType().name());
            ps.setInt(4, s.getId_Batiment());
            ps.setInt(5, s.getId_Salle());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SalleDAO.modifier : " + e.getMessage());
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM salles WHERE id_salle = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SalleDAO.supprimer : " + e.getMessage());
        }
    }
}