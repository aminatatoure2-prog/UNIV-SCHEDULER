package univ_scheduler.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de connexion a la base de donnees MySQL.
 * @author Votre Nom
 * @version 1.1
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/univ_scheduler?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }

            return connection;

        } catch (ClassNotFoundException e) {
            System.out.println("Driver non trouve : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur connexion : " + e.getMessage());
        }

        return null;
    }
}