package com.ifre.service.impl.brms;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ifre.service.brms.BiServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

import com.ifre.entity.brms.ModelResult;
import com.ifre.entity.brms.ModelResultEntity;
import com.ifre.exception.IfreException;

@Service("biService")
@Transactional
public class BiServiceImpl extends CommonServiceImpl implements BiServiceI {
	private final int page = 1;
	private final int rows = 1000;

	@Override
	public List<ModelResultEntity> getApiResultList() throws IfreException {
		updateSelfProdNameByProdId();
		String hql = "from ModelResultEntity ";
		List<ModelResultEntity> list = this.findHql(hql, "");
		if (list != null) {
			return list;
		} else {
			throw new IfreException("模型执行结果数据库连接失败");
		}
	}

	@Override
	public List<ModelResult> getApiResultListByDay(String orgCode) throws IfreException {
		updateSelfProdNameByProdId();
		String sql = "SELECT prod_id,prod_name,COUNT(ID) AS num,SUM(total_score) AS score,YEAR(c_time) AS yearNum,MONTH(c_time) AS monthNum,DAY(c_time) AS dayNum FROM `brms_model_result` WHERE handle_status = 'SUCCESS' GROUP BY yearNum,monthNum,dayNum,prod_id ORDER BY prod_id,yearNum,monthNum,dayNum";
		if(!"A01".equals(orgCode)){
			sql = "SELECT prod_id,prod_name,COUNT(ID) AS num,SUM(total_score) AS score,YEAR(c_time) AS yearNum,MONTH(c_time) AS monthNum,DAY(c_time) AS dayNum FROM `brms_model_result` WHERE ORG_CODE='"+orgCode+"' AND handle_status = 'SUCCESS' GROUP BY yearNum,monthNum,dayNum,prod_id ORDER BY prod_id,yearNum,monthNum,dayNum";
		}
		List<Map<String, Object>> relust = findForJdbc(sql, page, rows);
		List<ModelResult> list = new ArrayList<ModelResult>();
		ModelResult entity;
		// 亲测不可以，不是json的格式
		// List<ModelResultEntity> list = JSON.parseArray(findForJdbc(sql, page,
		// rows).toString(), ModelResultEntity.class);
		for (Map<String, Object> map : relust) {
			entity = new ModelResult();
			// 此处暗含一个策略，若一条数据出现异常，所有数据不显示
			entity.setProdId(map.get("prod_id").toString());
			entity.setProdName(map.get("prod_name").toString());
			entity.setNum(Integer.parseInt(map.get("num").toString()));
			entity.setScore(Integer.parseInt(map.get("score").toString()));
			entity.setDayNum(Integer.parseInt(map.get("dayNum").toString()));
			entity.setMonthNum(Integer.parseInt(map.get("monthNum").toString()));
			entity.setYearNum(Integer.parseInt(map.get("yearNum").toString()));
			// entity.setWeekNum(Integer.parseInt(map.get("weekNum").toString()));
			list.add(entity);
		}
		return list;
	}

