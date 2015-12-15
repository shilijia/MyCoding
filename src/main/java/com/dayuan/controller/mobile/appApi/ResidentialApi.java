package com.dayuan.controller.mobile.appApi;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dayuan.entity.Building;
import com.dayuan.entity.Equipment;
import com.dayuan.entity.Residential;
import com.dayuan.entity.Room;
import com.dayuan.entity.Unit;
import com.dayuan.entity.Users;
import com.dayuan.entity.UsersBridgeEquipment;
import com.dayuan.entity.UsersBridgeResidential;
import com.dayuan.repository.BuildingRepository;
import com.dayuan.repository.EquipmentRepository;
import com.dayuan.repository.ResidentialRepository;
import com.dayuan.repository.RoomRepository;
import com.dayuan.repository.UnitRepository;
import com.dayuan.repository.UsersBridgeEquipmentRepository;
import com.dayuan.repository.UsersBridgeResidentialRepository;
import com.dayuan.repository.UsersRepository;
import com.dayuan.utils.APIException;
import com.dayuan.utils.ClientReceive;
import com.dayuan.utils.Error;

import com.dayuan.utils.Utils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by q on 2015/12/12.
 */
@Transactional
@RestController
@RequestMapping(value = "api/residential_quarter/")
public class ResidentialApi {

    @Resource
    ResidentialRepository rdr;
    
    @Resource
    UsersRepository ur;
    
    @Resource
    UsersBridgeEquipmentRepository ube;
    
    @Resource
    EquipmentRepository er;
    
    @Resource
    BuildingRepository br;
    
    @Resource
    UnitRepository unitr;
    
