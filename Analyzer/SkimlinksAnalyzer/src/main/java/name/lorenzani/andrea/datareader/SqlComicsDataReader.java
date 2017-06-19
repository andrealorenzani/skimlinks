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

public class SqlComicsDataReader extends SQLLiteReader implements IComicsDataReader {

    public SqlComicsDataReader() {
        super(new Config().getDbFilename());
    }

    @Override
    public List<String> readCoOccurrence() {
        List<String> res = new ArrayList<>(10);
        try{
            ResultSet rs = c.createStatement().executeQuery("SELECT H.NAME, SUM(C3.CS) AS POP FROM CHARACTERS H, COMICS C1, COMICS C2, (SELECT CHAR, COUNT(COMIC) AS CS FROM COMICS GROUP BY CHAR) C3 WHERE C1.CHAR!=C2.CHAR AND C2.CHAR=C3.CHAR AND C1.COMIC=C2.COMIC AND H.ID=C1.CHAR GROUP BY C1.CHAR ORDER BY POP DESC LIMIT 10;");
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