	@Override
	public List<ModelResult> getApiResultListByMonth(String orgCode) throws IfreException {
		updateSelfProdNameByProdId();
		String sql = "SELECT prod_id,prod_name,COUNT(ID) AS num,SUM(total_score) AS score,YEAR(c_time) AS yearNum,MONTH(c_time) AS monthNum FROM `brms_model_result` WHERE handle_status = 'SUCCESS' GROUP BY yearNum,monthNum,prod_id ORDER BY prod_id,yearNum,monthNum";
		if(!"A01".equals(orgCode)){
			sql = "SELECT prod_id,prod_name,COUNT(ID) AS num,SUM(total_score) AS score,YEAR(c_time) AS yearNum,MONTH(c_time) AS monthNum FROM `brms_model_result`  WHERE ORG_CODE='"+orgCode+"' AND handle_status = 'SUCCESS'  GROUP BY yearNum,monthNum,prod_id ORDER BY prod_id,yearNum,monthNum";
		}
		List<Map<String, Object>> relust = findForJdbc(sql, page, rows);
		List<ModelResult> list = new ArrayList<ModelResult>();
		ModelResult entity;
		for (Map<String, Object> map : relust) {
			entity = new ModelResult();
			// 此处暗含一个策略，若一条数据出现异常，所有数据不显示
			entity.setProdId(map.get("prod_id").toString());
			entity.setProdName(map.get("prod_name").toString());
			entity.setNum(Integer.parseInt(map.get("num").toString()));
			entity.setScore(Integer.parseInt(map.get("score").toString()));
			// entity.setDayNum(Integer.parseInt(map.get("dayNum").toString()));
			entity.setMonthNum(Integer.parseInt(map.get("monthNum").toString()));
			entity.setYearNum(Integer.parseInt(map.get("yearNum").toString()));
			// entity.setWeekNum(Integer.parseInt(map.get("weekNum").toString()));
			list.add(entity);
		}
		return list;
	}
	
	@Override
	public List<ModelResult> getApiResultListByMonthAndLevel(String orgCode) throws IfreException {
		updateSelfProdNameByProdId();
		String sql = "SELECT prod_id,prod_name,COUNT(level) AS num,YEAR(c_time) AS yearNum,MONTH(c_time) AS monthNum,level FROM `brms_model_result` WHERE handle_status = 'SUCCESS' GROUP BY yearNum,monthNum,prod_id,level ORDER BY prod_id,yearNum,monthNum,level";
		if(!"A01".equals(orgCode)){
			sql = "SELECT prod_id,prod_name,COUNT(level) AS num,YEAR(c_time) AS yearNum,MONTH(c_time) AS monthNum,level FROM `brms_model_result` WHERE ORG_CODE='"+orgCode+"' AND handle_status = 'SUCCESS'  GROUP BY yearNum,monthNum,prod_id,level ORDER BY prod_id,yearNum,monthNum,level";
		}
		List<Map<String, Object>> relust = findForJdbc(sql, page, rows);
		List<ModelResult> list = new ArrayList<ModelResult>();
		ModelResult entity;
		for (Map<String, Object> map : relust) {
			entity = new ModelResult();
			// 此处暗含一个策略，若一条数据出现异常，所有数据不显示
			entity.setProdId(map.get("prod_id").toString());
			entity.setProdName(map.get("prod_name").toString());
			entity.setNum(Integer.parseInt(map.get("num").toString()));
			entity.setLevel(map.get("level").toString());
			//entity.setScore(Integer.parseInt(map.get("score").toString()));
			// entity.setDayNum(Integer.parseInt(map.get("dayNum").toString()));
			entity.setMonthNum(Integer.parseInt(map.get("monthNum").toString()));
			entity.setYearNum(Integer.parseInt(map.get("yearNum").toString()));
			// entity.setWeekNum(Integer.parseInt(map.get("weekNum").toString()));
			list.add(entity);
		}
		return list;
	}
	
