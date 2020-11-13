package geekbrains.currencyconverter.util;

import geekbrains.currencyconverter.model.Rate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Util {

    /**
     * Утилита получения текущей даты и времени
     * Для подстановки в объекты Pairs
     * @return текущая дата в заданном формате
     */
    public static String getCurrentDateTime() {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return today.format(formatter);
    }

    /**
     * Получение списка объектов Rate по выбранному периоду
     * @param sourceRates массив всех Rate объектов, полученных по API
     * @return список в нужном временном диапазоне
     */
    public static List<Rate> getPeriodOfRates(List<Rate> sourceRates) {
        List<Rate> resultPeriodOfTimeRates = new ArrayList<>();

        int periodOfDay = 14;
        int lastIndexOfList = sourceRates.size() - 1;

        Date date = new java.util.Date(sourceRates.get(lastIndexOfList).getTimeStamp() * 1000L);
        String day = new SimpleDateFormat("dd").format(date);
        resultPeriodOfTimeRates.add(sourceRates.get(lastIndexOfList));
        for (int i = lastIndexOfList; i >= 0; i--) {
            Date dateNext = new java.util.Date(sourceRates.get(i).getTimeStamp() * 1000L);
            String dayNext = new SimpleDateFormat("dd").format(dateNext);
            if (!day.equals(dayNext)) {
                resultPeriodOfTimeRates.add(sourceRates.get(i));
                day = dayNext;
                periodOfDay--;
            }
            if (periodOfDay == 0) {
                break;
            }
        }
        Collections.reverse(resultPeriodOfTimeRates);
        return resultPeriodOfTimeRates;
    }

    /**
     * Получаем значения оси X для вывода графика
     * @param sourceRates исходный массив объектов
     * @return List<String> для передачи в Model
     */
    public static List<String> getDateList(List<Rate> sourceRates) {
        List<String> dateList = new ArrayList<>();
        for (Rate each : sourceRates) {
            Date date = new java.util.Date(each.getTimeStamp() * 1000L);
            dateList.add(new SimpleDateFormat("dd-MM-yy").format(date));
        }
        return dateList;
    }

    /**
     * Получаем значения оси Y для вывода графика
     * @param sourceRates исходный массив объектов
     * @return List<String> для передачи в Model
     */
    public static List<Float> getRateList(List<Rate> sourceRates) {
        List<Float> dateList = new ArrayList<>();
        for (Rate each : sourceRates) {
            dateList.add(each.getRateValue());
        }
        return dateList;
    }

}
