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

package name.lorenzani.andrea.datareader;


import name.lorenzani.andrea.datareader.baseclasses.TextFileReader;
import name.lorenzani.andrea.utils.Config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TextCharsDataReader extends TextFileReader<Map<Integer, String>>
        implements ICharsDataReader, IListCharactersDataReader {

    public TextCharsDataReader() {
        super("characters_"+new Config().getDbFilename()+".txt");
    }

    @Override
    public Map<Integer, String> readCharacters() {
        return readFileAndCreateResult();
    }

    @Override
    protected Map<Integer, String> readAndAnalyze() throws IOException {
        HashMap<Integer, String> res = new HashMap<>(20000);
        String sCurrentLine;
        while ((sCurrentLine = br.readLine()) != null) {
            String[] values = sCurrentLine.split("\\|");
            res.put(Integer.parseInt(values[0]), values[1]);
        }
        return res;
    }

    @Override
    public List<String> readCharactersAlphabeticalOrder() {
        TreeSet<String> characters = new TreeSet<>(readCharacters().values());
        return characters.stream().collect(Collectors.toList());
    }
}
