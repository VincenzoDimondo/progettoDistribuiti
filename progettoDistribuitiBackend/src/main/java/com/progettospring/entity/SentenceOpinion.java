package com.progettospring.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sentence_opinion", schema = "sentiment_analysis")
public class SentenceOpinion implements Serializable {

    public SentenceOpinion(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sentence_opinion", nullable = false, unique = true)
    private long idSentenceOpinion;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "target")
    private TargetSentiment target;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "assessment")
    private List<AssessmentSentiment> assessment ;

    public long getId() {
        return idSentenceOpinion;
    }

    public TargetSentiment getTarget() {
        return target;
    }

    public void setTarget(TargetSentiment target) {
        this.target = target;
    }

    public List<AssessmentSentiment> getAssessment() {
        return assessment;
    }

    public void setAssessment(List<AssessmentSentiment> assessment) {
        this.assessment = assessment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SentenceOpinion that = (SentenceOpinion) o;
        return idSentenceOpinion == that.idSentenceOpinion &&
                Objects.equals(target, that.target) &&
                Objects.equals(assessment, that.assessment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSentenceOpinion, target, assessment);
    }

    @Override
    public String toString() {
        return "SentenceOpinion{" +
                "id=" + idSentenceOpinion +
                ", target=" + target.toString() +
                ", assessment=" + assessment.toString() +
                '}';
    }

}
