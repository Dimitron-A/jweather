/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.eweather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Entities.City;
import Entities.WeatherForecast;
import Entities.WeatherSnapshot;

public class TempUtils {

    // ΠΡΟΣΟΧΗ: Αλλάξτε αυτό το API key με το δικό σας από https://openweathermap.org/
    // Το τρέχον key ΔΕΝ λειτουργεί! Πρέπει να κάνετε sign up και να πάρετε το δικό σας!
    private String key = "accebd4aa5e89e1bbdb492f9620e2a32";  // <-- ΑΛΛΑΞΤΕ ΑΥΤΟ!
    private String baseUrl = "https://api.openweathermap.org/data/2.5/group";
    private String forecastBaseUrl = "https://api.openweathermap.org/data/2.5/forecast";

    public String getKey() {
        return key;
    }
    public String getBaseUrl() {
        return baseUrl;
    }
    public String getForecastBaseUrl() {
        return forecastBaseUrl;
    }

    public void refreshWeatherForAllCities() throws Exception {
        // get all cities from database
        EntityManager entityManager =  Persistence.createEntityManagerFactory("com.eweather_eWeather_jar_1.0-SNAPSHOTPU").createEntityManager();
        Query query = entityManager.createQuery("SELECT e FROM City e");
        List<City> resultList = query.getResultList();
        
        ObjectMapper mapper = new ObjectMapper();
        
        // Fetch weather for each city individually (group API requires paid plan)
        for (City city : resultList) {
            try {
                String weatherResponse = getWeatherForCity(city.getWeatherId(), this.getKey());
                JsonNode rootNode = mapper.readTree(weatherResponse);
                
                // Check if we already have this snapshot
                long timestamp = rootNode.get("dt").longValue() * 1000;
                query = entityManager.createQuery("SELECT e FROM WeatherSnapshot e WHERE e.city.id=:city_id AND e.dt=:dt")
                    .setParameter("city_id", city.getId())
                    .setParameter("dt", new Date(timestamp));
                List<WeatherSnapshot> results = query.getResultList();
                
                if (results.size() > 0) {
                    continue; // Already have this data
                }
                
                WeatherSnapshot weatherSnapshot = new WeatherSnapshot();
                weatherSnapshot.setCity(city);
                weatherSnapshot.setTemperature(rootNode.get("main").get("temp").floatValue());
                weatherSnapshot.setClouds(rootNode.get("clouds").get("all").intValue());
                
                JsonNode rain = rootNode.get("rain");
                if (rain != null && rain.has("1h")) {
                    weatherSnapshot.setRain_mm(rain.get("1h").floatValue());
                }
                
                JsonNode snow = rootNode.get("snow");
                if (snow != null && snow.has("1h")) {
                    weatherSnapshot.setSnow_mm(snow.get("1h").floatValue());
                }
                
                weatherSnapshot.setWind_speed(rootNode.get("wind").get("speed").floatValue());
                weatherSnapshot.setDt(new Date(timestamp));
                weatherSnapshot.setDescription(rootNode.get("weather").get(0).get("description").asText());
                
                entityManager.getTransaction().begin();
                entityManager.persist(weatherSnapshot);
                entityManager.getTransaction().commit();
                
                System.out.println("✓ Fetched weather for " + city.getName());
            } catch (Exception e) {
                System.err.println("✗ Error fetching weather for " + city.getName() + ": " + e.getMessage());
            }
        }
        entityManager.close();
    }
    
    // New method to fetch weather for a single city
    public String getWeatherForCity(Long cityId, String apiKey) throws IOException {
        String urlStr = "https://api.openweathermap.org/data/2.5/weather?id=" + cityId + "&units=metric&appid=" + apiKey;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }
        
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        conn.disconnect();
        return sb.toString();
    }
    
    
    public void refreshForecastForAllCities() throws Exception {
        // get all cities from database
        EntityManager entityManager =  Persistence.createEntityManagerFactory("com.eweather_eWeather_jar_1.0-SNAPSHOTPU").createEntityManager();
        Query query = entityManager.createQuery("SELECT e FROM City e");
        List<City> resultList = query.getResultList();
        int i;
            // build forecast request and do for each city
        for (i=0;i<resultList.size();i++) {
            List<String> cityIds = new ArrayList<>();
            cityIds.add(String.valueOf(resultList.get(i).getWeatherId())); 
            String forecastResponse = this.getWeatherForCities(this.getForecastBaseUrl(), this.getKey(), cityIds);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(forecastResponse);
            int j;
            for (j=0;j<rootNode.get("cnt").intValue();j++) {
                WeatherForecast weatherForecast = new WeatherForecast();
                weatherForecast.setCity(resultList.get(i));
                weatherForecast.setTemperature(rootNode.get("list").get(j).get("main").get("temp").floatValue());
                weatherForecast.setClouds(rootNode.get("list").get(j).get("clouds").get("all").intValue());
                JsonNode rain = rootNode.get("list").get(j).get("rain");
                if (rain!=null) {
                    weatherForecast.setRain_mm(rain.floatValue());
                }
                JsonNode snow = rootNode.get("list").get(j).get("Snow");
                if (snow!=null) {
                    weatherForecast.setSnow_mm(snow.floatValue());                
                }
                weatherForecast.setWind_speed(rootNode.get("list").get(j).get("wind").get("speed").floatValue());
                
                weatherForecast.setDt(new Date(rootNode.get("list").get(j).get("dt").longValue()*1000));

                weatherForecast.setDescription(rootNode.get("list").get(j).get("weather").get(0).get("description").asText());

                entityManager.getTransaction().begin();
                entityManager.persist(weatherForecast);
                entityManager.getTransaction().commit();
                
            }
        }

    }
    
    public String getWeatherForCities(
            String baseUrl, 
            String apiKey,
            List<String> cityIds)
            throws MalformedURLException, IOException {
        StringBuilder sb = new StringBuilder();

        String urlStr
                = baseUrl + "?id=";
        int count = cityIds.size();
        for (int i = 0; i < count; i++) {
            urlStr += cityIds.get(i);
            if (i < count - 1) {
                urlStr += ",";
            }
        }
        urlStr += "&units=metric&appid=" + apiKey;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;

        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        conn.disconnect();

        return sb.toString();
    }

}