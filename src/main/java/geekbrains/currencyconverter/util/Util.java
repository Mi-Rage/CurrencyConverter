package geekbrains.currencyconverter.util;

import geekbrains.currencyconverter.model.Rate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util {

    /**
     * Утилита получения текущей даты и времени
     *
     * @return текущая дата в заданном формате
     */
    public static String getCurrentDateTime() {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return today.format(formatter);
    }

    /**
     * Метод преобразования timestamp в нормальный вид
     *
     * @param timestamp long значение из объекта API
     * @return String обычное форматированное представление даты
     */
    public static String getDateFromTimestamp(long timestamp) {
        Date date = new java.util.Date(timestamp * 1000L);
//        String[] patterns = {"dd-MM-yyyy HH:mm:ss z", "yyyy", "MM", "dd"};
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3"));
        String formattedDate = sdf.format(date);
//        for (String pattern : patterns) {
//            System.out.println(new SimpleDateFormat(pattern).format(date));
//        }
        return formattedDate;
    }

    public static void getAllTimestamp(List<Rate> sourceRates) {
        for (Rate each : sourceRates) {
            Date date = new java.util.Date(each.getTimeStamp() * 1000L);
            String day = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
            System.out.println(each.getTimeStamp() + " " + day);
        }
    }

    /**
     * Получение списка объектов Rate по заданному периоду
     *
     * @param sourceRates массив всех Rate объектов, полученных по API
     * @return список в нужном временном диапазоне
     */
    public static List<Rate> getPeriodOfRates(List<Rate> sourceRates) {
        List<Rate> resultPeriodOfTimeRates = new ArrayList<>();

        int countOfDay = 0;
        Date date = new java.util.Date(sourceRates.get(0).getTimeStamp() * 1000L);
        String day = new SimpleDateFormat("dd").format(date);
        resultPeriodOfTimeRates.add(sourceRates.get(0));
        for (int i = 1; i < sourceRates.size(); i++) {
            Date dateNext = new java.util.Date(sourceRates.get(i).getTimeStamp() * 1000L);
            String dayNext = new SimpleDateFormat("dd").format(dateNext);
            if (!day.equals(dayNext)) {
                resultPeriodOfTimeRates.add(sourceRates.get(i));
                day = dayNext;
                countOfDay++;
            }
            if (countOfDay > 8) {
                break;
            }
        }
        return resultPeriodOfTimeRates;
    }

    public static List<String> getDateList(List<Rate> sourceRates) {
        List<String> dateList = new ArrayList<>();
        for (Rate each : sourceRates) {
            Date date = new java.util.Date(each.getTimeStamp() * 1000L);
            dateList.add(new SimpleDateFormat("dd-MM-yyyy").format(date));
        }
        return dateList;
    }

    public static List<Float> getRateList(List<Rate> sourceRates) {
        List<Float> dateList = new ArrayList<>();
        for (Rate each : sourceRates) {
            dateList.add(each.getRateValue());
        }
        return dateList;
    }

}
