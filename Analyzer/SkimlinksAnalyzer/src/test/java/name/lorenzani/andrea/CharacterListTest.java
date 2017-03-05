package name.lorenzani.andrea;

import junit.framework.Assert;
import name.lorenzani.andrea.datareader.ICharsDataReader;
import name.lorenzani.andrea.datareader.SqlCharsDataReader;
import name.lorenzani.andrea.datareader.TextCharsDataReader;
import org.junit.Test;

public class CharacterListTest {

    @Test
    public void testCharList() {
        ICharsDataReader text = new TextCharsDataReader();
        ICharsDataReader sql = new SqlCharsDataReader();
        Assert.assertEquals(text.readCharacters(), sql.readCharacters());
        Assert.assertTrue(text.readCharacters().size()>0);
        Assert.assertTrue(sql.readCharacters().size()>0);
    }

}
