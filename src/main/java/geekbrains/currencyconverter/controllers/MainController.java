package geekbrains.currencyconverter.controllers;

import geekbrains.currencyconverter.model.Pairs;
import geekbrains.currencyconverter.parser.Parser;
import geekbrains.currencyconverter.services.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class MainController {
    // localhost:8889/...
    private final ConverterService service;

    @Autowired
    public MainController(ConverterService service){
        this.service = service;
    }

    /**
     * Главная страница для ввода данных нашего приложения.
     * При загрузке обращается к бирже через API и получет все доступные к обмену пары
     * Выбираем и вводим название желаемой пары криптовалюты, например DASHEUR или ETHUSDT
     * @return страница localhost:8889/app/new
     */
    @GetMapping(path = "/app/new", produces = "text/html")
    public String homePage(Model model) throws IOException {
        List<String> allPairs = Parser.getAllPairs();
        model.addAttribute("allPairs", allPairs);
        return "index";
    }

    /**
     * На этой странице получаем результат в виде "пара" "цена"
     * Данные берем из API биржи по запрошенной в странице index паре
     * @param model - модель дял thymeleaf
     * @return страница localhost:8889/app/price
     */
    @GetMapping(path = "/app/price", produces = "text/html")
    public String somePage(Model model) {
        int lastIndex = service.getRepository().size() - 1;
        Pairs somePair = service.getRepository().get(lastIndex);
        model.addAttribute("somePair", somePair);
        model.addAttribute("somePrice", somePair);
        return "price";
    }

}