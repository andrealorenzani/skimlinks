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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class TextFileReader<T> {

    protected BufferedReader br = null;
    protected FileReader fr = null;
    protected final String filename;

    /**
     * Base class for the TextReaders. I know it is not nice to put it like that
     * cause without IoC tests are harder. But this makes easier the coding.
     * Please note that by implementing the method readAndAnalyze and by using
     * the template of the class we can focus on the logic of the algorithm
     * @param filename The file to be opened
     */
    public TextFileReader(String filename) {
        this.filename = filename;
    }

    /**
     * This is the main method. To use this implement the readAndAnalyze method
     * @return An object of the template of the class
     */
    public T readFileAndCreateResult() {
        T res = null;
        try {

            fr = new FileReader(filename);
            br = new BufferedReader(fr);

            res = readAndAnalyze();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }
        return res;
    }

    /**
     * The method to implement. It is the return of the readFileAndCreateResult
     * so they share the return value
     * @return The object to be returned by readFileAndCreateResult
     * @throws IOException Reading the file can throw a IOException so... don't care about it
     */
    protected abstract T readAndAnalyze() throws IOException;
}
