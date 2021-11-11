package com.cwk2.jaxrs.ext_webs;

/*
 * Created as part of Coursework 2 on Distributed Systems [COMP_3211]
 *
 * Authors: Konstantinos Biris [sc19kb], Raulian-Ionut Chiorescu [sc19ric]
 * Supervisor: Karim Djemame
 *
 * University of Leeds, Autumn 2021.
 */


import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class TweetREST {


    public static void main(String args) {

        /*
         * Get access to @DScwk2 twitter account
         * by using the credentials saved in twitter4j.properties file
         */
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            try {
                // request token.
                // this will throw IllegalStateException if access token is already available
                RequestToken requestToken = twitter.getOAuthRequestToken();
                System.out.println("Request token received.");
                System.out.println("Request token: " + requestToken.getToken());
                System.out.println("Request token secret: " + requestToken.getTokenSecret());
                AccessToken accessToken = null;

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (null == accessToken) {
                    System.out.println("Open the following URL and grant access to your account:");
                    System.out.println(requestToken.getAuthorizationURL());
                    System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                    String pin = br.readLine();
                    try {
                        if (pin.length() > 0) {
                            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                        } else {
                            accessToken = twitter.getOAuthAccessToken(requestToken);
                        }
                    } catch (TwitterException te) {
                        if (401 == te.getStatusCode()) {
                            System.out.println("Unable to find access token.");
                        } else {
                            te.printStackTrace();
                        }
                    }
                }
                System.out.println("Access token received.");
                System.out.println("Access token: " + accessToken.getToken());
                System.out.println("Access token secret: " + accessToken.getTokenSecret());
            } catch (IllegalStateException ie) {
                // access token is already available, or consumer key/secret is not set.
                if (!twitter.getAuthorization().isEnabled()) {
                    System.out.println("OAuth consumer key/secret not set.");
                    System.exit(-1);
                }
            }

            /*
             * Post a tweet as DScwk2MovieRecommender
             */
            Status status = twitter.updateStatus(args);
            System.out.println("Status successfully updated to [" + status.getText() + "].");
//            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Failed to read the system input.");
            System.exit(-1);
        }
    }
}

