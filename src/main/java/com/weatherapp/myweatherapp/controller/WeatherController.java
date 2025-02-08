package com.weatherapp.myweatherapp.controller;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;

@Controller
public class WeatherController {

  @Autowired
  WeatherService weatherService;

  @GetMapping("/forecast/{city}")
  public ResponseEntity<?> forecastByCity(@PathVariable("city") String city) {
    try {
      if (city == null || city.isEmpty()) {
        return ResponseEntity.badRequest().body("City name is required");
      }

      CityInfo ci = weatherService.forecastByCity(city);

      if (ci == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("City not found");
      }

      return ResponseEntity.ok(ci);

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
  }

  // TODO: given two city names, compare the length of the daylight hours and return the city with the longest day
  @GetMapping("/compareDaylightHours/{city1}/{city2}")
  public ResponseEntity<String> compareDaylightHours(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {
      try {
          if (areCityNamesValid(city1, city2)) {
            return ResponseEntity.badRequest().body("City names are required");
          }

          String result = weatherService.compareDaylightHours(city1, city2);
          return ResponseEntity.ok(result);

      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
      }
  }

  private boolean areCityNamesValid(String city1, String city2) {
      return city1 == null || city1.isEmpty() || city2 == null || city2.isEmpty();
  }

  // TODO: given two city names, check which city its currently raining in
  @GetMapping("/rainCheck/{city1}/{city2}")
  public ResponseEntity<String> rainCheck(@PathVariable("city1") String city1, @PathVariable("city2") String city2) {
    try {
      if (areCityNamesValid(city1, city2)) {
        return ResponseEntity.badRequest().body("City names are required");
      }
      
      String result = weatherService.rainCheck(city1, city2);
      return ResponseEntity.ok(result);
      
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
  }
}
