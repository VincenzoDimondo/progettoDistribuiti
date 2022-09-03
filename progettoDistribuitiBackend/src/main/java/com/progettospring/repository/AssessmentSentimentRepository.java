package com.progettospring.repository;

import com.progettospring.entity.AssessmentSentiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AssessmentSentimentRepository extends JpaRepository<AssessmentSentiment, Long> {

    AssessmentSentiment getByIdAssessmentSentiment(long id);




}
