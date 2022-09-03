package com.progettospring.service;

import com.azure.ai.textanalytics.implementation.SentenceSentimentPropertiesHelper;
import com.azure.ai.textanalytics.models.TextSentiment;
import com.azure.core.util.IterableStream;
import com.progettospring.entity.SentenceOpinion;
import com.progettospring.entity.SentenceSentiment;
import com.progettospring.entity.SentimentConfidenceScores;
import com.progettospring.repository.SentenceSentimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class SentenceSentimentService {

    @Autowired
    private SentenceSentimentRepository sentenceSentimentRepository;

    @Autowired
    private SentimentConfidenceScoresService sentimentConfidenceScoresService;

    @Autowired
    private SentenceOpinionService sentenceOpinionService;

    @Transactional(readOnly = false)
    public SentenceSentiment createSentenceSentiment(LinkedHashMap sentenceSentiment){
        SentenceSentiment sentenceSentimentToSave = new SentenceSentiment();
        SentimentConfidenceScores sentimentConfidenceScores = sentimentConfidenceScoresService.createSentimentConfidenceScores((LinkedHashMap)sentenceSentiment.get("confidenceScores"));
        sentenceSentimentToSave.setConfidenceScores(sentimentConfidenceScores);
        switch (sentenceSentiment.get("sentiment").toString()) {
            case "positive" : sentenceSentimentToSave.setSentimentPositive(); break;
            case "neutral" : sentenceSentimentToSave.setSentimentNeutre(); break;
            case "negative" : sentenceSentimentToSave.setSentimentNegative(); break;
            default: sentenceSentimentToSave.setSentimentMixed();
        }
        sentenceSentimentToSave.setText(sentenceSentiment.get("text").toString());
        sentenceSentimentToSave.setLength( (long) ((int) sentenceSentiment.get("length")) );
        sentenceSentimentToSave.setOffset( (long) ((int) sentenceSentiment.get("offset")) );
        List<SentenceOpinion> sentenceOpinionList = new LinkedList<>();
        for (LinkedHashMap so: (ArrayList<LinkedHashMap>) sentenceSentiment.get("opinions")) {
            SentenceOpinion sentenceOpinion = sentenceOpinionService.createSentenceOpinion(so);
            sentenceOpinionList.add(sentenceOpinion);
        }
        sentenceSentimentToSave.setOpinions(sentenceOpinionList);
        //System.out.println("________________ " + sentenceSentimentToSave);
        return sentenceSentimentRepository.save(sentenceSentimentToSave);
    }

    @Transactional(readOnly = false)
    public SentenceSentiment createSentenceSentiment(com.azure.ai.textanalytics.models.SentenceSentiment sentenceSentiment){
        SentenceSentiment sentenceSentimentToSave = new SentenceSentiment();
        SentimentConfidenceScores sentimentConfidenceScores = sentimentConfidenceScoresService.createSentimentConfidenceScores(sentenceSentiment.getConfidenceScores());
        sentenceSentimentToSave.setConfidenceScores(sentimentConfidenceScores);
        switch (sentenceSentiment.getSentiment().toString()) {
            case "positive" : sentenceSentimentToSave.setSentimentPositive(); break;
            case "neutral" : sentenceSentimentToSave.setSentimentNeutre(); break;
            case "negative" : sentenceSentimentToSave.setSentimentNegative(); break;
            default: sentenceSentimentToSave.setSentimentMixed();
        }
        sentenceSentimentToSave.setText(sentenceSentiment.getText());
        sentenceSentimentToSave.setOffset(sentenceSentiment.getOffset());
        sentenceSentimentToSave.setLength(sentenceSentiment.getLength());
        List<SentenceOpinion> sentenceOpinionList = new LinkedList<>();
        for (com.azure.ai.textanalytics.models.SentenceOpinion so: sentenceSentiment.getOpinions()) {
            SentenceOpinion sentenceOpinion = sentenceOpinionService.createSentenceOpinion(so);
            sentenceOpinionList.add(sentenceOpinion);
        }
        sentenceSentimentToSave.setOpinions(sentenceOpinionList);
        return sentenceSentimentRepository.save(sentenceSentimentToSave);
    }

    @Transactional(readOnly = true)
    public com.azure.ai.textanalytics.models.SentenceSentiment getSentenceSentimentById(long id){
        SentenceSentiment sentenceSentiment = sentenceSentimentRepository.getByIdSentenceSentiment(id);
        com.azure.ai.textanalytics.models.SentenceSentiment sentenceSentimentAzure ;
        switch (sentenceSentiment.getSentiment() + ""){
            case "positive" :
                sentenceSentimentAzure = new com.azure.ai.textanalytics.models.
                        SentenceSentiment(sentenceSentiment.getText(), TextSentiment.POSITIVE,
                        sentimentConfidenceScoresService.getSentimentConfidenceScoresById(sentenceSentiment.getConfidenceScores().getId()));
                break;
            case "neutre" :
                sentenceSentimentAzure = new com.azure.ai.textanalytics.models.
                        SentenceSentiment(sentenceSentiment.getText(), TextSentiment.NEUTRAL,
                        sentimentConfidenceScoresService.getSentimentConfidenceScoresById(sentenceSentiment.getConfidenceScores().getId()));
                break;
            case "negative" :
                sentenceSentimentAzure = new com.azure.ai.textanalytics.models.
                        SentenceSentiment(sentenceSentiment.getText(), TextSentiment.NEGATIVE,
                        sentimentConfidenceScoresService.getSentimentConfidenceScoresById(sentenceSentiment.getConfidenceScores().getId()));
                break;
            default: sentenceSentimentAzure = new com.azure.ai.textanalytics.models.
                    SentenceSentiment(sentenceSentiment.getText(), TextSentiment.MIXED,
                    sentimentConfidenceScoresService.getSentimentConfidenceScoresById(sentenceSentiment.getConfidenceScores().getId()));
        }
        SentenceSentimentPropertiesHelper.setLength(sentenceSentimentAzure, (int) sentenceSentiment.getLength());
        SentenceSentimentPropertiesHelper.setOffset(sentenceSentimentAzure, (int) sentenceSentiment.getOffset());
        List<com.azure.ai.textanalytics.models.SentenceOpinion> sentenceOpinionList = new LinkedList<>();
        for (SentenceOpinion so: sentenceSentiment.getOpinions()) {
            sentenceOpinionList.add(sentenceOpinionService.getSentenceOpinionById(so.getId()));
        }
        SentenceSentimentPropertiesHelper.setOpinions(sentenceSentimentAzure, (IterableStream<com.azure.ai.textanalytics.models.SentenceOpinion>)sentenceOpinionList);
        return sentenceSentimentAzure;
    }

}










