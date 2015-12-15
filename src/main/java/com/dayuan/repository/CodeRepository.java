package com.dayuan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayuan.entity.Code;

public interface CodeRepository extends JpaRepository<Code, Long>{
       
	   Code findByTelephone(String telephone);
}
