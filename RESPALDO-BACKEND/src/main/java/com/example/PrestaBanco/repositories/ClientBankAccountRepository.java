package com.example.PrestaBanco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.PrestaBanco.entities.ClientBankAccountEntity;

import java.util.List;

@Repository
public interface ClientBankAccountRepository extends JpaRepository<ClientBankAccountEntity, Long> {

    @Query("SELECT c FROM ClientBankAccountEntity c WHERE c.client_bank_account_id = :client_bank_account_id")
    ClientBankAccountEntity findByClientBankAccountId(@Param("client_bank_account_id") Long client_bank_account_id);

    @Query("SELECT c FROM ClientBankAccountEntity c WHERE c.client_id = :client_id")
    List<ClientBankAccountEntity> findByClientId(@Param("client_id") Long client_id);

    @Query("SELECT c FROM ClientBankAccountEntity c WHERE c.bank_name = :bank_name")
    ClientBankAccountEntity findByBankName(@Param("bank_name") String bank_name);

    @Query("SELECT c FROM ClientBankAccountEntity c WHERE c.account_number = :account_number")
    ClientBankAccountEntity findByAccountNumber(@Param("account_number") String account_number);

    @Query("SELECT c FROM ClientBankAccountEntity c WHERE c.amount = :amount")
    ClientBankAccountEntity findByAmount(@Param("amount") double amount);

    @Query("SELECT c FROM ClientBankAccountEntity c WHERE c.transaction = :transaction")
    ClientBankAccountEntity findByTransaction(@Param("transaction") String transaction);

    @Query("SELECT c FROM ClientBankAccountEntity c WHERE c.transaction_date = :transaction_date")
    ClientBankAccountEntity findByTransactionDate(@Param("transaction_date") String transaction_date);

    @Query("SELECT c FROM ClientBankAccountEntity c WHERE c.account_opening = :account_opening")
    ClientBankAccountEntity findByAccountOpening(@Param("account_opening") String account_opening);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM ClientBankAccountEntity c WHERE c.client_id = :client_id")
    boolean existsByClientId(Long client_id);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM ClientBankAccountEntity c WHERE c.bank_name = :bank_name")
    boolean existsByBankName(String bank_name);





}
