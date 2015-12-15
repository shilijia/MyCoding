package com.dayuan.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by q on 2015/12/7.
 */

@SuppressWarnings("unchecked")
public class ClientReceive{
      static final int EXP_INTERVAL = 3;
      Map map = new HashMap();
      
	public Map successMessage(){
           this.map.put("success", true);
           this.map.put("timestamp", System.currentTimeMillis());
           this.map.put("expired_interval", EXP_INTERVAL);
           return this.map;
      }

      public ClientReceive push(String key, Object obj){
             this.map.put(key, obj);
             return this;
      }

    
	public static Map errorMessage(String msg){
          Map errorMap = new HashMap();
          errorMap.put("success", false);
          errorMap.put("timestamp", System.currentTimeMillis());
          errorMap.put("expired_interval", EXP_INTERVAL);
          if(null != msg ){
              errorMap.put("feedback", msg);
          }else {
              errorMap.put("feedback", "");
          }
          return errorMap;
      }
}
