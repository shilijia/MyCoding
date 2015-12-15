package com.dayuan.controller.mobile.appApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dayuan.entity.City;
import com.dayuan.entity.Residential;
import com.dayuan.repository.CityRepository;
import com.dayuan.utils.APIException;
import com.dayuan.utils.ClientReceive;
import com.dayuan.utils.Error;

@Transactional
@RestController
@RequestMapping(value = "api/city/")
public class CityApi {
    
	@Resource
	CityRepository cr;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	Map getAll(@RequestParam(value = "start", defaultValue = "0") int start, 
    		@RequestParam(value = "limit", defaultValue = "10") int limit){
		 try{
             Pageable pageable = new PageRequest(start, limit);
             Page<City> page = cr.findAll(pageable);
             List<City> result = page.getContent();
             return new ClientReceive().push("result", result).successMessage();
        }catch (Exception e){
           return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
        }
	}
}
