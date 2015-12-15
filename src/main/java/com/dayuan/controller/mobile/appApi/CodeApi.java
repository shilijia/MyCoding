package com.dayuan.controller.mobile.appApi;

import java.sql.Timestamp;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dayuan.entity.Code;
import com.dayuan.repository.CodeRepository;
import com.dayuan.utils.APIException;
import com.dayuan.utils.ClientReceive;
import com.dayuan.utils.Error;
import com.dayuan.utils.Success;
import com.dayuan.utils.Utils;

@Transactional
@RestController
public class CodeApi {
   
	@Resource
	CodeRepository coderepos;
	
	@RequestMapping(value = "/api/validation_code/", method = RequestMethod.GET)
	Map getCode(@RequestParam("telephone") String telephone){
		
		try {
			if(null == telephone || !Utils.isMobile(telephone))
				throw new APIException(Error.TEL_ERROR);
			
			int code_num = Utils.getCode();
			Code code = coderepos.findByTelephone(telephone);
			if(null == code){
				Code c = new Code();
				c.setCodeNum(code_num);
				c.setTelephone(telephone);
				coderepos.save(c);
			}else{
				code.setCodeNum(code_num);
				code.setDataTime(new Timestamp(System.currentTimeMillis()));
				coderepos.save(code);
			}
			
			return new ClientReceive().push("feedback", Success.OP_SUCCESS).successMessage();
		} catch (APIException ex) {
			return ClientReceive.errorMessage(ex.getMessage());
		} catch (Exception e) {
			return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
		}
	}
}
