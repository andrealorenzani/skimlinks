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
