To create the JAR file use
   mvn clean compile assembly:single
** PLEASE NOTE that the jar file will be in the target directory with a long name. You can move it a rename if you want. **

To run the tests use
   mvn test

To run the app use the following
   java -jar <JARFILE>
** PLEASE NOTE the jar file needs a config file**
** if you want to use the compiled one try from Skimlinks/Analyzer/SkimlinksAnalyzer to use the following command**
     java -jar target/SkimlinksAnalyzer-1.0-SNAPSHOT-jar-with-dependencies.jar

The app is using a config file (config.properties) that should be in the root when you invoke the jar
The config file is a property file with the following values:
  dbfile=<A BASE NAME FOR THE DATABASE>
  verbose=<true FOR ENABLING MORE LOGS>

The Analyzer can be used with text files or sqlite files. In both cases what I am trying to do is to use the
same algorithm. THERE IS AN EXCEPTION: the virus question. For that I coded an visit to a graph for knowing
how many characters would be infected directly or indirectly (by coexisting in the same STORY), and to this
value I added the number of direct infection (cause directly the virus is stronger :P)
I discovered that more or less all the characters are connected directly or indirectly, so for the SQL version
I just return the number of coexistences in the same story

There are things I am already aware they are not nice in a project.
I don't like the lacking of tests. Sorry
I don't like as well the handling of exception, but in this project is less bad...
But we will discuss that on Monday