package name.lorenzani.andrea.connector;

import name.lorenzani.andrea.utils.Config;
import name.lorenzani.andrea.utils.StaticVars;
import org.glassfish.jersey.client.ClientConfig;

public class ConnectorFactory {
    public static IConnector getConnector() {
        Config conf = new Config();
        BaseJerseyConnector bjc = new BaseJerseyConnector(StaticVars.marvelEndpoint, new ClientConfig());
        return new MarvelJerseyConnector(conf.getApiPublicKey(), conf.getApiPrivateKey(), bjc);
    }
}
