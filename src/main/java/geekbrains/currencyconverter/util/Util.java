package geekbrains.currencyconverter.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    /**
     * Утилита получения текущей даты и времени
     * @return текущая дата в заданном формате
     */
    public static String getCurrentDateTime() {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return today.format(formatter);
    }

}
