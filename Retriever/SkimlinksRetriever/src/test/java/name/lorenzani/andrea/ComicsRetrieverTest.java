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
import name.lorenzani.andrea.encapsulator.ComicsRetriever;
import name.lorenzani.andrea.encapsulator.RetrievedData;
import org.junit.Assert;
import org.junit.Test;

public class ComicsRetrieverTest extends UtilsTest {
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
    public void testCharactersRetriever(){
        IConnector conn = generateConnector(null);
        ComicsRetriever cr = new ComicsRetriever(0, conn, new ObjectMapper());
        try{
            RetrievedData rd = cr.call();
            Assert.assertTrue(rd.data.size()==53);
            Assert.assertTrue(rd.total==39063); //??? Server returns a count different from the real number è.é
            Assert.assertTrue(rd.offset==39000);
            Assert.assertTrue(rd.count==53); // As before
            Assert.assertTrue(rd.data.get(0).get(0).equals("10599"));
            Assert.assertTrue(rd.data.get(0).get(1).equals("The Punisher: War Zone (1992) #10"));
        }
        catch(Exception e ){

        }
    }
}
