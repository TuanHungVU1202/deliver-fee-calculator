package hw.hv.wolt.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class Utils {

    public static String checkNullVal(String val) {
        if (val == null || val.equals("null")) {
            return null;
        }
        return val;
    }

    public static String customMessageObj(String key, Object value) throws JsonProcessingException {
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        return mapToJsonString(map);
    }

    public static <K, V> String mapToJsonString(HashMap<K, V> map) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String ret = mapper.writeValueAsString(map);
        return ret;
    }

    public static Calendar isoTimeStrToDateTimeUTC (String dateTimeStr){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeStr, timeFormatter);
        Date date = Date.from(Instant.from(offsetDateTime));
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("UTC");

        calendar.setTimeZone(timeZone);
        calendar.setTime(date);
        return calendar;
    }
}
