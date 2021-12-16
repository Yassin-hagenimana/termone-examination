package rw.ac.rca.termOneExam.controller;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class CityControllerIntegrationTest {
  @Autowired
  private TestRestTemplate restTemplate;



  @Test
  public void getAll_cities_success() throws JSONException {
    String response = this.restTemplate.getForObject("api/cities/all",String.class);

    JSONAssert.assertEquals("[{id:101},{id:102},{id:103},{id:104}]",response,false);

  }

  @Test
  public void getById_success() throws JSONException {
    ResponseEntity<City> city = this.restTemplate.getForEntity("api/cities/id/101",City.class);

    assertTrue(city.getStatusCode().is2xxSuccessful());
    assertEquals("Kigali",city.getBody().getName());
    assertEquals(24,city.getBody().getWeather());

  }

  @Test
  public void getById_fail() throws JSONException {
    ResponseEntity<APICustomResponse> response = this.restTemplate.getForEntity("api/cities/id/20",APICustomResponse.class);

    assertTrue(response.getStatusCodeValue()==404);
    assertEquals(false, response.getBody().isStatus());
    assertEquals("City not found with id 20",response.getBody().getMessage());

  }

  @Test
  public void post_city_success() throws JSONException {
    City body = new City(105,"Muhanga",70,12);
    ResponseEntity<City> item = this.restTemplate.postForEntity("api/cities/add",body, City.class);

    assertTrue(item.getStatusCode().is2xxSuccessful());
    assertEquals("Cairo",item.getBody().getName());

  }

  @Test
  public void post_City_failure() throws JSONException {
    City body = new City(101,"Rubavu",70,12);
    ResponseEntity<APICustomResponse> response = this.restTemplate.postForEntity("api/cities/add",body,APICustomResponse.class);

    assertTrue(response.getStatusCodeValue()==400);
    assertFalse(response.getBody().isStatus());
    assertEquals("City name Rubavu is registered already",response.getBody().getMessage());

  }
}
