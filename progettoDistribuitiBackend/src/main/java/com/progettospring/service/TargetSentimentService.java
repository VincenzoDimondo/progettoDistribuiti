package com.progettospring.service;

import com.azure.ai.textanalytics.implementation.TargetSentimentPropertiesHelper;
import com.azure.ai.textanalytics.models.TextSentiment;
import com.progettospring.entity.SentimentConfidenceScores;
import com.progettospring.entity.TargetSentiment;
import com.progettospring.repository.TargetSentimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;

@Service
public class TargetSentimentService {

    @Autowired
    private TargetSentimentRepository targetSentimentRepository;

    @Autowired
    private SentimentConfidenceScoresService sentimentConfidenceScoresService;

    @Transactional(readOnly = false)
    public TargetSentiment createTargetSentiment(LinkedHashMap targetSentiment){
        TargetSentiment targetSentimentToSave = new TargetSentiment();
        targetSentimentToSave.setText(targetSentiment.get("text").toString());
        switch(targetSentiment.get("sentiment").toString()){
            case "positive" : targetSentimentToSave.setSentimentPositive(); break;
            case "neutral" : targetSentimentToSave.setSentimentNeutre(); break;
            case "negative" : targetSentimentToSave.setSentimentNegative(); break;
            default: targetSentimentToSave.setSentimentMixed();
        }
        SentimentConfidenceScores sentimentConfidenceScores = sentimentConfidenceScoresService.createSentimentConfidenceScores((LinkedHashMap) targetSentiment.get("confidenceScores"));
        targetSentimentToSave.setConfidenceScores(sentimentConfidenceScores);
        targetSentimentToSave.setOffset( (long) ((int) targetSentiment.get("offset")) );
        targetSentimentToSave.setLength( (long) ((int) targetSentiment.get("length")) );
        return targetSentimentRepository.save(targetSentimentToSave);
    }

    @Transactional(readOnly = false)
    public TargetSentiment createTargetSentiment(com.azure.ai.textanalytics.models.TargetSentiment targetSentiment){
        TargetSentiment targetSentimentToSave = new TargetSentiment();
        targetSentimentToSave.setText(targetSentiment.getText());
        switch(targetSentiment.getSentiment().toString()){
            case "positive" : targetSentimentToSave.setSentimentPositive(); break;
            case "neutral" : targetSentimentToSave.setSentimentNeutre(); break;
            case "negative" : targetSentimentToSave.setSentimentNegative(); break;
            default: targetSentimentToSave.setSentimentMixed();
        }
        SentimentConfidenceScores sentimentConfidenceScores = sentimentConfidenceScoresService.createSentimentConfidenceScores(targetSentiment.getConfidenceScores());
        targetSentimentToSave.setConfidenceScores(sentimentConfidenceScores);
        targetSentimentToSave.setOffset(targetSentiment.getOffset());
        targetSentimentToSave.setLength(targetSentiment.getLength());
        return targetSentimentRepository.save(targetSentimentToSave);
    }

    @Transactional(readOnly = true)
    public com.azure.ai.textanalytics.models.TargetSentiment getTargetSentimentById(long id){
        TargetSentiment targetSentiment = targetSentimentRepository.getByIdTargetSentiment(id);
        /*(targetSentiment.getText(), targetSentiment.getSentiment(), targetSentiment.getConfidenceScores(),
                targetSentiment.getOffset(), targetSentiment.getLength()) ;*/
        com.azure.ai.textanalytics.models.TargetSentiment targetSentimentAzure = new com.azure.ai.textanalytics.models.TargetSentiment();
        TargetSentimentPropertiesHelper.setText(targetSentimentAzure, targetSentiment.getText());
        switch (targetSentiment.getSentiment() + ""){
            case "positive" : TargetSentimentPropertiesHelper.setSentiment(targetSentimentAzure, TextSentiment.POSITIVE); break;
            case "neutre" : TargetSentimentPropertiesHelper.setSentiment(targetSentimentAzure, TextSentiment.NEUTRAL); break;
            case "negative" : TargetSentimentPropertiesHelper.setSentiment(targetSentimentAzure, TextSentiment.NEGATIVE); break;
            default: TargetSentimentPropertiesHelper.setSentiment(targetSentimentAzure, TextSentiment.MIXED);
        }
        TargetSentimentPropertiesHelper.setConfidenceScores(targetSentimentAzure,
                sentimentConfidenceScoresService.getSentimentConfidenceScoresById(targetSentiment.getConfidenceScores().getId()));
        TargetSentimentPropertiesHelper.setOffset(targetSentimentAzure, (int) targetSentiment.getOffset());
        TargetSentimentPropertiesHelper.setLength(targetSentimentAzure, (int) targetSentiment.getLength());
        return targetSentimentAzure;

    }

}
