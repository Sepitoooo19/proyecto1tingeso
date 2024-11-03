package com.example.PrestaBanco.controllers;
import ch.qos.logback.core.net.server.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.PrestaBanco.entities.DebtEntity;
import com.example.PrestaBanco.services.DebtService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/debt")
@CrossOrigin("*")
public class DebtController {

    @Autowired
    private DebtService debtService;

    @GetMapping
    public ResponseEntity<List<DebtEntity>> findAll() {
        return ResponseEntity.ok(debtService.findAll());
    }

    @GetMapping("/{debt_id}")
    public ResponseEntity<DebtEntity> findByDebtId(@PathVariable Long debt_id) {
        return ResponseEntity.ok(debtService.findByDebtId(debt_id));
    }

    @GetMapping("/client_id/{client_id}")
    public ResponseEntity<List<DebtEntity>> findByClientId(@PathVariable Long client_id) {
        List<DebtEntity> debts = debtService.findByClientId(client_id);
        if (debts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(debts);
    }
    @GetMapping("/debt_amount/{debt_amount}")
    public ResponseEntity<DebtEntity> findByDebtAmount(@PathVariable double debt_amount) {
        return ResponseEntity.ok(debtService.findByDebtAmount(debt_amount));
    }

    @GetMapping("/debt_type/{debt_type}")
    public ResponseEntity<DebtEntity> findByDebtType(@PathVariable String debt_type) {
        return ResponseEntity.ok(debtService.findByDebtType(debt_type));
    }

    @GetMapping("/debt_date/{debt_date}")
    public ResponseEntity<DebtEntity> findByDebtDate(@PathVariable String debt_date) {
        return ResponseEntity.ok(debtService.findByDebtDate(debt_date));
    }

    @GetMapping("/debt_status/{debt_status}")
    public ResponseEntity<DebtEntity> findByDebtStatus(@PathVariable String debt_status) {
        return ResponseEntity.ok(debtService.findByDebtStatus(debt_status));
    }


}
