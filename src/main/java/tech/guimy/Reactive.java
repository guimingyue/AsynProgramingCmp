package tech.guimy;// Licensed under the Apache License, Version 2.0 (the "License");

import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class Reactive implements Handler {
    @Override
    public List<byte[]> handle(final List<String> urls) {
        return Flux.fromStream(urls.stream()).parallel().map(url -> {
            try {
                return fetchURL(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(ArrayList::new, null);
    }
}
