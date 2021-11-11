package com.cwk2.jaxrs.webs1;

/*
 * Created as part of Coursework 2 on Distributed Systems [COMP_3211]
 *
 * Author: Konstantinos Biris [sc19kb]
 * Supervisor: Karim Djemame
 *
 * University of Leeds, Autumn 2021.
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;


/*
 * Accesses the csdbdev university server,
 * Queries the sc19kb_comp3211 database,
*/
class ActorsQuery {

    //File containing properties/credentials to access csdbdev server and sc19kb_comp3211 database
    private static final String propsFile = "jdbc1.properties";



    private static Connection getActorsConnection() throws IOException, SQLException {

        //Load properties/credentials to access csdbdev server and sc19kb_comp3211 database
        FileInputStream in = new FileInputStream(propsFile);
        Properties props = new Properties();
        props.load(in);

        // Define JDBC driver
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null)
            System.setProperty("jdbc.drivers", drivers);

        //Authentication Credentials
        String url = props.getProperty("jdbc.url");
        String user = props.getProperty("jdbc.user");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, user, password);
    }

    /*
    * Queries the sc19kb_comp3211 database,
    * returns a random actor of a certain type
    */
    private static String findActor(String type, Connection database)
            throws SQLException {
        String actorName = null;
        Statement statement = database.createStatement();
        ResultSet results = statement.executeQuery(
                "SELECT * FROM Actors WHERE type = '" + type + "' ORDER BY RAND() LIMIT 1");

        while (results.next()) {
            actorName = results.getString("name");
            System.out.println(actorName);
        }

        statement.close();

        return actorName;
    }


    /*
     * Queries the sc19kb_comp3211 database,
     * returns a random actress of a certain type
     */
    private static String findActress(String type, Connection database)
            throws SQLException {
        String actressName = null;
        Statement statement = database.createStatement();
        ResultSet results = statement.executeQuery(
                "SELECT * FROM Actresses WHERE type = '" + type + "' ORDER BY RAND() LIMIT 1");


        while (results.next()) {
            actressName = results.getString("name");
            System.out.println(actressName);
        }


        statement.close();

        return actressName;
    }


    /*
     * Calls either findActor()/findActress() depending on their gender
     * returns their name as given by the above functions
     */
    static String recommend(String gender, String type) {

        String name = null;
        Connection connection = null;

        if (!gender.equals("male") && !gender.equals("female")) {
            System.err.println("INVALID gender INPUT: MUST EITHER BE \"male\" OR \"female\" ");
            System.exit(1);
        }

        if (!type.equals("Young") && !type.equals("Old-School")) {
            System.err.println("INVALID type INPUT: MUST EITHER BE \"Young\" OR \"Old-School\" ");
            System.exit(1);
        }


        try {
            connection = getActorsConnection();

            if (gender.equals("male")) {
                name = findActor(type, connection);
            } else if (gender.equals("female")) {
                name = findActress(type, connection);
            }
        } catch (Exception error) {
            error.printStackTrace();
        } finally {

            // This will always execute, even if an exception has
            // been thrown elsewhere in the code - so this is
            // the ideal place to close the connection to the DB...

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception error) {
                }
            }
        }
        return name;
    }
}

@Path("/protagonist")
public class ProtagonistREST {


    @GET
    @Path("/find/{gender}/{type}")
    @Produces(MediaType.TEXT_PLAIN)
    public String findPlainText(@PathParam("gender") String gender, @PathParam("type") String type) {
        return ActorsQuery.recommend(gender,type);
    }


}

