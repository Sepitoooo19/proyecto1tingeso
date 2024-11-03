package com.example.PrestaBanco.controllers;
import ch.qos.logback.core.net.server.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.PrestaBanco.entities.ClientBankAccountEntity;
import com.example.PrestaBanco.services.ClientBankAccountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client-bank-account")
@CrossOrigin("*")
public class ClientBankAccountController {

    @Autowired
    private ClientBankAccountService clientBankAccountService;

    @GetMapping
    public ResponseEntity<List<ClientBankAccountEntity>> findAll() {
        return ResponseEntity.ok(clientBankAccountService.findAll());
    }

    @GetMapping("/{client_bank_account_id}")
    public ResponseEntity<ClientBankAccountEntity> findByClientBankAccountId(@PathVariable Long client_bank_account_id) {
        return ResponseEntity.ok(clientBankAccountService.findByClientBankAccountId(client_bank_account_id));
    }

    @GetMapping("/client_id/{client_id}")
    public ResponseEntity<List<ClientBankAccountEntity>> findByClientId(@PathVariable Long client_id) {
        return ResponseEntity.ok(clientBankAccountService.findByClientId(client_id));
    }

    @GetMapping("/bank_name/{bank_name}")
    public ResponseEntity<ClientBankAccountEntity> findByBankName(@PathVariable String bank_name) {
        return ResponseEntity.ok(clientBankAccountService.findByBankName(bank_name));
    }

    @GetMapping("/account_number/{account_number}")
    public ResponseEntity<ClientBankAccountEntity> findByAccountNumber(@PathVariable String account_number) {
        return ResponseEntity.ok(clientBankAccountService.findByAccountNumber(account_number));
    }

    @GetMapping("/amount/{amount}")
    public ResponseEntity<ClientBankAccountEntity> findByAmount(@PathVariable double amount) {
        return ResponseEntity.ok(clientBankAccountService.findByAmount(amount));
    }

    @GetMapping("/transaction/{transaction}")
    public ResponseEntity<ClientBankAccountEntity> findByTransaction(@PathVariable String transaction) {
        return ResponseEntity.ok(clientBankAccountService.findByTransaction(transaction));
    }

    @GetMapping("/transaction_date/{transaction_date}")
    public ResponseEntity<ClientBankAccountEntity> findByTransactionDate(@PathVariable String transaction_date) {
        return ResponseEntity.ok(clientBankAccountService.findByTransactionDate(transaction_date));
    }

    @GetMapping("/exists/client_id/{client_id}")
    public ResponseEntity<Boolean> existsByClientId(@PathVariable Long client_id) {
        return ResponseEntity.ok(clientBankAccountService.existsByClientId(client_id));
    }

    @GetMapping("/exists/bank_name/{bank_name}")
    public ResponseEntity<Boolean> existsByBankName(@PathVariable String bank_name) {
        return ResponseEntity.ok(clientBankAccountService.existsByBankName(bank_name));
    }

    @GetMapping("/account_opening/{account_opening}")
    public ResponseEntity<ClientBankAccountEntity> findByAccountOpening(@PathVariable String account_opening) {
        return ResponseEntity.ok(clientBankAccountService.findByAccountOpening(account_opening));
    }
}
