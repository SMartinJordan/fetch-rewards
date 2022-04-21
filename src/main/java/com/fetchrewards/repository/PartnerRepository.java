package com.fetchrewards.repository;

import com.fetchrewards.models.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    @Query("SELECT p FROM Partner p  WHERE p.payer=(:payer)")
    Partner findPartnerByName(@Param("payer") String payer);


    @Transactional //
    @Modifying
    @Query("UPDATE Partner p SET p.points = p.points + :points WHERE p.payer = :payer")
    void updatePartnerPoints(@Param("payer") String payer, @Param("points") int points);
}
