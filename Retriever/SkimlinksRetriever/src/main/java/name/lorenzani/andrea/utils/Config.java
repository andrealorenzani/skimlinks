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
