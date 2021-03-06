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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TextPopularityDataReader extends TextFileReader<List<String>> implements IPopularityDataReader{
    private final ICharsDataReader charsDataReader;

    public TextPopularityDataReader(ICharsDataReader charsDataReader) {
        super("comics_"+new Config().getDbFilename()+".txt");
        this.charsDataReader = charsDataReader;
    }

    @Override
    protected List<String> readAndAnalyze() throws IOException {
        HashMap<Integer, Integer> occ = new HashMap<>(20000);
        Map<Integer, String> theChrs = charsDataReader.readCharacters();
        String sCurrentLine;
        while ((sCurrentLine = br.readLine()) != null) {
            String[] values = sCurrentLine.split("\\|");
            if (values.length < 3 || values[2].isEmpty()) continue;
            String[] chars = values[2].split(",");
            for (String character : chars) {
                occ.putIfAbsent(Integer.parseInt(character), 0);
                occ.computeIfPresent(Integer.parseInt(character), (id, charocc) -> charocc + 1);
            }
        }
        TreeMap<Integer, String> res = new TreeMap<>();
        occ.entrySet().stream().forEach(x -> {
            res.put(x.getValue(), theChrs.get(x.getKey()));
        });
        return res.descendingMap().values().stream().limit(10).collect(Collectors.toList());
    }

    @Override
    public List<String> readOccurrences() {
        return readFileAndCreateResult();
    }
}
