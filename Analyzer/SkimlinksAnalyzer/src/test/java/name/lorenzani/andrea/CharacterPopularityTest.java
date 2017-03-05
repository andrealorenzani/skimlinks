package name.lorenzani.andrea;

import junit.framework.Assert;
import name.lorenzani.andrea.datareader.*;
import org.junit.Test;

import java.util.List;

public class CharacterPopularityTest {
    @Test
    public void testCharListOrder() {
        IPopularityDataReader text = new TextPopularityDataReader(new TextCharsDataReader());
        IPopularityDataReader sql = new SqlPopularityDataReader();
        List<String> txtPop = text.readOccurrences();
        List<String> sqlPop = sql.readOccurrences();
        Assert.assertEquals(txtPop, sqlPop);
        Assert.assertTrue(txtPop.size()>0);
        Assert.assertTrue(sqlPop.size()>0);
        Assert.assertEquals(txtPop.get(0), sqlPop.get(0));
    }

}
