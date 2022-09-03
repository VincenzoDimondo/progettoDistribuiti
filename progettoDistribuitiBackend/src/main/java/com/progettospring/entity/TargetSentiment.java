package com.progettospring.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "target_sentiment", schema = "sentiment_analysis")
public class TargetSentiment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY
    //        , generator = "SQ_TARGET"
    )
    //@SequenceGenerator(name = "id_target_sentiment", sequenceName = "SQ_TARGET", initialValue = 1)
    @Column(name = "id_target_sentiment",nullable = false, unique = true)
    private long idTargetSentiment;

    @Basic
    @Column(name = "text")
    private String text;

    @Basic
    @Column(name = "sentiment")
    private String sentiment;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "confidence_scores")
    private SentimentConfidenceScores confidenceScores;

    @Basic
    @Column(name = "offset_number")
    private long offset;

    @Basic
    @Column(name = "length")
    private long length;

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public String getSentiment() { return sentiment.toString(); }

    public void setSentimentPositive() { this.sentiment = "positive"; }

    public void setSentimentNeutre() { this.sentiment = "neutre"; }

    public void setSentimentNegative() { this.sentiment = "negative"; }

    public void setSentimentMixed() { this.sentiment = "mixed"; }

    public long getOffset() { return offset; }

    public void setOffset(long offset) { this.offset = offset; }

    public long getLength() { return length; }

    public void setLength(long length) { this.length = length; }

    public SentimentConfidenceScores getConfidenceScores() { return confidenceScores; }

    public void setConfidenceScores(SentimentConfidenceScores confidenceScores) { this.confidenceScores = confidenceScores; }

    public long getId() { return this.idTargetSentiment; }

    public TargetSentiment() { super(); }

    public TargetSentiment(String text, String sentiment, SentimentConfidenceScores confidenceScores, long offset, long length){
        super();
        this.setText(text);
        //this.setSentiment(sentiment);
        switch(sentiment){
            case "positive" : this.setSentimentPositive(); break;
            case "neutral" : this.setSentimentNeutre(); break;
            case "negative" : this.setSentimentNegative(); break;
            default: this.setSentimentMixed();
        }
        this.setConfidenceScores(confidenceScores);
        this.setOffset(offset);
        this.setLength(length);
    }

    @Override
    public String toString() {
        return "TargetSentiment{" +
                "id=" + idTargetSentiment +
                ", text='" + text + '\'' +
                ", sentiment=" + sentiment +
                ", confidenceScores=" + confidenceScores.toString() +
                ", offset=" + offset +
                ", length=" + length +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetSentiment that = (TargetSentiment) o;
        return idTargetSentiment == that.idTargetSentiment ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTargetSentiment, text, sentiment, confidenceScores, offset, length);
    }

}
