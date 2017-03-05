package name.lorenzani.andrea;

import name.lorenzani.andrea.datareader.*;

import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class Analyzer {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        boolean sqlDatabase = true;
        printInteractiveMode();
        while (true) {
            System.out.print("\rTXT Files [" + ((!sqlDatabase) ? "X" : " ") + "] - SQLite database [" + ((sqlDatabase) ? "X" : " ") + "]    Your input > ");

            String input = keyboard.next();
            if (input.equals("6")) break;
            if (input.toLowerCase().equals("h")) printInteractiveMode();
            if (input.equals("5")) sqlDatabase = !sqlDatabase;
            if (input.equals("1")) {
                IListCharactersDataReader charsDR = null;
                if (!sqlDatabase) {
                    charsDR = new TextCharsDataReader();
                } else {
                    charsDR = new SqlCharsDataReader();
                }
                List<String> res = charsDR.readCharactersAlphabeticalOrder();
                for (String character : res) {
                    System.out.println(character);
                }
                printInteractiveMode();
            }
            if (input.equals("2")) {
                IPopularityDataReader popdr;
                if (!sqlDatabase) popdr = new TextPopularityDataReader(new TextCharsDataReader());
                else popdr = new SqlPopularityDataReader();
                List<String> res = popdr.readOccurrences();
                for (String character : res) {
                    System.out.println(character);
                }
            }

            if (input.equals("3")) {
                IComicsDataReader infldr;
                if (!sqlDatabase) infldr = new TextComicsDataReader(new TextCharsDataReader());
                else infldr = new SqlComicsDataReader();
                List<String> res = infldr.readCoOccurrence();
                for (String character : res) {
                    System.out.println(character);
                }
            }
            if (input.equals("4")) {
                IStoriesDataReader strdr;
                if (!sqlDatabase) strdr = new TextStoriesDataReader(new TextCharsDataReader());
                else strdr = new SqlStoriesDataReader();
                List<String> res = strdr.readCharsWithMoreContacts();
                for (String character : res) {
                    System.out.println(character);
                }
            }
        }
    }

    public static void printInteractiveMode() {
        System.out.println("Interactive mode - please type the number (and return) of the question you want to see");
        System.out.println("                   H  Show this guide");
        System.out.println("                   1  List of characters in Alphabetical order");
        System.out.println("                   2  Top ten popular characters");
        System.out.println("                   3  Top ten \"influencing\" characters");
        System.out.println("                   4  Who to infect");
        System.out.println("                   5  Switch between data in txt files or database");
        System.out.println("                   6  Exit");
        System.out.println();
    }
}
