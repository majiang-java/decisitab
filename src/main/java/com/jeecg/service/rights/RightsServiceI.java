package com.jeecg.service.rights;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.jeecg.entity.rights.RightsEntity;

public interface RightsServiceI extends CommonService{
	
 	public void delete(RightsEntity entity) throws Exception;
 	
 	public Serializable save(RightsEntity entity) throws Exception;
 	
 	public void saveOrUpdate(RightsEntity entity) throws Exception;
 	
}
