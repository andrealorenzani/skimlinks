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
