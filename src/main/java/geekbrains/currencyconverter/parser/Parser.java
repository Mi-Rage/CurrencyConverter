package geekbrains.currencyconverter.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Parser {

    private static final String PRISE_BY_PAIR = "https://api.kraken.com/0/public/Ticker?info=margin&pair=";
    private static final String ALL_PAIRS_NAMES = "https://api.kraken.com/0/public/AssetPairs";


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
     * @return List<String> массив всех имен пар.
     * @throws IOException - вылетит если начудили с url запросом
     */
    public static List<String> getAllPairs() throws IOException {
        List<String> allPairsNames = new ArrayList<>();
        String url = ALL_PAIRS_NAMES;

        String parsingData = getDataFromApi(url);

        JsonObject jsonObject = JsonParser.parseString(parsingData).getAsJsonObject();
        JsonObject resultObject = jsonObject.getAsJsonObject("result");
        for (Map.Entry<String, JsonElement> item : resultObject.entrySet()) {
            String name = item.getKey();
            allPairsNames.add(name);
        }

        System.out.println(Arrays.toString(allPairsNames.toArray()));

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
}
