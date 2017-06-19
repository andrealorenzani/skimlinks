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

package name.lorenzani.andrea.connector;

import name.lorenzani.andrea.utils.Config;
import org.glassfish.jersey.client.ClientConfig;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class MarvelJerseyConnector implements IConnector {
    private static Map<String,AtomicInteger> tsPath=new HashMap<>();

    private final String apiPublicKey;
    private final String apiPrivateKey;
    private final IConnector bjc;
    private final boolean verboseMode;

    /**
     * This is a specialization of the Connector interface. I used the
     * IoC for improving testability. In this way I am able to use Mockito
     * to test the class
     * @param apiPublicKey The API public key given by Marvel
     * @param apiPrivateKey The API private key given by Marvel
     * @param bjc The base Connector
     */
    public MarvelJerseyConnector(String apiPublicKey,
                                 String apiPrivateKey,
                                 IConnector bjc) {
        this.apiPrivateKey = apiPrivateKey;
        this.apiPublicKey = apiPublicKey;
        this.bjc = bjc;
        this.verboseMode = new Config().getVerbose(); // I knoooowww I should have injected, but...
    }

    /**
     * The implementation for the {@link IConnector} class
     * @param path The path
     * @param headers The headers
     * @param params The params (they will be enriched with the requested params of Marvel API)
     * @return As the Interface says, this is a future with the result of the server
     */
    @Override
    public Future<String> request(String path, Map<String, String> headers, Map<String, String> params) {
        tsPath.putIfAbsent(path, new AtomicInteger(0));
        String ts = path+tsPath.get(path).getAndIncrement();//UUID.randomUUID().toString();
        String hash = getMD5Hash(ts + apiPrivateKey + apiPublicKey);
        HashMap<String, String> enrichedParams = new HashMap<>(params);
        enrichedParams.put("ts", ts);
        enrichedParams.put("apikey", apiPublicKey);
        enrichedParams.put("hash", hash);
        return bjc.request(path, headers, enrichedParams);
    }

    /**
     * Utility method for computing the MD5 digest
     * @param input The string we want to hash (for marvel is ts+private key+public key
     * @return The digest for the input
     */
    private String getMD5Hash(String input) {
        String res = "";
        try{
            byte[] bytesOfMessage = input.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] theDigest = md.digest(bytesOfMessage);

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < theDigest.length; i++) {
                sb.append(Integer.toString((theDigest[i] & 0xff) + 0x100, 16).substring(1));
            }
            res = sb.toString();
        }
        catch(Exception ex){
            if(verboseMode) System.out.println("Error generating hash for the message: "+ex.getMessage());
        }
        return res;
    }
}
