package name.lorenzani.andrea;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CharactersRetrieverTest.class,
        StoriesRetrieverTest.class,
        ComicsRetrieverTest.class,
        CharInComicRetrieverTest.class,
        CharInStoryRetrieverTest.class,
        MarvelJerseyConnectorTest.class
        // BaseJersey must use more IoC for being tested
        // Storers (especially SQLite)
})
public class RetrieverSuite {
}