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

import junit.framework.Assert;
import name.lorenzani.andrea.datareader.ICharsDataReader;
import name.lorenzani.andrea.datareader.SqlCharsDataReader;
import name.lorenzani.andrea.datareader.TextCharsDataReader;
import org.junit.Test;

public class CharacterListTest {

    @Test
    public void testCharList() {
        ICharsDataReader text = new TextCharsDataReader();
        ICharsDataReader sql = new SqlCharsDataReader();
        Assert.assertEquals(text.readCharacters(), sql.readCharacters());
        Assert.assertTrue(text.readCharacters().size()>0);
        Assert.assertTrue(sql.readCharacters().size()>0);
    }

}
