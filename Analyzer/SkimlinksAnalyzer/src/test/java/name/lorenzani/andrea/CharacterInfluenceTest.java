package name.lorenzani.andrea;

import junit.framework.Assert;
import name.lorenzani.andrea.datareader.*;
import org.junit.Test;

import java.util.List;

public class CharacterInfluenceTest {

    @Test
    public void testCharListOrder() {
        IComicsDataReader text = new TextComicsDataReader(new TextCharsDataReader());
        IComicsDataReader sql = new SqlComicsDataReader();
        List<String> txtPop = text.readCoOccurrence();
        List<String> sqlPop = sql.readCoOccurrence();
        Assert.assertEquals(txtPop, sqlPop);
        Assert.assertTrue(txtPop.size()>0);
        Assert.assertTrue(sqlPop.size()>0);
    }
}
