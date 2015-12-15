package com.dayuan.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dayuan.entity.UsersBridgeResidential;

public interface UsersBridgeResidentialRepository extends JpaRepository<UsersBridgeResidential, Long>{
       
	List<UsersBridgeResidential> findByResidentialId(Long residentialId);
	
	List<UsersBridgeResidential> findByTelephoneAndResidentialId(String telephone, Long residentialId);
	
	List<UsersBridgeResidential> findByUserIdAndResidentialId(Long userId, Long residentialId);
	
	UsersBridgeResidential findByTelephoneAndRoomId(String telephone, Long roomId);
	
	@Modifying
	@Query("update UsersBridgeResidential u set u.status = ?1,u.updateTime = ?2 where u.telephone = ?3 and u.residentialId = ?4")
	int updateStatus(Integer status, Timestamp updateTime, String telephone, Long residentialId);
	
	List<UsersBridgeResidential> findByUserIdAndResidentialIdAndStatus(Long userId, Long residentialId, Integer status);
	
	@Query("select u from UsersBridgeResidential u where u.roomId = ?1 and u.sendUserId = ?2 u.startTime >= ?3 and u.endTime <= ?4")
	List<UsersBridgeResidential> findByAuthTime(Long roomId, Long sendUserId, Timestamp startTime, Timestamp endTime);
	
}
