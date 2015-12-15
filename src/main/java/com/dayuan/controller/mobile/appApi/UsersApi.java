package com.dayuan.controller.mobile.appApi;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dayuan.entity.Building;
import com.dayuan.entity.Code;
import com.dayuan.entity.Photo;
import com.dayuan.entity.Residential;
import com.dayuan.entity.Room;
import com.dayuan.entity.Unit;
import com.dayuan.entity.Users;
import com.dayuan.entity.UsersBridgeResidential;
import com.dayuan.entity.UsersCheckInfos;
import com.dayuan.repository.BuildingRepository;
import com.dayuan.repository.CodeRepository;
import com.dayuan.repository.PhotoRepository;
import com.dayuan.repository.ResidentialRepository;
import com.dayuan.repository.RoomRepository;
import com.dayuan.repository.UnitRepository;
import com.dayuan.repository.UsersBridgeResidentialRepository;
import com.dayuan.repository.UsersCheckInfosRepository;
import com.dayuan.repository.UsersRepository;
import com.dayuan.utils.APIException;
import com.dayuan.utils.ClientReceive;
import com.dayuan.utils.Consts;
import com.dayuan.utils.Error;
import com.dayuan.utils.Success;
import com.dayuan.utils.Utils;


@Transactional
@RestController
@RequestMapping("api/user/")	
public class UsersApi {
	
     @Resource
     UsersRepository ur;
     
     @Resource
     CodeRepository cr;
     
     @Resource
     UsersBridgeResidentialRepository ubrr;
     
     @Resource
     PhotoRepository pr;
     
     @Resource
     CodeRepository codepos;
     
     @Resource
     ResidentialRepository respos;
     
     @Resource
     BuildingRepository buildingpos;
     
     @Resource
     UnitRepository unitpos;
     
     @Resource
     RoomRepository roompos;
     
     @Resource
     UsersCheckInfosRepository ucr;
     
     @RequestMapping(value = "regiester", method = RequestMethod.POST)
     Map userRegiester(@RequestBody Map map){
         try {
        	 Object code = map.get("validation_code");
        	 Object telephone = map.get("telephone");
        	 Object password = map.get("password");
        
             if(null == telephone || !Utils.isMobile(telephone.toString()))
                 throw new APIException(Error.TEL_ERROR);
             
             Code data_code = codepos.findByTelephone(telephone.toString());
           
             if(null == code || data_code == null || 
            		 data_code.getCodeNum() != Integer.parseInt(code.toString())) 
                 throw new APIException(Error.CODE_ERROR);

             if(null == password || password.toString().length() < 6)
                 throw new APIException(Error.LOGIN_ERROR);
             
             Users out_user = null;
             Users user = ur.findByTelephone(telephone.toString());
             if(null != user){
            	 user.setPassword(Utils.shaEncode(password.toString()));
            	 out_user = ur.save(user);
            	 
             }else{
            	 Users u = new Users();
            	 u.setTelephone(telephone.toString());
            	 u.setPassword(Utils.shaEncode(password.toString()));
            	 out_user = ur.save(u);
             }
             if(null != out_user)
                 return new ClientReceive().push("feedback", "").push("result", user).successMessage();
             
             return ClientReceive.errorMessage(Error.REG_FAIL); 
         }catch(APIException ex){
        	 return ClientReceive.errorMessage(ex.getMessage());
         }catch (Exception e){
             return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
         }
     }
     
