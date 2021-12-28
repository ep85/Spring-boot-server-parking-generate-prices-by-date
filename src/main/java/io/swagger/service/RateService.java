package io.swagger.service;

import io.swagger.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateService {
    private final RateRepository rateRepository;
    @Autowired
    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }
}
