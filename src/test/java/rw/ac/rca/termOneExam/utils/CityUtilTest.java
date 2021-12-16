package rw.ac.rca.termOneExam.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@RunWith(SpringRunner.class)
public class CityUtilTest {

  @Autowired
  private ICityRepository cityRepository;

  @Test
  public void noCitiesWithWeathergreaterThan40() {
    boolean cityTemperatureresult = false;
    List<City> cities = cityRepository.findAll();
    for (City city : cities)
      if (city.getWeather() > 40)
        cityTemperatureresult = true;


    assertEquals(false, cityTemperatureresult);
  }

  @Test
  public void noCitiesWithWeatherBelow10() {
    boolean cityTemperatureresult = false;
    List<City> cities = cityRepository.findAll();
    for (City city : cities)
      if (city.getWeather() < 10)
        cityTemperatureresult= true;


    assertEquals(false, cityTemperatureresult);
  }

  @Test
  public void AssertingCitiesHaveMusanzeAndKigali() {
    List<City> musanzeAndRubavu = cityRepository.findAll();
    assertEquals(true, cityRepository.existsByName("Musanze") && cityRepository.existsByName("Kigali"));
  }


  @Test
  public void testSpying() {
    List<City> spyingList = Mockito.spy(ArrayList.class);
    City city = new City("Kigali", 24);
    spyingList.add(city);
    Mockito.verify(spyingList).add(city);

    assertEquals(1, spyingList.size());
  }

  @Test
  public void testMocking() {
    List<City> mockingList = Mockito.mock(ArrayList.class);
    City city = new City("Kigali", 24);
    mockingList.add(city);
    Mockito.verify(mockingList).add(city);

    assertEquals(0, mockingList.size());
  }
}