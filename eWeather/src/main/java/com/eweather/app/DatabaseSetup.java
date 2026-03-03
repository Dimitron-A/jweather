package com.mycompany.eweather;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import Entities.City;

public class DatabaseSetup {
    
    public static void main(String[] args) {
        try {
            System.out.println("==========================================");
            System.out.println("Database Setup - Connecting to Derby server");
            System.out.println("==========================================");
            System.out.println("");
            
            // Connect to network Derby database
            EntityManager entityManager = Persistence.createEntityManagerFactory("com.eweather_eWeather_jar_1.0-SNAPSHOTPU").createEntityManager();
            
            System.out.println("✓ Connected to database");
            System.out.println("");
            System.out.println("Adding Greek cities...");
            
            // Insert Greek cities with OpenWeatherMap IDs
            String[][] cities = {
                {"Athens", "264371"},
                {"Thessaloniki", "734077"},
                {"Patras", "255683"},
                {"Heraklion", "261745"},
                {"Larissa", "258576"}
            };
            
            entityManager.getTransaction().begin();
            
            for (String[] cityData : cities) {
                try {
                    City city = new City();
                    city.setName(cityData[0]);
                    city.setWeatherId(Long.parseLong(cityData[1]));
                    entityManager.persist(city);
                    System.out.println("✓ Added " + cityData[0]);
                } catch (Exception e) {
                    System.out.println("  " + cityData[0] + " already exists");
                }
            }
            
            entityManager.getTransaction().commit();
            entityManager.close();
            
            System.out.println("");
            System.out.println("==========================================");
            System.out.println("Database setup complete!");
            System.out.println("==========================================");
            System.out.println("");
            System.out.println("Cities in database:");
            System.out.println("  - Athens");
            System.out.println("  - Thessaloniki");
            System.out.println("  - Patras");
            System.out.println("  - Heraklion");
            System.out.println("  - Larissa");
            System.out.println("");
            System.out.println("You can now run the eWeather application!");
            
        } catch (Exception e) {
            System.err.println("Error setting up database:");
            e.printStackTrace();
        }
    }
}
