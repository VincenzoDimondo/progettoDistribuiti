package com.progettospring.service;

import com.azure.ai.textanalytics.models.WarningCode;
import com.progettospring.entity.TextAnalyticsWarning;
import com.progettospring.repository.TextAnalyticsWarningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
public class TextAnalyticsWarningService {

    @Autowired
    private TextAnalyticsWarningRepository textAnalyticsWarningRepository;

    @Transactional(readOnly = false)
    public TextAnalyticsWarning createTextAnalyticsWatning(LinkedHashMap textAnalyticsWarning){
        TextAnalyticsWarning textAnalyticsWarningToSave = new TextAnalyticsWarning();
        textAnalyticsWarningToSave.setMessage(textAnalyticsWarning.get("message").toString());
        textAnalyticsWarningToSave.setWarningCode(textAnalyticsWarning.get("warningCode").toString());
        return textAnalyticsWarningRepository.save(textAnalyticsWarningToSave);
    }

    @Transactional(readOnly = false)
    public TextAnalyticsWarning createTextAnalyticsWatning(com.azure.ai.textanalytics.models.TextAnalyticsWarning textAnalyticsWarning){
        TextAnalyticsWarning textAnalyticsWarningToSave = new TextAnalyticsWarning();
        textAnalyticsWarningToSave.setMessage(textAnalyticsWarning.getMessage());
        textAnalyticsWarningToSave.setWarningCode(textAnalyticsWarning.getWarningCode().toString());
        return textAnalyticsWarningRepository.save(textAnalyticsWarningToSave);
    }

    @Transactional(readOnly = true)
    public com.azure.ai.textanalytics.models.TextAnalyticsWarning getTextAnalyticsWarningById(long id){
        TextAnalyticsWarning textAnalyticsWarning = textAnalyticsWarningRepository.getByIdTextAnalyticsWarning(id);
        com.azure.ai.textanalytics.models.WarningCode warningCodeAzure = WarningCode.fromString(textAnalyticsWarning.getWarningCode());
        return new com.azure.ai.textanalytics.models.TextAnalyticsWarning(warningCodeAzure, textAnalyticsWarning.getMessage());
    }

}
