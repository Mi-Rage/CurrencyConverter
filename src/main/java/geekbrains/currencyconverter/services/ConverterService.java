package geekbrains.currencyconverter.services;

import geekbrains.currencyconverter.model.Pairs;
import geekbrains.currencyconverter.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public int getSizeRepository() {
        return repository.getStorage().size();
    }

    public int getLastIndexRepository() {
        return getSizeRepository() - 1;
    }

    public Pairs getPairFromIndex(int index) {
        return  repository.getStorage().get(index);
    }

    public Pairs getPreviousPair() {
        if (getSizeRepository() > 1) {
            return getPairFromIndex(getLastIndexRepository() - 1);
        } else {
            return getPairFromIndex(0);
        }
    }

    public List<Pairs> getLastPairs() {
        int NUMBER_ON_PAGE = 20;

        List<Pairs> listLastPairs = new ArrayList<>();
        int lastIdRepository = getLastIndexRepository();
        int outputLimitId = getSizeRepository() % NUMBER_ON_PAGE == getSizeRepository() ? 0
                : getSizeRepository() % NUMBER_ON_PAGE;

        for (int i = lastIdRepository; i >= outputLimitId; i--) {
            Pairs eachPair = getPairFromIndex(i);
            listLastPairs.add(eachPair);
        }

        return listLastPairs;
    }

    public void clearRepository() {
        List<Pairs> cleanList = new ArrayList<>();
        repository.setStorage(cleanList);
    }
}
