package geekbrains.currencyconverter.controllers;

import com.google.gson.JsonObject;
import geekbrains.currencyconverter.model.Pairs;
import geekbrains.currencyconverter.model.PairsDTO;
import geekbrains.currencyconverter.services.ConverterService;
import geekbrains.currencyconverter.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ApiController {

    private ConverterService service;


    public ApiController() {
    }

    @Autowired
    private ApiController(ConverterService service){
        this.service = service;
    }


    /**
     * По нажатию кнопки "Submit" на странице запроса имени пары будет вызван этот метод
     * Здесь мы получаем имя искомой пары, получаем её цену и к-во к конвертации, сохраняем в репозитории на всякий
     * Зачем сохраняем? Потому что можем.
     * Производим расчет итоговой суммы
     * @param newPairs - объект из формы на странице запроса пары
     * @return String, данные для проверки в POSTMAN или можно использовать как свой API
     * @throws IOException - вылетит если нет такой пары
     */
    @PostMapping(path = "/api/new", produces = "application/json;charset=UTF-8")
    public String setApiCode(@RequestBody PairsDTO newPairs) throws IOException {
        Pairs responsePairs = new Pairs();

        String[] pairs = newPairs.getPairNameDTO().split(" ");
        responsePairs.setPairName(pairs[0]);

        responsePairs.setSourceCurrency(pairs[3]);
        responsePairs.setRequiredCurrency(pairs[5]);

        String quantity = newPairs.getQuantityDTO();
        responsePairs.setQuantity(quantity);

        String lastPairPrice = Parser.getLastPriseByPairs(pairs[0]);
        responsePairs.setPairPrice(lastPairPrice);

        float amount = Integer.parseInt(quantity) * Float.parseFloat(lastPairPrice);
        responsePairs.setAmount(String.valueOf(amount));

        service.setToRepository(responsePairs);

        String response = "{ \"id\" : \"" + responsePairs.getPairName() + "\" , \n" +
                " \"price\" : \"" + responsePairs.getPairPrice() + "\" , \n" +
                " \"quantity\" : \"" + responsePairs.getQuantity() + "\", \n" +
                " \"sourceCurrency\" : \"" + responsePairs.getSourceCurrency() + "\", \n" +
                " \"requiredCurrency\" : \"" + responsePairs.getRequiredCurrency() + "\", \n" +
                " \"amount\" : \"" + responsePairs.getAmount() + "\" } \n";
        System.out.println(response);
        return response;
    }

}

