package com.ayaanalam.journalapp.service;

import com.ayaanalam.journalapp.api.reponse.WeatherResponse;
import com.ayaanalam.journalapp.cache.AppCache;
import com.ayaanalam.journalapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private AppCache appCache;

    @Value("${weather.api.key}")
    private String apiKey;
//    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {

        String finalAPI = appCache.APP_CACHE.get(AppCache.keys.weather_api_key.toString()).replace("<city>", city).replace("<apiKey>", apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class); // For GET
        WeatherResponse body = response.getBody();
        return body;
    }

}


//        HttpHeaders httpHeaders = new HttpHeaders();   // To send Headers via POST
//        httpHeaders.set("key","value");
//        User user = User.builder().userName("Ayaan").password("Ayaan").build();  // To Send POST calls with the help http entity
//        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

//        ResponseEntity<WeatherResponse> response2 = restTemplate.exchange(finalAPI, HttpMethod.POST, httpEntity, WeatherResponse.class); // For POST


