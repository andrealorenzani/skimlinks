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

package name.lorenzani.andrea.encapsulator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.lorenzani.andrea.connector.IConnector;
import name.lorenzani.andrea.utils.Config;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class RetrieverThread implements Callable<RetrievedData> {

    private final String path;
    private final int start;
    protected final IConnector connector;
    protected final ObjectMapper om;
    protected final boolean verboseMode;

    /**
     * This is the base class for all the specific retrievers (characters, comics, characters IN comics...)
     * It extends {@link Callable} to make possible the use with an {@link java.util.concurrent.ExecutorService}
     * The method handles the retrieving of the envelopes and leave the specific data to the specializations
     * @param path The URL for the resource
     * @param start The offset of the data to be requested
     * @param connector The {@link IConnector} (better a {@link name.lorenzani.andrea.connector.MarvelJerseyConnector})
     * @param om The {@link ObjectMapper}
     */
    public RetrieverThread(String path, int start, IConnector connector, ObjectMapper om) {
        this.path = path;
        this.start = start;
        this.connector = connector;
        this.om = om;
        this.verboseMode = new Config().getVerbose();
    }

    /**
     * This method implements the interface {@link Callable}
     * @return The data retrieved by the server
     * @throws Exception No exception should be thrown (everything is catched... not a good idea, usually)
     */
    @Override
    public RetrievedData call() {
        RetrievedData result = null;
        String rawdata=null;
        try {
            HashMap<String, String> pagination = new HashMap<>();
            pagination.put("offset", Integer.toString(start));
            pagination.put("limit", "100");
            rawdata = connector.request(path, new HashMap<>(), pagination).get();
            JsonNode root = om.readValue(rawdata, JsonNode.class);
            JsonNode data = root.findValue("data");
            result = new RetrievedData(data.findValue("offset").asInt(),
                    data.findValue("count").asInt(),
                    data.findValue("total").asInt(),
                    data.findValue("results"));
            if (result.rawData.isArray()) {
                for (final JsonNode element : result.rawData) {
                    result.data.add(returnParsedElement(element));
                }
            }
        } catch (Exception e) {
            if(verboseMode) {
                System.out.println("RawData? " + ((rawdata == null) ? "no" : rawdata.substring(50)));
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * That is the method to specialize for the different type of infos (characters, stories...)
     * @param elem One Json node representing the data we have to extract
     * @return A List of parameters for one occurrence of  the element we are parsing
     */
    protected abstract List<String> returnParsedElement(JsonNode elem);
}
