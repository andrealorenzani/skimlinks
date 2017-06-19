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
 * How is working a virus so contagious? Who knows... but being very contagious I can assume that
 * if a character is next to another it can infect him. Being in the same Comic is not enough so
 * the two characters have to be in the same story.
 * *** THIS IS WHAT THE ALGORITHM DOES ***
 * It considers (unfortunately not in a chronological way) a character and it gives back how many
 * characters are infected by him and by anyone who is infected
 * To this number it adds the number of cooccurrencies in a story, cause the direct contagion is
 * more valuable
 */

public class TextStoriesDataReader extends TextFileReader<List<String>> implements IStoriesDataReader{
    private final ICharsDataReader charReader;

    public TextStoriesDataReader(ICharsDataReader charReader) {
        super("stories_"+new Config().getDbFilename()+".txt");
        this.charReader = charReader;
    }

    @Override
    protected List<String> readAndAnalyze() throws IOException {
        HashMap<Integer, Set<Integer>> costory = new HashMap<>();
        String sCurrentLine;
        while ((sCurrentLine = br.readLine()) != null) {
            String[] values = sCurrentLine.split("\\|");
            if(values.length<3 || values[2].isEmpty()) continue;
            String[] copart = values[2].split(",");
            if(copart.length<2) continue;
            for(int i=0; i<copart.length; i++){
                for(int j=0; j<copart.length; j++){
                    if(i==j || copart[i].equals(copart[j])) continue;
                    costory.putIfAbsent(Integer.parseInt(copart[i]), new TreeSet<>());
                    costory.get(Integer.parseInt(copart[i])).add(Integer.parseInt(copart[j]));
                }
            }
        }
        TreeMap<Integer, Integer> perc = new TreeMap<>();
        Map<Integer, Set<Integer>> dynamic = new HashMap<>(); // Yes, I am using dynamic programming
        costory.entrySet().stream().forEach(x -> {
            Set<Integer> infected = new TreeSet<>();
            Set<Integer> infecting = new TreeSet<>();
            Integer toBeInfected = x.getKey();
            infected.add(toBeInfected);
            infecting.addAll(x.getValue());
            infect(infected, infecting, costory, dynamic);
            dynamic.put(toBeInfected, infected);
            perc.put(infected.size()+costory.get(toBeInfected).size(), toBeInfected);
        });
        List<Integer> toBeInfected = perc.descendingMap().values().stream().limit(10).collect(Collectors.toList());
        Map<Integer, String> chars = charReader.readCharacters();
        List<String> res = new ArrayList<>(10);
        toBeInfected.stream().forEach(x -> res.add(chars.get(x)));
        return res;
    }

    public void infect(Set<Integer> infected, Set<Integer> infecting, Map<Integer, Set<Integer>> costory, Map<Integer, Set<Integer>> dynamic){
        Set<Integer> newInfecting = new TreeSet<>();
        infecting.stream().filter(x -> !infected.contains(x)).forEach(x -> {
            infected.add(x);
            if(dynamic.containsKey(x)) {
                infected.addAll(dynamic.get(x));
            }
            else {
                newInfecting.addAll(costory.get(x).stream().filter(z -> !infected.contains(z)).collect(Collectors.toList()));
            }
        });
        if(newInfecting.size()>0) infect(infected, newInfecting, costory, dynamic);
    }

    @Override
    public List<String> readCharsWithMoreContacts() {
        return readFileAndCreateResult();
    }
}
