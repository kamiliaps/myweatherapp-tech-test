package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;

/**
 * Handles all incoming HTTP requests
 */

@RestController
public class WeatherController {

  @Autowired
  WeatherService weatherService;

  // Given a city name, return the weather forecast for that city
  @GetMapping("/forecast/{city}")
  public ResponseEntity<?> forecastByCity(@PathVariable("city") String city) {
    try {
      CityInfo ci = weatherService.forecastByCity(city);
      return ResponseEntity.ok(ci);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The following error was received - " + e.getMessage() + "<br>" + "Please try again with a valid city name.");
    }
  }

  // Given two city names, compare the length of the daylight hours and return the city with the longest day
  @GetMapping("/compareDaylightHours/{city1}/{city2}")
  public ResponseEntity<String> compareDaylightHours(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {
    try {
      String result = weatherService.compareDaylightHours(city1, city2);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The following error was received - " + e.getMessage() + "<br>" + "Please try again with valid city names.");
    }
  }

  // Given two city names, check which city its currently raining in
  @GetMapping("/rainCheck/{city1}/{city2}")
  public ResponseEntity<String> rainCheck(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {
    try {
      String result = weatherService.rainCheck(city1, city2);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The following error was received - " + e.getMessage() + "<br>" + "Please try again with valid city names.");
    }
  }
}
