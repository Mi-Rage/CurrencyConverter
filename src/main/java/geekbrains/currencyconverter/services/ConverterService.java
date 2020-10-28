package geekbrains.currencyconverter.services;

import geekbrains.currencyconverter.model.Pairs;
import geekbrains.currencyconverter.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConverterService {

    private final Repository repository;

    @Autowired
    public ConverterService(Repository repository) {
        this.repository = repository;
    }

    public void setToRepository(Pairs pairs) {
        repository.getStorage().add(pairs);
    }

    public List<Pairs> getRepository() {
        return repository.getStorage();
    }
}