    @Resource
    RoomRepository roompos;
    

    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list_coordinates", method = RequestMethod.GET)
    Map getNear(@RequestParam(value = "latitude", defaultValue = "0") double latitude, 
    		@RequestParam(value = "longitude", defaultValue = "0") double longitude){
        try{
            if(latitude == 0 || longitude == 0)
            	throw new APIException(Error.MISS_PARAM);
            
            List<Residential> list = rdr.findNears(latitude, longitude);
            List result = null;
            if(null != list){
            	result = new ArrayList();
            	for(Residential r: list){
            		double distance = Utils.getDistance(latitude, longitude, r.getLatitude(), r.getLongitude());
                	Map map = new HashMap();
                	map.put("complex_id", r.getId());
                	map.put("complex_name", r.getName());
                	map.put("address", r.getAddress());
                	map.put("distance", distance);
                	result.add(map);
                }
            }
            return new ClientReceive().push("result", result).successMessage();
        }catch (APIException ex) {
			 return ClientReceive.errorMessage(ex.getMessage());
		}catch (Exception e){
            return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
        }
    }

    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list_complex", method = RequestMethod.GET)
    Map getByResidentialName(@RequestParam("complex_name") String complex_name,
    		@RequestParam(value = "start", defaultValue = "0") int start, 
    		@RequestParam(value = "limit", defaultValue = "10") int limit){
        try{
        	 if(null == complex_name || "" == complex_name)
          	      throw new APIException(Error.MISS_PARAM);
             
             Pageable pageable = new PageRequest(start, limit);
             List<Residential> list = rdr.findByResidentialName(complex_name, pageable);
             List result = null;
             if(null != list){
             	result = new ArrayList();
             	for(Residential r: list){
             		//double distance = Utils.getDistance(latitude, longitude, r.getLatitude(), r.getLongitude());
                 	Map map = new HashMap();
                 	map.put("complex_id", r.getId());
                 	map.put("complex_name", r.getName());
                 	map.put("address", r.getAddress());
                 	//map.put("distance", distance);
                 	result.add(map);
                 }
             }
             return new ClientReceive().push("result", result).successMessage();
        }catch (APIException ex) {
			 return ClientReceive.errorMessage(ex.getMessage());
		}catch (Exception e){
           return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
       }
    }

    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list_city", method = RequestMethod.GET)
    Map getByResidentialCity(@RequestParam(value = "city_id", defaultValue = "0") long city_id, 
    		@RequestParam(value = "start", defaultValue = "0") int start, 
    		@RequestParam(value = "limit", defaultValue = "10") int limit){
        try{
           if(city_id == 0)
        	   throw new APIException(Error.MISS_PARAM);
           
           Pageable pageable = new PageRequest(start, limit);
           List<Residential> list = rdr.findByCityId(new Long(city_id), pageable);
           List result = null;
           if(null != list){
           	result = new ArrayList();
           	for(Residential r: list){
           		//double distance = Utils.getDistance(latitude, longitude, r.getLatitude(), r.getLongitude());
               	Map map = new HashMap();
               	map.put("complex_id", r.getId());
               	map.put("complex_name", r.getName());
               	map.put("address", r.getAddress());
               	//map.put("distance", distance);
               	result.add(map);
               }
           }
           return new ClientReceive().push("result", result).successMessage();
        }catch (APIException ex) {
			 return ClientReceive.errorMessage(ex.getMessage());
		}catch (Exception e){
            return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
        }
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "acs", method = RequestMethod.GET)
    Map getUsersAuths(@RequestParam(value = "user_id", defaultValue = "0") long user_id){
    	try {
			if(user_id == 0)
				throw new APIException(Error.MISS_PARAM);
			
			Users user = ur.findOne(user_id);
			
			if(null == user)
				throw new APIException(Error.NOT_USER);
			
			List<UsersBridgeEquipment> ueList = ube.findByReceiveUsersId(user_id);
			List result = null;
			if( null != ueList){
				result = new ArrayList();
				for(UsersBridgeEquipment ue:ueList){
					Map map = new HashMap();
					Long equipment_id = ue.getEquipmentId();
					Equipment e = er.findOne(equipment_id);
					
					map.put("dev_mac", e.getMac());
					map.put("customer_code", e.getCustomCode());
					map.put("dev_pwd", e.getPassword());
					map.put("dev_name", e.getName());
					map.put("valid_from", ue.getStartTime());
					map.put("valid_unitl", ue.getEndTime());
					
					result.add(map);
				}
			}
			return new ClientReceive().push("result", result).successMessage();
			
		} catch (APIException ex) {
			 return ClientReceive.errorMessage(ex.getMessage());
		} catch (Exception e){
             return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
       }
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "building/list/", method = RequestMethod.GET)
    Map getBuilding(@RequestParam(value = "complex_id", defaultValue = "0") long complex_id){
    	try {
			if(complex_id == 0)
				throw new APIException(Error.MISS_PARAM);
			
			List<Building> buList = br.findByResidentialId(complex_id);
			List result = null;
			if( null != buList){
				result = new ArrayList();
				for(Building bu:buList){
					Map map = new HashMap();
					map.put("building_id", bu.getId());
					map.put("building_name", bu.getName());
					result.add(map);
				}
			}
			return new ClientReceive().push("result", result).successMessage();
			
		} catch (APIException ex) {
			 return ClientReceive.errorMessage(ex.getMessage());
		} catch (Exception e){
             return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
       }
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "building/unit/list/", method = RequestMethod.GET)
    Map getUnits(@RequestParam(value = "building_id", defaultValue = "0") long building_id){
    	try {
			if(building_id == 0)
				throw new APIException(Error.MISS_PARAM);
			
			List<Unit> unList = unitr.findByBuilingId(building_id);
			List result = null;
			if( null != unList){
				result = new ArrayList();
				for(Unit u:unList){
					Map map = new HashMap();
					map.put("unit_id", u.getId());
					map.put("unit_name", u.getName());
					result.add(map);
				}
			}
			return new ClientReceive().push("result", result).successMessage();
			
		} catch (APIException ex) {
			 return ClientReceive.errorMessage(ex.getMessage());
		} catch (Exception e){
             return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
       }
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "building/unit/room/list/", method = RequestMethod.GET)
    Map getRooms(@RequestParam(value = "unit_id", defaultValue = "0") long unit_id){
    	try {
			if(unit_id == 0)
				throw new APIException(Error.MISS_PARAM);
			
			List<Room> rlist = roompos.findByUnitId(unit_id);
			List result = null;
			if( null != rlist){
				result = new ArrayList();
				for(Room r:rlist){
					Map map = new HashMap();
					map.put("room_id", r.getId());
					map.put("room_name", r.getRoomNum());
					result.add(map);
				}
			}
			return new ClientReceive().push("result", result).successMessage();
			
		} catch (APIException ex) {
			 return ClientReceive.errorMessage(ex.getMessage());
		} catch (Exception e){
             return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
       }
    }
    
    @RequestMapping(value = "infos", method = RequestMethod.GET)
    Map ResidentialInfos(@RequestParam(value = "complex_id", defaultValue = "0") long complex_id){
    	try {
			if(complex_id == 0)
				throw new APIException(Error.MISS_PARAM);
			
			Residential residential = rdr.findOne(complex_id);
			int count = ur.countUsersByResidential(complex_id);
			
			if(null == residential)
				throw new APIException(Error.SYSTEM_ERROR);
			
			return new ClientReceive().push("complex_name", residential.getName())
					.push("property_company_name", residential.getManagerName())
					.push("complex_user_count", count).successMessage();
			
		} catch (APIException ex) {
			 return ClientReceive.errorMessage(ex.getMessage());
		} catch (Exception e){
            return ClientReceive.errorMessage(Error.SYSTEM_ERROR);
        }
    }
}
