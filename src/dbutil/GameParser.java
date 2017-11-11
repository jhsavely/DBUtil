/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jsavely
 */
public class GameParser {

    /**
     * @param fileName
     */
    public void parseNames(String fileName) {
        //String fileName = "/home/jsavely/Desktop/myDiplomaWebSite/base/names.txt";
        //  reference one line at a time
        String line = null;
        try {
            //default encoding
            FileReader fileReader = new FileReader(fileName);
            //wraps FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            System.out.println("Inserting records into the table...");
            Connection con = ConnectionProvider.getConnection();
            String sql = "INSERT INTO games(Game) VALUES(?)";
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    
                    System.out.println(line);
                  
                    if (line.length() > 0) {
                        String lastName = line.trim();
                        preparedStmt.setString(1, lastName);
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
        } catch (SQLException ex) {
            Logger.getLogger(GameParser.class.getName()).log(Level.SEVERE, "SQLException", ex);
        }
    }

    public static void main(String[] args) {

        GameParser pt = new GameParser();
        pt.parseNames("/home/jsavely/Desktop/myDiplomaWebSite/base/games.txt");
    }

}
