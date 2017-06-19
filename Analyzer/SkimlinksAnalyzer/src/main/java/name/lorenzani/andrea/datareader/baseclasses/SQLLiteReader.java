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

package name.lorenzani.andrea.datareader.baseclasses;

import name.lorenzani.andrea.utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLLiteReader {

    protected Connection c = null;
    protected String dbName;
    private final boolean verbose;

    /**
     * This class open the connection to the database (and eventually it closes it)
     * The database is read-only (no write is done... even if it is eventually possible)
     * @param dbName The database name
     */
    public SQLLiteReader(String dbName) {
        this.dbName = dbName;
        verbose=new Config().getVerbose();
        init();
    }

    public void close() {
        try{
            c.close();
            if(verbose) System.out.println("Closed database successfully");
        }
        catch(Exception e){
            if(verbose) e.printStackTrace();
        }
    }

    /**
     * This method initialize the connection to the db through JDBC
     */
    public void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            c.setAutoCommit(false);
            if(verbose) System.out.println("Opened database successfully");
        } catch (Exception e) {
            if(verbose) e.printStackTrace();
        }
    }
}
