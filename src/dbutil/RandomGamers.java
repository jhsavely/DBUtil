/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutil;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jsavely
 */
public class RandomGamers {

    Connection con = null;
    Random rand = null;
    PreparedStatement preparedStmt = null;
    ArrayList<String> avatarList, nameList, lastNameList, gameList, stateList;

    public int randomNumber(int top) {
        int value = rand.nextInt(top);
        return value;
    }

    public final void listAvatar(File myDir) {

        File[] filesList = myDir.listFiles();
        for (File f : filesList) {
            if (f.isFile() || f.exists() || f.canRead()) {
                avatarList.add(f.getName());
            }
        }
        // for(String s:avatarList) System.out.println(s);
    }

    RandomGamers() throws SQLException {
        try {
            avatarList = new ArrayList();
            nameList = new ArrayList();
            lastNameList = new ArrayList();
            gameList = new ArrayList();
            stateList = new ArrayList();
            listAvatar(new File("./input/avatar"));
            con = ConnectionProvider.getConnection();
            Statement st = con.createStatement();
            String insert = "INSERT INTO gamers(avatar,name,email,game,state,flag,result,joined) VALUES(?,?,?,?,?,?,?,?)";
            preparedStmt = con.prepareStatement(insert);
            rand = new Random();
            ResultSet rs0 = st.executeQuery("SELECT name FROM names ");
            while (rs0.next()) {
                String name = rs0.getString(1);
                if (name.length() > 1) {
                    name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
                    nameList.add(name);
                }
            }
            rs0 = st.executeQuery("SELECT lastname FROM lastnames ");
            while (rs0.next()) {
                String lastname = rs0.getString(1);
                if (lastname.length() > 1) {
                    lastname = lastname.substring(0, 1).toUpperCase() + lastname.substring(1).toLowerCase();
                    lastNameList.add(lastname);
                }
            }
            rs0 = st.executeQuery("SELECT game FROM games ");
            while (rs0.next()) {
                gameList.add(rs0.getString(1));
            }
            rs0 = st.executeQuery("SELECT country FROM countries ");
            while (rs0.next()) {
                stateList.add(rs0.getString(1));
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameParser.class.getName()).log(Level.SEVERE, "SQLException", ex);

        }
    }

    public String getStateFlag(String state) {
        state = state.trim();
        String flag = state.toLowerCase().replaceAll("\\s", "_") + ".png";
        return flag;
    }

    String[] domens = {
        "com",
        "dom",
        "org",
        "edu",
        "live",
        "gov",
        "info",
        "net"
    };

    String[] prov = {
        "google",
        "bing",
        "yahoo",
        "duck",
        "gmail",
        "nm",
        "orcl",
        "ibm",
        "sun",
        "mozilla",
        "gandi",
        "fb",
        "sams",
        "mits",
        "smns",
        "ge"
    };

    public String getRandMail(String name, String lastname, String state) {
        return name.substring(0, 2).toLowerCase() + lastname.toLowerCase() + "@"
                + prov[randomNumber(prov.length)]
                + "."
                + domens[randomNumber(domens.length)]
                + "."
                + state.substring(0, 2).toLowerCase();
    }

    public void myResultTable() {
        try {
            for (int i = 0; i < 10000;) {
                String name = nameList.get(randomNumber(nameList.size()));
                String lastname = lastNameList.get(randomNumber(lastNameList.size()));
                String game = gameList.get(randomNumber(gameList.size()));
                String avatar = avatarList.get(randomNumber(avatarList.size()));
                String state = stateList.get(randomNumber(stateList.size()));
                if (name.isEmpty() || avatar.isEmpty() || game.isEmpty() || state.isEmpty()) {
                    continue;
                }
                String flag = getStateFlag(state);
                String email = getRandMail(name, lastname, state);
                int result = 1000+randomNumber(20000);
                long tm = System.currentTimeMillis() - (long)(10L*12L*31L*24L*60L*60L*1000L*Math.random());
                Date joined_date = new Date(tm);
                String joined = joined_date.toString();
                System.out.println(name + " " + lastname + " " + game + " " + state + " " + avatar + " " + email);
                try {
                    preparedStmt.setString(1,avatar.trim());
                    preparedStmt.setString(2,  name.trim()+" "+lastname.trim());
                    preparedStmt.setString(3,email.trim() );
                    preparedStmt.setString(4, game.trim());
                    preparedStmt.setString(5, state.trim());
                    preparedStmt.setString(6, flag.trim());
                    preparedStmt.setInt(7, result);
                    preparedStmt.setString(8, joined);
                    preparedStmt.executeUpdate();
                     i++;
                } catch (SQLException ex) {
                    Logger.getLogger(GameParser.class
                            .getName()).log(Level.SEVERE, "SQLException", ex);
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(RandomGamers.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws SQLException {
        RandomGamers rt = new RandomGamers();
        rt.myResultTable();
    }
}
