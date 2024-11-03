package com.example.PrestaBanco.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.PrestaBanco.repositories.DocumentRepository;
import com.example.PrestaBanco.entities.DocumentEntity;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;


    public List<DocumentEntity> findAll() {
        return documentRepository.findAll();
    }

    public DocumentEntity findByDocumentId(Long document_id) {
        if (document_id == null || document_id <= 0) {
            throw new IllegalArgumentException("Document ID must be a positive non-null value.");
        }
        return documentRepository.findByDocumentId(document_id);
    }

    public DocumentEntity findByType(String document_type) {
        if (document_type == null || document_type.trim().isEmpty()) {
            throw new IllegalArgumentException("Document type must be a non-null, non-empty value.");
        }
        return documentRepository.findByType(document_type);
    }

    public DocumentEntity findBySubmitDate(LocalDateTime submitDate) {
        if (submitDate == null) {
            throw new IllegalArgumentException("Submit date must be a non-null value.");
        }
        return documentRepository.findBySubmitDate(submitDate);
    }

    @Transactional
    public List<DocumentEntity> findByClientId(Long clientId) {
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("Client ID must be a positive non-null value.");
        }
        return documentRepository.findByClientId(clientId);
    }
    public boolean existsByDocumentId(Long documentId) {
        if (documentId == null || documentId <= 0) {
            throw new IllegalArgumentException("Document ID must be a positive non-null value.");
        }
        return documentRepository.existsByDocumentId(documentId);
    }


    public boolean existsByClientId(Long clientId) {
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("Client ID must be a positive non-null value.");
        }
        return documentRepository.existsByClientId(clientId);
    }

    @Transactional
    public DocumentEntity save(DocumentEntity document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null.");
        }return documentRepository.save(document);
    }



}
