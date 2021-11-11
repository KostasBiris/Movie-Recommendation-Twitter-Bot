package com.cwk2.jaxrs.webs1;

/*
 * Created as part of Coursework 2 on Distributed Systems [COMP_3211]
 *
 * Author: Konstantinos Biris [sc19kb]
 * Supervisor: Karim Djemame
 *
 * University of Leeds, Autumn 2021.
 */


import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class ProtagonistRESTStartUp {

    private static final String ACTORS_BASE_URI = "http://localhost:9999/protrest/";

    public static void main(String[] args) {
        try {
            HttpServer server1 = HttpServerFactory.create(ACTORS_BASE_URI);
            server1.start();
            System.out.println("Press Enter to stop the server. ");
            System.in.read();
            server1.stop(0);
            System.out.println("Server stopped. ");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}