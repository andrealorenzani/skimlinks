package name.lorenzani.andrea.connector;

import java.util.Map;
import java.util.concurrent.Future;

@FunctionalInterface
public interface IConnector {
    Future<String> request(String path, Map<String, String> headers, Map<String, String> params);
}
