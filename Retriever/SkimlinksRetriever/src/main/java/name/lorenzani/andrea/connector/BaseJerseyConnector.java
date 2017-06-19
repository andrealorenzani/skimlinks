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

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BaseJerseyConnector implements IConnector {
    protected final WebTarget baseTarget;
    protected static final ExecutorService threadpool = Executors.newCachedThreadPool();
    private final boolean verboseMode;

    /**
     * Base implementation of a connector using Jersey lib. This is tipically hard to
     * test with unit test, due to its nature (integration tests are fine).
     * I tried to separate the functions in order to create mocks
     * @param endPoint The endpoint of the rest api
     * @param config A config object
     */
    public BaseJerseyConnector(String endPoint,
                               ClientConfig config){
        baseTarget = ClientBuilder.newClient(config).target(endPoint);
        verboseMode = new Config().getVerbose();
    }

    /**
     * This does a synchronous call to an endpoint. In order to not block the process
     * we spawn a thread per request. So the method returns a {@link Future}
     * @param path The path of the resource to be added to the endpoint
     * @param headers The headers of the request
     * @param params The parameters of the request
     * @return A {@link Future} with the String returned by the server
     */
    @Override
    public Future<String> request(String path, Map<String, String> headers, Map<String, String> params) {
        WebTarget tmpTarget = baseTarget.path(path);
        for(Map.Entry<String, String> param: params.entrySet()){
            tmpTarget = tmpTarget.queryParam(param.getKey(), param.getValue());
        }
        Invocation.Builder invocationBuilder =
                tmpTarget.request(MediaType.APPLICATION_JSON_TYPE);
        for(Map.Entry<String, String> header: headers.entrySet()){
            invocationBuilder.header(header.getKey(), header.getValue());
        }
        return invokeREST(invocationBuilder, tmpTarget.toString());
    }

    /**
     * This method has been separated for the ease of testing (mocks, Mockito...)
     * @param invocation The object that does the request
     * @param target This parameter is there only for logging purpose
     * @return The same {@link Future} of the request method
     */
    protected Future<String> invokeREST(Invocation.Builder invocation, String target) {
        Callable<String> restCall = () -> {
            Response res = invocation.get();
            if(verboseMode) System.out.println("Get " + res.getStatus() + " invoking '" + target + "'");
            return res.readEntity(String.class);
        };
        return threadpool.submit(restCall);
    }
}
