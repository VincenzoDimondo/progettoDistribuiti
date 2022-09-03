package com.progettospring.repository;

import com.progettospring.entity.DocumentAnalyzed;
import com.progettospring.entity.DocumentSentiment;
import com.progettospring.entity.SentenceSentiment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DocumentAnalyzedRepository extends JpaRepository<DocumentAnalyzed, Long> {

    DocumentAnalyzed getByIdDocumentAnalyzed(long id);

    boolean existsById(long id);

    @Query(value = "SELECT m FROM DocumentAnalyzed m" )
    Page<DocumentAnalyzed> getAllDocumentsPaged(Pageable pageable);



}
