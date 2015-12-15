package com.dayuan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayuan.entity.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long>{
	
        List<Unit> findByBuilingId(Long builingId);
}
