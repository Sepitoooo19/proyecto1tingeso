package com.example.PrestaBanco.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


@Entity
@Table(name = "Executive")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankExecutiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long executive_id;

    @Column(name = "name")
    private String name;

    @Column(name = "rut")
    private String rut;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;


}
