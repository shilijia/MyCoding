package com.dayuan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayuan.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{
	
     Users findByTelephone(String telephone);
      
}
