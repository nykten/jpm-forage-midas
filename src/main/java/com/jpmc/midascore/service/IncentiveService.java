package com.jpmc.midascore.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;

@Service
public class IncentiveService {
    private static final Logger logger = LoggerFactory.getLogger(IncentiveService.class);
    private static final String INCENTIVE_URL = "http://localhost:8080/incentive";

    private final RestTemplate restTemplate;

    public IncentiveService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Incentive getIncentive(Transaction transaction) {
        try {
            logger.info("Getting incentive for transaction {}", transaction);
            Incentive incentive = restTemplate.postForObject(INCENTIVE_URL, transaction, Incentive.class);
            logger.info("Incentive received: {}", incentive);
            return incentive;
        } catch (Exception e) {
            logger.error("Error getting incentive: {}", transaction, e);
            return new Incentive(0.0f);
        }
    }
}
