package com.fetchrewards.repository;


import com.fetchrewards.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * This method gets transactions by asc order
     **/
    @Query("SELECT t FROM Transaction t ORDER BY t.timestamp ASC")
    List<Transaction> getTransactionsByDateAsc();

    @Transactional
    @Modifying
    @Query("UPDATE Transaction t SET t.points = t.points + :updatedPoints WHERE t.id = :id")
    void updateTransactionPointsLeft(@Param("updatedPoints") int updatedPoints, @Param("id") Long id);

}
