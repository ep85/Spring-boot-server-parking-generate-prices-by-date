package io.swagger.service;

import io.swagger.model.RateItem;
import io.swagger.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateService {
    private final RateRepository rateRepository;
    @Autowired
    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public void insertAll(List<RateItem> rateItemList) throws Exception {
        for (RateItem rateItem: rateItemList) {
            rateRepository.insertRateItem(rateItem.getDays(), rateItem.getTimes(), rateItem.getTz(), rateItem.getPrice());
        }
    }

    public List<RateItem> getRates() {
        return rateRepository.getAllItems();
    }
}
