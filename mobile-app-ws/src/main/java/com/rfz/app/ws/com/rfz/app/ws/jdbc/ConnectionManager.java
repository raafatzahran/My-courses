package com.rfz.app.ws.com.rfz.app.ws.jdbc;

//import com.mysql.jdbc.Connection;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static String url = "jdbc:mysql://localhost:3306/photo_app";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "raafat";
    private static String password = "raafat";
    private static Connection con;
    private static String urlstring;

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName(driverName).newInstance());
            //Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found.");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
