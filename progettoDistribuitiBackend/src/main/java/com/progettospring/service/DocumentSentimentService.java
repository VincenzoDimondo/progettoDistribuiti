package com.progettospring.service;

import com.azure.ai.textanalytics.models.TextSentiment;
import com.azure.core.util.IterableStream;
import com.progettospring.entity.*;
import com.progettospring.exceptions.EntityNotFoundException;
import com.progettospring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class DocumentSentimentService{

    @Autowired
    private DocumentSentimentRepository documentSentimentRepository;

    @Autowired
    private SentimentConfidenceScoresService sentimentConfidenceScoresService;

    @Autowired
    private TextAnalyticsWarningService textAnalyticsWarningService;

    @Autowired
    private SentenceSentimentService sentenceSentimentService;

    @Transactional(readOnly = false)
    public DocumentSentiment createDocumentSentiment(LinkedHashMap documentSentiment){
        DocumentSentiment documentSentimentToSave = new DocumentSentiment();
        switch (documentSentiment.get("sentiment").toString()){
            case "positive": documentSentimentToSave.setSentimentPositive(); break;
            case "neutral":  documentSentimentToSave.setSentimentNeutre(); break;
            case "negative": documentSentimentToSave.setSentimentNegative(); break;
            default: documentSentimentToSave.setSentimentMixed();
        }
        //com.azure.ai.textanalytics.models.SentimentConfidenceScores sentimentConfidenceScoresAzure =
        //        new com.azure.ai.textanalytics.models.SentimentConfidenceScores( ((double) ((LinkedHashMap)documentSentiment.get("confidenceScores")).get("negative")), ((double) ((LinkedHashMap)documentSentiment.get("confidenceScores")).get("neutral")), ((double) ((LinkedHashMap)documentSentiment.get("confidenceScores")).get("positive")));
        SentimentConfidenceScores sentimentConfidenceScores = sentimentConfidenceScoresService
                .createSentimentConfidenceScores((LinkedHashMap)documentSentiment.get("confidenceScores"));
        documentSentimentToSave.setConfidenceScores(sentimentConfidenceScores);
        List<TextAnalyticsWarning> textAnalyticsWarningList = new LinkedList<>();
        for (LinkedHashMap taw: (ArrayList<LinkedHashMap>)documentSentiment.get("warnings")) {
            TextAnalyticsWarning textAnalyticsWarning = textAnalyticsWarningService.createTextAnalyticsWatning(taw);
            textAnalyticsWarningList.add(textAnalyticsWarning);
        }
        documentSentimentToSave.setWarnings(textAnalyticsWarningList);
        List<SentenceSentiment> sentenceSentimentList = new LinkedList<>();
        for (LinkedHashMap sent: (ArrayList<LinkedHashMap>)documentSentiment.get("sentences")) {
            SentenceSentiment sentenceSentiment = sentenceSentimentService.createSentenceSentiment(sent);
            sentenceSentimentList.add(sentenceSentiment);
        }
        documentSentimentToSave.setSentences(sentenceSentimentList);
        return documentSentimentRepository.save(documentSentimentToSave);
    }

    @Transactional(readOnly = false)
    public DocumentSentiment createDocumentSentiment(com.azure.ai.textanalytics.models.DocumentSentiment documentSentiment){
        DocumentSentiment documentSentimentToSave = new DocumentSentiment();
        switch (documentSentiment.getSentiment().toString()){
            case "positive": documentSentimentToSave.setSentimentPositive(); break;
            case "neutral":  documentSentimentToSave.setSentimentNeutre(); break;
            case "negative": documentSentimentToSave.setSentimentNegative(); break;
            default: documentSentimentToSave.setSentimentMixed();
        }
        com.azure.ai.textanalytics.models.SentimentConfidenceScores sentimentConfidenceScoresAzure =
                new com.azure.ai.textanalytics.models.SentimentConfidenceScores((documentSentiment.getConfidenceScores().getNegative()), (documentSentiment.getConfidenceScores().getNeutral()), (documentSentiment.getConfidenceScores().getPositive()));
        SentimentConfidenceScores sentimentConfidenceScores = sentimentConfidenceScoresService
                .createSentimentConfidenceScores(sentimentConfidenceScoresAzure);
        documentSentimentToSave.setConfidenceScores(sentimentConfidenceScores);
        List<TextAnalyticsWarning> textAnalyticsWarningList = new LinkedList<>();
        for (com.azure.ai.textanalytics.models.TextAnalyticsWarning taw: documentSentiment.getWarnings()) {
            TextAnalyticsWarning textAnalyticsWarning = textAnalyticsWarningService.createTextAnalyticsWatning(taw);
            textAnalyticsWarningList.add(textAnalyticsWarning);
        }
        documentSentimentToSave.setWarnings(textAnalyticsWarningList);
        List<SentenceSentiment> sentenceSentimentList = new LinkedList<>();
        for (com.azure.ai.textanalytics.models.SentenceSentiment sent: documentSentiment.getSentences()) {
            SentenceSentiment sentenceSentiment = sentenceSentimentService.createSentenceSentiment(sent);
            sentenceSentimentList.add(sentenceSentiment);
        }
        documentSentimentToSave.setSentences(sentenceSentimentList);
        return documentSentimentRepository.save(documentSentimentToSave);
    }

    @Transactional(readOnly = true)
    public List<com.azure.ai.textanalytics.models.DocumentSentiment> getAll(){
        List<DocumentSentiment> documentSentimentListDataBase = documentSentimentRepository.findAll();
        List<com.azure.ai.textanalytics.models.DocumentSentiment> documentSentimentListAzure = new LinkedList<>();
        for (DocumentSentiment ds: documentSentimentListDataBase) {
            com.azure.ai.textanalytics.models.TextSentiment textSentiment = new TextSentiment();
            switch (ds.getSentimentToString()){
                case "positive": textSentiment = TextSentiment.POSITIVE; break;
                case "neutre": textSentiment = TextSentiment.NEUTRAL; break;
                case "negative": textSentiment = TextSentiment.NEGATIVE; break;
                default: textSentiment = TextSentiment.MIXED;
            }
            List<com.azure.ai.textanalytics.models.TextAnalyticsWarning> textAnalyticsWarningList = new LinkedList<>();
            List<com.azure.ai.textanalytics.models.SentenceSentiment> sentenceSentimentList = new LinkedList<>();
            for (TextAnalyticsWarning taw: ds.getWarnings()) {
                textAnalyticsWarningList.add(textAnalyticsWarningService.getTextAnalyticsWarningById(taw.getId()));
            }
            for (SentenceSentiment sent: ds.getSentences()) {
                sentenceSentimentList.add(sentenceSentimentService.getSentenceSentimentById(sent.getId()));
            }
            com.azure.ai.textanalytics.models.DocumentSentiment documentSentimentAzure = new com.azure.ai.textanalytics.models.DocumentSentiment(
                    textSentiment,sentimentConfidenceScoresService.getSentimentConfidenceScoresById(ds.getConfidenceScores().getId()),
                    (IterableStream<com.azure.ai.textanalytics.models.SentenceSentiment>) sentenceSentimentList,
                    (IterableStream<com.azure.ai.textanalytics.models.TextAnalyticsWarning>) textAnalyticsWarningList
            );
        }
        return documentSentimentListAzure;
    }

    @Transactional(readOnly = true)
    public com.azure.ai.textanalytics.models.DocumentSentiment getDocumentSentimentById(long id){
        DocumentSentiment documentSentimentDataBase = documentSentimentRepository.getByIdDocumentSentiment(id);
        com.azure.ai.textanalytics.models.TextSentiment textSentiment = new TextSentiment();
        switch (documentSentimentDataBase.getSentimentToString()){
            case "positive": textSentiment = TextSentiment.POSITIVE; break;
            case "neutre": textSentiment = TextSentiment.NEUTRAL; break;
            case "negative": textSentiment = TextSentiment.NEGATIVE; break;
            default: textSentiment = TextSentiment.MIXED;
        }
        List<com.azure.ai.textanalytics.models.TextAnalyticsWarning> textAnalyticsWarningList = new LinkedList<>();
        List<com.azure.ai.textanalytics.models.SentenceSentiment> sentenceSentimentList = new LinkedList<>();
        for (TextAnalyticsWarning taw: documentSentimentDataBase.getWarnings()) {
            textAnalyticsWarningList.add(textAnalyticsWarningService.getTextAnalyticsWarningById(taw.getId()));
        }
        for (SentenceSentiment sent: documentSentimentDataBase.getSentences()) {
            sentenceSentimentList.add(sentenceSentimentService.getSentenceSentimentById(sent.getId()));
        }
        com.azure.ai.textanalytics.models.DocumentSentiment documentSentimentAzure = new com.azure.ai.textanalytics.models.DocumentSentiment(
                textSentiment,sentimentConfidenceScoresService.getSentimentConfidenceScoresById(documentSentimentDataBase.getConfidenceScores().getId()),
                (IterableStream<com.azure.ai.textanalytics.models.SentenceSentiment>) sentenceSentimentList,
                (IterableStream<com.azure.ai.textanalytics.models.TextAnalyticsWarning>) textAnalyticsWarningList
        );
        return documentSentimentAzure;
    }

    @Transactional(readOnly = true)
    public Page<DocumentSentiment> getSentencesPaged(long idDocument, Pageable paging) throws EntityNotFoundException {
        return documentSentimentRepository.getPageable(idDocument, paging);
    }





}
