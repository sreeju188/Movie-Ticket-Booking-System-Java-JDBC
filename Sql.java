package com.movieticket;

import java.sql.Connection;
import java.sql.DriverManager;

public class Sql {

    static Connection conn = null;

    public static Connection getConnection() {

        if (conn != null)
            return conn;

        String database = "Database name";//your dadabase name
        String username = "root";
        String password = "your  password";//your sql workbench password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/" + database,
                username,
                password
            );
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}