     @RequestMapping(value = "infos/update/", method = RequestMethod.PUT)
     Map updateUserInfos(@RequestBody Map map){
    	 try {
    		 Object user_id = map.get("user_id");
    		 Object nickname = map.get("nickname");
    		 Object role = map.get("role");
    		 Object complex_id = map.get("complex_id");
    		 
    		 if(null == user_id || null == role || null == complex_id || null == nickname || null == complex_id)
    			 throw new APIException(Error.MISS_PARAM);
    		 
    		 Long u_id = Long.parseLong(user_id.toString());
    		 int r = Integer.parseInt(role.toString());
    		 Long c_id = Long.parseLong(complex_id.toString());
    		 
    		 Users user = ur.findOne(u_id);
    		 if(user == null)
    			 throw new APIException(Error.NOT_USER);
    		 
    		 user.setNickname(nickname.toString());
    		 user.setRole(r);
    		 user.setResidentialId(c_id);
    		 Users u = ur.save(user);
    		 
    		 if(u == null)
    	         throw new APIException(Error.UPDATE_USER_FAIL);
             
    		 //小区录入业主信息时：需要在添加UsersBridgeResidential表： 小区ID，业主电话，身份默认为业主
    		 /*UsersBridgeResidential ub = ubrr.findByTelephoneAndResidentialId(u.getTelephone(), c_id);
    		 if(null == ub){
    			 UsersBridgeResidential br = new UsersBridgeResidential();
    			 br.setResidentialId(c_id);
    			 br.setTelephone(u.getTelephone());
    			 br.setUserId(u_id);
    			 br.setUserRole(r);
    			 ubrr.save(br);
    		 }else{
    			 ub.setUserId(u_id);
    			 ubrr.save(ub);
    		 }*/
    		 
    		 return new ClientReceive().push("feedback", Consts.UPDATE_USERINFOS_SUCCESS).successMessage();
    	     
		 } catch (APIException ex) {
			 return ClientReceive.errorMessage(ex.getMessage());
		 } catch (Exception e) {
			 return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
		 }
     }
     
     @SuppressWarnings("unchecked")
	 @RequestMapping(value = "login", method = RequestMethod.POST)
     Map login(@RequestBody Map map){
    	 try{
    		 Object telephone = map.get("telephone");
    		 Object password = map.get("password");
    		 
    		 if(null == telephone || null == password)
    			 throw new APIException(Error.MISS_PARAM);
    		 
    		 Users user = ur.findByTelephone(telephone.toString());
    		 
    		 if(null == user)
    			 throw new APIException(Error.NOT_USER);
    		 
    		 if(!password.equals(user.getPassword()))
    		     throw new APIException(Error.PASS_ERROR);
    		 
    		 //UsersBridgeResidential ubr = ubrr.findByTelephoneAndResidentialId(telephone.toString(), user.getResidentialId());
    		 
    		 Photo ph = pr.findByTargetIdAndTargetName(user.getId(), Consts.USER_PHOTO);
    		 
    		 
			 Map result = new HashMap();
    		 result.put("avatar_url", ph == null? null:ph.getPurl());
    		 result.put("nickname", user.getNickname());
    		 result.put("complex_id", user.getResidentialId());
    		 result.put("register_role", user.getRole());
    		 //result.put("verified_role", ubr == null?null:ubr.getUserRole());
    		 result.put("user_id", user.getId());
    		 
    		 return new ClientReceive().push("feedback", "").push("result", result).successMessage();
    		 
	     } catch (APIException ex) {
			 return ClientReceive.errorMessage(ex.getMessage());
		 } catch (Exception e) {
			 return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
		 }
     }
     
     @RequestMapping(value = "update/password/", method = RequestMethod.PUT)
     Map updatePassword(@RequestBody Map map){
         try {
        	 Object user_id = map.get("user_id");
        	 Object old_password = map.get("old_password");
        	 Object new_password = map.get("new_password");
        	 
        	 if(null == user_id || null == old_password || null == new_password) 
                  throw  new APIException(Error.MISS_PARAM);
               
             Users user = ur.findOne(Long.parseLong(user_id.toString()));
             if(null == user)
            	 throw  new APIException(Error.NOT_USER);
             
             if(!Utils.shaEncode(old_password.toString()).equals(user.getPassword()))
            	 throw new APIException(Error.PASS_ERROR);
             
             user.setPassword(Utils.shaEncode(new_password.toString()));
             Users u = ur.save(user);
             if(null == u)
            	 throw new APIException(Error.UPDATE_USER_PASS_FAIL);
             
             return new ClientReceive().push("feedback", Success.OP_SUCCESS).successMessage();
             
         }catch(APIException ex){
        	 return ClientReceive.errorMessage(ex.getMessage());
         }catch (Exception e){
             return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
         }
     }
     
