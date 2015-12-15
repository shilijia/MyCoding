package com.dayuan.utils;

import javax.servlet.ServletException;
import javax.xml.ws.http.HTTPException;

/**
 * Created by q on 2015/12/7.
 */
public class APIException extends Exception {

        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public APIException(){}

    public APIException(String msg) {
         super(msg);
    }
}
