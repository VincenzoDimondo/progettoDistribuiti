package com.progettospring.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sentence_sentiment", schema = "sentiment_analysis")
public class SentenceSentiment implements Serializable {

    private enum TextSentiment { positive, neutre, negative, mixed };

    public SentenceSentiment(){ super(); }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sentence_sentiment", nullable = false, unique = true)
    private long idSentenceSentiment;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinColumn(name = "confidence_scores")
    private SentimentConfidenceScores confidenceScores;

    @Basic
    @Column(name = "sentiment")
    private String sentiment;

    @Basic
    @Column(name = "text", length = 2550000)
    private String text;

    @Basic
    @Column(name = "length")
    private long length;

    @Basic
    @Column(name = "offset_number")
    private long offset;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "opinions")
    private List<SentenceOpinion> opinions ;


    public SentenceSentiment(SentimentConfidenceScores sentimentConfidenceScores, String sentiment, String text, long offset, long length, List<SentenceOpinion> opinions){
        super();
        this.setConfidenceScores(sentimentConfidenceScores);
        switch (sentiment) {
            case "negative" : this.setSentimentNegative(); break;
            case "neutre" : this.setSentimentNeutre(); break;
            case "positive" : this.setSentimentPositive(); break;
            default: this.setSentimentMixed();
        };
        this.setText(text);
        this.setOffset(offset);
        this.setLength(length);
        this.setOpinions(opinions);
    }

    public long getId() {
        return idSentenceSentiment;
    }

    public SentimentConfidenceScores getConfidenceScores() {
        return confidenceScores;
    }

    public void setConfidenceScores(SentimentConfidenceScores confidenceScores) {
        this.confidenceScores = confidenceScores;
    }

    public String getSentiment() {
        return sentiment.toString();
    }

    public void setSentimentPositive() {
        this.sentiment = TextSentiment.positive.toString();
    }

    public void setSentimentNeutre() {
        this.sentiment = TextSentiment.neutre.toString();
    }

    public void setSentimentNegative() {
        this.sentiment = TextSentiment.negative.toString();
    }

    public void setSentimentMixed() { this.sentiment = TextSentiment.mixed.toString(); }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) { this.offset = offset; }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public List<SentenceOpinion> getOpinions() {
        return opinions;
    }

    public void setOpinions(List<SentenceOpinion> opinions) {
        this.opinions = opinions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SentenceSentiment that = (SentenceSentiment) o;
        return idSentenceSentiment == that.idSentenceSentiment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSentenceSentiment, confidenceScores, sentiment, text, offset, length, opinions);
    }

    @Override
    public String toString() {
        return "SentenceSentiment{" +
                "idSentenceSentiment=" + idSentenceSentiment +
                ", confidenceScores=" + confidenceScores.toString() +
                ", sentiment=" + sentiment +
                ", text='" + text + '\'' +
                ", offset=" + offset +
                ", length=" + length +
                ", opinions=" + opinions.toString() +
                '}';
    }

}
