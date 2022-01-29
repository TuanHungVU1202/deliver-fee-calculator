package hw.hv.wolt.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

    public static Date parseStrToDateTimeUTC (String dateTimeStr){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;

        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeStr, timeFormatter);

        Date date = Date.from(Instant.from(offsetDateTime));
        System.out.println(date);
        return date;
    }
}
