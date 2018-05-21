package com.eva.dao;

import com.eva.domain.City;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CityMapper {
    @Select("select * from city where state = #{state}")
    City findByState(@Param("state") String state);

    City selectCityById(Long id);
}
