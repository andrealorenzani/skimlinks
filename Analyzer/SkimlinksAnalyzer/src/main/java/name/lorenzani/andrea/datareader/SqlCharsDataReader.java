package name.lorenzani.andrea.datareader;

import name.lorenzani.andrea.datareader.baseclasses.SQLLiteReader;
import name.lorenzani.andrea.utils.Config;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SqlCharsDataReader extends SQLLiteReader implements ICharsDataReader, IListCharactersDataReader {
    public SqlCharsDataReader() {
        super(new Config().getDbFilename());
    }

    /**
     * This is actually never used (but it could be used with a TextBlablaReader)
     * @return The mapping between the Character ID and its name (it is used mainly with TextReaders)
     */
    @Override
    public Map<Integer, String> readCharacters() {
        HashMap<Integer, String> res = new HashMap<>();
        try{
            ResultSet rs = c.createStatement().executeQuery("SELECT ID, NAME FROM CHARACTERS;");
            while(rs.next()) {
                Integer id = rs.getInt("ID");
                String name = rs.getString("NAME");
                res.put(id, name);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<String> readCharactersAlphabeticalOrder() {
        List<String> res = new LinkedList<>();
        try{
            ResultSet rs = c.createStatement().executeQuery("SELECT NAME FROM CHARACTERS ORDER BY NAME ASC;");
            while(rs.next()) {
                String name = rs.getString("NAME");
                res.add(name);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
