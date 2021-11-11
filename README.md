# COMP3211 Distributed Systems Cwk2

Coursework 2 on Distributed Systems

### Team Members:

- Konstantinos Biris [sc19kb]
- Raulian Ionut Chiorescu [sc19ric]

### Supervisor:

- Karim Djemame

## Project Description

This project consists of an integrated client and the following three web services:
- Webs1 made by Konstantinos Biris
- Webs2 made by Raulian Ionut Chiorescu
- Ext_Webs that uses Twitter's RESTful API

The client enters a web interface where they are asked to input some specifications about an actor, namely their gender and type (Young/Old-School).

The first web service is invoked and sends according to that input connects to the _csdbdev_ university server and accesses the _sc19kb_comp3211_ database. If the actor's gender is male, it queries the _Actors_ table and if they are female it queries the _Actresses_ table. If the type is _Young_ it returns the name of a random actor/actress of that type and vice versa if it is _Old-School_.

The client receives the name of that actor/actress and invokes the second web service which connects to the _csdbdev_ university server and accesses the _sc19ric_comp3211_ database and returns the name of a movie starring the actor by that name. Then, it returns the name of that movie and the year it was released.

Finally, the external web service gets invoked and a tweet gets generated which is then posted on _DScwk2MovieRecommender_ on Twitter, and this tweet recommends a movie starring an actor with the attributes that the user specified.

## How to run
1. Copy the web_int.war file into the webapps directory of tomcat.
2. Open the project in IntelliJ and run ProtagonistRESTStartUp.java to start the server for webs1.
3. Run MoviesRESTUp.java to start the server for webs2.
4. Open a terminal window and start tomcat.
5. In a web-browser go to "http://localhost:8080/web_int/" and select your actor preferences and click on submit.
6. Go twitter and check the latest tweet by @DSCwk2 which is a movie recommendation according to your preferences.

