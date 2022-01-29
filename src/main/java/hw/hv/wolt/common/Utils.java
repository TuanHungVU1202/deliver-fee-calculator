package hw.hv.wolt.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class Utils {

    public static String checkNullVal(String val) {
        if (val == null || val.equals("null")) {
            return null;
        }
        return val;
    }

    public static String objToJsonString(String key, Object value) throws JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        return mapToJsonString(map);
    }

    public static <K, V> String mapToJsonString(HashMap<K, V> map) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String ret = mapper.writeValueAsString(map);
        return ret;
    }

    public static int eurToCents(int eur){
        return eur * 100;
    }

    public static int centsToEur(int cents){
        return cents/100;
    }
}
