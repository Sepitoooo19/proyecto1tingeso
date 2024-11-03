package com.example.PrestaBanco.services;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


import com.example.PrestaBanco.entities.DocumentEntity;
import com.example.PrestaBanco.repositories.DocumentRepository;
import com.example.PrestaBanco.services.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. Prueba para verificar que findAll devuelve una lista vacía cuando no hay documentos.
    @Test
    public void whenFindAll_thenReturnEmptyList() {
        // given
        given(documentRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<DocumentEntity> documents = documentService.findAll();

        // then
        assertThat(documents).isEmpty();
    }

    // 2. Prueba para verificar que findAll devuelve una lista con un solo documento.
    @Test
    public void whenFindAll_thenReturnSingleDocument() {
        // given
        DocumentEntity document = new DocumentEntity(1, "Document1", new byte[]{1, 2, 3}, "application/pdf", 300, 1L, LocalDateTime.now());
        given(documentRepository.findAll()).willReturn(Collections.singletonList(document));

        // when
        List<DocumentEntity> documents = documentService.findAll();

        // then
        assertThat(documents).hasSize(1);
        assertThat(documents.get(0).getName()).isEqualTo("Document1");
    }

    // 3. Prueba para verificar que findAll devuelve múltiples documentos correctamente.
    @Test
    public void whenFindAll_thenReturnMultipleDocuments() {
        // given
        DocumentEntity document1 = new DocumentEntity(1, "Document1", new byte[]{1, 2, 3}, "application/pdf", 300, 1L, LocalDateTime.now());
        DocumentEntity document2 = new DocumentEntity(2, "Document2", new byte[]{4, 5, 6}, "image/png", 500, 2L, LocalDateTime.now());
        given(documentRepository.findAll()).willReturn(Arrays.asList(document1, document2));

        // when
        List<DocumentEntity> documents = documentService.findAll();

        // then
        assertThat(documents).hasSize(2);
        assertThat(documents.get(0).getName()).isEqualTo("Document1");
        assertThat(documents.get(1).getName()).isEqualTo("Document2");
    }

    // 4. Prueba para verificar que los datos del documento son correctos.
    @Test
    public void whenFindAll_thenCheckDocumentData() {
        // given
        DocumentEntity document = new DocumentEntity(1, "Document1", new byte[]{1, 2, 3}, "application/pdf", 300, 1L, LocalDateTime.now());
        given(documentRepository.findAll()).willReturn(Collections.singletonList(document));

        // when
        List<DocumentEntity> documents = documentService.findAll();

        // then
        assertThat(documents.get(0).getType()).isEqualTo("application/pdf");
        assertThat(documents.get(0).getSize()).isEqualTo(300);
    }

    // 5. Prueba para verificar que el campo submit_date de los documentos es correcto.
    @Test
    public void whenFindAll_thenCheckSubmitDate() {
        // given
        LocalDateTime now = LocalDateTime.now();
        DocumentEntity document = new DocumentEntity(1, "Document1", new byte[]{1, 2, 3}, "application/pdf", 300, 1L, now);
        given(documentRepository.findAll()).willReturn(Collections.singletonList(document));

        // when
        List<DocumentEntity> documents = documentService.findAll();

        // then
        assertThat(documents.get(0).getSumbit_date()).isEqualTo(now);
    }

    // 1. Prueba para verificar que se lanza una excepción cuando el ID es null.
    @Test
    public void whenFindByDocumentIdWithNullId_thenThrowException() {
        // given
        Long nullId = null;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            documentService.findByDocumentId(nullId);
        });
    }

    // 2. Prueba para verificar que se lanza una excepción cuando el ID es negativo.
    @Test
    public void whenFindByDocumentIdWithNegativeId_thenThrowException() {
        // given
        Long negativeId = -1L;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            documentService.findByDocumentId(negativeId);
        });
    }

    // 3. Prueba para verificar que se devuelve un documento cuando el ID existe.
    @Test
    public void whenFindByDocumentIdWithValidId_thenReturnDocument() {
        // given
        Long documentId = 1L;
        DocumentEntity document = new DocumentEntity(1, "Document1", new byte[]{1, 2, 3}, "application/pdf", 300, 1L, LocalDateTime.now());
        given(documentRepository.findByDocumentId(documentId)).willReturn(document);

        // when
        DocumentEntity foundDocument = documentService.findByDocumentId(documentId);

        // then
        assertThat(foundDocument).isNotNull();
        assertThat(foundDocument.getName()).isEqualTo("Document1");
        assertThat(foundDocument.getType()).isEqualTo("application/pdf");
    }

    // 4. Prueba para verificar que se devuelve null cuando el documento no existe.
    @Test
    public void whenFindByDocumentIdWithNonExistentId_thenReturnNull() {
        // given
        Long nonExistentId = 2L;
        given(documentRepository.findByDocumentId(nonExistentId)).willReturn(null);

        // when
        DocumentEntity foundDocument = documentService.findByDocumentId(nonExistentId);

        // then
        assertThat(foundDocument).isNull();
    }

    // 5. Prueba para verificar que se recupera correctamente el tamaño del documento.
    @Test
    public void whenFindByDocumentId_thenCheckDocumentSize() {
        // given
        Long documentId = 3L;
        DocumentEntity document = new DocumentEntity(3, "Document2", new byte[]{1, 2, 3, 4, 5}, "image/png", 500, 2L, LocalDateTime.now());
        given(documentRepository.findByDocumentId(documentId)).willReturn(document);

        // when
        DocumentEntity foundDocument = documentService.findByDocumentId(documentId);

        // then
        assertThat(foundDocument).isNotNull();
        assertThat(foundDocument.getSize()).isEqualTo(500);
    }

    // 1. Prueba para verificar que se lanza una excepción cuando el tipo es null.
    @Test
    public void whenFindByTypeWithNullType_thenThrowException() {
        // given
        String nullType = null;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            documentService.findByType(nullType);
        });
    }

    // 2. Prueba para verificar que se lanza una excepción cuando el tipo es una cadena vacía.
    @Test
    public void whenFindByTypeWithEmptyType_thenThrowException() {
        // given
        String emptyType = "";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            documentService.findByType(emptyType);
        });
    }

    // 3. Prueba para verificar que se devuelve un documento cuando el tipo existe.
    @Test
    public void whenFindByTypeWithValidType_thenReturnDocument() {
        // given
        String documentType = "application/pdf";
        DocumentEntity document = new DocumentEntity(1, "Document1", new byte[]{1, 2, 3}, documentType, 300, 1L, LocalDateTime.now());
        given(documentRepository.findByType(documentType)).willReturn(document);

        // when
        DocumentEntity foundDocument = documentService.findByType(documentType);

        // then
        assertThat(foundDocument).isNotNull();
        assertThat(foundDocument.getType()).isEqualTo("application/pdf");
        assertThat(foundDocument.getName()).isEqualTo("Document1");
    }

    // 4. Prueba para verificar que se devuelve null cuando no existe un documento con el tipo dado.
    @Test
    public void whenFindByTypeWithNonExistentType_thenReturnNull() {
        // given
        String nonExistentType = "image/jpg";
        given(documentRepository.findByType(nonExistentType)).willReturn(null);

        // when
        DocumentEntity foundDocument = documentService.findByType(nonExistentType);

        // then
        assertThat(foundDocument).isNull();
    }

    // 5. Prueba para verificar que se recupera correctamente el tamaño del documento por tipo.
    @Test
    public void whenFindByType_thenCheckDocumentSize() {
        // given
        String documentType = "image/png";
        DocumentEntity document = new DocumentEntity(3, "Document2", new byte[]{1, 2, 3, 4, 5}, documentType, 500, 2L, LocalDateTime.now());
        given(documentRepository.findByType(documentType)).willReturn(document);

        // when
        DocumentEntity foundDocument = documentService.findByType(documentType);

        // then
        assertThat(foundDocument).isNotNull();
        assertThat(foundDocument.getSize()).isEqualTo(500);
    }

    // 1. Prueba para verificar que se lanza una excepción cuando la fecha de envío es null.
    @Test
    public void whenFindBySubmitDateWithNullDate_thenThrowException() {
        // given
        LocalDateTime nullDate = null;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            documentService.findBySubmitDate(nullDate);
        });
    }

    // 2. Prueba para verificar que se devuelve un documento cuando la fecha de envío existe.
    @Test
    public void whenFindBySubmitDateWithValidDate_thenReturnDocument() {
        // given
        LocalDateTime submitDate = LocalDateTime.of(2023, 10, 31, 10, 0);
        DocumentEntity document = new DocumentEntity(1, "TestDoc", new byte[]{1, 2, 3}, "application/pdf", 300, 1L, submitDate);
        given(documentRepository.findBySubmitDate(submitDate)).willReturn(document);

        // when
        DocumentEntity foundDocument = documentService.findBySubmitDate(submitDate);

        // then
        assertNotNull(foundDocument);
        assertEquals("TestDoc", foundDocument.getName());
        assertEquals("application/pdf", foundDocument.getType());
    }

    // 3. Prueba para verificar que se devuelve null cuando no se encuentra ningún documento para la fecha de envío.
    @Test
    public void whenFindBySubmitDateWithNonExistentDate_thenReturnNull() {
        // given
        LocalDateTime nonExistentDate = LocalDateTime.of(2022, 1, 1, 12, 0);
        given(documentRepository.findBySubmitDate(nonExistentDate)).willReturn(null);

        // when
        DocumentEntity foundDocument = documentService.findBySubmitDate(nonExistentDate);

        // then
        assertNull(foundDocument);
    }

    // 4. Prueba para verificar que el documento recuperado tiene el tamaño esperado.
    @Test
    public void whenFindBySubmitDate_thenCheckDocumentSize() {
        // given
        LocalDateTime submitDate = LocalDateTime.of(2023, 10, 31, 10, 0);
        DocumentEntity document = new DocumentEntity(2, "AnotherDoc", new byte[]{1, 2, 3, 4}, "image/png", 400, 2L, submitDate);
        given(documentRepository.findBySubmitDate(submitDate)).willReturn(document);

        // when
        DocumentEntity foundDocument = documentService.findBySubmitDate(submitDate);

        // then
        assertNotNull(foundDocument);
        assertEquals(400, foundDocument.getSize());
    }

    // 5. Prueba para verificar que el documento recuperado tiene el tipo esperado.
    @Test
    public void whenFindBySubmitDate_thenCheckDocumentType() {
        // given
        LocalDateTime submitDate = LocalDateTime.of(2023, 10, 31, 10, 0);
        DocumentEntity document = new DocumentEntity(3, "ImageDoc", new byte[]{5, 6, 7}, "image/jpeg", 200, 3L, submitDate);
        given(documentRepository.findBySubmitDate(submitDate)).willReturn(document);

        // when
        DocumentEntity foundDocument = documentService.findBySubmitDate(submitDate);

        // then
        assertNotNull(foundDocument);
        assertEquals("image/jpeg", foundDocument.getType());
    }

    // 1. Prueba para verificar que se lanza una excepción cuando el ID del cliente es null.
    @Test
    public void whenFindByClientIdWithNullId_thenThrowException() {
        // given
        Long nullId = null;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            documentService.findByClientId(nullId);
        });
    }

    // 2. Prueba para verificar que se lanza una excepción cuando el ID del cliente es negativo.
    @Test
    public void whenFindByClientIdWithNegativeId_thenThrowException() {
        // given
        Long negativeId = -1L;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            documentService.findByClientId(negativeId);
        });
    }

    // 3. Prueba para verificar que se devuelven documentos cuando el ID del cliente es válido.
    @Test
    public void whenFindByClientIdWithValidId_thenReturnDocumentList() {
        // given
        Long clientId = 1L;
        DocumentEntity document1 = new DocumentEntity(1, "Doc1", new byte[]{1, 2}, "application/pdf", 300, clientId, LocalDateTime.now());
        DocumentEntity document2 = new DocumentEntity(2, "Doc2", new byte[]{3, 4}, "image/png", 200, clientId, LocalDateTime.now());
        given(documentRepository.findByClientId(clientId)).willReturn(Arrays.asList(document1, document2));

        // when
        List<DocumentEntity> foundDocuments = documentService.findByClientId(clientId);

        // then
        assertNotNull(foundDocuments);
        assertEquals(2, foundDocuments.size());
        assertEquals("Doc1", foundDocuments.get(0).getName());
        assertEquals("Doc2", foundDocuments.get(1).getName());
    }

    // 4. Prueba para verificar que se devuelve una lista vacía cuando no hay documentos para un cliente dado.
    @Test
    public void whenFindByClientIdWithNoDocuments_thenReturnEmptyList() {
        // given
        Long clientId = 2L;
        given(documentRepository.findByClientId(clientId)).willReturn(Collections.emptyList());

        // when
        List<DocumentEntity> foundDocuments = documentService.findByClientId(clientId);

        // then
        assertNotNull(foundDocuments);
        assertTrue(foundDocuments.isEmpty());
    }
    // 1. Prueba para verificar que se lanza una excepción cuando el ID del documento es null.
    @Test
    public void whenExistsByDocumentIdWithNullId_thenThrowException() {
        // given
        Long nullId = null;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            documentService.existsByDocumentId(nullId);
        });
    }

    // 2. Prueba para verificar que se lanza una excepción cuando el ID del documento es negativo.
    @Test
    public void whenExistsByDocumentIdWithNegativeId_thenThrowException() {
        // given
        Long negativeId = -1L;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            documentService.existsByDocumentId(negativeId);
        });
    }

    // 3. Prueba para verificar que se devuelve true cuando el documento existe.
    @Test
    public void whenExistsByDocumentIdWithValidId_thenReturnTrue() {
        // given
        Long documentId = 1L;
        given(documentRepository.existsByDocumentId(documentId)).willReturn(true);

        // when
        boolean exists = documentService.existsByDocumentId(documentId);

        // then
        assertTrue(exists);
    }

    // 4. Prueba para verificar que se devuelve false cuando el documento no existe.
    @Test
    public void whenExistsByDocumentIdWithNonExistentId_thenReturnFalse() {
        // given
        Long documentId = 2L;
        given(documentRepository.existsByDocumentId(documentId)).willReturn(false);

        // when
        boolean exists = documentService.existsByDocumentId(documentId);

        // then
        assertFalse(exists);
    }

    // 1. Prueba para verificar que se lanza una excepción cuando el ID del cliente es null.
    @Test
    public void whenExistsByClientIdWithNullId_thenThrowException() {
        // given
        Long nullId = null;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            documentService.existsByClientId(nullId);
        });
    }

    // 2. Prueba para verificar que se lanza una excepción cuando el ID del cliente es negativo.
    @Test
    public void whenExistsByClientIdWithNegativeId_thenThrowException() {
        // given
        Long negativeId = -1L;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            documentService.existsByClientId(negativeId);
        });
    }

    // 3. Prueba para verificar que se devuelve true cuando el cliente existe.
    @Test
    public void whenExistsByClientIdWithValidId_thenReturnTrue() {
        // given
        Long clientId = 1L;
        given(documentRepository.existsByClientId(clientId)).willReturn(true);

        // when
        boolean exists = documentService.existsByClientId(clientId);

        // then
        assertTrue(exists);
    }

    // 4. Prueba para verificar que se devuelve false cuando el cliente no existe.
    @Test
    public void whenExistsByClientIdWithNonExistentId_thenReturnFalse() {
        // given
        Long clientId = 2L;
        given(documentRepository.existsByClientId(clientId)).willReturn(false);

        // when
        boolean exists = documentService.existsByClientId(clientId);

        // then
        assertFalse(exists);
    }

    // 1. Prueba para verificar que se lanza una excepción cuando el documento es nulo.
    @Test
    public void whenSaveWithNullDocument_thenThrowException() {
        // given
        DocumentEntity nullDocument = null;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            documentService.save(nullDocument);
        });
    }

    // 2. Prueba para verificar que se guarda un documento válido correctamente.
    @Test
    public void whenSaveWithValidDocument_thenReturnSavedDocument() {
        // given
        DocumentEntity document = new DocumentEntity(1, "Test Document", new byte[]{1, 2, 3}, "application/pdf", 300, 1L, LocalDateTime.now());
        given(documentRepository.save(document)).willReturn(document);

        // when
        DocumentEntity savedDocument = documentService.save(document);

        // then
        assertNotNull(savedDocument);
        assertEquals("Test Document", savedDocument.getName());
        assertEquals("application/pdf", savedDocument.getType());
    }

    // 3. (Opcional) Prueba para verificar que se lanza una excepción cuando se guarda un documento con campos inválidos.@Test
    public void whenSaveWithInvalidDocument_thenThrowException() {
        // given
        DocumentEntity invalidDocument = new DocumentEntity(); // Simulando un documento inválido

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            documentService.save(invalidDocument);
        });
    }



}

