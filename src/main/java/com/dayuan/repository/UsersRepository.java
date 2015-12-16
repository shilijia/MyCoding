package com.dayuan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dayuan.entity.Residential;
import com.dayuan.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{
	
     Users findByTelephone(String telephone);
     
     @Query("select count(u.Id) from Users u where u.residentialId = ?1")
     int countUsersByResidential(Long residentialId);
}
