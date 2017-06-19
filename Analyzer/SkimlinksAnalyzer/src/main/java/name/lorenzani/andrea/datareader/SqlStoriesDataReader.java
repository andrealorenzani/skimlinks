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

package name.lorenzani.andrea.datareader;

import name.lorenzani.andrea.datareader.baseclasses.SQLLiteReader;
import name.lorenzani.andrea.utils.Config;

import java.sql.ResultSet;
import java.util.*;

/**
 * How is working a virus so contagious? Who knows... but being very contagious I can assume that
 * if a character is next to another it can infect him. Being in the same Comic is not enough so
 * the two characters have to be in the same story.
 * Please note that with the TextFile version I did something more complicated but the result is the
 * same cause Marvel characters are more or less all in contact (as I discovered by testing the
 * other algorithm :P) so I simplify this
 */
public class SqlStoriesDataReader extends SQLLiteReader implements IStoriesDataReader {

    public SqlStoriesDataReader() {
        super(new Config().getDbFilename());
    }

    /*
    sql = "CREATE TABLE IF NOT EXISTS STORIES " +
                    "(ID INTEGER PRIMARY KEY     AUTOINCREMENT," +
                    " CHAR           INT     NOT NULL," +
                    " STORY          INT     NOT NULL)";
     */

    @Override
    public List<String> readCharsWithMoreContacts() {
        List<String> res = new ArrayList<>(10);
        try{
            ResultSet rs = c.createStatement().executeQuery("SELECT H.NAME, COUNT(DISTINCT C2.CHAR) AS CONT FROM CHARACTERS H, STORIES C1, STORIES C2 WHERE C1.STORY=C2.STORY AND C1.CHAR!=C2.CHAR AND C1.CHAR=H.ID GROUP BY C1.CHAR ORDER BY CONT DESC LIMIT 10;");
            while(rs.next()) {
                String charName = rs.getString(1);
                //Integer ncontact = rs.getInt(2);
                res.add(charName);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
