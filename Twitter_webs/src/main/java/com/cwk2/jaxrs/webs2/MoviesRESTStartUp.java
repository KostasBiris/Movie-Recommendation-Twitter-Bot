package com.cwk2.jaxrs.webs2;

/*
 * Created as part of Coursework 2 on Distributed Systems [COMP_3211]
 *
 * Author: Raulian-Ionut Chiorescu [sc19ric]
 * Supervisor: Karim Djemame
 *
 * University of Leeds, Autumn 2021.
 */

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

//Method to startup the webservice
public class MoviesRESTStartUp {
    static final String MOVIES_BASE_URI = "http://localhost:9998/moviesrest/";

    public static void main(String[] args)  {
        try {
            HttpServer server2 = HttpServerFactory.create(MOVIES_BASE_URI);
            server2.start();
            System.out.println("Press enter to stop the server.");
            System.in.read();
            server2.stop(0);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
