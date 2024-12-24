package com.ayaanalam.journalapp.cache;

import com.ayaanalam.journalapp.entity.ConfigJournalAppEntity;
import com.ayaanalam.journalapp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        weather_api_key;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> APP_CACHE;

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity> allData = configJournalAppRepository.findAll();
         for(ConfigJournalAppEntity configJournalAppEntity : allData){
             APP_CACHE.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
         }
    }
}
