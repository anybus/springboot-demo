package com.eva.service.impl;

import com.eva.config.DS;
import com.eva.dao.CityMapper;
import com.eva.domain.City;
import com.eva.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public City findByState(String state) {
        return cityMapper.findByState(state);
    }

    @Override
    public City selectCityById(Long id) {
        return cityMapper.selectCityById(id);
    }

    @Override
    @DS("datasource2")
    public City selectCityByIdDB2(Long id) {
        return cityMapper.selectCityById(id);
    }
}
