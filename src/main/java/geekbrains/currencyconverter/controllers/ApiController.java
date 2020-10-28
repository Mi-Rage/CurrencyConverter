package geekbrains.currencyconverter.controllers;

import geekbrains.currencyconverter.model.Pairs;
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
     * Здесь мы получаем имя искомой пары, получаем её цену и сохраняем в репозитории на всякий
     * Зачем сохраняем? Потому что можем.
     * @param newPairs - объект из формы на странице запроса пары
     * @return String, данные для проверки в POSTMAN или можно использовать как свой API
     * @throws IOException - вылетит если нет такой пары
     */
    @PostMapping(path = "/api/new", produces = "application/json;charset=UTF-8")
    public String setApiCode(@RequestBody Pairs newPairs) throws IOException {
        Pairs responsePairs = new Pairs();
        String pairs = newPairs.getPairName();
        responsePairs.setPairName(pairs);
        responsePairs.setPairPrice(Parser.getLastPriseByPairs(pairs));
        service.setToRepository(responsePairs);
        String response = "{ \"id\" : \"" + responsePairs.getPairName() + "\" , \n" +
                " \"price\" : \"" + responsePairs.getPairPrice() + "\" } \n";
        System.out.println(response);
        return response;
    }

}

