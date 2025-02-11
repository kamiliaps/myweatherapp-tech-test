package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.service.WeatherService;
import com.weatherapp.myweatherapp.model.CityInfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testForecastByCity() {
        String city = "london";
        CityInfo mockCityInfo = new CityInfo();
        when(weatherService.forecastByCity(city)).thenReturn(mockCityInfo);
        ResponseEntity<?> response = weatherController.forecastByCity(city);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCityInfo, response.getBody());
    }

    @Test
    void testForecastByCityException() {
        String city = "";
        when(weatherService.forecastByCity(city)).thenThrow(new RuntimeException("400: Bad API Request:Invalid location parameter value."));
        ResponseEntity<?> response = weatherController.forecastByCity(city);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The following error was received - 400: Bad API Request:Invalid location parameter value.<br>Please try again with a valid city name.", response.getBody());
    }

    @Test
    void testCompareDaylightHours() {
        String city1 = "london";
        String city2 = "paris";
        when(weatherService.compareDaylightHours(city1, city2)).thenReturn(city2);
        ResponseEntity<String> response = weatherController.compareDaylightHours(city1, city2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(city2, response.getBody());
    }

    @Test
    void testCompareDaylightHoursException() {
        String city1 = "";
        String city2 = "";
        when(weatherService.compareDaylightHours(city1, city2)).thenThrow(new RuntimeException("400: Bad API Request:Invalid location parameter value."));
        ResponseEntity<String> response = weatherController.compareDaylightHours(city1, city2);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The following error was received - 400: Bad API Request:Invalid location parameter value.<br>Please try again with valid city names.", response.getBody());
    }

    @Test
    void testRainCheck() {
        String city1 = "london";
        String city2 = "paris";
        when(weatherService.rainCheck(city1, city2)).thenReturn("RAIN CHECK<br>london: true<br>paris: false");
        ResponseEntity<String> response = weatherController.rainCheck(city1, city2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("RAIN CHECK<br>london: true<br>paris: false", response.getBody());
    }

    @Test
    void testRainCheckException() {
        String city1 = "";
        String city2 = "";
        when(weatherService.rainCheck(city1, city2)).thenThrow(new RuntimeException("400: Bad API Request:Invalid location parameter value."));
        ResponseEntity<String> response = weatherController.rainCheck(city1, city2);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The following error was received - 400: Bad API Request:Invalid location parameter value.<br>Please try again with valid city names.", response.getBody());
    }
}