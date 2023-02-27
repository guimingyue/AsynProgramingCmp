package tech.guimy;// Licensed under the Apache License, Version 2.0 (the "License");

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
public class ThreadPool implements Handler {
    int processors = Runtime.getRuntime().availableProcessors();
    ThreadPoolExecutor pool = new ThreadPoolExecutor(processors, 2 * processors, 30, TimeUnit.MINUTES, new ArrayBlockingQueue<>(512));
    @Override
    public List<byte[]> handle(final List<String> urls) {
        List<Future<byte[]>> futures = new ArrayList<>();
        for (String url : urls) {
            Future<byte[]> future = pool.submit(() -> fetchURL(url));
            futures.add(future);
        }
        List<byte[]> res = new ArrayList<>();
        try {
            for (var future : futures) {
                    res.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