     @RequestMapping(value = "find/password/", method = RequestMethod.PUT)
     Map findPassword(@RequestBody Map map){
         try {
        	 Object telephone = map.get("telephone");
        	 Object validation_code = map.get("validation_code");
        	 Object new_password = map.get("new_password");
        	 
        	 if(null == telephone || null == validation_code || null == new_password) 
                  throw  new APIException(Error.MISS_PARAM);
               
             Users user = ur.findByTelephone(telephone.toString());
             Code code = codepos.findByTelephone(telephone.toString());
             
             if(null == user)
            	 throw  new APIException(Error.NOT_USER);
             
             if((validation_code.toString()).equals(code))
            	 throw new APIException(Error.CODE_ERROR);
             
             user.setPassword(Utils.shaEncode(new_password.toString()));
             Users u = ur.save(user);
             
             if(null == u)
            	 throw new APIException(Error.UPDATE_USER_PASS_FAIL);
             
             return new ClientReceive().push("feedback", Success.OP_SUCCESS).successMessage();
             
         }catch(APIException ex){
        	 return ClientReceive.errorMessage(ex.getMessage());
         }catch (Exception e){
             return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
         }
     }
     
    //TODO 不知道查个人所有小区还是当前注册小区的信息，暂时查询当前注册小区
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "infos", method = RequestMethod.GET)
     Map getUserInfos(@RequestParam("telephone") String telephone, 
    		 @RequestParam("complex_id") long complex_id){
    	 try{
    		 Users user = ur.findByTelephone(telephone);
    		 if(null == user )
    			 throw new APIException(Error.NOT_USER);
    		 
    		 if(user.getStatus() == 0){
    			 Map map = new HashMap();
    			 map.put("id", user.getId());
    			 map.put("telephone", telephone);
    			 map.put("real_name", null);
    			 map.put("activated", false);
    			 map.put("block", null);
    			 return new ClientReceive().push("result", map).successMessage();
    		 }else{
    			 
    			 List<UsersBridgeResidential> ub = ubrr.findByTelephoneAndResidentialId(telephone, complex_id);
    			 if(null == ub)
    				 throw new APIException(Error.NOT_USER_RESIDENTIAL);
    	
    			 Residential residential = respos.findOne(complex_id);
    			 Map map = new HashMap();
    			 map.put("id", user.getId());
    			 map.put("telephone", user.getTelephone());
    			 map.put("real_name", ub.get(0).getRealName());
    			 map.put("activated", true);
    			 List list = new ArrayList();
    			
    			 for(UsersBridgeResidential ubt:ub){
    				 Building building = buildingpos.findOne(ubt.getBuildingId());
        			 Unit unit = unitpos.findOne(ubt.getUnitId());
        			 Room room = roompos.findOne(ubt.getRoomId());
    				 Map block = new HashMap();
        			 block.put("complex_name", residential.getName());
        			 block.put("complex_id", residential.getId());
        			 block.put("building", building.getName());
        			 block.put("unit", unit.getName());
        			 block.put("room", room.getRoomNum());
        			 block.put("room_id", room.getId());
        			 block.put("unit_id", unit.getId());
        			 block.put("building_id", building.getId());
        			 list.add(block);
    			 }
    			 map.put("block", list);
    			 return new ClientReceive().push("result", map).successMessage();
    		 }
    	 }catch(APIException ex){
        	 return ClientReceive.errorMessage(ex.getMessage());
         }catch (Exception e){
             return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
         }
     }
    
    //TODO 多个小区的问题，业主验证时，是验证所有的还是只验证当前小区的？？？？  暂时按验证当前小区的
    @SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "update/oauth", method = RequestMethod.PUT)
    Map updateOauth(@RequestBody Map map){
    	try {
    		Object telephone = map.get("telephone");
        	Object real_name = map.get("real_name");
        	Object building_id = map.get("building_id");
        	Object unit_id = map.get("unit_id");
        	Object room_id = map.get("room_id");
        	
        	if(null == telephone || null == real_name || null == building_id 
        			|| null == unit_id || null == room_id)
        		throw new APIException(Error.MISS_PARAM);
        	
        	Users user = ur.findByTelephone(telephone.toString());
        	if(null == user)
        		throw new APIException(Error.NOT_USER);
        	
        	Long complex_id = user.getResidentialId();
        	
        	List<UsersBridgeResidential> urList = ubrr.findByTelephoneAndResidentialId(telephone.toString(), complex_id);
        	
        	if(null == urList){
        		//TODO 该用户当前所选小区物管处发送验证消息
        		UsersCheckInfos uc = new UsersCheckInfos();
        		uc.setRealName(real_name.toString());
        		uc.setResidentialId(complex_id);
        		uc.setBuildingId(Long.parseLong(building_id.toString()));
        		uc.setUnitId(Long.parseLong(unit_id.toString()));
        		uc.setRoomId(Long.parseLong(room_id.toString()));
        		ucr.save(uc);
        		ubrr.updateStatus(Consts.USERS_CHECKING, new Timestamp(System.currentTimeMillis()), telephone.toString(), complex_id);
        		return new ClientReceive().push("feedback", Success.USERS_CHECKING).successMessage();
        	}else{
        		ubrr.updateStatus(Consts.USERS_CHECK_SUC, new Timestamp(System.currentTimeMillis()), telephone.toString(), complex_id);
        		return new ClientReceive().push("feedback", Success.USERS_CHECK_SUCCESS).successMessage();
        	}
		} catch(APIException ex){
       	    return ClientReceive.errorMessage(ex.getMessage());
        } catch (Exception e){
            return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
        }
    }
    
    @RequestMapping(value = "oauths/list/", method = RequestMethod.GET)
    Map oauthList(@RequestParam(value = "user_id", defaultValue = "0") long user_id, 
   		 @RequestParam(value = "complex_id", defaultValue = "0") long complex_id){
    	 try{
    		 if(user_id == 0 || complex_id == 0)
    			 throw new APIException(Error.MISS_PARAM);
    		 List<UsersBridgeResidential> urList = ubrr.findByUserIdAndResidentialId(user_id, complex_id);
    		 
    		 if(null == urList)
    			 throw new APIException(Error.NOT_USER_RESIDENTIAL);
    		 return new ClientReceive().push("result", urList).successMessage();
    		 
    	 }catch(APIException ex){
        	    return ClientReceive.errorMessage(ex.getMessage());
         } catch (Exception e){
             return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
         }
    }
    
    
    @RequestMapping(value = "oauths/cancle/", method = RequestMethod.PUT)
    Map oauthCanle(@RequestBody Map map){
    	 try{
    		 Object id = map.get("id");
    		 
    		 if(null == id)
    			 throw new APIException(Error.MISS_PARAM);
    		 
    		 UsersBridgeResidential ub = ubrr.findOne(Long.parseLong(id.toString()));
    		 
    		 if(null == ub)
    			 throw new APIException(Error.NOT_USER_RESIDENTIAL);
    		 
    		 ub.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    		 ub.setStatus(Consts.USERS_CANCLE_AUTH_ROOM);
    		 UsersBridgeResidential u = ubrr.save(ub);
    		 if(null == u)
    			 throw new APIException(Error.ROOM_CANCLE_AUTH_FIAL);
    		 
    		 return new ClientReceive().push("feedback", Success.ROOM_CANCLE_AUTH).successMessage();
    		 
    	 }catch(APIException ex){
        	    return ClientReceive.errorMessage(ex.getMessage());
         } catch (Exception e){
             return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
         }
    }
    
    @RequestMapping(value = "oauth/rooms/list/", method = RequestMethod.GET)
    Map oauthForOthers(@RequestParam(value = "user_id", defaultValue = "0") long user_id, 
      		 @RequestParam(value = "complex_id", defaultValue = "0") long complex_id){
    	try {
			if( user_id == 0 || complex_id == 0 )
				throw new APIException(Error.MISS_PARAM);
			
			List<UsersBridgeResidential> list = ubrr.findByUserIdAndResidentialIdAndStatus(user_id, complex_id, Consts.USERS_CHECK_SUC);
			if( null == list )
				throw new APIException(Error.NOT_AUTH);
			
			return new ClientReceive().push("result", list).successMessage();
			
		} catch(APIException ex){
    	    return ClientReceive.errorMessage(ex.getMessage());
        } catch (Exception e){
            return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
        }
    }
    
    /**
     * 1. 该用户之前已有授权。
     * 2. 该用户之前未有授权
     */
    @RequestMapping(value = "add/oauth/rooms/", method = RequestMethod.POST)
    Map oauthForOtherByRoom(@RequestBody Map map){
    	try {
    		Object real_name = map.get("real_name");
    		Object member_type = map.get("member_type");
    		Object room_id = map.get("room_id");
    		Object valid_from = map.get("valid_from");
    		Object valid_until = map.get("valid_until");
    		Object telephone = map.get("telephone");
    		Object user_id = map.get("user_id");
    		
    		
			if( null == real_name || null == member_type || null == room_id || 
					null == valid_from || null == valid_until || null == telephone 
					|| null == user_id)
				throw new APIException(Error.MISS_PARAM);
			
			Users user = ur.getOne(Long.parseLong(user_id.toString()));
			if(null == user)
				throw new APIException(Error.NOW_NOT_USER);
			
			String owner_telephone = user.getTelephone();
			UsersBridgeResidential ownerub = ubrr.findByTelephoneAndRoomId(owner_telephone, Long.parseLong(room_id.toString()));
			
			if(null == ownerub || ownerub.getStatus() != Consts.USERS_CHECK_SUC)
				throw new APIException(Error.ROOM_NOT_USER_AUTH);
			
			UsersBridgeResidential ub = ubrr.findByTelephoneAndRoomId(telephone.toString(), Long.parseLong(room_id.toString()));
			
			//如果该手机未被授权过
            if(null != ub){
            	if(real_name.toString().equals(ub.getRealName()))
            		throw new APIException(Error.REAL_NAME);
            	ub.setUserRole(Integer.parseInt(member_type.toString()));
            	ub.setSendUserId(user.getId());
            	ub.setBuildingId(ownerub.getBuildingId());
            	ub.setResidentialId(ownerub.getResidentialId());
            	ub.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            	ub.setStartTime(Utils.strToTimestamp(valid_from.toString()));
            	ub.setEndTime(Utils.strToTimestamp(valid_until.toString()));
            	ub.setTelephone(telephone.toString());
            	ub.setRoomId(Long.parseLong(room_id.toString()));
            	
            	if(null == ubrr.save(ub))
            		throw new APIException(Error.AUTH_TO_FAIL);
            	return new ClientReceive().push("feedback", Success.OP_SUCCESS).successMessage();
            }else{
            	UsersBridgeResidential ubb = new UsersBridgeResidential();
            	ubb.setUserRole(Integer.parseInt(member_type.toString()));
            	ubb.setSendUserId(user.getId());
            	ubb.setBuildingId(ownerub.getBuildingId());
            	ubb.setResidentialId(ownerub.getResidentialId());
            	ubb.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            	ubb.setStartTime(Utils.strToTimestamp(valid_from.toString()));
            	ubb.setEndTime(Utils.strToTimestamp(valid_until.toString()));
            	ubb.setTelephone(telephone.toString());
            	ubb.setRoomId(Long.parseLong(room_id.toString()));
            	
            	if(null == ubrr.save(ub))
            		throw new APIException(Error.AUTH_TO_FAIL);
            	return new ClientReceive().push("feedback", Success.OP_SUCCESS).successMessage();
            }
			
		} catch(APIException ex){
    	    return ClientReceive.errorMessage(ex.getMessage());
        } catch (Exception e){
            return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
        }
    }
    
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "oauth/rooms/send/list/", method = RequestMethod.GET)
    Map oauthSendList(@RequestParam(value = "complex_id", defaultValue = "0") long complex_id, 
    		 @RequestParam(value = "user_id", defaultValue = "0") long user_id){
    	try {
			if( complex_id == 0 || user_id ==0)
				throw new APIException(Error.MISS_PARAM);
			
			List<UsersBridgeResidential> list = ubrr.findBySendUserIdAndResidentialId(user_id, complex_id);
			
			if( null == list )
				throw new APIException(Error.ROOM_TO_AUTH);
			
			List result = new ArrayList();
			for(UsersBridgeResidential ubr:list){
				Map map = new HashMap();
				Users user = ur.findOne(ubr.getUserId());
				map.put("id", ubr.getId());
				map.put("real_name", ubr.getRealName());
				map.put("type", ubr.getUserRole());
				map.put("valid_from", ubr.getStartTime());
				map.put("valid_unitl", ubr.getEndTime());
				Building b = buildingpos.getOne(ubr.getBuildingId());
				Unit u = unitpos.findOne(ubr.getUnitId());
				Room r = roompos.findOne(ubr.getRoomId());
				map.put("building_name", b == null?"":b.getName());
				map.put("unit_name", u == null?"":u.getName());
				map.put("room_name", r == null?"":r.getRoomNum());
				Photo p = pr.findByTargetIdAndTargetName(user.getId(), Consts.USER_PHOTO);
				if(null != p)
					map.put("avatar_url", p.getPurl());
				map.put("avatar_url", "");
				result.add(map);
			}
			return new ClientReceive().push("result", list).successMessage();
			
		} catch(APIException ex){
    	    return ClientReceive.errorMessage(ex.getMessage());
        } catch (Exception e){
            return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
        }
    }
    
    
}
