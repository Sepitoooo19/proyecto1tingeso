package com.example.PrestaBanco.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


@Entity
@Table(name = "CreditApplication")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long credit_application_id;

    @Column(name = "client_id")
    private Long client_id;

    @Column(name = "name")
    private String name;

    @Column(name = "credit_date")
    private String credit_date;

    @Column(name = "status")
    private String status;

    @Column(name = "amount")
    private int amount;




}
