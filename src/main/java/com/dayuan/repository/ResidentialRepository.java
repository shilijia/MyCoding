package com.dayuan.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dayuan.entity.Residential;

public interface ResidentialRepository extends JpaRepository<Residential, Long>, ResidentialRepositoryCustom{
     
	 @Query("select r from Residential where cityId = ?1")
	 List<Residential> findByCityId(Long cityId, Pageable pageable);
	 
	 @Query("select r from Residential where name like %?1%")
	 List<Residential> findByResidentialName(String name, Pageable pageable);
}
