package com.progettospring.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "sentiment_confidence_scores", schema = "sentiment_analysis")
public class SentimentConfidenceScores implements Serializable {

    public SentimentConfidenceScores() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY
    //        , generator = "SQ_CONFIDENCE_SCORES"
    )
    //@SequenceGenerator(name = "id_sentiment_confidence_scores", sequenceName = "SQ_CONFIDENCE_SCORES", initialValue = 1)
    @Column(name = "id_sentiment_confidence_scores", nullable = false, unique = true)
    private long idSentimentConfidenceScores;

    @Basic
    @Column(name = "positive")
    private double positive;

    @Basic
    @Column(name = "neutre")
    private double neutre;

    @Basic
    @Column(name = "negative")
    private double negative;

    public long getId() {
        return idSentimentConfidenceScores;
    }

    public double getPositive() {   return positive; }

    public void setPositive(double positive) { this.positive = positive; }

    public double getNeutre() { return neutre;  }

    public void setNeutre(double neutre) { this.neutre = neutre; }

    public double getNegative() { return negative; }

    public void setNegative(double negative) { this.negative = negative; }

    public SentimentConfidenceScores(double positive, double neutre, double negative){
        super();
        this.setPositive(positive);
        this.setNeutre(neutre);
        this.setNegative(negative);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SentimentConfidenceScores that = (SentimentConfidenceScores) o;
        return idSentimentConfidenceScores == that.idSentimentConfidenceScores;
    }

    @Override
    public int hashCode() {
        return Objects.hash(positive, neutre, negative);
    }

    @Override
    public String toString() {
        return "SentimentConfidenceScores{" +
                "id=" + idSentimentConfidenceScores +
                ", positive=" + positive +
                ", neutre=" + neutre +
                ", negative=" + negative +
                '}';
    }

}
