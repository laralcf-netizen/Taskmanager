package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final String URL = "jdbc:sqlite:taskmanager.db";

    public static Connection conectar() throws SQLException {
        try {
            
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do SQLite não foi encontrado nas bibliotecas: " + e.getMessage());
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar com o SQLite: " + e.getMessage());
        }
    }
}