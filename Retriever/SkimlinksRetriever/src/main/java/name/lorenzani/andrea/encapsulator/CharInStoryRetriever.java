package name.lorenzani.andrea.encapsulator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.lorenzani.andrea.connector.IConnector;

import java.util.ArrayList;
import java.util.List;

public class CharInStoryRetriever extends RetrieverThread {

    /**
     * Unfortunately I cannot use the IoC for RetrieverThread without really
     * sacrifice the readability. This class retrieves all the characters
     * appearing in a Story. All the envelopes are extracted in {@link RetrieverThread}
     * and all the specialization is left in the implemented method
     * @param storyId The ID of the story for which we want to retrieve the characters
     * @param start The number of elements we have already retrieved, as for the offset of Marvel Api
     * @param connector The connector {@link name.lorenzani.andrea.connector.MarvelJerseyConnector}
     * @param om The {@link ObjectMapper} for parsing the json
     */
    public CharInStoryRetriever(String storyId, int start, IConnector connector, ObjectMapper om) {
        super("v1/public/stories/" + storyId + "/characters", start, connector, om);
    }

    @Override
    protected List<String> returnParsedElement(JsonNode elem) {
        ArrayList<String> res = new ArrayList<>(2);
        res.add(0, elem.findValue("id").asText());
        res.add(1, elem.findValue("name").asText());
        return res;
    }
}
