package com.cwk2.jaxrs.webs2;

/*
 * Created as part of Coursework 2 on Distributed Systems [COMP_3211]
 *
 * Author: Raulian-Ionut Chiorescu [sc19ric]
 * Supervisor: Karim Djemame
 *
 * University of Leeds, Autumn 2021.
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import static com.cwk2.jaxrs.webs2.MoviesQuery.getMovie;

/*
 * Accesses the csdbdev university server,
 * Queries the sc19kb_comp3211 database,
 */
class MoviesQuery {

     //File containing properties/credentials to access csdbdev server and sc19ric_comp3211 database
     public static final String propsFile = "jdbc2.properties";


     public static Connection getConnection() throws IOException, SQLException {

         //Load properties/credentials to access csdbdev server and sc19ric_comp3211 database
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
     * Queries the sc19ric_comp3211 database,
     * returns a string containing the movie name, year of release and the starring actor name
     */
     public static String findMovie(String starring, Connection database)

             throws SQLException {
         Statement statement = database.createStatement();
         String result = null;
         ResultSet results = statement.executeQuery(
                 "SELECT * FROM Movies WHERE starring='" + starring + "'");
         while (results.next()) {
             String name = results.getString("name");
             String year = results.getString("year");


             System.out.println("Our movie recommendation for today is: " + name + " released in " + year + " starring " + starring);
             result = "Our movie recommendation for today is: \"" + name + "\" released in " + year + " starring " + starring;
         }

         statement.close();
         return result;
     }


     /*
      * Call method for the findMovie function, returns a string
      */
      

     public static String getMovie(String starring) {
         Connection connection = null;
         String result = null;
         try {
             connection = getConnection();
             result = findMovie(starring, connection);
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             if (connection != null) {
                 try {
                     connection.close();
                 } catch (Exception e) {
                 }
             }
         }
         return result;
     }

 }

// RESTful implementation of the web service
@Path("/moviesrest")
public class MoviesREST{

@GET
@Path("/getmovie/{starring}")
@Produces(MediaType.TEXT_PLAIN)
public String getmoviePlainText (@PathParam("starring") String starring) {
    return   getMovie(starring);
}



@GET
@Path("/getmovie/{starring}")
@Produces(MediaType.TEXT_XML)
public String getmovie (@PathParam("starring") String starring) {
   return "<?xml version=\"1.0\"?>" + "<result>" +  getMovie(starring) + "</result>";
}


}



