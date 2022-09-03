package com.progettospring.repository;

import com.progettospring.entity.SentimentConfidenceScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SentimentConfidenceScoresRepository extends JpaRepository<SentimentConfidenceScores, Long> {

    SentimentConfidenceScores getByIdSentimentConfidenceScores(long id);







}
