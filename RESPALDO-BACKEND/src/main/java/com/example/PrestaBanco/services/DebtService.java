package com.example.PrestaBanco.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.PrestaBanco.repositories.DebtRepository;
import com.example.PrestaBanco.entities.DebtEntity;
import java.util.List;


@Service
public class DebtService {

    @Autowired
    private DebtRepository debtRepository;

    public List<DebtEntity> findAll() {
        return debtRepository.findAll();
    }

    public DebtEntity findByDebtId(Long debt_id) {
        return debtRepository.findByDebtId(debt_id);
    }



    public DebtEntity findByDebtAmount(double debt_amount) {
        return debtRepository.findByDebtAmount(debt_amount);
    }

    public DebtEntity findByDebtType(String debt_type) {
        return debtRepository.findByDebtType(debt_type);
    }

    public DebtEntity findByDebtDate(String debt_date) {
        return debtRepository.findByDebtDate(debt_date);
    }

    public DebtEntity findByDebtStatus(String debt_status) {
        return debtRepository.findByDebtStatus(debt_status);
    }

    public boolean existsByClientId(Long client_id) {
        return debtRepository.existsByClientId(client_id);
    }

    public List<DebtEntity> findByClientId(Long client_id) {
        return debtRepository.findByClientId(client_id);
    }
}
