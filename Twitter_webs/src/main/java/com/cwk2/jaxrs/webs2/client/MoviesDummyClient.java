//package com.cwk2.jaxrs.webs2.client;
//
//import com.cwk2.jaxrs.ext_webs.TweetREST;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.config.ClientConfig;
//import com.sun.jersey.api.client.config.DefaultClientConfig;
//
//import javax.ws.rs.core.MediaType;
//
//
//public class MoviesDummyClient {
//
//    static final String REST_URI = "http://localhost:9999/moviesrest";
//    static final String GET_PATH = "moviesrest/getmovie";
//
//
//    public static void main(String[] argv) {
//        String starring = argv[0];
//
//
//
//        ClientConfig config = new DefaultClientConfig();
//        Client client = Client.create(config);
//        WebResource service = client.resource(REST_URI);
//
//        WebResource addService = service.path(GET_PATH).path(starring);
//        System.out.println("Get Movie Response: " + getResponse(addService));
//        System.out.println("Get Movie Output as XML: " + getOutputAsXML(addService));
//        System.out.println("Get Movie Output as Text: " + getOutputAsText(addService));
//        System.out.println("Get Movie Output as Tweet: " + runnable(addService));
//        System.out.println("---------------------------------------------------");
//
//
//    }
//
//    private static String getResponse(WebResource service) {
//        return service.accept(MediaType.TEXT_XML).get(ClientResponse.class).toString();
//    }
//
//    private static String getOutputAsXML(WebResource service) {
//        return service.accept(MediaType.TEXT_XML).get(String.class);
//    }
//
//    private static String getOutputAsText(WebResource service) {
//        return service.accept(MediaType.TEXT_PLAIN).get(String.class);
//    }
//
//    public static String runnable(WebResource service) {
//        String string= service.accept(MediaType.TEXT_PLAIN).get(String.class);
//        TweetREST.main(string);
//        return null;
//    }
//
//
//}
