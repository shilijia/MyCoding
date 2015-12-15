package com.dayuan.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dayuan.entity.City;

public interface CityRepository extends JpaRepository<City, Long>{
     
	
}
