package com.fetchrewards.services;


import com.fetchrewards.models.Partner;
import com.fetchrewards.models.Points;
import com.fetchrewards.models.Transaction;

import java.util.List;
import java.util.Map;

public interface FetchRewardsService {
    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsByDateAsc();

    Map<String, Integer> sendUserBalanceReport();

    List<Partner> spendPoints(Points points);

    Transaction addTransaction(Transaction transaction);

    void updatePartnerPoints(String payer, int points);

    Partner getPartner(String payer);
}
