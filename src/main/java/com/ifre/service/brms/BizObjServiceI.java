package com.ifre.service.brms;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;
import com.ifre.entity.brms.BizObjEntity;
import com.ifre.entity.brms.ObjPropEntity;

public interface BizObjServiceI extends CommonService{

	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(BizObjEntity bizObj,
	        List<ObjPropEntity> objPropList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(BizObjEntity bizObj,
	        List<ObjPropEntity> objPropList);
	public void delMain (BizObjEntity bizObj);
}
