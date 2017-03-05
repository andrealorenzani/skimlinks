package name.lorenzani.andrea;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.lorenzani.andrea.connector.IConnector;
import name.lorenzani.andrea.encapsulator.ComicsRetriever;
import name.lorenzani.andrea.encapsulator.RetrievedData;
import name.lorenzani.andrea.encapsulator.StoriesRetriever;
import org.junit.Assert;
import org.junit.Test;

public class StoriesRetrieverTest extends UtilsTest {

    /***
     * TODO:
     * Test for nominal case
     *
     * The following test are based on RetrieverThread and are nice to have
     * but redundant because are covered by CharactersRetrieverTests
     *
     * Test without no result from server
     * Test "too many queries - try later"
     * Test with good envelope but no items (*2)
     * Test on parameters passed
     */

    @Test
    public void testStoriesRetriever(){
        IConnector conn = generateConnector(null);
        StoriesRetriever cr = new StoriesRetriever(0, conn, new ObjectMapper());
        try{
            RetrievedData rd = cr.call();
            Assert.assertTrue(rd.data.size()==100);
            Assert.assertTrue(rd.total==87038);
            Assert.assertTrue(rd.offset==0);
            Assert.assertTrue(rd.count==100);
            Assert.assertTrue(rd.data.get(0).get(0).equals("7")); // Ehm... the following should be a 'title'...
            Assert.assertTrue(rd.data.get(99).get(1).equals("Elektra is forced to confront her murderous past after being taken hostage by an adversary. Feeling desperate in her containment"));
        }
        catch(Exception e ){

        }
    }
}
