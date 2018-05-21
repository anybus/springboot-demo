package com.eva.service;


import com.eva.domain.City;

public interface CityService {

    City findByState(String state);

    City selectCityById(Long id);
    City selectCityByIdDB2(Long id);
}
