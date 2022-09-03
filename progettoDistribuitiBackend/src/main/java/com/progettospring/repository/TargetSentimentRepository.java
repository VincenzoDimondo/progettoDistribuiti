package com.progettospring.repository;

import com.progettospring.entity.TargetSentiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TargetSentimentRepository extends JpaRepository<TargetSentiment, Long>{

    TargetSentiment getByIdTargetSentiment(long id);



}
