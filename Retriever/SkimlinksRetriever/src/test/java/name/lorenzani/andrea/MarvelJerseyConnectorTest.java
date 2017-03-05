package name.lorenzani.andrea;

import junit.framework.Assert;
import name.lorenzani.andrea.connector.BaseJerseyConnector;
import name.lorenzani.andrea.connector.MarvelJerseyConnector;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class MarvelJerseyConnectorTest extends UtilsTest{

    @Test
    public void baseTestForConnector() throws Exception {
        String serverResp = "test";
        ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Map<String, String>> headersCaptor = ArgumentCaptor.forClass(HashMap.class);
        ArgumentCaptor<Map<String, String>> paramsCaptor = ArgumentCaptor.forClass(HashMap.class);;
        BaseJerseyConnector bjc = Mockito.mock(BaseJerseyConnector.class);
        Mockito.when(bjc.request(pathCaptor.capture(), headersCaptor.capture(), paramsCaptor.capture())).thenReturn(generateFuture(serverResp));

        String pubkey = "pubkey";
        String privKey = "privkey";
        String pathStr = "path";
        String expectedHash = "627780ac6a593fd5820f41498537420d"; //http://www.md5.cz/ with path0privkeypubkey
        MarvelJerseyConnector theConnector = new MarvelJerseyConnector(pubkey, privKey, bjc);
        Future<String> res = theConnector.request(pathStr, new HashMap<>(), new HashMap<>());

        Map<String, String> params = paramsCaptor.getValue();
        Map<String, String> headers = headersCaptor.getValue();
        String path = pathCaptor.getValue();
        Assert.assertEquals(path, pathStr);
        Assert.assertTrue(headers.isEmpty());
        Assert.assertTrue(params.containsKey("ts"));
        Assert.assertTrue(params.containsKey("hash"));
        Assert.assertEquals(expectedHash, params.get("hash"));
        Assert.assertTrue(params.containsKey("apikey"));
        Assert.assertTrue(params.get("ts").equals("path0"));
        Assert.assertTrue(params.get("apikey").equals(pubkey));
        Assert.assertEquals(serverResp, res.get());
    }
}
