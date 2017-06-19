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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComicsRetriever extends RetrieverThread {

    /**
     * Unfortunately I cannot use the IoC for RetrieverThread without really
     * sacrifice the readability. This class retrieves all the comics.
     * All the envelopes are extracted in {@link RetrieverThread} and
     * all the specialization is left in the implemented method
     * @param start The number of comics we have already retrieved, as for the offset of Marvel Api
     * @param connector The connector {@link name.lorenzani.andrea.connector.MarvelJerseyConnector}
     * @param om The {@link ObjectMapper} for parsing the json
     */
    public ComicsRetriever(int start, IConnector connector, ObjectMapper om) {
        super("v1/public/comics", start, connector, om);
    }

    @Override
    protected List<String> returnParsedElement(JsonNode elem) {
        JsonNode characters = elem.findValue("characters");
        int totalChar = characters.findValue("available").asInt();
        ArrayList<String> res = new ArrayList<>(3);
        res.add(0, elem.findValue("id").asText());
        res.add(1, elem.findValue("title").asText());
        List<String> charCSV = new ArrayList<>(totalChar);
        if (totalChar <= 20) {
            for (JsonNode character : characters.findValue("items")) {
                String resURI = character.findValue("resourceURI").asText();
                if(resURI.indexOf("characters")+11<resURI.length()) {
                    charCSV.add(resURI.substring(resURI.indexOf("characters") + 11));
                }
            }
        } else {
            if(verboseMode) System.out.println("*** COMIC WITH MORE THAN 20 CHARACTERS!!! ***");
            int queryToBeDone = (totalChar / 100) + ((totalChar%100>0)?1:0);
            for(int i=0; i<queryToBeDone; i++) {
                try {
                    List<List<String>> chars = new CharInComicRetriever(res.get(0), i * 100, connector, om).call().data;
                    for(List<String> singleChar : chars) {
                        if(!singleChar.isEmpty())
                            charCSV.add(singleChar.get(0));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        res.add(2, charCSV.stream().collect(Collectors.joining(",")));
        return res;
    }
}
