package name.lorenzani.andrea.encapsulator;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.LinkedList;
import java.util.List;

public class RetrievedData {
    public int offset;
    public int count;
    public int total;
    public JsonNode rawData;
    public List<List<String>> data;

    public RetrievedData(int offset, int count, int total, JsonNode rawData) {
        this.offset = offset;
        this.count = count;
        this.total = total;
        this.rawData = rawData;
        data = new LinkedList<>();
    }
}
