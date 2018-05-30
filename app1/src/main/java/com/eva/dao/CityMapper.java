package com.eva.dao;


import com.eva.core.MyMapper;
import com.eva.model.City;

public interface CityMapper extends MyMapper<City> {
    public City selectCityById(Long id);

    City findByState(String state);
}
