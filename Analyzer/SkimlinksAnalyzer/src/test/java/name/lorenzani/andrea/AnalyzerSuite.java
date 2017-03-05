package name.lorenzani.andrea;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CharacterListTest.class,
        CharacterPopularityTest.class,
        CharacterOrderTest.class,
        CharacterInfluenceTest.class,
        VillianTest.class
})
public class AnalyzerSuite {
}