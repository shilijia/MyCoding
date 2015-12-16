package com.dayuan.utils;

/**
 * Created by q on 2015/12/7.
 */
public class Consts {
	public static final String PHOTO_DIR = "D://";
	public static final String PHOTO_URL = "";
	public static final String USER_PHOTO = "users";
    public static final int USERS_OWNER = 1;  //业主
    public static final int USERS_VISITOR = 2;  //家人
    public static final int USERS_RENTER = 3;  //租客
    public static final int USERS_PROPERTY = 4;  //访客
    
    public static final int USERS_ACTIVE = 1;  //用户账号激活状态
    public static final int USERS_CANCLE = 0;  //用户账号注销或者未激活状态
    
    public static final int USERS_CHECKING = 1;   //用户小区校验中状态
    public static final int USERS_CHECK_SUC = 3;   //用户小区校验中状态
    public static final int USERS_CHECK_FAIL = 2;   //用户小区校验中状态
    public static final int USERS_CANCLE_AUTH_ROOM = 0;   //取消该room用户授权
    
    public static final String UPDATE_USERINFOS_SUCCESS = "更新用户信息成功";
}
