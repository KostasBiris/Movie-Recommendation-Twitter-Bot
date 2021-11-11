package com.cwk2.jaxrs.client;

/*
 * Created as part of Coursework 2 on Distributed Systems [COMP_3211]
 *
 * Authors: Konstantinos Biris [sc19kb], Raulian-Ionut Chiorescu [sc19ric]
 * Supervisor: Karim Djemame
 *
 * University of Leeds, Autumn 2021.
 */

import javax.servlet.Servlet;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.cwk2.jaxrs.ext_webs.TweetREST;

/*
 * The Integrated Client
 *
 * Calls Web-Service 1 to get the name of an Actor/Actress
 * Calls Web-Service 2 to get a movie starring that Actor/Actress
 * Calls the External Web Service to post a tweet to recommend that movie on Tweeter.
 */

public class Client {

    static final String ACTOR_REST_URI = "http://localhost:9999/protrest";
    static final String MOVIE_REST_URI = "http://localhost:9998/moviesrest";
    static final String FIND_ACTOR_PATH = "protagonist/find";
    static final String GET_MOVIE_PATH = "moviesrest/getmovie";


    public static void main(String[] args) {

        //CLIENT CONFIGURATION
        ClientConfig config = new DefaultClientConfig();
        com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create(config);

        System.out.println(" ");
//=================================================== Call to Web-Service 1 : Start ===================================================================================================

        //name of the actor acquired after
        //call to web service 1
        String actorName;

        long start1 = System.nanoTime();

        WebResource service = client.resource(ACTOR_REST_URI);

        WebResource actorService = service.path(FIND_ACTOR_PATH).path(args[0] + "/" + args[1]);
        String getActor = getActorOutputAsText(actorService);

        long end1 = System.nanoTime();

        double elapsedTime1 = (end1 - start1) *  0.000001;

        System.out.println("--------------------Web-Service 1-----------------");
        System.out.println("Actor Response: " + getActorResponse(actorService));
        System.out.println("Actor Output as Text: " + getActor );
        System.out.println("Time Elapsed: " + elapsedTime1 + " milliseconds");
        System.out.println("---------------------------------------------------");


        //client sends this name to web-service 2
        actorName = getActor;
//================================================== Call to Web-Service 1 : End ================================================================================================================================

        System.out.println(" ");

//=================================================== Call to Web-Service 2 : Start ===================================================================================================
        service = client.resource(MOVIE_REST_URI);

        long start2 = System.nanoTime();


        WebResource movieService = service.path(GET_MOVIE_PATH).path(actorName);

        long end2 = System.nanoTime();

        double elapsedTime2 = (end2 - start2) *  0.000001;

        // calls to the second web service
        System.out.println("--------------------Web-Service 2-----------------");
        System.out.println("Get Movie Response: " + getMovieResponse(movieService));
        System.out.println("Get Movie Output as XML: " + getMovieOutputAsXML(movieService));
        System.out.println("Get Movie Output as Text: " + getMovieOutputAsText(movieService));
        System.out.println("Time Elapsed: " + elapsedTime2 + " milliseconds");
        System.out.println("---------------------------------------------------");

//================================================== Call to Web-Service 2 : End ================================================================================================================================

        System.out.println(" ");

//=================================================== Call to External Web-Service : Start ===================================================================================================

        System.out.println("--------------External-Web-Service-----------------");

        long start3 = System.nanoTime();

        // Invokes the twitter API with the Tweet contents retrieved from the second web service
        tweeter(movieService);
        System.out.println("Tweet Recommendation Posted Sucessfully!");



        long end3 = System.nanoTime();

        double elapsedTime3 = (end3 - start3) *  0.000001;

        System.out.println("Time Elapsed: " + elapsedTime3 + " milliseconds");
        System.out.println("---------------------------------------------------");




//================================================== Call to External Web-Service : End ================================================================================================================================





    }//end of main()


    //ACTOR GETTERS
    private static String getActorResponse(WebResource service) {
        return service.accept(MediaType.TEXT_XML).get(ClientResponse.class).toString();
    }

    private static String getActorOutputAsText(WebResource service) {
        return service.accept(MediaType.TEXT_PLAIN).get(String.class);
    }
    //---------------------------------

    //MOVIE GETTERS
    private static String getMovieResponse(WebResource service) {
        return service.accept(MediaType.TEXT_XML).get(ClientResponse.class).toString();
    }

    private static String getMovieOutputAsXML(WebResource service) {
        return service.accept(MediaType.TEXT_XML).get(String.class);
    }

    private static String getMovieOutputAsText(WebResource service) {
        return service.accept(MediaType.TEXT_PLAIN).get(String.class);
    }
    //----------------------------------

    //TWEETER
    public static String tweeter(WebResource service) {
        String string= service.accept(MediaType.TEXT_PLAIN).get(String.class);
        TweetREST.main(string);
        return null;
    }

}
