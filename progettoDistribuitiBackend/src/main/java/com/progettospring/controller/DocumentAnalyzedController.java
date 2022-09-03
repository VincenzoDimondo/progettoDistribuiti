package com.progettospring.controller;

import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.progettospring.entity.DocumentAnalyzed;
import com.progettospring.service.DocumentAnalyzedService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/document_sentiment")
@Tag(name = "document sentiment", description = "The Document Sentiment API")
public class DocumentAnalyzedController {

    @Autowired
    private DocumentAnalyzedService documentAnalyzedAService;

    @PostMapping("/create")
    public ResponseEntity creaDocumentSentiment(@RequestBody @Valid List<LinkedHashMap> documentSentimentList){
        documentAnalyzedAService.createDocumentAnalyzed(documentSentimentList);
        //System.out.println(documentSentimentList.toString());
        return ResponseEntity.ok("200");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DocumentAnalyzed>> getAll(){
        return new ResponseEntity(documentAnalyzedAService.getAllDocumentAnalyzed(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getById")
    public ResponseEntity<DocumentSentiment> getAllById(@RequestParam("id") long id){
        return new ResponseEntity(documentAnalyzedAService.getDocumentAnalyzedById(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("getSizeAll")
    public ResponseEntity<Integer> getSize(){
        return new ResponseEntity(documentAnalyzedAService.getAllDocumentAnalyzed().size(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getSizeOf")
    public ResponseEntity<Integer> getSizeOf(@RequestParam("id") long id){
        return new ResponseEntity(documentAnalyzedAService.getDocumentAnalyzedById(id).getDocumentSentiments().size(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getByIdPaged")
    public ResponseEntity getTwittPaged(@RequestParam(value = "idDocument") long idDocument,
                                                   @RequestParam(value = "pageNumber") int pageNumber,
                                                   @RequestParam(value = "pageSize") int pageSize) {
        return new ResponseEntity(documentAnalyzedAService.getDocumentAnalyzedByIdPaged(idDocument, pageNumber, pageSize), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAllPaged")
    public ResponseEntity getDocumentAnalyzedPaged(@RequestParam(value = "pageNumber") int pageNumber,
                                                   @RequestParam(value = "pageSize") int pageSize) {
        return new ResponseEntity(documentAnalyzedAService.getDocumentAnalyzedPaged(pageNumber, pageSize), HttpStatus.ACCEPTED);
    }

    @PostMapping("/remove")
    public ResponseEntity removeDocumentAnalyzed(@RequestParam(value = "id") long id){
        documentAnalyzedAService.removeDocumentAnalyzed(id);
        return ResponseEntity.ok("200");
    }

/*
    @PostMapping("/create")
    public ResponseEntity creaIndirizzo(@RequestBody @Valid DocumentSentiment documentSentiment) {
        documentSentimentService.create(documentSentiment);
        return ResponseEntity.ok("200");
    }

    @GetMapping("/getByUtente")
    public ResponseEntity<List<Indirizzo>> getIndirizziUtente(@RequestParam String emailUtente) {
        return new ResponseEntity(indirizzoService.getAllByUtente(utenteService.getUtenteByEmail(emailUtente).get(0)), HttpStatus.ACCEPTED);
    }

    @PostMapping("/update")
    public ResponseEntity updateIndirizzo(@RequestBody @Valid Indirizzo indirizzo) {
        if (!indirizzoService.existById(indirizzo.getId())) {
            return new ResponseEntity("UpdateFailedBecauseIndirizzoNotExists!", HttpStatus.BAD_REQUEST);
        }
        indirizzoService.updateIndirizzo(indirizzo);
        return new ResponseEntity("200", HttpStatus.ACCEPTED);
    }

    @GetMapping("/numIndirizzi")
    public ResponseEntity<Integer> getNumIndirizziUtente(@RequestBody @Valid Utente utente) {
        return new ResponseEntity(indirizzoService.countAllIndirizziByUtente(utente), HttpStatus.ACCEPTED);
    }

    @PostMapping("remove")
    public ResponseEntity removeIndirizzoUtente(@RequestBody @Valid Indirizzo indirizzo) {
        indirizzoService.removeIndirizzo(indirizzo);
        return ResponseEntity.ok("200");
    }
    */

}
