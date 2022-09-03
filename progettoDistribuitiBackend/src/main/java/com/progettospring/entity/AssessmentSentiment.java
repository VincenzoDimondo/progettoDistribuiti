package com.progettospring.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "assessment_sentiment", schema = "sentiment_analysis")
public class AssessmentSentiment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_assessment_sentiment", nullable = false, unique = true)
    private long idAssessmentSentiment;

    @Basic
    @Column(name = "text", length = 2550000)
    private String text;

    @Basic
    @Column(name = "sentiment")
    private String sentiment;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "confidence_scores")
    private SentimentConfidenceScores confidenceScores;

    @Basic
    @Column(name = "is_negated")
    private boolean isNegated;

    @Basic
    @Column(name = "offset_number")
    private long offset;

    @Basic
    @Column(name = "length")
    private long length;

    public SentimentConfidenceScores getConfidenceScores() {
        return confidenceScores;
    }

    public void setConfidenceScores(SentimentConfidenceScores confidenceScores) { this.confidenceScores = confidenceScores; }

    public String getSentiment() {
        return sentiment.toString();
    }

    public void setSentimentPositive() { this.sentiment = "positive"; }

    public void setSentimentNeutre() { this.sentiment = "neutre"; }

    public void setSentimentNegative() { this.sentiment = "negative"; }

    public void setSentimentMixed() { this.sentiment = "mixed"; }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getId() {
        return idAssessmentSentiment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isNegated() { return isNegated; }

    public void setNegated(boolean negated) { isNegated = negated; }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssessmentSentiment assessmentSentiment = (AssessmentSentiment) o;
        return idAssessmentSentiment == assessmentSentiment.idAssessmentSentiment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAssessmentSentiment);
    }

    public AssessmentSentiment() {
        super();
    }

    public AssessmentSentiment(String text, String sentiment, SentimentConfidenceScores confidenceScores, boolean isNegated,long offset, long length) {
        super();
        this.setText(text);
        this.setConfidenceScores(confidenceScores);
        this.setNegated(isNegated);
        this.setOffset(offset);
        this.setLength(length);
        switch (sentiment) {
            case "positive" : this.setSentimentPositive(); break;
            case "neutre" : this.setSentimentNeutre(); break;
            case "negative" : this.setSentimentNegative(); break;
            default: this.setSentimentMixed();
        };
    }

    @Override
    public String toString() {
        return "AssessmentSentiment{" +
                "id=" + idAssessmentSentiment +
                ", text='" + text + '\'' +
                ", sentiment=" + sentiment +
                ", confidenceScores=" + confidenceScores +
                ", isNegated=" + isNegated +
                ", offset=" + offset +
                ", length=" + length +
                '}';
    }

}