package name.lorenzani.andrea.datareader;


import java.util.List;

/**
 * How is working a virus so contagious? Who knows... but being very contagious I can assume that
 * if a character is next to another it can infect him. Being in the same Comic is not enough so
 * the two characters have to be in the same story.
 * The two algorithms for TextFile and Sqlite are slightly different but the result is the same
 * (the simplest one is the one in the Sqlite version)
 */

public interface IStoriesDataReader {
    List<String> readCharsWithMoreContacts();
}
