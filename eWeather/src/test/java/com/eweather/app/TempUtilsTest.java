/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.eweather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class TempUtilsTest {
    
    public TempUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getWeatherForCities method, of class TempUtils.
     */
    @Test
    public void testGetWeatherForCities() throws Exception {
        System.out.println("Testing getWeatherForCities");

        TempUtils temp_utils = new TempUtils();
        
        List<String> cityIds = new ArrayList<>();
        cityIds.add("735914"); 

        String response = temp_utils.getWeatherForCities(temp_utils.getBaseUrl(), temp_utils.getKey(), cityIds);
        
        assertThat(response, containsString("Katerini"));
    }
    
    @Test
    public void testGetWeatherForCitiesCount() throws Exception {
        System.out.println("Testing getWeatherForCities");

        TempUtils temp_utils = new TempUtils();
        
        List<String> cityIds = new ArrayList<>();
        cityIds.add("735914"); 
        cityIds.add("734077"); 
        cityIds.add("264371"); 

        String response = temp_utils.getWeatherForCities(temp_utils.getBaseUrl(), temp_utils.getKey(), cityIds);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response);
        assertTrue(rootNode.get("cnt").intValue() == 3);
    }

    @Test(expected = MalformedURLException.class)
    public void testGetWeatherForCitiesMalformedException() throws Exception {
        System.out.println("Testing getWeatherForCities");

        TempUtils temp_utils = new TempUtils();
        
        List<String> cityIds = new ArrayList<>();
        cityIds.add("735914"); 

        String baseUrl = "xttps://api.openweathermap.org/data/2.5/group";

        String response = temp_utils.getWeatherForCities(baseUrl, temp_utils.getKey(), cityIds);
    }

}
