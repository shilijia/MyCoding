package com.dayuan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayuan.entity.UsersBridgeEquipment;

public interface UsersBridgeEquipmentRepository extends JpaRepository<UsersBridgeEquipment, Long>{
      
	 List<UsersBridgeEquipment> findByReceiveUsersId(Long receiveUsersId);
}
