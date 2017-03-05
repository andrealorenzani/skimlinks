package name.lorenzani.andrea;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.lorenzani.andrea.connector.IConnector;
import name.lorenzani.andrea.encapsulator.CharInComicRetriever;
import name.lorenzani.andrea.encapsulator.ComicsRetriever;
import name.lorenzani.andrea.encapsulator.RetrievedData;
import org.junit.Assert;
import org.junit.Test;

public class CharInComicRetrieverTest extends UtilsTest {
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
    public void testCharsInComicRetriever(){
        IConnector conn = generateConnector(null);
        CharInComicRetriever cr = new CharInComicRetriever("999", 0, conn, new ObjectMapper());
        try{
            RetrievedData rd = cr.call();
            Assert.assertTrue(rd.data.size()==2);
            Assert.assertTrue(rd.total==2);
            Assert.assertTrue(rd.offset==0);
            Assert.assertTrue(rd.count==2);
            Assert.assertTrue(rd.data.get(0).get(0).equals("1009610"));
            Assert.assertTrue(rd.data.get(0).get(1).equals("Spider-Man"));
        }
        catch(Exception e ){

        }
    }
}
