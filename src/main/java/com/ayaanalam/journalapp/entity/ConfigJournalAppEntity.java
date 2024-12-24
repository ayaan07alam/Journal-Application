package com.ayaanalam.journalapp.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("configuration_journal_app")
@Data
public class ConfigJournalAppEntity {

    private String key;
    private String value;

}
