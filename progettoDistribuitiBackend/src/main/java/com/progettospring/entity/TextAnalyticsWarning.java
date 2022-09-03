package com.progettospring.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "text_analytics_warning", schema = "sentiment_analysis")
public class TextAnalyticsWarning implements Serializable {

    public TextAnalyticsWarning() { super();    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY
    //        , generator = "SQ_WARNING"
    )
    //@SequenceGenerator(name = "id_text_analytics_warning", sequenceName = "SQ_WARNING", initialValue = 1)
    @Column(name = "id_text_analytics_warning", nullable = false, unique = true)
    private long idTextAnalyticsWarning;

    public long getId() {   return idTextAnalyticsWarning;  }

    @Basic
    @Column(name = "message")
    private String message;

    public String getMessage() {   return message;    }

    @Basic
    @Column(name = "warning_code")
    private String warningCode;

    public void setMessage(String message) {    this.message = message;   }

    public String getWarningCode() {   return warningCode;    }

    public void setWarningCode(String warningCode) {    this.warningCode = warningCode;   }

    public TextAnalyticsWarning(String warningCode, String message) {
        super();
        this.warningCode = warningCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return "TextAnalyticsWarning{" +
                "id=" + idTextAnalyticsWarning +
                ", message='" + message + '\'' +
                ", warningCode='" + warningCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextAnalyticsWarning textAnalyticsWarning = (TextAnalyticsWarning) o;
        return Objects.equals(idTextAnalyticsWarning, textAnalyticsWarning.idTextAnalyticsWarning) &&
                Objects.equals(message, textAnalyticsWarning.message) &&
                Objects.equals(warningCode, textAnalyticsWarning.warningCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTextAnalyticsWarning, message, warningCode);
    }

}
