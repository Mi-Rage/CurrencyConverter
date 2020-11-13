package geekbrains.currencyconverter.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import geekbrains.currencyconverter.model.Rate;
import geekbrains.currencyconverter.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Parser {

    private static final String PRISE_BY_PAIR = "https://api.kraken.com/0/public/Ticker?info=margin&pair=";
    private static final String ALL_PAIRS_NAMES = "https://api.kraken.com/0/public/AssetPairs";
    private static final String HISTORY_PAIR_RATE = "https://api.kraken.com/0/public/Depth?pair=";


    /**
     * Парсим значение цены для запрошенной пары по API биржи
     * @param pairName - String, запрошенная пара
     * @return - String, полученное по API значение цены
     * @throws IOException - если нет пары вылетит вот это.
     */
    public static String getLastPriseByPairs(String pairName) throws IOException {
        String url = PRISE_BY_PAIR + pairName;

        String parsingData = getDataFromApi(url);

        JsonObject jsonObject = JsonParser.parseString(parsingData).getAsJsonObject();
        JsonObject resultObject = jsonObject.getAsJsonObject("result");
        JsonObject dataResultObject = resultObject.getAsJsonObject(pairName);
        JsonElement price = dataResultObject.get("o");

        return price.getAsString();
    }

    /**
     * Получаем по API список всех доступных к обмену криптовалютных пар
     * Заодно преобразуем в удобочитаемый вид
     * @return List<String> массив всех имен пар.
     * @throws IOException - вылетит если начудили с url запросом
     */
    public static List<String> getAllPairs() throws IOException {
        List<String> allPairsNames = new ArrayList<>();

        String parsingData = getDataFromApi(ALL_PAIRS_NAMES);

        JsonObject jsonObject = JsonParser.parseString(parsingData).getAsJsonObject();
        JsonObject resultObject = jsonObject.getAsJsonObject("result");
        for (Map.Entry<String, JsonElement> item : resultObject.entrySet()) {
            String name = item.getKey();
            JsonObject itemDetails = item.getValue().getAsJsonObject();
            JsonElement wsname = itemDetails.get("wsname");
            if (wsname != null) {
                allPairsNames.add(name + " : " + "from " + wsname.toString()
                        .replaceAll("\"", "")
                        .replaceAll("/", " to "));
            }
        }
        return allPairsNames;
    }

    /**
     * Получаем данные по API по заданному запросу
     * @param url - запрос на сервер в виде url
     * @return String, результат
     * @throws IOException вылетит если запрос не верный
     */
    public static String getDataFromApi(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    /**
     * Получаем историю ставок по выбранно паре, все данные из API объекта
     * @param pairName String имя пары в API
     * @return ArrayList с объектами Rate с выбранным периодом
     * @throws IOException вылетит если накосячили
     */
    public static List<Rate> getRateHistory(String pairName) throws IOException {
        List<Rate> rateList = new ArrayList<>();

        String parsingData = getDataFromApi(HISTORY_PAIR_RATE + pairName);

        JsonObject jsonObject = JsonParser.parseString(parsingData).getAsJsonObject();
        JsonObject resultObject = jsonObject.getAsJsonObject("result");
        for (Map.Entry<String, JsonElement> item : resultObject.entrySet()) {
            JsonObject itemDetails = item.getValue().getAsJsonObject();
            JsonElement asks = itemDetails.get("asks");
            if (asks != null) {
                for (JsonElement each : asks.getAsJsonArray()) {
                    float parsedRateValue = each.getAsJsonArray().get(0).getAsFloat();
                    long parsedDate = each.getAsJsonArray().get(2).getAsInt();
                    Rate rate = new Rate();
                    rate.setRateValue(parsedRateValue);
                    rate.setTimeStamp(parsedDate);
                    rateList.add(rate);
                }
            }
        }

        rateList.sort(Comparator.comparing(Rate::getTimeStamp).reversed());
//        Collections.sort(rateList, new Comparator<Rate>() {
//            public int compare(Rate o1, Rate o2) {
//                return o1.getTimeStamp().compareTo(o2.getTimeStamp());
//            }
//        });
        Util.getAllTimestamp(rateList);

        return Util.getPeriodOfRates(rateList);
    }
}
