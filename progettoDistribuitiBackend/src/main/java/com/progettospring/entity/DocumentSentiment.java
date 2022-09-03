package com.progettospring.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "document_sentiment", schema = "sentiment_analysis")
public class DocumentSentiment implements Serializable {

    public DocumentSentiment() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_document_sentiment", nullable = false, unique = true)
    private long idDocumentSentiment;

    @Basic
    @Column(name = "sentiment")
    private String sentiment;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "confidence_scores")
    private SentimentConfidenceScores confidenceScores;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "warnings")
    private List<TextAnalyticsWarning> warnings;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sentences")
    private List<SentenceSentiment> sentences;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "document_sentiments")
    private DocumentAnalyzed documentSentiments;

    public List<TextAnalyticsWarning> getWarnings() { return warnings; }

    public void setWarnings(List<TextAnalyticsWarning> warnings) { this.warnings = warnings; }

    public List<SentenceSentiment> getSentences() { return sentences; }

    public void setSentences(List<SentenceSentiment> sentences) { this.sentences = sentences; }

    public long getId() { return idDocumentSentiment; }

    public String getSentiment() { return sentiment.toString(); }

    public String getSentimentToString() { return sentiment.toString(); }

    public void setSentimentPositive() {
        this.sentiment = "positive";
    }

    public void setSentimentNeutre() {
        this.sentiment = "neutre";
    }

    public void setSentimentNegative() {
        this.sentiment = "negative";
    }

    public void setSentimentMixed() { this.sentiment = "mixed"; }

    public SentimentConfidenceScores getConfidenceScores() { return confidenceScores; }

    public void setConfidenceScores(SentimentConfidenceScores confidenceScores) { this.confidenceScores = confidenceScores; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentSentiment that = (DocumentSentiment) o;
        return idDocumentSentiment == that.idDocumentSentiment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDocumentSentiment, sentiment, confidenceScores);
    }

    @Override
    public String toString() {
        return "DocumentSentiment{" +
                "idDocument=" + idDocumentSentiment +
                ", warnings=" + warnings +
                ", sentiment=" + sentiment +
                ", confidenceScores=" + confidenceScores +
                ", sentences=" + sentences +
                '}';
    }

    public DocumentSentiment(SentimentConfidenceScores confidenceScores, String sentiment, List<SentenceSentiment> sentences, List<TextAnalyticsWarning> warnings){
        super();
        this.setConfidenceScores(confidenceScores);
        switch (sentiment) {
            case "positive" : this.setSentimentPositive(); break;
            case "neutre" : this.setSentimentNeutre(); break;
            case "negative" : this.setSentimentNegative(); break;
            default: this.setSentimentMixed();
        }
        this.setSentences(sentences);
        this.setWarnings(warnings);
    }

}
