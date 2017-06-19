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


import java.util.List;

/**
 * How is working a virus so contagious? Who knows... but being very contagious I can assume that
 * if a character is next to another it can infect him. Being in the same Comic is not enough so
 * the two characters have to be in the same story.
 * The two algorithms for TextFile and Sqlite are slightly different but the result is the same
 * (the simplest one is the one in the Sqlite version)
 */

public interface IStoriesDataReader {
    List<String> readCharsWithMoreContacts();
}
