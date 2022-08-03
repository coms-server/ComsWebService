package coms.kw.ac.kr.server.service.tools;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This interafce contains a {@code String} parsable date field.
 */
public interface DateStringContainer {

    List<LocalDateTime> getDates();

    void setDateStrings(List<String> strings);
}