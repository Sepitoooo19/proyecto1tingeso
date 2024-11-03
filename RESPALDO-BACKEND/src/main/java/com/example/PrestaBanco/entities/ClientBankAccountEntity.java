package com.example.PrestaBanco.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.*;


@Entity
@Table(name = "ClientBankAccount")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClientBankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long client_bank_account_id;

    @Column(name = "client_id")
    private Long client_id;

    @Column(name = "bank_name")
    private String bank_name;

    @Column(name = "account_number")
    private String account_number;

    @Column(name = "amount")
    private double amount;

    @Column(name = "transaction")
    private String transaction;

    @Column(name = "transaction_date")
    private LocalDate transaction_date;

    @Column(name = "account_opening")
    private LocalDate account_opening;


}
