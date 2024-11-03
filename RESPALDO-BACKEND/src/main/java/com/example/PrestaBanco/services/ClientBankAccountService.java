package com.example.PrestaBanco.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.PrestaBanco.repositories.ClientBankAccountRepository;
import com.example.PrestaBanco.entities.ClientBankAccountEntity;
import java.util.List;

@Service
public class ClientBankAccountService {

    @Autowired
    private ClientBankAccountRepository clientBankAccountRepository;

    public List<ClientBankAccountEntity> findAll() {
        return clientBankAccountRepository.findAll();
    }

    public ClientBankAccountEntity findByClientBankAccountId(Long client_bank_account_id) {
        return clientBankAccountRepository.findByClientBankAccountId(client_bank_account_id);
    }

    public List<ClientBankAccountEntity> findByClientId(Long client_id) {
        return clientBankAccountRepository.findByClientId(client_id);
    }

    public ClientBankAccountEntity findByBankName(String bank_name) {
        return clientBankAccountRepository.findByBankName(bank_name);
    }

    public ClientBankAccountEntity findByAccountNumber(String account_number) {
        return clientBankAccountRepository.findByAccountNumber(account_number);
    }

    public ClientBankAccountEntity findByAmount(double amount) {
        return clientBankAccountRepository.findByAmount(amount);
    }

    public ClientBankAccountEntity findByTransaction(String transaction) {
        return clientBankAccountRepository.findByTransaction(transaction);
    }

    public ClientBankAccountEntity findByTransactionDate(String transaction_date) {
        return clientBankAccountRepository.findByTransactionDate(transaction_date);
    }

    public ClientBankAccountEntity findByAccountOpening(String account_opening) {
        return clientBankAccountRepository.findByAccountOpening(account_opening);
    }

    public boolean existsByClientId(Long client_id) {
        return clientBankAccountRepository.existsByClientId(client_id);
    }

    public boolean existsByBankName(String bank_name) {
        return clientBankAccountRepository.existsByBankName(bank_name);
    }


}
