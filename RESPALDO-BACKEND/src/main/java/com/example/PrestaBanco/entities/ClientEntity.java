package com.example.PrestaBanco.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;


@Entity
@Table(name = "Client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long client_id;

    @Column(name = "name")
    private String name;

    @Column(name = "rut")
    private String rut;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "age")
    private int age;

    @Column(name = "monthly_salary")
    private double monthly_salary;

    @Column(name = "personal_savings")
    private Double personal_savings;

    @Column(name = "job_type")
    private String job_type;

    @Column(name = "expected_amount")
    private double expected_amount;

    @Column(name = "time_limit")
    private int time_limit;

    @Column(name = "interest_rate")
    private double interest_rate;

    @Column(name = "type_loan")
    private String type_loan;

    @Column(name = "independent_activity")
    private boolean independent_activity;

    @Column(name = "job_seniority")
    private int job_seniority;

    @Column(name = "actual_job")
    private String actual_job;

}
