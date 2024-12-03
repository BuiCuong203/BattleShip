package server.dao;

import java.sql.Connection;
import java.sql.DriverManager;


public class DAO {
    protected Connection con;

    public DAO() {
        final String DATABASE_NAME = "battleshipdb"; // TODO FILL YOUR DATABASE NAME
        final String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;
        final String DB_USERNAME = "root";  // TODO FILL YOUR DATABASE USER
        final String DB_PASSWORD = "buicuong2003"; // TODO FILL YOUR DATABASE PASSWORD
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection to database failed");
        }
    }
}

 
