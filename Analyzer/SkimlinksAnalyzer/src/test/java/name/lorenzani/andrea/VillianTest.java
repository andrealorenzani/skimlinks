package name.lorenzani.andrea;


import junit.framework.Assert;
import name.lorenzani.andrea.datareader.*;
import org.junit.Test;

import java.util.List;

public class VillianTest {

    @Test
    public void testInfected() {
        IStoriesDataReader text = new TextStoriesDataReader(new TextCharsDataReader());
        IStoriesDataReader sql = new SqlStoriesDataReader();
        List<String> txtPop = text.readCharsWithMoreContacts();
        List<String> sqlPop = sql.readCharsWithMoreContacts();
        Assert.assertEquals(txtPop, sqlPop);
        Assert.assertTrue(txtPop.size()>0);
        Assert.assertTrue(sqlPop.size()>0);
    }
}
