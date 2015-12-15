package com.dayuan.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 业主信息由物业录入
 */
@Entity
@Table(name = "users_bridge_residential")
public class UsersBridgeResidential implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "residential_id")
    private Long residentialId;
    
    @Column(name = "user_role")
    private int userRole;         //
    
    @Column(name = "telephone")
    private String telephone;
    
    @Column(name = "building_id")
    private Long buildingId;
    
    @Column(name = "unit_id")
    private Long unitId;
    
    @Column(name = "room_id")
    private Long roomId;
    
    @Column(name = "real_name")
    private String realName;
    
    @Column(name = "status")
    private int status = 0;          //验证用户账号状态
    
    @Column(name = "status_des", length = 200)
    private String statusDes;
    
    @Column(name = "update_time")
    private Timestamp updateTime = new Timestamp(System.currentTimeMillis());
    
    @Column(name = "send_user_id")
    private Long sendUserId;
    
    @Column(name = "start_time")
    private Timestamp startTime;
    
    @Column(name = "end_time")
    private Timestamp endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getResidentialId() {
        return residentialId;
    }

    public void setResidentialId(Long residentialId) {
        this.residentialId = residentialId;
    }

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusDes() {
		return statusDes;
	}

	public void setStatusDes(String statusDes) {
		this.statusDes = statusDes;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Long getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(Long sendUserId) {
		this.sendUserId = sendUserId;
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
}
