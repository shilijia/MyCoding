package com.dayuan.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by q on 2015/12/4.
 */
@Entity
@Table(name = "users")
public class Users implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "role")           //1.业主   99.物业   100.区域管理  101.总公司   999.超级管理员  
    private int role;

    @Column(name = "reg_data")
    private Timestamp regData = new Timestamp(System.currentTimeMillis());

    @Column(name = "status")
    private int status = 1;                            // 0.注销  1.激活
    
    @Column(name = "residential_id")
    private Long residentialId;

    @Temporal(TemporalType.TIMESTAMP)
    public Timestamp getRegData() {
        return regData;
    }

    public void setRegData(Timestamp regData) {
        this.regData = regData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getResidentialId() {
		return residentialId;
	}

	public void setResidentialId(Long residentialId) {
		this.residentialId = residentialId;
	}

	
}

