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
import name.lorenzani.andrea.encapsulator.CharInStoryRetriever;
import name.lorenzani.andrea.encapsulator.ComicsRetriever;
import name.lorenzani.andrea.encapsulator.RetrievedData;
import org.junit.Assert;
import org.junit.Test;

public class CharInStoryRetrieverTest extends UtilsTest {
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
    public void testCharsInStoryRetriever(){
        IConnector conn = generateConnector(null);
        CharInStoryRetriever cr = new CharInStoryRetriever("999", 0, conn, new ObjectMapper());
        try{
            RetrievedData rd = cr.call();
            Assert.assertTrue(rd.data.size()==6);
            Assert.assertTrue(rd.total==6);
            Assert.assertTrue(rd.offset==0);
            Assert.assertTrue(rd.count==6);
            Assert.assertTrue(rd.data.get(0).get(0).equals("1009596"));
            Assert.assertTrue(rd.data.get(0).get(1).equals("Banshee (Theresa Rourke)"));
        }
        catch(Exception e ){

        }
    }

    @Test
    public void testNoCharsInStoryRetriever(){
        IConnector conn = generateConnector(null);
        CharInStoryRetriever cr = new CharInStoryRetriever("null", 0, conn, new ObjectMapper());
        try{
            RetrievedData rd = cr.call();
            Assert.assertTrue(rd.data.size()==0);
            Assert.assertTrue(rd.total==0);
            Assert.assertTrue(rd.offset==5800);
            Assert.assertTrue(rd.count==0);
        }
        catch(Exception e ){

        }
    }
}
