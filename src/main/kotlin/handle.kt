import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

// Licensed under the Apache License, Version 2.0 (the "License");
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
/*suspend fun handle(urls: List<String>): List<ByteArray> = coroutineScope {
    val deferreds: List<Deferred<ByteArray>> = urls.map { url ->
        async(Dispatchers.IO) {
            fetchUrl(url)
        }
    }
    deferreds.awaitAll()
}*/

suspend fun handle(url: String): ByteArray = coroutineScope {
    val deferred: Deferred<ByteArray> = async(Dispatchers.IO) {
        delay(1000)
        fetchUrl(url)
    }
    deferred.await()
}

fun fetchUrl(url: String): ByteArray {
    with(URL(url).openConnection() as HttpURLConnection) {
        return inputStream.readBytes()
    }
}