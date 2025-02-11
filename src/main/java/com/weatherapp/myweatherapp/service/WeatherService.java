package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service class to handle business logic
 * Instead of just modifying the code in WeatherController.java as indicated with the "TO-DO" comments,
 * I decided to create new methods in WeatherService.java to handle the new requirements.
 * This is to ensure that the controller class remains clean and follows the single responsibility principle, and to help with code maintainability.
 */

@Service
public class WeatherService {

  @Autowired
  VisualcrossingRepository weatherRepo;

  /**
   * Get the weather forecast for a city
   * @param city the city to get the forecast for
   * @return CityInfo object containing the forecast
   */
  public CityInfo forecastByCity(String city) {
    validateCityName(city);
    CityInfo cityInfo = weatherRepo.getByCity(city);
    return cityInfo;
  }

  /**
   * Calculate the number of daylight hours between sunrise and sunset
   * @param sunrise sunrise time in HH:mm:ss format
   * @param sunset sunset time in HH:mm:ss format
   * @return daylightHours the number of daylight hours
   */
  public double calculateDayLightHours(String sunrise, String sunset) {
    LocalTime sunriseTime = LocalTime.parse(sunrise);
    LocalTime sunsetTime = LocalTime.parse(sunset);

    Duration daylightDuration = Duration.between(sunriseTime, sunsetTime);

    long hours = daylightDuration.toHours();
    long minutes = daylightDuration.toMinutes() % 60; // get remaining minutes after calculating hours

    double daylightHours = hours + (minutes / 60.0); // convert minutes to fraction of an hour

    return daylightHours;
  }

  /**
   * Compare the daylight hours of two cities and return the city with the longest day
   * @param city1 
   * @param city2
   * @return the city with the longest day or a message if the daylight hours are the same
   */
  public String compareDaylightHours(String city1, String city2) {
    validateCityName(city1);
    validateCityName(city2);
    CityInfo city1Info = forecastByCity(city1);
    CityInfo city2Info = forecastByCity(city2);

    double city1DaylightHours = calculateDayLightHours(city1Info.getSunrise(), city1Info.getSunset());
    double city2DaylightHours = calculateDayLightHours(city2Info.getSunrise(), city2Info.getSunset());

    if (city1DaylightHours > city2DaylightHours) {
      return city1;
    } else if (city2DaylightHours > city1DaylightHours) {
      return city2;
    } else {
      return String.format("The daylight hours are the same in both %s and %s", city1, city2);
    }
  }

  /**
   * Check if it is currently raining in two cities
   * @param city1
   * @param city2
   * @return a string indicating if it is raining in each city
   */
  public String rainCheck(String city1, String city2) {
    validateCityName(city1);
    validateCityName(city2);
    CityInfo city1Info = forecastByCity(city1);
    CityInfo city2Info = forecastByCity(city2);

    boolean city1Rain = city1Info.getConditions().contains("Rain");
    boolean city2Rain = city2Info.getConditions().contains("Rain");

    // I chose this format for the response to make it concise, readable and easy to understand.
    // If it's raining in a city, the value is true, otherwise it's false.
    return String.format("RAIN CHECK<br>%s: %s<br>%s: %s", city1, city1Rain, city2, city2Rain); 
  }

  /**
   * Validate city name against null or empty values
   * @param city 
   */
  public void validateCityName(String city1) {
    if (city1==null || city1.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City name cannot be empty");
    }
  }
}
