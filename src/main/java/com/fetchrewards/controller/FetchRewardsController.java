package com.fetchrewards.controller;

import com.fetchrewards.models.Partner;
import com.fetchrewards.models.Points;
import com.fetchrewards.models.Transaction;
import com.fetchrewards.services.FetchRewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
public class FetchRewardsController {
    @Autowired
    private FetchRewardsService fetchRewardsService;

    @GetMapping("/transactions/all")
    public ResponseEntity<List<Transaction>> getTransactions() {
        try {
            return new ResponseEntity<>(fetchRewardsService.getTransactionsByDateAsc(), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't retrieve Transactions: " + ex.getMessage(), ex.getCause());
        }
    }

    @PostMapping("/transactions/new")
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return fetchRewardsService.addTransaction(transaction);

    }

    @GetMapping("user/balance")
    public Map<String, Integer> getPartnersBalances() {
        return fetchRewardsService.sendUserBalanceReport();
    }

    @PostMapping("user/points")
    public List<Partner> spendPoints(@RequestBody Points points) {
        return fetchRewardsService.spendPoints(points);
    }

}
