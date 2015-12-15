package com.dayuan.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "unit")
public class Unit implements Serializable{
    //单元
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "residential_id")
	private Long residentialId;
	
    @Column(name = "building_id")
	private Long builingId;
    
    @Column(name = "room_id")
	private Long roomId;
    
    @Column(name = "name")
    private String name;

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

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
	
}
