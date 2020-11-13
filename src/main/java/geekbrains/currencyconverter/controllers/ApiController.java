package geekbrains.currencyconverter.controllers;

import geekbrains.currencyconverter.model.Pairs;
import geekbrains.currencyconverter.model.PairsDTO;
import geekbrains.currencyconverter.model.Rate;
import geekbrains.currencyconverter.services.ConverterService;
import geekbrains.currencyconverter.parser.Parser;
import geekbrains.currencyconverter.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

        responsePairs.setDate(Util.getCurrentDateTime());

        String[] pairs = newPairs.getPairNameDTO().split(" ");
        responsePairs.setPairName(pairs[0]);

        responsePairs.setSourceCurrency(pairs[3]);
        responsePairs.setRequiredCurrency(pairs[5]);

        int quantity = newPairs.getQuantityDTO();
        responsePairs.setQuantity(quantity);

        String lastPairPrice = Parser.getLastPriseByPairs(pairs[0]);
        responsePairs.setPairPrice(lastPairPrice);

        float amount = quantity * Float.parseFloat(lastPairPrice);
        responsePairs.setAmount(String.valueOf(amount));

        List<Rate> rateList = Parser.getRateHistory(responsePairs.getPairName());
        responsePairs.setRateList(rateList);

        service.setToRepository(responsePairs);

        String response = "{ \"id\" : \"" + responsePairs.getPairName() + "\" , \n" +
                " \"date\" : \"" + responsePairs.getDate() + "\", \n" +
                " \"price\" : \"" + responsePairs.getPairPrice() + "\" , \n" +
                " \"quantity\" : \"" + responsePairs.getQuantity() + "\", \n" +
                " \"sourceCurrency\" : \"" + responsePairs.getSourceCurrency() + "\", \n" +
                " \"requiredCurrency\" : \"" + responsePairs.getRequiredCurrency() + "\", \n" +
                " \"amount\" : \"" + responsePairs.getAmount() + "\" } \n";
        System.out.println(response);
        return response;
    }

    /**
     * Команда очистки репозитория
     * @return String сообщение об очистке.
     */
    @GetMapping(path = "/api/clear", produces = "application/json;charset=UTF-8")
    public String clearRepo() {
        service.clearRepository();
        System.out.println("Requested cleanup of the repository");
        return "Repository cleared";
    }

}

