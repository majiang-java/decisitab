package com.ifre.service.impl.brms;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.ifre.service.brms.BizObjServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.MyBeanUtils;
import com.ifre.entity.brms.BizObjEntity;
import com.ifre.entity.brms.ObjPropEntity;
@Service("bizObjService")
@Transactional
public class BizObjServiceImpl extends CommonServiceImpl implements BizObjServiceI {

	
	public void addMain(BizObjEntity bizObj,
	        List<ObjPropEntity> objPropList){
			//保存主信息
			this.save(bizObj);
		
			/**保存-对象属性*/
			for(ObjPropEntity objProp:objPropList){
				//外键设置
				objProp.setBizObjId(bizObj.getId());
				this.save(objProp);
			}
	}

	
	public void updateMain(BizObjEntity bizObj,
	        List<ObjPropEntity> objPropList) {
		//保存订单主信息
		this.saveOrUpdate(bizObj);
		
		
		//===================================================================================
		//获取参数
		Object id0 = bizObj.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-对象属性
	    String hql0 = "from ObjPropEntity where 1 = 1 AND bizObjId = ? ";
	    List<ObjPropEntity> objPropOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-对象属性
		for(ObjPropEntity oldE:objPropOldList){
			boolean isUpdate = false;
				for(ObjPropEntity sendE:objPropList){
					//需要更新的明细数据-对象属性
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							this.saveOrUpdate(oldE);
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的明细，前台没有传递过来则是删除-对象属性
		    		super.delete(oldE);
	    		}
	    		
			}
		//3.持久化新增的数据-对象属性
		for(ObjPropEntity objProp:objPropList){
			if(objProp.getId()==null){
				//外键设置
				objProp.setBizObjId(bizObj.getId());
				this.save(objProp);
			}
		}
		
	}

	
	public void delMain(BizObjEntity bizObj) {
		//删除主表信息
		this.delete(bizObj);
		
		//===================================================================================
		//获取参数
		Object id0 = bizObj.getId();
		//===================================================================================
		//删除-对象属性
	    String hql0 = "from ObjPropEntity where 1 = 1 AND bizObjId = ? ";
	    List<ObjPropEntity> objPropOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(objPropOldList);
	}
	
}