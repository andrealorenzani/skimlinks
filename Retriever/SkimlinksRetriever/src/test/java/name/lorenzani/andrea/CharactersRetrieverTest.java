/***
*   Copyright 2017 Andrea Lorenzani
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*
***/

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
