/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author savely
 */
public class CountryParser {

    /**
     * @param fileName
     */
    public void parseCountries(String fileName) throws SQLException {
        //String fileName = "/home/jsavely/Desktop/myDiplomaWebSite/base/names.txt";
        //  reference one line at a time

        try {
            //default encoding
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            System.out.println("Inserting records into the table...");
            Connection con = ConnectionProvider.getConnection();
            String sql = "INSERT INTO countries(Country,Flag) VALUES(?,?)";
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            String country = null;
            while ((country = bufferedReader.readLine()) != null) {
                try {
                    country = country.trim();
                    String flag = country.toLowerCase().replaceAll("\\s", "_") + ".png";
                    File f = new File("input/flag/" + flag);
                    if (f.exists() && f.canRead()) {
                        preparedStmt.setString(1, country);
                        preparedStmt.setString(2, flag);
                        preparedStmt.executeUpdate();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GameParser.class.getName()).log(Level.SEVERE, "SQLException", ex);
                }

            }
            // close file
            bufferedReader.close();
            System.out.println("Inserted records into the table...");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameParser.class.getName()).log(Level.SEVERE, "Error not found file '" + fileName + "'", ex);
        } catch (IOException ex) {
            Logger.getLogger(GameParser.class.getName()).log(Level.SEVERE, "Error reading file '" + fileName + "'", ex);
        }
    }

    public static void main(String[] args) throws SQLException {
        CountryParser cp = new CountryParser();
        cp.parseCountries("input/countries.txt");
    }

}
