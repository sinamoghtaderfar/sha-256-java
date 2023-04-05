package org.Hash.Helper;

import java.sql.*;

public class ConnectionProvider {
    public static Connection getSqlConnection(){
        try{
            String dbDriver = "com.mysql.cj.jdbc.Driver";
            String dbUrl = "jdbc:mysql://localhost:3306/hash";
            String dbUsername = "";
            String dbPassword = "";
            Class.forName(dbDriver);
            Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
            return connection;
        }catch (Exception error){
            System.out.println(error);
            return null;
        }
    }
}
