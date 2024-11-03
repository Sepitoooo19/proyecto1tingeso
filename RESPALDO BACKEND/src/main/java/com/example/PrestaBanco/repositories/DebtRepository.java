package com.example.PrestaBanco.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.PrestaBanco.entities.DebtEntity;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<DebtEntity, Long> {

    @Query("SELECT d FROM DebtEntity d WHERE d.debt_id = :debt_id")
    DebtEntity findByDebtId(@Param("debt_id") Long debt_id);

    @Query("SELECT d FROM DebtEntity d WHERE d.client_id = :client_id")
    List<DebtEntity> findByClientId(@Param("client_id") Long client_id);

    @Query("SELECT d FROM DebtEntity d WHERE d.debt_amount = :debt_amount")
    DebtEntity findByDebtAmount(@Param("debt_amount") double debt_amount);

    @Query("SELECT d FROM DebtEntity d WHERE LOWER(d.debt_type) = LOWER(:debt_type)")
    DebtEntity findByDebtType(@Param("debt_type") String debt_type);

    @Query("SELECT d FROM DebtEntity d WHERE LOWER(d.debt_date) = LOWER(:debt_date)")
    DebtEntity findByDebtDate(@Param("debt_date") String debt_date);

    @Query("SELECT d FROM DebtEntity d WHERE LOWER(d.debt_status) = LOWER(:debt_status)")
    DebtEntity findByDebtStatus(@Param("debt_status") String debt_status);

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END FROM DebtEntity d WHERE d.debt_id = :debt_id")
    boolean existsByDebtId(Long debt_id);

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END FROM DebtEntity d WHERE d.client_id = :client_id")
    boolean existsByClientId(Long client_id);
}
