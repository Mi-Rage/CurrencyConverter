package geekbrains.currencyconverter.repository;

import geekbrains.currencyconverter.model.Pairs;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Repository {
    List<Pairs> storage = new ArrayList<>();

    public List<Pairs> getStorage() {
        return storage;
    }

    public void setStorage(List<Pairs> storage) {
        this.storage = storage;
    }
}
