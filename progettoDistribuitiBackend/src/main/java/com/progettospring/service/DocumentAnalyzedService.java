package com.progettospring.service;

import com.azure.ai.textanalytics.models.SentimentConfidenceScores;
import com.progettospring.entity.DocumentSentiment;
import com.progettospring.entity.DocumentAnalyzed;
import com.progettospring.entity.SentenceSentiment;
import com.progettospring.exceptions.EntityNotFoundException;
import com.progettospring.repository.DocumentAnalyzedRepository;
import com.progettospring.repository.SentenceSentimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DocumentAnalyzedService {

    @Autowired
    private DocumentAnalyzedRepository documentAnalyzedRepository;

    @Autowired
    private DocumentSentimentService documentSentimentService;

    @Autowired
    private SentimentConfidenceScoresService sentimentConfidenceScoresService;

    @Transactional(readOnly = false)
    public DocumentAnalyzed createDocumentAnalyzed(List<LinkedHashMap> documentSentimentList){
        DocumentAnalyzed documentAnalyzed = new DocumentAnalyzed();
        List<DocumentSentiment> documentSentimentListDataBase = new LinkedList<>();
        List<LinkedHashMap> sentimentConfidenceScoresList = new LinkedList<>() ;
        int k = 0;
        for (LinkedHashMap ds: documentSentimentList) {
            k++;
            System.out.println(k);
            sentimentConfidenceScoresList.add( (LinkedHashMap) ds.get("confidenceScores"));
            documentSentimentListDataBase.add(documentSentimentService.createDocumentSentiment(ds));
        }
        documentAnalyzed.setConfidenceScores(sentimentConfidenceScoresService.createSentimentConfidenceScores(setDocumentsConfidenceScores(sentimentConfidenceScoresList)));
        documentAnalyzed.setDocumentSentiments(documentSentimentListDataBase);
        documentAnalyzed.setNumberDocs((int)documentSentimentList.size());
        documentAnalyzed.setDate(new Date());
        return documentAnalyzedRepository.save(documentAnalyzed);
    }

    private com.azure.ai.textanalytics.models.SentimentConfidenceScores setDocumentsConfidenceScores(List<LinkedHashMap> sentimentConfidenceScoresList){
        double positive = 0, neutre = 0, negative = 0;
        int num_pos = 0, num_neutre = 0, num_neg = 0;
        for (LinkedHashMap sent: sentimentConfidenceScoresList) {
            if(Double.parseDouble(sent.get("positive").toString()) > 0){
                positive += (Double.parseDouble(sent.get("positive").toString())  );
                num_pos++;
            }
            if(Double.parseDouble(sent.get("neutral").toString()) > 0){
                neutre += (Double.parseDouble(sent.get("neutral").toString()) );
                num_neutre++;
            }
            if(Double.parseDouble(sent.get("negative").toString()) > 0){
                negative += (Double.parseDouble(sent.get("negative").toString()) );
                num_neg++;
            }
        }
        if(num_pos!=0){ positive = positive / num_pos; }
        if(num_neutre!=0){ neutre = neutre / num_neutre; }
        if(num_neg!=0){ negative = negative / num_neg; }
        com.azure.ai.textanalytics.models.SentimentConfidenceScores sentimentConfidenceScores = new SentimentConfidenceScores(negative, neutre, positive);
        return sentimentConfidenceScores;
    }

/*
    @Transactional(readOnly = false)
    public DocumentAnalyzed createDocumentAnalyzed(List<com.azure.ai.textanalytics.models.DocumentSentiment> documentSentimentList){
        DocumentAnalyzed documentAnalyzed = new DocumentAnalyzed();
        List<DocumentSentiment> documentSentimentListDataBase = new LinkedList<>();
        List<com.azure.ai.textanalytics.models.SentimentConfidenceScores> sentimentConfidenceScoresList = new LinkedList<>() ;
        for (com.azure.ai.textanalytics.models.DocumentSentiment ds: documentSentimentList) {
            sentimentConfidenceScoresList.add(ds.getConfidenceScores());
            documentSentimentListDataBase.add(documentSentimentService.createDocumentSentiment(ds));
        }
        documentAnalyzed.setConfidenceScores(setDocumentsConfidenceScores(sentimentConfidenceScoresList));
        documentAnalyzed.setDocumentSentiments(documentSentimentListDataBase);
        documentAnalyzed.setNumberDocs(documentSentimentList.size());
        documentAnalyzed.setDate(new Date());
        return documentAnalyzedRepository.save(documentAnalyzed);
    }

    private SentimentConfidenceScores setDocumentsConfidenceScores(List<com.azure.ai.textanalytics.models.SentimentConfidenceScores> sentimentConfidenceScoresList){
        SentimentConfidenceScores sentimentConfidenceScores = new SentimentConfidenceScores();
        double positive = 0, neutre = 0, negative = 0;
        int num_pos = 0, num_neutre = 0, num_neg = 0;
        for (com.azure.ai.textanalytics.models.SentimentConfidenceScores sent: sentimentConfidenceScoresList) {

        }
        sentimentConfidenceScores.setPositive(positive);
        sentimentConfidenceScores.setNeutre(neutre);
        sentimentConfidenceScores.setNegative(negative);
        return sentimentConfidenceScores;
    }
*/

    @Transactional(readOnly = true)
    public List<DocumentAnalyzed> getAllDocumentAnalyzed(){
        List<DocumentAnalyzed> list = new LinkedList<DocumentAnalyzed>();
        for (DocumentAnalyzed documentAnalyzed: documentAnalyzedRepository.findAll()) {
            documentAnalyzed.setDocumentSentiments(new LinkedList<DocumentSentiment>());
            list.add(documentAnalyzed);
        }
        return list;
    }

    @Transactional(readOnly = true)
    public DocumentAnalyzed getDocumentAnalyzedById(long id){
        return documentAnalyzedRepository.getByIdDocumentAnalyzed(id);
    }

    @Transactional(readOnly = true)
    public List<DocumentSentiment> getDocumentAnalyzedByIdPaged(long idDocument, int pageNumber, int pageSize) throws EntityNotFoundException {
        DocumentAnalyzed documentAnalyzed;
        try {
            documentAnalyzed = (DocumentAnalyzed) documentAnalyzedRepository.findById(idDocument)
                    .orElseThrow(() -> new EntityNotFoundException(DocumentAnalyzed.class, "email", "" + idDocument));
        } catch (Throwable throwable) {
            throw new EntityNotFoundException(DocumentAnalyzed.class, "DocumentAnalyzedId", "" + idDocument);
        }
        Pageable paging = PageRequest.of(pageNumber + 0, pageSize + 0, Sort.by("idDocumentSentiment"));
        Page<DocumentSentiment> conversazionePage = documentSentimentService.getSentencesPaged(idDocument, paging);
        if (conversazionePage.hasContent()) {
            return conversazionePage.getContent();
        } else return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    public List<DocumentAnalyzed> getDocumentAnalyzedPaged(int pageNumber, int pageSize){
        List<DocumentAnalyzed> list = new LinkedList<DocumentAnalyzed>();
        Pageable paging = PageRequest.of(pageNumber + 0, pageSize + 0, Sort.by("idDocumentAnalyzed"));
        for (DocumentAnalyzed documentAnalyzed: documentAnalyzedRepository.findAll(paging).getContent()) {
            documentAnalyzed.setDocumentSentiments(new LinkedList<DocumentSentiment>());
            list.add(documentAnalyzed);
        }
        return list;
    }

    @Transactional(readOnly = false)
    public void removeDocumentAnalyzed(long id){
        if (documentAnalyzedRepository.existsById(id)) {
            documentAnalyzedRepository.deleteById(id);
        }
    }


}
