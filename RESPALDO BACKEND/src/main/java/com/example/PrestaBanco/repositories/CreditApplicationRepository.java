package com.example.PrestaBanco.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.PrestaBanco.entities.CreditApplicationEntity;

import java.util.List;

@Repository
public interface CreditApplicationRepository  extends JpaRepository<CreditApplicationEntity, Long> {

    @Query("SELECT c FROM CreditApplicationEntity c WHERE c.client_id = :client_id")
    List<CreditApplicationEntity> findByClientId(@Param("client_id") Long client_id);

    @Query("SELECT c FROM CreditApplicationEntity c WHERE c.credit_application_id = :credit_application_id")
    CreditApplicationEntity findByCreditApplicationId(@Param("credit_application_id") Long credit_application_id);

    @Query("SELECT c FROM CreditApplicationEntity c WHERE c.name = :name")
    CreditApplicationEntity findByName(@Param("name") String name);

    @Query("SELECT c FROM CreditApplicationEntity c WHERE c.credit_date = :credit_date")
    CreditApplicationEntity findByCreditDate(@Param("credit_date") String credit_date);
    @Query("SELECT c FROM CreditApplicationEntity c WHERE c.status = :status")
    CreditApplicationEntity findByStatus(@Param("status") String status);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM CreditApplicationEntity c WHERE c.client_id = :client_id")
    boolean existsByClientId(Long client_id);


}
