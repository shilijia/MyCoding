package com.dayuan.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dayuan.entity.Residential;
import com.dayuan.repository.ResidentialRepositoryCustom;

public class ResidentialRepositoryImpl implements ResidentialRepositoryCustom{
    
	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<Residential> findNears(double latitude, double longitude) {
		
		double r = 6371;//地球半径千米
		double dis = 2;//2千米距离
		double dlng =  2*Math.asin(Math.sin(dis/(2*r))/Math.cos(latitude*Math.PI/180));
		dlng = dlng*180/Math.PI;//角度转为弧度
		double dlat = dis/r;
		dlat = dlat*180/Math.PI;		
		double minlat =latitude-dlat;
		double maxlat = latitude+dlat;
		double minlng = longitude -dlng;
		double maxlng = longitude + dlng;
		
		String hql = "from Residential r where r.longitude>= ?1 and r.longitude =< ?2 and r.latitude>=?3 r.latitude=<?4";
		Query query = em.createQuery(hql, Residential.class);
		query.setParameter(1, minlng);
		query.setParameter(2, maxlng);
		query.setParameter(3, minlat);
		query.setParameter(4, maxlat);
		query.setFirstResult(0).setMaxResults(10);
		List<Residential> list = query.getResultList();
		return list;
	}
    
	
}
