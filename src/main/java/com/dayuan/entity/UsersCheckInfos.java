package com.dayuan.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users_check_infos")
public class UsersCheckInfos implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "real_name")
    private String realName;
	
	@Column(name = "telephone")
    private String telephone;
	
	@Column(name = "residential_id")
    private Long residentialId;
	
	@Column(name = "building_id")
    private Long buildingId;
	
	@Column(name = "unit_id")
    private Long unitId;
	
	@Column(name = "room_id")
    private Long roomId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Long getResidentialId() {
		return residentialId;
	}

	public void setResidentialId(Long residentialId) {
		this.residentialId = residentialId;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	
}
