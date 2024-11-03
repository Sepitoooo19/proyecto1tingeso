package com.example.PrestaBanco.controllers;
import ch.qos.logback.core.net.server.Client;
import com.example.PrestaBanco.entities.ClientEntity;
import com.example.PrestaBanco.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.PrestaBanco.entities.DocumentEntity;
import com.example.PrestaBanco.services.DocumentService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/document")
@CrossOrigin("*")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("rut") String rut) {

        // Validar el archivo
        if (file.isEmpty() || !file.getContentType().equals("application/pdf")) {
            return new ResponseEntity<>("El archivo debe ser un PDF y no debe estar vacío", HttpStatus.BAD_REQUEST);
        }

        // Buscar cliente por RUT
        ClientEntity client = clientService.findByRut(rut);
        if (client == null) {
            return new ResponseEntity<>("Cliente no encontrado con el RUT proporcionado", HttpStatus.BAD_REQUEST);
        }

        try {
            // Crear una nueva entidad de documento
            DocumentEntity document = new DocumentEntity();
            document.setName(name);
            document.setFile(file.getBytes());
            document.setType(type);
            document.setSize((int) file.getSize());
            document.setClientId(client.getClient_id());  // Asignar el ID del cliente encontrado
            document.setSumbit_date(LocalDateTime.now());

            // Guardar el documento en la base de datos
            documentService.save(document);

            return new ResponseEntity<>("Archivo PDF subido exitosamente", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al subir el archivo PDF", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<DocumentEntity>> findAll() {
        return ResponseEntity.ok(documentService.findAll());
    }

    @GetMapping("/{document_id}")
    public ResponseEntity<DocumentEntity> findByDocumentId(@PathVariable Long document_id) {
        return ResponseEntity.ok(documentService.findByDocumentId(document_id));
    }

    @GetMapping("/type/{document_type}")

    public ResponseEntity<DocumentEntity> findByType(@PathVariable String document_type) {
        return ResponseEntity.ok(documentService.findByType(document_type));
    }

    @GetMapping("/client/{rut}/documents")
    public ResponseEntity<List<DocumentEntity>> findDocumentsByRut(@PathVariable String rut) {
        // Busca al cliente por RUT
        ClientEntity client = clientService.findByRut(rut);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Obtiene los documentos asociados al clientId del cliente encontrado
        List<DocumentEntity> documents = documentService.findByClientId(client.getClient_id());
        return ResponseEntity.ok(documents);
    }
}

