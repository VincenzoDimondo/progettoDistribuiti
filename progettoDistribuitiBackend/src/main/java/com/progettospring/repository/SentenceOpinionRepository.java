package com.progettospring.repository;

import com.progettospring.entity.SentenceOpinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SentenceOpinionRepository extends JpaRepository<SentenceOpinion, Long> {

    SentenceOpinion getByIdSentenceOpinion(long id);





}
