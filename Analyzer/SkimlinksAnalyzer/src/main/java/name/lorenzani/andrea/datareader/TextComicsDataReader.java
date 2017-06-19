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
import java.util.*;
import java.util.stream.Collectors;

/**
 * PLEASE NOTE: This class could be divided into 3 pieces and we could use the IoC to test the
 * different part. But this would take more time
 */
public class TextComicsDataReader extends TextFileReader<List<String>> implements IComicsDataReader {

    private final ICharsDataReader charsDataReader;

    public TextComicsDataReader(ICharsDataReader charsDataReader) {
        super("comics_"+new Config().getDbFilename()+".txt");
        this.charsDataReader = charsDataReader;
    }

    /**
     * The alghoritm is quite complicated but it returns the number of influence
     * of the single characters. The influence is computed based on the occurrences
     * of characters in the comics plus, for each co-occurrence, a value that is the
     * occurrence of the co-character
     * @return The list of the 10 most influencing characters
     * @throws IOException The usual read exception
     */
    @Override
    protected List<String> readAndAnalyze() throws IOException {
        HashMap<Integer, Integer> occ = new HashMap<>(20000);
        HashMap<Integer, List<Integer>> coocc = new HashMap<>(20000);
        String sCurrentLine;
        while ((sCurrentLine = br.readLine()) != null) {
            String[] values = sCurrentLine.split("\\|");
            if(values.length<3 || values[2].isEmpty()) continue;
            String[] chars = values[2].split(",");
            // We update the occurrences
            for(String character : chars) {
                occ.putIfAbsent(Integer.parseInt(character), 0);
                occ.computeIfPresent(Integer.parseInt(character), (id, charocc) -> charocc + 1);
            }
            // We consider the cooccurrency
            if(chars.length>1){
                for(int i=0; i<chars.length; i++){
                    for(int j=0; j<chars.length; j++) {
                        if(i==j||chars[i].equals(chars[j])) continue;
                        coocc.putIfAbsent(Integer.parseInt(chars[i]), new LinkedList<>());
                        Integer coid = Integer.parseInt(chars[j]);
                        coocc.computeIfPresent(Integer.parseInt(chars[i]), (c, list) -> {
                            list.add(coid);
                            return list;
                        });
                    }
                }
            }
        }
        // We compute the popularity
        HashMap<Integer, Integer> popularity = new HashMap<>(occ);
        coocc.forEach((ch, cochs) -> {
            for(Integer coch: cochs){
                popularity.compute(ch, (x, y)-> y + occ.get(coch));
            }
        });
        // We order the result
        TreeMap<Integer, Integer> ordered = new TreeMap<>();
        popularity.forEach((x,y)->ordered.put(y,x));
        List<Integer> mostPopular = ordered.descendingMap().values().stream().limit(10).collect(Collectors.toList());
        Map<Integer, String> chars = charsDataReader.readCharacters();
        List<String> res = new LinkedList<>();
        mostPopular.forEach(x -> res.add(chars.get(x)));
        return res;
    }

    @Override
    public List<String> readCoOccurrence() {
        return readFileAndCreateResult();
    }
}
