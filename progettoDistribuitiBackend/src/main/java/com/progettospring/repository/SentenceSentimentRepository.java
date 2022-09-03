package com.progettospring.repository;

import com.progettospring.entity.SentenceSentiment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SentenceSentimentRepository extends JpaRepository<SentenceSentiment, Long> {

    SentenceSentiment getByIdSentenceSentiment(long id);









}
