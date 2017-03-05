package name.lorenzani.andrea.storer;

import java.util.List;
import java.util.Map;

public interface IStorer {
    void storeCharacters(List<List<String>> data);
    void storeStories(List<List<String>> data);
    void storeComics(List<List<String>> data);
    void close();
}
