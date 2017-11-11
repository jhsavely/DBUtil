/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

/**
 *
 * @author savely
 */
public class  ConnectionProvider {

    private static Connection con = null;
    public static Statement stmt = null;

    public static Connection getConnection() {
        if (con != null) {
            return con;
        } else {
            try {
                String driver = "com.mysql.jdbc.Driver";
                String url = "jdbc:mysql://localhost:3306/gamers";
                String user = "root";
                String password = "rithm";
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException | SQLException cnfe) {
                System.out.println(cnfe);
            }
            return con;
        }

    }

}
