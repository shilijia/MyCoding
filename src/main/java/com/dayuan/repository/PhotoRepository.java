package com.dayuan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayuan.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long>{
	
      Photo findByTargetIdAndTargetName(Long targetId, String targetName);
}
