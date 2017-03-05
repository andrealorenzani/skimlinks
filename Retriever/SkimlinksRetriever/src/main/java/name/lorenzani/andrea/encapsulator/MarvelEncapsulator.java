package name.lorenzani.andrea.encapsulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.lorenzani.andrea.connector.IConnector;
import name.lorenzani.andrea.utils.Config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MarvelEncapsulator implements IEncapsulator {

    /**
     * This class is retrieving the data from the server and encapsualte it
     * in a standard format (List of List). It uses the IoC for being easier
     * to test
     */
    protected final ObjectMapper om;
    protected final IConnector connector;
    protected static final ExecutorService threadpool = Executors.newCachedThreadPool();
    private final boolean verboseMode;

    public MarvelEncapsulator(IConnector connector, ObjectMapper om) {
        this.om = om;
        this.connector = connector;
        this.verboseMode = new Config().getVerbose();
    }

    /**
     * This method is returning the data related to all the Characters of Marvel
     * @return All the characters as List<List<String>> - the outer List contains
     *         one list of attributes per character
     */
    @Override
    public List<List<String>> encapsulateDataCharacters() {
        LinkedList<List<String>> result = new LinkedList<>();
        try {
            RetrievedData firstChr = new CharactersRetriever(0, connector, om).call();
            int reqToBeDone = firstChr.total / 100; // +1 for the flooring -1 already done
            List<Future<RetrievedData>> allCharacters = new ArrayList<>(reqToBeDone);
            for (int i = 0; i < reqToBeDone; i++) {
                allCharacters.add(threadpool.submit(new CharactersRetriever((i + 1) * 100, connector, om)));
            }
            result.addAll(firstChr.data);
            while (!allCharacters.stream().allMatch(el -> el.isDone() || el.isCancelled())) {
                long perc = (allCharacters.stream().filter(el -> el.isDone() || el.isCancelled()).count() + 1) * 10000 / firstChr.total;
                System.out.print("\rRetrieving characters: " + perc + "%");
                Thread.sleep(100);
            }
            System.out.print("\rRetrieving characters: completed!!!");
            // Here all the futures are available - please consider that some of them may be in failure
            for (Future<RetrievedData> req : allCharacters) {
                if (req.isDone() && !req.isCancelled()) {
                    RetrievedData data = req.get();
                    if (data != null) result.addAll(data.data);
                }
            }
            System.out.print("\r\t\t\t\t\t\t\t\n");
        } catch (Exception e) {
            if (verboseMode) e.printStackTrace();
        }
        return result;
    }

    /**
     * This method is returning the data related to all the Comics of Marvel
     * @return All the comics as List<List<String>> - the outer List contains
     *         one list of attributes per comic
     */
    @Override
    public List<List<String>> encapsulateDataComics() {
        LinkedList<List<String>> result = new LinkedList<>();
        try {
            // I have to serialize cause the server reset the connection
            RetrievedData firstCmc = new ComicsRetriever(0, connector, om).call();
            int reqToBeDone = firstCmc.total / 100; // +1 for the flooring -1 already done
            List<Future<RetrievedData>> allComics = new ArrayList<>(reqToBeDone);
            for (int i = 0; i < reqToBeDone; i = i + 15) {
                // I have to serialize cause the server reset the connection
                for (int j = 0; j < 15 && (i + j) < reqToBeDone; j++) {
                    allComics.add(threadpool.submit(new ComicsRetriever((i + j + 1) * 100, connector, om)));
                }
                while (!allComics.stream().allMatch(el -> el.isDone() || el.isCancelled())) {
                    long perc = (allComics.stream().filter(el -> el.isDone() || el.isCancelled()).count()) * 100 / reqToBeDone;
                    System.out.print("\rRetrieving comics: " + perc + "%");
                    Thread.sleep(100);
                }
                // TODO: this is a great point for saving the state if we want to introduce a recovery mechanism
            }
            result.addAll(firstCmc.data);
            System.out.print("\rRetrieving comics: completed!!!");
            // Here all the futures are available - please consider that some of them may be in failure
            for (Future<RetrievedData> req : allComics) {
                if (req.isDone() && !req.isCancelled()) {
                    RetrievedData data = req.get();
                    if (data != null) result.addAll(data.data);
                }
            }
            System.out.print("\r\t\t\t\t\t\t\t\n");
        } catch (Exception e) {
            if (verboseMode) e.printStackTrace();
        }
        return result;
    }

    /**
     * This method is returning the data related to all the Stories of Marvel
     * @return All the stories as List<List<String>> - the outer List contains
     *         one list of attributes per story
     */
    @Override
    public List<List<String>> encapsulateDataStories() {
        // This method and encapsulateDataComics are really similar, and it could be the same
        // for the one for characters. I didn't merge in one single method for testing purpose
        // because Stories are much more (80k) than characters (1.5k) and comics (30k)
        LinkedList<List<String>> result = new LinkedList<>();
        try {
            RetrievedData firstStr = new StoriesRetriever(0, connector, om).call();
            int reqToBeDone = firstStr.total / 100; // +1 for the flooring -1 already done
            List<Future<RetrievedData>> allStories = new ArrayList<>(reqToBeDone);
            for (int i = 0; i < reqToBeDone; i = i + 15) {
                // I have to serialize cause the server reset the connection
                for (int j = 0; j < 15 && (i + j) < reqToBeDone; j++) {
                    allStories.add(threadpool.submit(new StoriesRetriever((i + j + 1) * 100, connector, om)));
                }
                while (!allStories.stream().allMatch(el -> el.isDone() || el.isCancelled())) {
                    long perc = (allStories.stream().filter(el -> el.isDone() || el.isCancelled()).count()) * 100 / reqToBeDone;
                    System.out.print("\rRetrieving stories: " + perc + "%");
                    Thread.sleep(100);
                }
                // TODO: this is a great point for saving the state if we want to introduce a recovery mechanism
            }
            System.out.print("\rRetrieving stories: completed!!!");
            result.addAll(firstStr.data);
            // Here all the futures are available - please consider that some of them may be in failure
            for (Future<RetrievedData> req : allStories) {
                if (req.isDone() && !req.isCancelled()) {
                    RetrievedData data = req.get();
                    if (data != null) result.addAll(data.data);
                }
            }
            System.out.print("\r\t\t\t\t\t\t\t\n");
        } catch (Exception e) {
            if (verboseMode) e.printStackTrace();
        }
        return result;
    }

}
