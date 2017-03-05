package name.lorenzani.andrea.encapsulator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import name.lorenzani.andrea.connector.IConnector;

import java.util.ArrayList;
import java.util.List;

public class CharactersRetriever extends RetrieverThread {

    /**
     * Unfortunately I cannot use the IoC for RetrieverThread without really
     * sacrifice the readability. This class retrieves all the characters.
     * All the envelopes are extracted in {@link RetrieverThread} and
     * all the specialization is left in the implemented method
     * @param start The number of characters we have already retrieved, as for the offset of Marvel Api
     * @param conn The connector {@link name.lorenzani.andrea.connector.MarvelJerseyConnector}
     * @param om The {@link ObjectMapper} for parsing the json
     */
    public CharactersRetriever(int start, IConnector conn, ObjectMapper om){
        super("v1/public/characters", start, conn, om);
    }

    @Override
    protected List<String> returnParsedElement(JsonNode elem) {
        List<String> res = new ArrayList<>();
        res.add(0, elem.findValue("id").asText());
        res.add(1, elem.findValue("name").asText());
        return res;
    }

}
