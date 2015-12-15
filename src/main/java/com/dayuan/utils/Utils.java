package com.dayuan.utils;

import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hp on 2015-12-8.
 */
public class Utils {
	private static double EARTH_RADIUS = 6378.137;
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean result = false;
        p = Pattern.compile("^[1][3,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        result = m.matches();
        return result;
    }

    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    
    private static double rad(double d){
       return d * Math.PI / 180.0;
    }
    
    public static double getDistance(double latA, double lonA, double latB, double lonB){
    	  double distance=0;
    	  double radLat=rad(latA)-rad(latB);
    	  double radLon=rad(lonA)-rad(lonB);
    	  distance=2 * Math.asin(Math.sqrt(Math.pow(Math.sin(radLat/2),2) +
    	       Math.cos(rad(latA))*Math.cos(rad(latB))*Math.pow(Math.sin(radLon/2),2)));
    	  distance=distance*EARTH_RADIUS;
    	  distance=Math.round(distance*10000);
    	  distance=distance/10000;
    	  return distance;
    }
    
    public static int getCode(){
    	return (int)(Math.random()*9000+1000);
    }
    
    public static Timestamp strToTimestamp(String str){
    	Timestamp time = new Timestamp(System.currentTimeMillis());
    	if( null != str && "" != str)
    		return Timestamp.valueOf(str);
    	return time;
    }
}
