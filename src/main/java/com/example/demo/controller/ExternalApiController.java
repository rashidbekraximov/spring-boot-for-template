package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Api(value = "External Api Controller")
@RequestMapping("/api/v1")
@CrossOrigin
public class ExternalApiController {

    private final RestTemplate restTemplate;

    public ExternalApiController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @ApiOperation(value = "get weather api", response = Long.class)
    @GetMapping(value = "/weather")
    public Object getWeather() {
        return restTemplate.getForObject("http://api.weatherapi.com/v1/current.json?key=70452c67861d41d787861437221508&q=Urgench&aqi=no", Object.class);
    }

    @ApiOperation(value = "get currency api", response = Long.class)
    @GetMapping(value = "/currency")
    public Object getCurrency() {
        return restTemplate.getForObject("https://cbu.uz/uz/arkhiv-kursov-valyut/json", Object.class);
    }
}
