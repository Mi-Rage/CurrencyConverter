package geekbrains.currencyconverter.controllers;

import geekbrains.currencyconverter.model.Pairs;
import geekbrains.currencyconverter.model.Rate;
import geekbrains.currencyconverter.parser.Parser;
import geekbrains.currencyconverter.services.ConverterService;
import geekbrains.currencyconverter.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class MainController {
    // localhost:5000/...
    private final ConverterService service;

    @Autowired
    public MainController(ConverterService service){
        this.service = service;
    }

    /**
     * Главная страница для ввода данных нашего приложения.
     * При загрузке обращается к бирже через API и получет все доступные к обмену пары
     * а так же значения цены для BTC и ETH
     * Выбираем в выпадающем списке нужную пару криптовалюты и вводим количсество к обмену
     * @return страница localhost:5000/index
     */
    @GetMapping(path = "/", produces = "text/html")
    public String homePage(Model model) throws IOException {
        List<String> allPairs = Parser.getAllPairs();
        model.addAttribute("allPairs", allPairs);
        float bitcoinPriceValue = Float.parseFloat((Parser.getLastPriseByPairs("XBTUSDT")));
        float ethPriceValue = Float.parseFloat((Parser.getLastPriseByPairs("ETHUSDT")));
        model.addAttribute("bitcoinPrice", bitcoinPriceValue);
        model.addAttribute("ethPrice", ethPriceValue);
        return "index";
    }

    /**
     * На этой странице получаем результат в виде "пара" "цена"
     * Данные берем из API биржи по запрошенной в странице index паре
     * Заодно передаем данные для графика последних изменений курса
     * @param model - модель для thymeleaf
     * @return страница localhost:5000/result
     */
    @GetMapping(path = "/result", produces = "text/html")
    public String somePage(Model model) {
        int lastIndex = service.getLastIndexRepository();
        Pairs somePair = service.getRepository().get(lastIndex);
        model.addAttribute("somePair", somePair);
        model.addAttribute("somePrice", somePair);

        Pairs previousPair = service.getPreviousPair();
        model.addAttribute("previousPair", previousPair);

        List<Rate> rateList = somePair.getRateList();
        List<String> dateList = Util.getDateList(rateList);
        List<Float> valueList = Util.getRateList(rateList);
        model.addAttribute("dateList", dateList);
        model.addAttribute("valueList", valueList);

        return "result";
    }

    /**
     * Страница админки.
     * Здесь можем посмотреть общее к-во запросов, последние запросы
     * @param model - модель для thymeleaf
     * @return страница localhost:5000/admin
     */
    @GetMapping(path = "/admin", produces = "text/html")
    public String enterAdmin(Model model) {
        model.addAttribute("lastPairsList", service.getLastPairs());
        model.addAttribute("totalConversion", service.getSizeRepository());
        return "admin";
    }

}