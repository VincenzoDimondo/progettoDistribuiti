package com.progettospring.service;

import com.azure.ai.textanalytics.implementation.SentenceOpinionPropertiesHelper;
import com.azure.core.util.IterableStream;
import com.progettospring.entity.AssessmentSentiment;
import com.progettospring.entity.SentenceOpinion;
import com.progettospring.entity.TargetSentiment;
import com.progettospring.repository.SentenceOpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class SentenceOpinionService {

    @Autowired
    private SentenceOpinionRepository sentenceOpinionRepository;

    @Autowired
    private TargetSentimentService targetSentimentService;

    @Autowired
    private AssessmentSentimentService assessmentSentimentService;

    @Transactional(readOnly = false)
    public SentenceOpinion createSentenceOpinion(LinkedHashMap sentenceOpinion){
        SentenceOpinion sentenceOpinionToSave = new SentenceOpinion();
        TargetSentiment targetSentiment = targetSentimentService.createTargetSentiment((LinkedHashMap)sentenceOpinion.get("target"));
        sentenceOpinionToSave.setTarget(targetSentiment);
        List<AssessmentSentiment> assessmentSentimentList = new LinkedList<>(); /***************************************/
        for (LinkedHashMap as : (ArrayList<LinkedHashMap>) sentenceOpinion.get("assessments")) {
            AssessmentSentiment assessmentSentiment = assessmentSentimentService.createAssessmentSentiment(as);
            assessmentSentimentList.add(assessmentSentiment);
        }
        sentenceOpinionToSave.setAssessment(assessmentSentimentList);
        return sentenceOpinionRepository.save(sentenceOpinionToSave);
    }

    @Transactional(readOnly = false)
    public SentenceOpinion createSentenceOpinion(com.azure.ai.textanalytics.models.SentenceOpinion sentenceOpinion){
        SentenceOpinion sentenceOpinionToSave = new SentenceOpinion();
        TargetSentiment targetSentiment = targetSentimentService.createTargetSentiment(sentenceOpinion.getTarget());
        sentenceOpinionToSave.setTarget(targetSentiment);
        List<AssessmentSentiment> assessmentSentimentList = new LinkedList<>(); /***************************************/
        for (com.azure.ai.textanalytics.models.AssessmentSentiment as : sentenceOpinion.getAssessments()) {
            AssessmentSentiment assessmentSentiment = assessmentSentimentService.createAssessmentSentiment(as);
            assessmentSentimentList.add(assessmentSentiment);
        }
        sentenceOpinionToSave.setAssessment(assessmentSentimentList);
        return sentenceOpinionRepository.save(sentenceOpinionToSave);
    }

    @Transactional(readOnly = true)
    public com.azure.ai.textanalytics.models.SentenceOpinion getSentenceOpinionById(long id){
        SentenceOpinion sentenceOpinion = sentenceOpinionRepository.getByIdSentenceOpinion(id);
        com.azure.ai.textanalytics.models.SentenceOpinion sentenceOpinionAzure = new com.azure.ai.textanalytics.models.SentenceOpinion();
        SentenceOpinionPropertiesHelper.setTarget(sentenceOpinionAzure, targetSentimentService.getTargetSentimentById(sentenceOpinion.getTarget().getId()));
        List<com.azure.ai.textanalytics.models.AssessmentSentiment> assessmentSentimentList = new LinkedList<>() ;
        for (AssessmentSentiment as: sentenceOpinion.getAssessment()) {
            assessmentSentimentList.add(assessmentSentimentService.getAssessmentSentimentById(as.getId()));
        }
        SentenceOpinionPropertiesHelper.setAssessments(sentenceOpinionAzure, (IterableStream<com.azure.ai.textanalytics.models.AssessmentSentiment>) assessmentSentimentList);
        return sentenceOpinionAzure;
    }

}
