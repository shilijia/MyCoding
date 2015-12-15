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
@Table(name = "fix_auth_infos")
public class FixAuthInfos implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "telephone")
	private String telephone;
	
	@Column(name = "resudential_id")
	private Long resudentialId;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "fix_time")
	private Timestamp fixTime = new Timestamp(System.currentTimeMillis());

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Long getResudentialId() {
		return resudentialId;
	}

	public void setResudentialId(Long resudentialId) {
		this.resudentialId = resudentialId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Timestamp getFixTime() {
		return fixTime;
	}

	public void setFixTime(Timestamp fixTime) {
		this.fixTime = fixTime;
	}
	
}
