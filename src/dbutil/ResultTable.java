/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

/**
 *
 * @author jsavely
 */
public class ResultTable {

    Connection con = ConnectionProvider.getConnection();

    public int myRandomNumber(int top) {
        Random rand = new Random();
        int value = rand.nextInt(top);
        return value;
    }

    public String getRandName() {
        String randName = "";
        try {
            Statement st = con.createStatement();
            int count = 0;
            ResultSet rs0 = st.executeQuery("SELECT COUNT(*) FROM names ");
            while (rs0.next()) {
                count = rs0.getInt(1);
            }
            System.out.println(count);
            int myValName = myRandomNumber(count);
            String selectLastName = "SELECT Name FROM names WHERE id =" + myValName;
            ResultSet rs = st.executeQuery(selectLastName);
            while (rs.next()) {
                randName = rs.getString(1);
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameParser.class.getName()).log(Level.SEVERE, "SQLException", ex);
            System.out.println("SQLExeption" + ex.getMessage());
            System.out.println("SQLVendorExeption" + ex.getErrorCode());
        }
        System.out.println(randName);
        return randName;
    }

    public String getRandLastName() {
        String randLastname = "";
        try {
            Statement st = con.createStatement();
            int count = 0;
            ResultSet rs0 = st.executeQuery("SELECT COUNT(*) FROM lastnames ");
            while (rs0.next()) {
                count = rs0.getInt(1);
            }
            System.out.println(count);
            int myValLastName = myRandomNumber(count);
            String selectLastName = "SELECT lastname FROM lastnames WHERE id =" + myValLastName;
            ResultSet rs = st.executeQuery(selectLastName);
            while (rs.next()) {
                randLastname = rs.getString(1);
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameParser.class.getName()).log(Level.SEVERE, "SQLException", ex);
            System.out.println("SQLExeption" + ex.getMessage());
            System.out.println("SQLVendorExeption" + ex.getErrorCode());
        }
        System.out.println(randLastname);
        return randLastname;
    }

    public String getRandGame() {
        String randGame = "";
        try {
            Statement st = con.createStatement();
            int count = 0;
            ResultSet rs0 = st.executeQuery("SELECT COUNT(*) FROM games ");
            while (rs0.next()) {
                count = rs0.getInt(1);
            }
            System.out.println(count);
            int myValGame = myRandomNumber(count);
            String selectLastName = "SELECT Game FROM games WHERE id =" + myValGame;
            ResultSet rs = st.executeQuery(selectLastName);
            while (rs.next()) {
                randGame = rs.getString(1);
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameParser.class.getName()).log(Level.SEVERE, "SQLException", ex);
            System.out.println("SQLExeption" + ex.getMessage());
            System.out.println("SQLVendorExeption" + ex.getErrorCode());
        }
        System.out.println(randGame);
        return randGame;
    }

    public String getRandCountry() {
        String randCountry = "";
        try {
            Statement st = con.createStatement();
            int count = 0;
            ResultSet rs0 = st.executeQuery("SELECT COUNT(*) FROM countries ");
            while (rs0.next()) {
                count = rs0.getInt(1);
            }
            System.out.println(count);
            int myValCountry = myRandomNumber(count);
            String selectLastName = "SELECT Country FROM countries WHERE id =" + myValCountry;
            ResultSet rs = st.executeQuery(selectLastName);
            while (rs.next()) {
                randCountry = rs.getString(1);
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameParser.class.getName()).log(Level.SEVERE, "SQLException", ex);
            System.out.println("SQLExeption" + ex.getMessage());
            System.out.println("SQLVendorExeption" + ex.getErrorCode());
        }
        System.out.println(randCountry);
        return randCountry;
    }

    public String getRandMail() {
        String randName = "";
        String[] partName = new String[10];

        try {
            Statement st = con.createStatement();
            int count = 0;
            ResultSet rs0 = st.executeQuery("SELECT Name FROM names");
            while (rs0.next()) {
                count = rs0.getInt(1);
            }
            System.out.println(count);
//            
//            int myValName = myRandomNumber(count);
//            String selectLastName = "SELECT Name FROM names WHERE id =" + myValName;
//            ResultSet rs = st.executeQuery(selectLastName);
//            while (rs.next()) {
//                 randName = rs.getString(1);
//            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameParser.class.getName()).log(Level.SEVERE, "SQLException", ex);
            System.out.println("SQLExeption" + ex.getMessage());
            System.out.println("SQLVendorExeption" + ex.getErrorCode());
        }
        System.out.println(randName);
        return randName;
    }

    public void myResultTable() {
        try {
//
//           
//            String selectGame = "SELECT Game FROM games WHERE id =" + myValGame;
//            String selectCountry = "SELECT Country FROM countries WHERE id =" + myValCountry;
            for (int i = 0; i < 100; i++) {
                String name = getRandName();
                String lastname = getRandLastName();
                String game = getRandGame();
                String country = getRandCountry();

                try {
                    String insert = "INSERT INTO result(Name,Lastname,Game,Country) VALUES(?,?,?,?)";
                    PreparedStatement preparedStmt = con.prepareStatement(insert);
                    preparedStmt.setString(1, name);
                    preparedStmt.setString(2, lastname);
                    preparedStmt.setString(3, game);
                    preparedStmt.setString(4, country);
                    preparedStmt.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(GameParser.class.getName()).log(Level.SEVERE, "SQLException", ex);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(ResultTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        /*This is testing of random funcition*/
        ResultTable rt = new ResultTable();
        rt.myResultTable();
    }
}
