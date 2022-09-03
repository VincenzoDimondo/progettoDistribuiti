package com.progettospring.service;

import com.progettospring.entity.SentimentConfidenceScores;
import com.progettospring.repository.SentimentConfidenceScoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;

@Service
public class SentimentConfidenceScoresService {

    @Autowired
    private SentimentConfidenceScoresRepository sentimentConfidenceScoresRepository;

    @Transactional(readOnly = false)
    public SentimentConfidenceScores createSentimentConfidenceScores(LinkedHashMap sentimentConfidenceScores){
        SentimentConfidenceScores sentimentConfidenceScoresToSave = new SentimentConfidenceScores();
        sentimentConfidenceScoresToSave.setNeutre(Double.parseDouble(sentimentConfidenceScores.get("neutral").toString()));
        sentimentConfidenceScoresToSave.setNegative(Double.parseDouble(sentimentConfidenceScores.get("negative").toString()));
        sentimentConfidenceScoresToSave.setPositive(Double.parseDouble(sentimentConfidenceScores.get("positive").toString()));
        return sentimentConfidenceScoresRepository.save(sentimentConfidenceScoresToSave);
    }

    @Transactional(readOnly = false)
    public SentimentConfidenceScores createSentimentConfidenceScores(com.azure.ai.textanalytics.models.SentimentConfidenceScores sentimentConfidenceScores){
        SentimentConfidenceScores sentimentConfidenceScoresToSave = new SentimentConfidenceScores();
        sentimentConfidenceScoresToSave.setNeutre(sentimentConfidenceScores.getNeutral());
        sentimentConfidenceScoresToSave.setNegative(sentimentConfidenceScores.getNegative());
        sentimentConfidenceScoresToSave.setPositive(sentimentConfidenceScores.getPositive());
        return sentimentConfidenceScoresRepository.save(sentimentConfidenceScoresToSave);
    }

    @Transactional(readOnly = true)
    public com.azure.ai.textanalytics.models.SentimentConfidenceScores getSentimentConfidenceScoresById(long id){
        SentimentConfidenceScores sentimentConfidenceScores = sentimentConfidenceScoresRepository.getByIdSentimentConfidenceScores(id);
        return new com.azure.ai.textanalytics.models.SentimentConfidenceScores(sentimentConfidenceScores.getNegative(), sentimentConfidenceScores.getNeutre(), sentimentConfidenceScores.getPositive());
    }

}
