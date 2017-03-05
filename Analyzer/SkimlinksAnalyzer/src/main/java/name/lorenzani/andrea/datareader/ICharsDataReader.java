package name.lorenzani.andrea.datareader;

import java.util.Map;

@FunctionalInterface
public interface ICharsDataReader {
    Map<Integer, String> readCharacters();
}
