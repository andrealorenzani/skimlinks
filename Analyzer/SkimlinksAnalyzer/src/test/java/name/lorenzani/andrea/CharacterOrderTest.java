package name.lorenzani.andrea;

import junit.framework.Assert;
import name.lorenzani.andrea.datareader.*;
import org.junit.Test;

import java.util.List;

public class CharacterOrderTest {

    @Test
    public void testCharListOrder() {
        IListCharactersDataReader text = new TextCharsDataReader();
        IListCharactersDataReader sql = new SqlCharsDataReader();
        List<String> txtPop = text.readCharactersAlphabeticalOrder();
        List<String> sqlPop = sql.readCharactersAlphabeticalOrder();
        sqlPop.remove("Jean Grey"); // This is double in SQLite
        Assert.assertEquals(txtPop, sqlPop);
        Assert.assertTrue(txtPop.size()>0);
        Assert.assertTrue(sqlPop.size()>0);
        Assert.assertEquals(txtPop.get(0), sqlPop.get(0));
    }
}
