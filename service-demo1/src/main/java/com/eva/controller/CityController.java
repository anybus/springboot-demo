package com.eva.controller;


import com.eva.domain.City;
import com.eva.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @RequestMapping("/findCityByState")
    public City findCityByState(String state) {
        return cityService.findByState(state);
    }

    @RequestMapping("/findCityById")
    public City findCityById(Long id) {
        return cityService.selectCityById(id);
    }

    @RequestMapping("/findCityByIdDB2")
    public City findCityByIdDB2(Long id) {
        return cityService.selectCityByIdDB2(id);
    }
}
