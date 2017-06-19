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
import name.lorenzani.andrea.utils.StaticVars;
import org.glassfish.jersey.client.ClientConfig;

public class ConnectorFactory {
    public static IConnector getConnector() {
        Config conf = new Config();
        BaseJerseyConnector bjc = new BaseJerseyConnector(StaticVars.marvelEndpoint, new ClientConfig());
        return new MarvelJerseyConnector(conf.getApiPublicKey(), conf.getApiPrivateKey(), bjc);
    }
}
