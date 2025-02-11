package com.weatherapp.myweatherapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CityInfo {

  @JsonProperty("address")
  String address;

  @JsonProperty("description")
  String description;

  @JsonProperty("currentConditions")
  CurrentConditions currentConditions = new CurrentConditions(); // initialise to avoid null pointer exception in tests

  @JsonProperty("days")
  List<Days> days;

  static class CurrentConditions {
    @JsonProperty("temp")
    String currentTemperature;

    @JsonProperty("sunrise")
    String sunrise;

    @JsonProperty("sunset")
    String sunset;

    @JsonProperty("feelslike")
    String feelslike;

    @JsonProperty("humidity")
    String humidity;

    @JsonProperty("conditions")
    String conditions;
  }

  static class Days {

    @JsonProperty("datetime")
    String date;

    @JsonProperty("temp")
    String currentTemperature;

    @JsonProperty("tempmax")
    String maxTemperature;

    @JsonProperty("tempmin")
    String minTemperature;

    @JsonProperty("conditions")
    String conditions;

    @JsonProperty("description")
    String description;

  }

  // GET METHODS
  public String getCity() {
    return address;
  }

  public String getSunrise() {
    return currentConditions.sunrise;
  }

  public String getSunset() {
    return currentConditions.sunset;
  }

  public String getConditions() {
    return currentConditions.conditions;
  }

  // SET METHODS (for testing purposes only)
  public void setCity(String city) {
    address = city;
  }

  public void setSunrise(String sunrise) {
    currentConditions.sunrise = sunrise;
  }

  public void setSunset(String sunset) {
    currentConditions.sunset = sunset;
  }

  public void setConditions(String conditions) {
    currentConditions.conditions = conditions;
  }
}
