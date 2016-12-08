package com.ifre.service.impl.brms;

import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.entity.ruleengin.BrmsConditionDetailEntity;
import com.ifre.entity.ruleengin.BrmsRuleConditionEntity;
import com.ifre.entity.ruleengin.BrmsRuleTableEntity;
import com.ifre.exception.IfreException;
import com.ifre.form.api.Model;
import com.ifre.form.api.ModelElement;
import com.ifre.form.api.ModelElementItem;
import com.ifre.service.brms.RuleProdServiceI;


@Service("ruleProdService")
@Transactional
public class RuleProdServiceImpl extends CommonServiceImpl implements RuleProdServiceI {
	
	/**
     * 根据组织ID查询决策产品
     * @author 王传圣
     * @param  String orgId 组织ID
     * @return List<RuleProdEntity> 决策产品
     * @throws IfreException 
     */  
	public List<RuleProdEntity> findRuleProdEntityByOrgId(String orgId) throws IfreException {
		
		List<RuleProdEntity> result = null;
		
		try {
			StringBuffer hql = new StringBuffer();
			hql.append(" from RuleProdEntity where orgId = ? ");
			result = this.findHql(hql.toString(),new Object[]{orgId});
		} catch (Exception ex) {
			throw new IfreException(100000,"根据组织ID查询决策产品异常",ex); 
		}
		
		return result;
	}

	@Override
	public void updateProductStatus(String prodId, String status) {
		
		String sql = "update brms_rule_prod set status = ? where id = ?";
		executeSql(sql, new Object[]{status,prodId});
	}

	@Override
	public Model getModelByProdId(String prodId) throws IfreException {
		Model model = new Model();
		model.setProdId(prodId);
		//第一步根据prodId获取对应的所有决策表信息
		String hql = "from BrmsRuleTableEntity where prodId = ?  order by salience desc";
		List<BrmsRuleTableEntity> propList = this.findHql(hql,prodId);
		if(propList != null && propList.size() > 0){
			String hqlcondition = "from BrmsRuleConditionEntity where ruleTableId = ?";
			List<BrmsRuleConditionEntity> conditionList = this.findHql(hqlcondition, propList.get(0).getId());
			try{
				List<List<BrmsConditionDetailEntity>> conditionTotalList = new ArrayList<List<BrmsConditionDetailEntity>>();
				for (int i = 0; i < conditionList.size(); i++) {
					BrmsRuleConditionEntity condition = conditionList.get(i);
					String hqlConditionDetails = "from BrmsConditionDetailEntity where condId = ? and ruleTableId = ? ";
					List<BrmsConditionDetailEntity> conditionDetails = this.findHql(hqlConditionDetails,condition.getId(),condition.getRuleTableId());
					conditionTotalList.add(conditionDetails);
				}
				String[][] total = new String[conditionTotalList.get(0).size()][conditionList.size()];
				//获取变量信息
				for (int i = 0; i < conditionTotalList.size(); i++) {
					for (int j = 0; j < conditionTotalList.get(i).size(); j++) {
						total[j][i] = conditionTotalList.get(i).get(j).getCondValue();
					}
				}
				
				//根据变量名排序保证相同变量名相邻，作用是保存时相同变量的属性不一定是相邻的
				String[] temps;
				for (int row = 0; row < total.length; row++) {
					for (int rowCompare = row+1; rowCompare < total.length; rowCompare++) {
						if(total[row][3] != null&& total[rowCompare][3] != null && total[row][3].compareTo(total[rowCompare][3]) > 0){
							//列交换
							temps = total[rowCompare];
							total[rowCompare] = total[row];
							total[row] = temps;
						}
					}
				}
				List<ModelElement> modelElement = new ArrayList<ModelElement>();
				ModelElement element;
				List<ModelElementItem> modelElementItem = null;
				ModelElementItem item;
				String temp = "";
				for (int row = 0; row < total.length; row++) {
					if(temp != null && total[row][3] != null && !temp.equals(total[row][3])){
						element = new ModelElement();
						element.setName(total[row][3]);
						element.setValue(total[row][2]);
						modelElementItem = new ArrayList<ModelElementItem>();
						item = new ModelElementItem();
						item.setCodeValue(total[row][4]);
						item.setValue(total[row][1]);
						modelElementItem.add(item);
						element.setModelElementItem(modelElementItem);
						modelElement.add(element);
						temp = total[row][3];
					}else if(total[row][3] != null && modelElementItem != null){
						item = new ModelElementItem();
						item.setCodeValue(total[row][4]);
						item.setValue(total[row][1]);
						modelElementItem.add(item);
					}
				}
				if(total.length > 0){
					model.setModelType(total[0][5]);
				}
				model.setModelElement(modelElement);
				System.out.println(total);
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	        }
		}else{
			throw new IfreException("未创建模型");
		}
		return model;
	}

	@Override
	public List<RuleProdEntity> findRuleProdList(String orgCode) throws IfreException {
		try {
			String status = "3";
			String hql;
			if(!"A01".equals(orgCode)){
				hql = " from RuleProdEntity where status = ? AND rightStatus = 1   AND orgCode = ? ";
				return this.findHql(hql, status,orgCode);
			}else{
				hql = " from RuleProdEntity where status = ? AND rightStatus = 1  ";
				return this.findHql(hql, status);
			}
		} catch (Exception e) {
			throw new IfreException(100000, "获取产品列表信息异常", e);
		}
	}
	
	@Override
	public List<RuleProdEntity> findRuleProdList(String orgCode,boolean isDuressFilter) throws IfreException {
		try {
			String status = "3";
			String hql;
			if(isDuressFilter || !"A01".equals(orgCode)){
				hql = " from RuleProdEntity where status = ? AND rightStatus = 1   AND orgCode = ? ";
				return this.findHql(hql, status,orgCode);
			}else{
				hql = " from RuleProdEntity where status = ? AND rightStatus = 1  ";
				return this.findHql(hql, status);
			}
		} catch (Exception e) {
			throw new IfreException(100000, "获取产品列表信息异常", e);
		}
	}
}
