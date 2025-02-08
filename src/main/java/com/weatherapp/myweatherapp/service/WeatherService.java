package com.weatherapp.myweatherapp.service;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  @Autowired
  VisualcrossingRepository weatherRepo;

  public CityInfo forecastByCity(String city) {

    return weatherRepo.getByCity(city);
  }

  public double calculateDayLightHours(String sunrise, String sunset) {
    LocalTime sunriseTime = LocalTime.parse(sunrise);
    LocalTime sunsetTime = LocalTime.parse(sunset);

    Duration daylightDuration = Duration.between(sunriseTime, sunsetTime);

    long hours = daylightDuration.toHours();
    long minutes = daylightDuration.toMinutes() % 60; // get remaining minutes after calculating hours

    double daylightHours = hours + (minutes / 60.0); // convert minutes to fraction of an hour

    return daylightHours;
  }

  public String compareDaylightHours(String city1, String city2) {
    CityInfo city1Info = forecastByCity(city1);
    CityInfo city2Info = forecastByCity(city2);

    double city1DaylightHours = calculateDayLightHours(city1Info.getSunrise(), city1Info.getSunset());
    double city2DaylightHours = calculateDayLightHours(city2Info.getSunrise(), city2Info.getSunset());

    if (city1DaylightHours > city2DaylightHours) {
      return city1;
    } else if (city2DaylightHours > city1DaylightHours) {
      return city2;
    } else {
      return "The daylight hours are the same in both " + city1 + " and " + city2;
    }
  }
}
