package com.dayuan.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class Room implements Serializable{
     //房间
	 @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
	 private Long id;
	 
	 @Column(name = "residential_id")
	 private Long residentialId;
	 
	 @Column(name = "building_id")
	 private Long builingId;
	 
	 @Column(name = "unit_id")
	 private Long unitId;
	 
	 @Column(name = "room_num")
	 private String roomNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getResidentialId() {
		return residentialId;
	}

	public void setResidentialId(Long residentialId) {
		this.residentialId = residentialId;
	}

	public Long getBuilingId() {
		return builingId;
	}

	public void setBuilingId(Long builingId) {
		this.builingId = builingId;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	
}
