package com.example.PrestaBanco.controllers;
import ch.qos.logback.core.net.server.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.PrestaBanco.entities.ClientEntity;
import com.example.PrestaBanco.services.ClientService;
import java.util.List;
import com.example.PrestaBanco.entities.CreditApplicationEntity;




@RestController
@RequestMapping("/api/v1/client")
@CrossOrigin("*")
public class ClientController {

        @Autowired
        private ClientService clientService;

        @GetMapping
        public ResponseEntity<List<ClientEntity>> findAll() {
            return ResponseEntity.ok(clientService.findAll());
        }

        @GetMapping("/{rut}")
        public ResponseEntity<ClientEntity> findByRut(@PathVariable String rut) {
            return ResponseEntity.ok(clientService.findByRut(rut));
        }

        @GetMapping("/email/{email}")
        public ResponseEntity<ClientEntity> findByEmail(@PathVariable String email) {
            return ResponseEntity.ok(clientService.findByEmail(email));
        }

        @GetMapping("/client_id/{client_id}")
        public ResponseEntity<ClientEntity> findByClientId(@PathVariable Long client_id) {
            return ResponseEntity.ok(clientService.findByClientId(client_id));
        }

        @GetMapping("/name/{name}")
        public ResponseEntity<ClientEntity> findByName(@PathVariable String name) {
            return ResponseEntity.ok(clientService.findByName(name));
        }

        @GetMapping("/phone/{phone}")
        public ResponseEntity<ClientEntity> findByPhone(@PathVariable String phone) {
            return ResponseEntity.ok(clientService.findByPhone(phone));
        }

        @GetMapping("/job_type/{job_type}")
        public ResponseEntity<ClientEntity> findByJobType(@PathVariable String job_type) {
            return ResponseEntity.ok(clientService.findByJobType(job_type));
        }

        @GetMapping("/type_loan/{type_loan}")
        public ResponseEntity<ClientEntity> findByTypeLoan(@PathVariable String type_loan) {
            return ResponseEntity.ok(clientService.findByTypeLoan(type_loan));
        }

        @GetMapping("/independent_activity/{independent_activity}")
        public ResponseEntity<ClientEntity> findByIndependentActivity(@PathVariable boolean independent_activity) {
            return ResponseEntity.ok(clientService.findByIndependentActivity(independent_activity));
        }

        @GetMapping("/age/{age}")
        public ResponseEntity<ClientEntity> findByAge(@PathVariable int age) {
            return ResponseEntity.ok(clientService.findByAge(age));
        }

        @GetMapping("/monthly_salary/{monthly_salary}")
        public ResponseEntity<ClientEntity> findByMonthlySalary(@PathVariable double monthly_salary) {
            return ResponseEntity.ok(clientService.findByMonthlySalary(monthly_salary));
        }

        @GetMapping("/personal_savings/{personal_savings}")
        public ResponseEntity<ClientEntity> findByPersonalSavings(@PathVariable Double personal_savings) {
            return ResponseEntity.ok(clientService.findByPersonalSavings(personal_savings));
        }

        @GetMapping("/expected_amount/{expected_amount}")
        public ResponseEntity<ClientEntity> findByExpectedAmount(@PathVariable double expected_amount) {
            return ResponseEntity.ok(clientService.findByExpectedAmount(expected_amount));
        }

        @GetMapping("/time_limit/{time_limit}")
        public ResponseEntity<ClientEntity> findByTimeLimit(@PathVariable int time_limit) {
            return ResponseEntity.ok(clientService.findByTimeLimit(time_limit));
        }

        //Nuevo cliente
        @PostMapping("/new")
        ClientEntity newClient(@RequestBody ClientEntity newClient) {
            return clientService.save(newClient);


    }

    @PutMapping("/{client_id}")
    public ResponseEntity<ClientEntity> updateClient(@PathVariable Long client_id, @RequestBody ClientEntity client) {
        // Busca el cliente por ID, actualiza y retorna
        client.setClient_id(client_id);
        ClientEntity clientUpdated = clientService.update(client);
        return ResponseEntity.ok(clientUpdated);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<String> deleteClientById(@PathVariable Long clientId) {
        if (clientId == null || clientId <= 0) {
            return ResponseEntity.badRequest().body("Invalid client ID. It must be a positive number.");
        }

        clientService.deleteById(clientId);
        return ResponseEntity.ok("Client deleted successfully.");
    }




}
