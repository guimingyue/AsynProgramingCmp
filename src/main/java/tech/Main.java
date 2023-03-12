package tech;// Licensed under the Apache License, Version 2.0 (the "License");

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        byte[] res = new Main().handle("http://img.alicdn.com/bao/uploaded/i3/O1CN019bWWGM25tWZpem5JN_!!0-paimai.jpg_460x460.jpg");
        System.out.println(res);
        ExecutorService vt = Executors.newVirtualThreadPerTaskExecutor();
        ExecutorService pt = Executors.newThreadPerTaskExecutor(Thread::new);
        for (int i = 0; i < 100_000; i++) {
            pt.submit(() -> {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.print(".");
            });
        }
    }
    
    ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    
    byte[] handle(String url) throws ExecutionException, InterruptedException {
        Future<byte[]> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                return fetchURL(url);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return future.get();
    }
    
    byte[] fetchURL(String url) throws IOException {
        URL u = new URL(url);
        try (var in = u.openStream()) {
            return in.readAllBytes();
        }
    }
}