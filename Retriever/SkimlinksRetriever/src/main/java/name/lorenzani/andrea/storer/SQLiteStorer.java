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

package name.lorenzani.andrea.storer;

import name.lorenzani.andrea.utils.Config;

import java.sql.*;
import java.util.List;

public class SQLiteStorer implements IStorer {

    private Connection c = null;
    private String dbName;
    private final boolean verboseMode;

    /**
     * Sorry, no time to test the storers. But it was less important,
     * because I have 2 of them, and I had anyway to test them manually
     * @param dbName The database name to open/use
     */
    public SQLiteStorer(String dbName) {
        this.dbName = dbName;
        verboseMode = new Config().getVerbose();
        init();
    }

    /**
     * Utility method to release the resources (no need to use {@link AutoCloseable})
     */
    @Override
    public void close() {
        try{
            if(!c.getAutoCommit()) c.commit();
            c.close();
            if(verboseMode) System.out.println("Closed database successfully");
        }
        catch(Exception e){
            if(verboseMode) e.printStackTrace();
        }
    }

    /**
     * This method creates the Tables, if they are not already created
     * (meaning if the DB file has just been created)
     */
    public void initDatabase() {
        try {
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS CHARACTERS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL)";
            stmt.executeUpdate(sql);
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS STORIES " +
                    "(ID INTEGER PRIMARY KEY     AUTOINCREMENT," +
                    " CHAR           INT     NOT NULL," +
                    " STORY          INT     NOT NULL)";
            stmt.executeUpdate(sql);
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS COMICS " +
                    "(ID INTEGER PRIMARY KEY     AUTOINCREMENT," +
                    " CHAR           INT     NOT NULL," +
                    " COMIC          INT     NOT NULL)";
            stmt.executeUpdate(sql);
            c.commit();
        } catch (SQLException sexc) {
            if(verboseMode) sexc.printStackTrace();
        }
    }

    /**
     * This method estabilishes the connection to the database through JDBC
     */
    public void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            if(verboseMode) System.out.println("Opened database successfully");
            initDatabase();
        } catch (Exception e) {
            if(verboseMode) e.printStackTrace();
        }
    }

    /**
     * This method takes the data retrieved by the Encapsulator (for characters)
     * and store them in the database
     * @param data The data to be stored in a SQLite db
     */
    @Override
    public void storeCharacters(List<List<String>> data) {
        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO CHARACTERS (ID,NAME) " +
                    "VALUES (?, ?);");
            for (List<String> singleData : data) {
                if(singleData.size()<2) continue;
                String charId = singleData.get(0);
                String charName = singleData.get(1);
                stmt.clearParameters();
                stmt.setInt(1, Integer.parseInt(charId));
                stmt.setString(2, charName);
                stmt.executeUpdate();
            }
            stmt.close();
            c.commit();
        } catch (Exception e) {
            if(verboseMode) e.printStackTrace();
        }
    }

    /**
     * This method takes the data retrieved by the Encapsulator (for comics)
     * and store them in the database
     * @param data The data to be stored in a SQLite db
     */
    public void storeComics(List<List<String>> data) {
        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO COMICS (CHAR,COMIC) " +
                    "VALUES (?, ?);");
            for (List<String> comic : data) {
                try {
                    if (comic.size() < 3) continue;
                    String comicId = comic.get(0);
                    String comicChars = comic.get(2);
                    String[] chars = comicChars.split(",");
                    if (chars.length == 0 || chars[0].isEmpty()) continue;
                    for (String charId: chars) {
                        stmt.clearParameters();
                        stmt.setInt(1, Integer.parseInt(charId));
                        stmt.setInt(2, Integer.parseInt(comicId));
                        stmt.executeUpdate();
                    }
                }
                catch(Exception e) {
                    if(verboseMode) System.out.println("Ignoring "+e.getMessage()+" {"+comic+"}");
                }
            }
            stmt.close();
            c.commit();
        } catch (Exception e) {
            if(verboseMode) e.printStackTrace();
        }
    }

    /**
     * This method takes the data retrieved by the Encapsulator (for stories)
     * and store them in the database
     * @param data The data to be stored in a SQLite db
     */
    public void storeStories(List<List<String>> data) {
        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO STORIES (CHAR,STORY) " +
                    "VALUES (?, ?);");
            for (List<String> story : data) {
                try {
                    String storyChrs = story.get(2);
                    String storyId = story.get(0);
                    String[] chars = storyChrs.split(",");
                    if (chars.length == 0 || chars[0].isEmpty()) continue;
                    for (String charId: chars) {
                        stmt.setInt(1, Integer.parseInt(charId));
                        stmt.setInt(2, Integer.parseInt(storyId));
                        stmt.executeUpdate();
                    }
                }
                catch(Exception e) {
                    if(verboseMode) System.out.println("Ignoring "+e.getMessage()+" {"+story+"}");
                }
            }
            stmt.close();
            c.commit();
        } catch (Exception e) {
            if(verboseMode) e.printStackTrace();
        }
    }
}
