package com.progettospring.repository;

import com.progettospring.entity.DocumentSentiment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DocumentSentimentRepository extends JpaRepository<DocumentSentiment, Long> {

    DocumentSentiment getByIdDocumentSentiment(long id);

    @Query(value =
            "SELECT m FROM DocumentSentiment m WHERE (m.documentSentiments.idDocumentAnalyzed = :id)"
    )
    Page<DocumentSentiment> getPageable(@Param("id")long idDocumentAnalyzed, Pageable pageable);






}
