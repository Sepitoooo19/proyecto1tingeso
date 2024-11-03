package com.example.PrestaBanco.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "Document")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int document_id;

    @Column(nullable = false)
    private String name;

    @Lob // Usa LOB (Large Object) para almacenar datos binarios grandes
    @Column(nullable = false)
    private byte[] file;

    @Column(name = "type")
    private String type;

    @Column(name = "size")
    private int size;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "sumbit_date")
    private LocalDateTime sumbit_date;
}
