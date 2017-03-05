package name.lorenzani.andrea.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Config extends Properties {

    public Config() {
        init();
    }


    public String getDbFilename() {
        return getProperty("dbfile");
    }
    public boolean getVerbose(){ return Boolean.parseBoolean(getOrDefault("verbose", "true").toString()); }

    public void init() {
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
