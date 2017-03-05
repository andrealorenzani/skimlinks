package name.lorenzani.andrea;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.lorenzani.andrea.connector.IConnector;
import name.lorenzani.andrea.encapsulator.CharactersRetriever;
import name.lorenzani.andrea.encapsulator.RetrievedData;
import org.junit.Assert;
import org.junit.Test;

public class CharactersRetrieverTest  extends UtilsTest {

    /***
     * TODO:
     * Test for nominal case
     * Test without no result from server
     * Test "too many queries - try later"
     * Test with good envelope but no items (*2)
     * Test on parameters passed
     */
    @Test
    public void testCharactersRetriever(){
        IConnector conn = generateConnector(null);
        CharactersRetriever cr = new CharactersRetriever(0, conn, new ObjectMapper());
        try{
            RetrievedData rd = cr.call();
            Assert.assertTrue(rd.data.size()==100);
            Assert.assertTrue(rd.total==1485);
            Assert.assertTrue(rd.offset==0);
            Assert.assertTrue(rd.count==100);
            Assert.assertTrue(rd.data.get(0).get(0).equals("1011334"));
            Assert.assertTrue(rd.data.get(0).get(1).equals("3-D Man"));
        }
        catch(Exception e ){

        }
    }

    @Test
    public void testFailingCharactersRetriever(){
        IConnector conn = generateConnector("uselessstring");
        CharactersRetriever cr = new CharactersRetriever(0, conn, new ObjectMapper());
        RetrievedData rd = null;
        try{
            rd = cr.call();
        }
        catch(Exception e ){

        }
        Assert.assertTrue(rd==null);
    }

}
