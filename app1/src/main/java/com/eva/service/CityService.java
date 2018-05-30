package com.eva.service;

import com.eva.core.AbstractService;
import com.eva.dao.CityMapper;
import com.eva.datasource.DS;
import com.eva.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService extends AbstractService<City> {

    @Autowired
    private CityMapper cityMapper;

    public City findByState(String state) {
        return cityMapper.findByState(state);
    }

    public City selectCityById(Long id) {
        return cityMapper.selectCityById(id);
    }

    @DS("datasource2")
    public City selectCityByIdDB2(Long id) {
        return mapper.selectByPrimaryKey(id);
    }
}
