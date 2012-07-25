Insieme
=======

Expose, Listen and Share Music Together.

Current Scope: 

1) A user is able to register as regular user and upgrade to being an artist.
2) As an artist the user is able to manage tracks uploaded by setting a download limit and managing the people that 
are allowed to download files from the artist repo.
3) A user is able to download tracks which are added automatically to a playlist.

Project Structure:

Insieme Core - This is the core rest based web app that can run as a standalone java application. I hope to put this
into a web container sometime before the project deadline. (See run instructions for how to run server on your local machine).

Insieme Android - This is the android client side project which interacts with the core Insieme webapp.

Insieme Common - This is a placeholder project for some of the common entities and tools between the client and server.
The commons module includes some utilities for quickly serializing/deserializing data transfer objects (dto)s 
between client and server and the dtos themselves.

Technology Stack:

Java - Not much to say. Language of choice.

Maven - http://maven.apache.org/. In a nutshell maven is a dependency management tool. Rather than copying jars and 
dependencies from other local projects and downloading other dependencies from third party web sites, maven provides 
a centralized solution which, from a simple configuration file (pom), manages all dependencies for you. I was able to 
mavenize both the core and commons project but due to a bug in the android maven configruator, I am still unable to 
mavenize the android project.

RESTlett - http://www.restlet.org/. Restlet is a very convinient rest based java framework. For more information
on the REST architecture, refer to http://en.wikipedia.org/wiki/Representational_state_transfer. Why did I choose REST? because of the flexibility it provides in terms of implementing
new clients and the decoupling of resources that can be distributed. If it were up to me, I would go a stp further and look
into OSGI to get even more decoupling. Each request from the client is sent in the form of uri and is mapped to a specific 
resource. A rest request can include information in the form of json and can be used in processing the request to retrieve,
create or update data on the server side (CRUD).

GSON (JSON) - GSON is google's solution to json object transfer. Much like objects can be passed via XML syntax or even
serialized, json is a standard javascript-like notation for data being transferred across the web. See http://en.wikipedia.org/wiki/Json.
So objects are serialized using gson from the client and deserialized by gson on the server side. As you may have guessed,
the commmon util for doing this I have placed in the commons project. See http://code.google.com/p/google-gson/ for more
information on GSON.

GUICE - I am using GUICE for dependency injection of resources, android UI views and object, and interface implementations.
For a treatment of dependency injections see http://tutorials.jenkov.com/dependency-injection/index.html.

MySQL - Used for storing user, tracks and artist data.

RoboGuice - Extension of Guice for client side injections.

Running the application:

Requirements and Setup:

1) at least java 1.6
2) install maven 3.0 and follow instructions for setting environment variables to point to installed JDK. 
(Optional) install maven eclipse plugin: http://www.eclipse.org/m2e/
3)Once maven is installed, use the settings.xml file that I have committed at the root of the project and place it in
your /.m2/ folder.
4) download the latest android sdk and follow instructions on setting it up in eclipse. You also do want to install the ADT
plugin for eclipse. Refer to these instructions for a full set up: http://developer.android.com/sdk/installing/index.html.
5) Git client. I use a git extension of cygwin on windows.

Running the project: 

1) Make sure you checkout the project from git hub and importing the projects into eclipse.

Running core web app: 

1) from the command line, using maven, I have made it possible to run the standalone webapp with all of its dependencies
as a simple java application. 
2) Open the Insieme-core project and execute a "mvn clean install assembly:single". This will compile and generate 
the core web app jar with all its dependencies included. 
3) navigate to /Insieme-core/target and run java -jar Insieme-core-1.0-SNAPSHOT-jar-with-dependencies.jar
4) if all goes well, the web app should start on port 8184.

Running the android client: 

TO DO: mavenize project.

I currently run the android application on my tablet given that teh emulator is incredibly slow. Once the android project is 
imported into eclipse, it is possible to simple create an emulator. The samsung galaxy tab has a resolution of 1024x600. Use the
resolution in the emulator options rather than the predefined skins.





