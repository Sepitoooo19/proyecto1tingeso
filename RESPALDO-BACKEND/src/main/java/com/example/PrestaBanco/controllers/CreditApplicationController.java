package com.example.PrestaBanco.controllers;
import ch.qos.logback.core.net.server.Client;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.PrestaBanco.entities.CreditApplicationEntity;
import com.example.PrestaBanco.services.CreditApplicationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/credit_application")
@CrossOrigin("*")
public class CreditApplicationController {

    @Autowired
    private CreditApplicationService creditApplicationService;

    @GetMapping("/client_id/{client_id}")
    public ResponseEntity<List<CreditApplicationEntity>> findByClientId(@PathVariable Long client_id) {
        return ResponseEntity.ok(creditApplicationService.findByClientId(client_id));
    }

    @GetMapping("/credit_application_id/{credit_application_id}")
    public ResponseEntity<CreditApplicationEntity> findByCreditApplicationId(@PathVariable Long credit_application_id) {
        return ResponseEntity.ok(creditApplicationService.findByCreditApplicationId(credit_application_id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CreditApplicationEntity> findByName(@PathVariable String name) {
        return ResponseEntity.ok(creditApplicationService.findByName(name));
    }

    @GetMapping("/credit_date/{credit_date}")
    public ResponseEntity<CreditApplicationEntity> findByCreditDate(@PathVariable String credit_date) {
        return ResponseEntity.ok(creditApplicationService.findByCreditDate(credit_date));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<CreditApplicationEntity> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(creditApplicationService.findByStatus(status));
    }

    @GetMapping("/exists/client_id/{client_id}")
    public ResponseEntity<Boolean> existsByClientId(@PathVariable Long client_id) {
        return ResponseEntity.ok(creditApplicationService.existsByClientId(client_id));
    }

    @PostMapping("/create")
    public ResponseEntity<CreditApplicationEntity> createCreditApplication(
            @RequestBody Map<String, String> requestBody) {

        String rut = requestBody.get("rut");
        String loan_type = requestBody.get("loan_type");

        System.out.println("Cuerpo de la solicitud: " + requestBody);
        System.out.println("Tipo de préstamo recibido: " + loan_type);

        // Llamada al servicio para crear la solicitud de crédito
        try {
            CreditApplicationEntity createdApplication = creditApplicationService.createCreditApplicationByRut(rut, loan_type);
            // Retorna la solicitud de crédito creada con el estado HTTP 201 (Created)
            return new ResponseEntity<>(createdApplication, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            // Manejar el caso donde no se encuentra el cliente con el rut dado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Manejar otros errores
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
