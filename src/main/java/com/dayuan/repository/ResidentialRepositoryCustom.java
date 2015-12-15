package com.dayuan.repository;

import java.util.List;

import com.dayuan.entity.Residential;

public interface ResidentialRepositoryCustom {
	
     List<Residential> findNears(double latitude, double longitude);
}
