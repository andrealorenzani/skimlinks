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

package name.lorenzani.andrea.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config extends Properties{

    /**
     * Property file with useful methods for avoiding typos
     */
    public Config(){
        init();
    }

    public String getApiPublicKey(){ return getProperty("publicKey"); }
    public String getApiPrivateKey(){ return getProperty("privateKey"); }
    public String getDbFilename(){ return getOrDefault("dbfile", "dbfile").toString(); }
    public boolean getVerbose(){ return Boolean.parseBoolean(getOrDefault("verbose", "true").toString()); }

    public void init(){
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            // load a properties file
            load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
