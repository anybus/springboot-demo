package com.eva.controller;

import com.eva.service.RedisHandle;
import com.eva.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/redis")
public class RedisTestController {

	private final static Logger logger = LoggerFactory.getLogger(RedisTestController.class);

	private static List<String> keys = Collections.synchronizedList(new ArrayList<String>());


	@Autowired
	RedisHandle redisHandle;

	@GetMapping("/add" )
	public String addString(String key,String value) throws Exception {
		redisHandle.set(key,value);
		return (String) redisHandle.get(key);
	}


	@RequestMapping(value = { "/get/{key}" }, method = RequestMethod.GET)
	public Object getString(@PathVariable String key) throws Exception {
		Object v = redisHandle.get(key);
		return v;
	}

	@RequestMapping("getAllKeys")
	public Set<String> getAllKeys(){
		return redisHandle.getAllKeys();
	}

}
