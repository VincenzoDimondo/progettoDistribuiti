package com.progettospring.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "document_analyzed", schema = "sentiment_analysis")
public class DocumentAnalyzed implements Serializable {

    public DocumentAnalyzed () { super(); }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document_analyzed", nullable = false, unique = true)
    private long idDocumentAnalyzed ;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "document_sentiments")
    private List<DocumentSentiment> documentSentiments;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "confidence_scores")
    private SentimentConfidenceScores confidenceScores;

    @Basic
    @Column(name = "number_docs")
    private int numberDocs;

    @Basic
    @DateTimeFormat
    @Column(name = "date")
    private Date date;

    public long getId() {
        return idDocumentAnalyzed;
    }

    public void setIdDocumentAnalyzed(long idDocumentAnalyzed) { this.idDocumentAnalyzed = idDocumentAnalyzed; }

    public List<DocumentSentiment> getDocumentSentiments() {
        return documentSentiments;
    }

    public void setDocumentSentiments(List<DocumentSentiment> documentSentiments) {
        this.documentSentiments = documentSentiments;
    }

    public SentimentConfidenceScores getConfidenceScores() {
        return confidenceScores;
    }

    public void setConfidenceScores(SentimentConfidenceScores confidenceScores) {
        this.confidenceScores = confidenceScores;
    }

    public int getNumberDocs() {
        return numberDocs;
    }

    public void setNumberDocs(int numberDocs) {
        this.numberDocs = numberDocs;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DocumentAnalyzed{" +
                "idDocumentAnalyzed=" + idDocumentAnalyzed +
                ", documentSentiments=" + documentSentiments.toString() +
                ", confidenceScores=" + confidenceScores.toString() +
                ", numberDocs=" + numberDocs +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentAnalyzed that = (DocumentAnalyzed) o;
        return idDocumentAnalyzed == that.idDocumentAnalyzed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDocumentAnalyzed);
    }

    public DocumentAnalyzed(List<DocumentSentiment> documentSentiments, SentimentConfidenceScores confidenceScores, int numberDocs, Date date){
        super();
        this.setDocumentSentiments(documentSentiments);
        this.setConfidenceScores(confidenceScores);
        this.setDate(date);
        this.setNumberDocs(numberDocs);
    }

}
