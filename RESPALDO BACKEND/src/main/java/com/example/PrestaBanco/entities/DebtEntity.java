package com.example.PrestaBanco.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


@Entity
@Table(name = "Debt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long debt_id;

    @Column(name = "client_id")
    private Long client_id;

    @Column(name = "debt_amount")
    private double debt_amount;

    @Column(name = "debt_type")
    private String debt_type;

    @Column(name ="debt_date")
    private String debt_date;

    @Column(name = "debt_status")
    private String debt_status;


}
