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

package name.lorenzani.andrea;

import name.lorenzani.andrea.connector.IConnector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UtilsTest {
    IConnector generateConnector(String connRet) {
        if(connRet!=null){
            IConnector res = (path, headers, params) -> this.generateFuture(connRet);
            return res;
        }
        IConnector res = (path, headers, params) -> {
            FileReader fr = null;
            Future<String> inres = null;
            try {
                if ("v1/public/characters".equals(path))
                    fr = new FileReader("examples/chars.txt");
                else if("v1/public/comics".equals(path))
                    fr = new FileReader("examples/comics.txt");
                else if("v1/public/stories".equals(path))
                    fr = new FileReader("examples/stories.txt");
                else if(path != null && path.startsWith("v1/public/comics") && path.endsWith("characters"))
                    fr = new FileReader("examples/charsincomic.txt");
                else if("v1/public/stories/null/characters".equals(path))
                    fr = new FileReader("examples/charsinstoryempty.txt");
                else if(path != null && path.startsWith("v1/public/stories") && path.endsWith("characters"))
                    fr = new FileReader("examples/charsinstory.txt");
                BufferedReader br = new BufferedReader(fr);
                StringBuilder sb = new StringBuilder();
                String str = null;
                while((str=br.readLine())!=null){
                    sb.append(str+"\n");
                }
                inres = generateFuture(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return inres;
        };
        return res;
    }

    public Future<String> generateFuture(String str){
        return new Future<String>() {
            @Override
            public boolean cancel(boolean b) {
                return b;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return true;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {
                return str;
            }

            @Override
            public String get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
                return str;
            }
        };
    }
}