	@Override
	public List<ModelResult> getApiResultListBy3MonthAndLevel(String orgCode) throws IfreException {
		updateSelfProdNameByProdId();
		String sql = "SELECT prod_id,prod_name,COUNT(level) AS num,YEAR(c_time) AS yearNum,MONTH(c_time) AS monthNum,level FROM `brms_model_result` WHERE (YEAR(NOW())-YEAR(c_time))*12+MONTH(NOW())-MONTH(c_time) < 3 AND handle_status = 'SUCCESS' GROUP BY yearNum,monthNum,prod_id,level ORDER BY prod_id,yearNum,monthNum,level";
		if(!"A01".equals(orgCode)){ 
			sql = "SELECT prod_id,prod_name,COUNT(level) AS num,YEAR(c_time) AS yearNum,MONTH(c_time) AS monthNum,level FROM `brms_model_result` WHERE (YEAR(NOW())-YEAR(c_time))*12+MONTH(NOW())-MONTH(c_time) < 3  AND ORG_CODE='"+orgCode+"' AND handle_status = 'SUCCESS' GROUP BY yearNum,monthNum,prod_id,level ORDER BY prod_id,yearNum,monthNum,level";
		}
		List<Map<String, Object>> relust = findForJdbc(sql, page, rows);
		List<ModelResult> list = new ArrayList<ModelResult>();
		ModelResult entity;
		for (Map<String, Object> map : relust) {
			entity = new ModelResult();
			// 此处暗含一个策略，若一条数据出现异常，所有数据不显示
			entity.setProdId(map.get("prod_id").toString());
			entity.setProdName(map.get("prod_name").toString());
			entity.setNum(Integer.parseInt(map.get("num").toString()));
			entity.setLevel(map.get("level").toString());
			//entity.setScore(Integer.parseInt(map.get("score").toString()));
			// entity.setDayNum(Integer.parseInt(map.get("dayNum").toString()));
			entity.setMonthNum(Integer.parseInt(map.get("monthNum").toString()));
			entity.setYearNum(Integer.parseInt(map.get("yearNum").toString()));
			// entity.setWeekNum(Integer.parseInt(map.get("weekNum").toString()));
			list.add(entity);
		}
		return list;
	}

	@Override
	public List<ModelResult> getApiResultListByWeek(String orgCode) throws IfreException {
		updateSelfProdNameByProdId();
		String sql = "SELECT prod_id,prod_name,COUNT(ID) AS num,SUM(total_score) AS score,YEAR(c_time) AS yearNum,WEEK(c_time) AS weekNum FROM `brms_model_result` WHERE handle_status = 'SUCCESS' GROUP BY yearNum,weekNum,prod_id ORDER BY prod_id,yearNum,weekNum";
		if(!"A01".equals(orgCode)){
			sql = "SELECT prod_id,prod_name,COUNT(ID) AS num,SUM(total_score) AS score,YEAR(c_time) AS yearNum,WEEK(c_time) AS weekNum FROM `brms_model_result` WHERE ORG_CODE='"+orgCode+"' AND handle_status = 'SUCCESS'  GROUP BY yearNum,weekNum,prod_id ORDER BY prod_id,yearNum,weekNum";
		}
		List<Map<String, Object>> relust = findForJdbc(sql, page, rows);
		List<ModelResult> list = new ArrayList<ModelResult>();
		ModelResult entity;
		for (Map<String, Object> map : relust) {
			entity = new ModelResult();
			// 此处暗含一个策略，若一条数据出现异常，所有数据不显示
			entity.setProdId(map.get("prod_id").toString());
			entity.setProdName(map.get("prod_name").toString());
			entity.setNum(Integer.parseInt(map.get("num").toString()));
			entity.setScore(Integer.parseInt(map.get("score").toString()));
			// entity.setDayNum(Integer.parseInt(map.get("dayNum").toString()));
			// entity.setMonthNum(Integer.parseInt(map.get("monthNum").toString()));
			entity.setYearNum(Integer.parseInt(map.get("yearNum").toString()));
			entity.setWeekNum(Integer.parseInt(map.get("weekNum").toString()));
			list.add(entity);
		}
		return list;
	}
	
	/**
	 * 更新模型结果表
	 */
	private void updateSelfProdNameByProdId(){
		//ORG_CODE保存接口的机构编码brms_model_result.ORG_CODE = brms_rule_prod.ORG_CODE
		String sql = "UPDATE brms_model_result  LEFT JOIN brms_rule_prod ON brms_model_result.prod_id = brms_rule_prod.id AND handle_status = 'SUCCESS' SET brms_model_result.prod_name = brms_rule_prod.`NAME`";
		//executeSql(sql);
		updateBySqlString(sql);
	}

}