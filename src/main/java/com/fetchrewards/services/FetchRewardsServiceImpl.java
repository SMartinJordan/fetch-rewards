package com.fetchrewards.services;

import com.fetchrewards.models.Partner;
import com.fetchrewards.models.Points;
import com.fetchrewards.models.Transaction;
import com.fetchrewards.repository.PartnerRepository;
import com.fetchrewards.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FetchRewardsServiceImpl implements FetchRewardsService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByDateAsc() {
        return transactionRepository.getTransactionsByDateAsc();
    }

    @Override
    public Map<String, Integer> sendUserBalanceReport() {
        return getUserBalanceReport(partnerRepository.findAll());
    } // change List by Map

    public Map<String, Integer> getUserBalanceReport(List<Partner> partners) {
        Map<String, Integer> balanceReport = new HashMap<>();
        for (Partner p : partners) {
            balanceReport.put(p.getPayer(), p.getPoints());
        }
        return balanceReport;
    }

    @Override
    public List<Partner> spendPoints(Points points) {
        return spend(points.getPoints());
    }

    /**
     * @param points
     * @return the spending report = list of partners with the amount of points spent for each partner
     */
    public List<Partner> spend(int points) {
        List<Partner> partners = new ArrayList<>();
        List<Transaction> transactionSortedByDateAsc = getTransactionsByDateAsc();
        Map<String, Integer> expenses = new HashMap<>();
        String payer = "";
        int balance = 0;
        int t_amount = 0;
        int t_points = 0;
        int amount = 0;

        for (Transaction transaction : transactionSortedByDateAsc) {
            if (points == 0) break;
            payer = transaction.getPayer();
            t_points = transaction.getPoints();

            balance = getPartner(payer).getPoints();
            if (balance > 0) {
                amount = Math.min(t_points, balance);
                if (t_points < points) {
                    updatePartnerPoints(payer, -1 * t_points);
                    expenses.put(payer, expenses.getOrDefault(payer, 0) - t_points);
                    transactionRepository.deleteById(transaction.getId());
                } else {
                    amount = Math.min(amount, points);
                    updatePartnerPoints(payer, -1 * amount);
                    expenses.put(payer, expenses.getOrDefault(payer, 0) - amount);
                    transactionRepository.updateTransactionPointsLeft(amount, transaction.getId());
                }


                points -= amount;

            }
        }

        for (Map.Entry<String, Integer> expense : expenses.entrySet()) {
            partners.add(new Partner(expense.getKey(), -1 * expense.getValue()));
        }

        return getSpendingReport(expenses);
    }

    /**
     * This method will create a spending report after spending points.
     *
     * @param expenses
     * @return the spending report.
     */
    private List<Partner> getSpendingReport(Map<String, Integer> expenses) {
        List<Partner> report = new ArrayList<>();
        for (Map.Entry<String, Integer> expense : expenses.entrySet()) {
            report.add(new Partner(expense.getKey(), expense.getValue()));
        }
        return report;
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        int points = transaction.getPoints();
        String payer = transaction.getPayer();
        if (partnerRepository.findPartnerByName(payer) != null) {
            updatePartnerPoints(payer, points);
        } else {
            if (points < 0) partnerRepository.save(new Partner(payer, 0));
            else partnerRepository.save(new Partner(payer, points));
        }
        return transactionRepository.save(transaction);
    }

    @Override
    public void updatePartnerPoints(String payer, int points) {
        partnerRepository.updatePartnerPoints(payer, points);
    }

    @Override
    public Partner getPartner(String payer) {
        return partnerRepository.findPartnerByName(payer);
    }
}
