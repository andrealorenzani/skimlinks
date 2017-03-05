package name.lorenzani.andrea;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.lorenzani.andrea.connector.ConnectorFactory;
import name.lorenzani.andrea.connector.IConnector;
import name.lorenzani.andrea.encapsulator.EncapsulatorFactory;
import name.lorenzani.andrea.encapsulator.IEncapsulator;
import name.lorenzani.andrea.encapsulator.MarvelEncapsulator;
import name.lorenzani.andrea.storer.IStorer;
import name.lorenzani.andrea.storer.SQLiteStorer;
import name.lorenzani.andrea.storer.TextFileStorer;
import name.lorenzani.andrea.utils.Config;
import name.lorenzani.andrea.utils.StaticVars;

import java.util.*;

/**
 * Hello world!
 *
 */
public class Retriever
{
    public static void main( String[] args )
    {
        boolean interactiveMode, retrieveChars, retrieveStories, retrieveComics;
        interactiveMode = false;
        retrieveChars = retrieveStories = retrieveComics = true;
        if(args.length!=0){
            interactiveMode = retrieveChars = retrieveStories = retrieveComics = false;
            for (String arg : args){
                if(arg.equals("-c")) retrieveChars = true;
                if(arg.equals("-x")) retrieveComics = true;
                if(arg.equals("-s")) retrieveStories = true;
                if(arg.equals("-i")) interactiveMode = true;
            }
        }
        if(interactiveMode) {
            retrieveChars = retrieveStories = retrieveComics = true;
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Interactive mode - please type the number (and return) of the data do you want to enable/disable for retrival");
            System.out.println("                   1. Characters");
            System.out.println("                   2. Comics");
            System.out.println("                   3. Stories");
            System.out.println("                   4. Start retrieving");
            System.out.println("                   When you will be ready, type start (and return) to start the retrieving process");
            while (true) {
                System.out.print("\rCharacters ["+((retrieveChars)?"X":" ")+"] - Comics ["+((retrieveComics)?"X":" ")+"] - Stories ["+((retrieveStories)?"X":" ")+"]   Your input > ");
                String input = keyboard.next();
                if(input.equals("start")||input.equals("4")) break;
                if(input.equals("1")) retrieveChars=!retrieveChars;
                if(input.equals("2")) retrieveComics=!retrieveComics;
                if(input.equals("3")) retrieveStories=!retrieveStories;
            }
        }
        Config conf = new Config();
        IEncapsulator encapsulator = EncapsulatorFactory.getEncapsulator();
        IStorer fs = new TextFileStorer(conf.getDbFilename()+".txt");
        IStorer sqls = new SQLiteStorer(conf.getDbFilename());
        if(retrieveChars){
            List<List<String>> chars = encapsulator.encapsulateDataCharacters();
            fs.storeCharacters(chars);
            sqls.storeCharacters(chars);
        }
        if(retrieveStories){
            List<List<String>> stories = encapsulator.encapsulateDataStories();
            fs.storeStories(stories);
            sqls.storeStories(stories);
        }
        if(retrieveComics){
            List<List<String>> comics = encapsulator.encapsulateDataComics();
            fs.storeComics(comics);
            sqls.storeComics(comics);
        }
        fs.close();
        sqls.close();
    }
}
