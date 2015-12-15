package com.dayuan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayuan.entity.Building;

public interface BuildingRepository extends JpaRepository<Building, Long>{
       
	   List<Building> findByResidentialId(Long residentialId);
}
