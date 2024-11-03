package com.example.PrestaBanco.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.PrestaBanco.entities.DocumentEntity;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {



    @Query("SELECT d FROM DocumentEntity d WHERE d.document_id = :document_id")
    DocumentEntity findByDocumentId(@Param("document_id") Long document_id);

    @Query("SELECT d FROM DocumentEntity d WHERE d.clientId = :client_id")
    List<DocumentEntity> findByClientId(@Param("client_id") Long client_id);

    @Query("SELECT d FROM DocumentEntity d WHERE d.type = :type")
    DocumentEntity findByType(@Param("type") String type);

    @Query("SELECT d FROM DocumentEntity d WHERE d.sumbit_date = :submitDate")
    DocumentEntity findBySubmitDate(@Param("submitDate") LocalDateTime submitDate);

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END FROM DocumentEntity d WHERE d.document_id = :document_id")
    boolean existsByDocumentId(Long document_id);

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END FROM DocumentEntity d WHERE d.clientId = :client_id")
    boolean existsByClientId(Long client_id);


}
