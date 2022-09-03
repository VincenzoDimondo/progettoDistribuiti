package com.progettospring.service;

import com.azure.ai.textanalytics.implementation.AssessmentSentimentPropertiesHelper;
import com.azure.ai.textanalytics.implementation.TargetSentimentPropertiesHelper;
import com.azure.ai.textanalytics.models.TextSentiment;
import com.progettospring.entity.AssessmentSentiment;
import com.progettospring.entity.SentimentConfidenceScores;
import com.progettospring.repository.AssessmentSentimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;

@Service
public class AssessmentSentimentService {

    @Autowired
    private AssessmentSentimentRepository assessmentSentimentRepository;

    @Autowired
    private SentimentConfidenceScoresService sentimentConfidenceScoresService;

    @Transactional(readOnly = false)
    public AssessmentSentiment createAssessmentSentiment(LinkedHashMap assessmentSentiment){
        AssessmentSentiment assessmentSentimentToSave = new AssessmentSentiment();
        assessmentSentimentToSave.setText(assessmentSentiment.get("text").toString());
        switch (assessmentSentiment.get("sentiment").toString()) {
            case "positive" : assessmentSentimentToSave.setSentimentPositive(); break;
            case "neutral" : assessmentSentimentToSave.setSentimentNeutre(); break;
            case "negative" : assessmentSentimentToSave.setSentimentNegative(); break;
            default: assessmentSentimentToSave.setSentimentMixed();
        }
        SentimentConfidenceScores sentimentConfidenceScores = sentimentConfidenceScoresService.createSentimentConfidenceScores((LinkedHashMap) assessmentSentiment.get("confidenceScores"));
        assessmentSentimentToSave.setConfidenceScores(sentimentConfidenceScores);
        assessmentSentimentToSave.setNegated((boolean) assessmentSentiment.get("isNegated"));
        assessmentSentimentToSave.setOffset((int) assessmentSentiment.get("offset"));
        assessmentSentimentToSave.setLength((int) assessmentSentiment.get("length"));
        return assessmentSentimentRepository.save(assessmentSentimentToSave);
    }

    @Transactional(readOnly = false)
    public AssessmentSentiment createAssessmentSentiment(com.azure.ai.textanalytics.models.AssessmentSentiment assessmentSentiment){
        AssessmentSentiment assessmentSentimentToSave = new AssessmentSentiment();
        assessmentSentimentToSave.setText(assessmentSentiment.getText());
        switch (assessmentSentiment.getSentiment().toString()) {
            case "positive" : assessmentSentimentToSave.setSentimentPositive(); break;
            case "neutral" : assessmentSentimentToSave.setSentimentNeutre(); break;
            case "negative" : assessmentSentimentToSave.setSentimentNegative(); break;
            default: assessmentSentimentToSave.setSentimentMixed();
        }
        SentimentConfidenceScores sentimentConfidenceScores = sentimentConfidenceScoresService.createSentimentConfidenceScores(assessmentSentiment.getConfidenceScores());
        assessmentSentimentToSave.setConfidenceScores(sentimentConfidenceScores);
        assessmentSentimentToSave.setNegated(assessmentSentiment.isNegated());
        assessmentSentimentToSave.setOffset(assessmentSentiment.getOffset());
        assessmentSentimentToSave.setLength(assessmentSentiment.getLength());
        return assessmentSentimentRepository.save(assessmentSentimentToSave);
    }

    @Transactional(readOnly = true)
    public com.azure.ai.textanalytics.models.AssessmentSentiment getAssessmentSentimentById(long id){
        AssessmentSentiment assessmentSentiment = assessmentSentimentRepository.getByIdAssessmentSentiment(id);
        com.azure.ai.textanalytics.models.AssessmentSentiment assessmentSentimentAzure = new com.azure.ai.textanalytics.models.AssessmentSentiment();
        AssessmentSentimentPropertiesHelper.setText(assessmentSentimentAzure, assessmentSentiment.getText());
        switch (assessmentSentiment.getSentiment() + ""){
            case "positive" : AssessmentSentimentPropertiesHelper.setSentiment(assessmentSentimentAzure, TextSentiment.POSITIVE); break;
            case "neutre" : AssessmentSentimentPropertiesHelper.setSentiment(assessmentSentimentAzure, TextSentiment.NEUTRAL); break;
            case "negative" : AssessmentSentimentPropertiesHelper.setSentiment(assessmentSentimentAzure, TextSentiment.NEGATIVE); break;
            default: AssessmentSentimentPropertiesHelper.setSentiment(assessmentSentimentAzure, TextSentiment.MIXED);
        }
        AssessmentSentimentPropertiesHelper.setConfidenceScores(assessmentSentimentAzure, sentimentConfidenceScoresService.getSentimentConfidenceScoresById(assessmentSentiment.getConfidenceScores().getId()));
        AssessmentSentimentPropertiesHelper.setNegated(assessmentSentimentAzure, assessmentSentiment.isNegated());
        AssessmentSentimentPropertiesHelper.setOffset(assessmentSentimentAzure, (int) assessmentSentiment.getOffset());
        AssessmentSentimentPropertiesHelper.setLength(assessmentSentimentAzure, (int) assessmentSentiment.getLength());
        return assessmentSentimentAzure;
    }

}
