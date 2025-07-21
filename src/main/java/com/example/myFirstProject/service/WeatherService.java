package com.example.myFirstProject.service;

import com.example.myFirstProject.api.response.WeatherResponse;
import com.example.myFirstProject.cache.AppCache;
import com.example.myFirstProject.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
@Service
public class WeatherService {

    @Value("${weather.api.key}")    //@Value("${weather_api_key}")
    private String apiKey;   // not static

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {
        String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace("<apiKey>", apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse responseBody = response.getBody();
        return responseBody;
    }

}
