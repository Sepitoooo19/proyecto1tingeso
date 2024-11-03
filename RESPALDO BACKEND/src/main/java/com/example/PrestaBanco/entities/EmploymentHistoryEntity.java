package com.example.PrestaBanco.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


@Entity
@Table(name = "EmploymentHistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employment_id;

    @Column(name = "client_id")
    private Long client_id;

    @Column(name = "job_title")
    private String job_title;

    @Column(name = "company_name")
    private String company_name;

    @Column(name = "salary")
    private double salary;

    @Column(name = "time_of_employment")
    private int time_of_employment;


}
