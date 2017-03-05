To create the JAR file use
   mvn clean compile assembly:single
** PLEASE NOTE that the jar file will be in the target directory with a long name. You can move it a rename if you want. **

To run the tests use
   mvn test

To run the app use the following
   java -jar <JARFILE> [-c -i -x -s]
   where -c is to retrieve only characters
         -x is to retrieve only comics
         -s is to retrieve only stories
         -i is for the interactive mode
** PLEASE NOTE the jar file needs a config file (read below) **
** if you want to use the compiled one try from Skimlinks/Retriever/SkimlinksRetriever to use the following command**
java -jar target/Skimlinks-Retriever-1.0-SNAPSHOT-jar-with-dependencies.jar

Without any parameter you will retrieve everything. You can use more than one parameter for retrieving more than one entity

The app is using a config file (config.properties) that should be in the root when you invoke the jar
The config file is a property file with the following values:
  privateKey=<MARVEL PRIVATE KEY>
  publicKey=<MARVEL PUBLIC KEY>
  dbfile=<A BASE NAME FOR THE DATABASE>
  verbose=<true FOR ENABLING MORE LOGS>

The retriever is storing the data in two formats: a sqlite database and three text files. Those are the basis for using the Analyzer

There are things I am already aware they are not nice in a project.
I don't like the lacking of tests. Sorry
I don't like as well the handling of exception, you can wait hours before crashing...
But we will discuss that on Monday