package com.dayuan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayuan.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	
      List<Room> findByUnitId(Long unitId);
}
