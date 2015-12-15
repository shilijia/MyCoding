package com.dayuan.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users_bridge_equipment")
public class UsersBridgeEquipment implements Serializable{
	
	//保存用户设备授权信息
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "receive_users_id")
    private Long receiveUsersId;
	
	@Column(name = "telephone")
    private String telephone;
	
	@Column(name = "start_time")
    private Timestamp startTime;
	
	@Column(name = "end_time")
    private Timestamp endTime;
	
	@Column(name = "equipment_id")
    private Long equipmentId;
	
	@Column(name = "send_users_id")
    private Long sendUsersId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReceiveUsersId() {
		return receiveUsersId;
	}

	public void setReceiveUsersId(Long receiveUsersId) {
		this.receiveUsersId = receiveUsersId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Long getSendUsersId() {
		return sendUsersId;
	}

	public void setSendUsersId(Long sendUsersId) {
		this.sendUsersId = sendUsersId;
	}
    
}
