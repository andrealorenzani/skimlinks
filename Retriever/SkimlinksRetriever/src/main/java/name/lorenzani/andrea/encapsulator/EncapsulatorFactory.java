package name.lorenzani.andrea.encapsulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import name.lorenzani.andrea.connector.ConnectorFactory;

public class EncapsulatorFactory {
    public static IEncapsulator getEncapsulator() {
        return new MarvelEncapsulator(ConnectorFactory.getConnector(), new ObjectMapper());
    }
}
