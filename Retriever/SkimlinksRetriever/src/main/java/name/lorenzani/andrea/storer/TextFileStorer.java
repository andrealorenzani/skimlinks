package name.lorenzani.andrea.storer;

import name.lorenzani.andrea.utils.Config;
import name.lorenzani.andrea.utils.StaticVars;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextFileStorer implements IStorer {

    private final String file;
    private final boolean verboseMode;

    /**
     * This {@link IStorer} writes the data on a text file, It creates a
     * different file for any entity (characters, stories and comics). It
     * separates the data with a | char. Please note that characters (in
     * stories or comics) are already in CSV format
     * @param file The basename for the files
     */
    public TextFileStorer(String file){
        this.verboseMode = new Config().getVerbose();
        this.file = file;
    }

    /**
     * This stores characters data retrieved by the {@link name.lorenzani.andrea.encapsulator.IEncapsulator}
     * @param data the data to be stored
     */
    @Override
    public void storeCharacters(List<List<String>> data) {
        store(StaticVars.CHARACTERS, data);
    }

    /**
     * This stores stories data retrieved by the {@link name.lorenzani.andrea.encapsulator.IEncapsulator}
     * @param data the data to be stored
     */
    @Override
    public void storeStories(List<List<String>> data) {
        store(StaticVars.STORIES, data);
    }

    /**
     * This stores comics data retrieved by the {@link name.lorenzani.andrea.encapsulator.IEncapsulator}
     * @param data the data to be stored
     */
    @Override
    public void storeComics(List<List<String>> data) {
        store(StaticVars.COMICS, data);
    }

    /**
     * Close method (file is closed in store method, for avoiding to forget about it)
     */
    @Override
    public void close() {
        if(verboseMode) System.out.println("Closing file writing...");
    }

    /**
     * Stores generic data. The pattern is a | separated value
     * @param prefix The prefix of the filename (the filename will be prefix + base name + txt)
     * @param data The data to be stored
     */
    private void store(String prefix, List<List<String>> data){
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File(prefix+"_"+file);
            writer = new BufferedWriter(new FileWriter(logFile));
            for(List<String> singleData : data) {
                writer.write(singleData.stream().collect(Collectors.joining("|"))+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(writer!=null) {
            try {
                // Close the writer regardless of what happens...
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
