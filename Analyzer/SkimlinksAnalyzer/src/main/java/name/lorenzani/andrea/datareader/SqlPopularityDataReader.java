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
import java.util.ArrayList;
import java.util.List;

public class SqlPopularityDataReader extends SQLLiteReader implements IPopularityDataReader {
    public SqlPopularityDataReader() {
        super(new Config().getDbFilename());
    }

    @Override
    public List<String> readOccurrences() {
        List<String> res = new ArrayList<>(10);
        try{
            ResultSet rs = c.createStatement().executeQuery("SELECT H.NAME, COUNT(C.COMIC) AS POP FROM CHARACTERS H, COMICS C WHERE H.ID = C.CHAR GROUP BY H.ID ORDER BY POP DESC LIMIT 10;");
            while(rs.next()) {
                String charId = rs.getString(1);
                res.add(charId);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
