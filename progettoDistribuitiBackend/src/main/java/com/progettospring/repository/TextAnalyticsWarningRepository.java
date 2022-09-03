package com.progettospring.repository;


import com.progettospring.entity.TextAnalyticsWarning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TextAnalyticsWarningRepository extends JpaRepository<TextAnalyticsWarning, Long> {

    TextAnalyticsWarning getByIdTextAnalyticsWarning(long id);




}
