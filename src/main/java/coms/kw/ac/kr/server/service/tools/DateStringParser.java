package coms.kw.ac.kr.server.service.tools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// TODO: javadoc
public class DateStringParser {

    public static final DateTimeFormatter FORMATTER_TYPE_DB = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    private static final DateTimeFormatter DATE_STRING_FORMATTER_NORMAL = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final DateTimeFormatter DATE_STRING_FORMATTER_TODAY = DateTimeFormatter.ofPattern("kk:mm");

    public static LocalDateTime parse(String localDateTimeString) {
        try {
            return LocalDateTime.parse(localDateTimeString, FORMATTER_TYPE_DB);
        } catch (Exception exception) {
            return null;
        }
    }

    public static void prettifyDateString(Collection<? extends DateStringContainer> list) {
        for (DateStringContainer vo : list)
            prettifyDateString(vo);
    }

    public static <T extends DateStringContainer> void prettifyDateString(T vo) {
        List<LocalDateTime> dates = vo.getDates();
        List<String> strings = new ArrayList<>();
        
        for (LocalDateTime date : dates) {
            boolean isToday = date.toLocalDate().isEqual(LocalDate.now());
            if (isToday)
                strings.add(date.format(DATE_STRING_FORMATTER_TODAY));
            else
                strings.add(date.format(DATE_STRING_FORMATTER_NORMAL));
        }

        vo.setDateStrings(strings);
    }
}
