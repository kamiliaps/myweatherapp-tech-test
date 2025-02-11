package com.weatherapp.myweatherapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.weatherapp.myweatherapp.model.CityInfo;
import com.weatherapp.myweatherapp.repository.VisualcrossingRepository;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
  
  @Mock
  VisualcrossingRepository weatherRepo;

  @InjectMocks
  private WeatherService weatherService;
  private CityInfo city1Info;
  private CityInfo city2Info;

  @BeforeEach
  void setUp() {
    city1Info = new CityInfo();
    city1Info.setCity("london");
    city1Info.setSunrise("06:00:00");
    city1Info.setSunset("18:00:00");
    city1Info.setConditions("Rain");

    city2Info = new CityInfo();
    city2Info.setCity("paris");
    city2Info.setSunrise("06:00:00");
    city2Info.setSunset("18:10:00");
    city2Info.setConditions("Sunny");
  }

  @Test
  void testCalculateDayLightHours() {
    double daylightHours = weatherService.calculateDayLightHours("06:00:00", "18:00:00");
    assertEquals(12.0, daylightHours);
  }

  @Test
  void testCompareDaylightHours() {
    when(weatherRepo.getByCity("london")).thenReturn(city1Info);
    when(weatherRepo.getByCity("paris")).thenReturn(city2Info);

    String result = weatherService.compareDaylightHours("london", "paris");
    assertEquals("paris", result);
  }

  @Test 
  void testCompareDaylightHoursWhenEqual() {
    when(weatherRepo.getByCity("london")).thenReturn(city1Info);
    when(weatherRepo.getByCity("paris")).thenReturn(city2Info);
    city2Info.setSunset("18:00:00");

    String result = weatherService.compareDaylightHours("london", "paris");
    assertEquals("The daylight hours are the same in both london and paris", result);
  }

  @Test
  void testRainCheck() {
    when(weatherRepo.getByCity("london")).thenReturn(city1Info);
    when(weatherRepo.getByCity("paris")).thenReturn(city2Info);

    String result = weatherService.rainCheck("london", "paris");
    assertEquals("RAIN CHECK<br>london: true<br>paris: false", result);
  }

  @Test
  void testRainCheckWhenBothCitiesRain() {
    when(weatherRepo.getByCity("london")).thenReturn(city1Info);
    when(weatherRepo.getByCity("paris")).thenReturn(city2Info);
    city2Info.setConditions("Rain, Sunny");

    String result = weatherService.rainCheck("london", "paris");
    assertEquals("RAIN CHECK<br>london: true<br>paris: true", result);
  }

  @Test
  void testValidateCityName() {
    assertDoesNotThrow(() -> weatherService.validateCityName("london"));
  }

  @Test
  void testValidateCityNameWithNull() {
    assertThrows(ResponseStatusException.class, () -> weatherService.validateCityName(null));
  }

  @Test
  void testValidateCityNameWithEmpty() {
    assertThrows(ResponseStatusException.class, () -> weatherService.validateCityName(""));
  }
}