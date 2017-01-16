import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by WangTao on 2016/12/21.
 */
public class MapPutNullDemo {
    public static void main(String[] args) {
        Map<String, String> hashtable = new Hashtable<>();
        Map<String, String> hashmap = new HashMap<>();
        Map<String, String> concurrentHashmap = new ConcurrentHashMap<>();

        String nullKey = null;
        String nullValue = null;
        String nonNullKey = "key";
        String nonNullValue = "value";

        hashtable.put(nullKey, nullValue);
        hashtable.put(nullKey, nonNullValue);
        hashtable.put(nonNullKey, nullValue);
        hashtable.put(nonNullKey, nonNullValue);

        hashmap.put(nullKey, nullValue);
        hashmap.put(nullKey, nonNullValue);
        hashmap.put(nonNullKey, nullValue);
        hashmap.put(nonNullKey, nonNullValue);

        concurrentHashmap.put(nullKey, nullValue);
        concurrentHashmap.put(nullKey, nonNullValue);
        concurrentHashmap.put(nonNullKey, nullValue);
        concurrentHashmap.put(nonNullKey, nonNullValue);
    }
}
