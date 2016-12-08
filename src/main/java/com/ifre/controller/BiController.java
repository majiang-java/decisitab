package com.ifre.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ifre.entity.brms.ModelResult;
import com.ifre.entity.brms.RuleProdEntity;
import com.ifre.exception.IfreException;
import com.ifre.form.api.EChaetObject;
import com.ifre.form.api.EChaetObject3Month;
import com.ifre.form.api.EChaetObjectList;
import com.ifre.service.brms.BiServiceI;
import com.ifre.service.brms.RuleProdServiceI;
import com.ifre.service.exception.IfreExceptionServiceI;

@Scope("prototype")
@Controller
@RequestMapping("/biController")
public class BiController {

	@Autowired
	private BiServiceI biService;
	@Autowired
	private IfreExceptionServiceI ifreExceptionService;

	@RequestMapping(params = "bi")
	public ModelAndView bi(HttpServletRequest request) {
		//机构过滤-后台实现
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		try {
			List<ModelResult> modelResultListByDay = biService.getApiResultListByDay(orgCode);
			request.setAttribute("modelResultListByDay", modelResultListByDay);
		} catch (IfreException e) {
			ifreExceptionService.resolveIfreException(e, Globals.Log_Leavel_exception);
		}
		try {
			List<ModelResult> modelResultListByMonth = biService.getApiResultListByMonth(orgCode);
			request.setAttribute("modelResultListByMonth", modelResultListByMonth);
		} catch (IfreException e) {
			ifreExceptionService.resolveIfreException(e, Globals.Log_Leavel_exception);
		}
		try {
			List<ModelResult> modelResultListByWeek = biService.getApiResultListByWeek(orgCode);
			request.setAttribute("modelResultListByWeek", modelResultListByWeek);
		} catch (IfreException e) {
			ifreExceptionService.resolveIfreException(e, Globals.Log_Leavel_exception);
		}
		try {
			List<ModelResult> modelResultListByLevel = biService.getApiResultListByMonthAndLevel(orgCode);
			request.setAttribute("modelResultListByLevel", modelResultListByLevel);
		} catch (IfreException e) {
			ifreExceptionService.resolveIfreException(e, Globals.Log_Leavel_exception);
		}
		
		return new ModelAndView("com/ifre/show/bi");
	}
	
	@RequestMapping(params = "biCharts")
	public ModelAndView biCharts(HttpServletRequest request) {
		try {
			//机构过滤-后台实现
			String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
			List<ModelResult> modelResultListByMonth = biService.getApiResultListByMonth(orgCode);
			request.setAttribute("modelResultListByMonth", modelResultListByMonth);
			//页面图表数据组装
			EChaetObjectList objectList = getEChaetElementList(modelResultListByMonth,typeDate);
			request.setAttribute("modelResultListByLevelAndEChaet", JSON.toJSONString(objectList.getKeyList()));
			request.setAttribute("modelResultListByMonthAndEChaet", JSON.toJSONString(objectList.getObjectList()));
		} catch (IfreException e) {
			ifreExceptionService.resolveIfreException(e, Globals.Log_Leavel_exception);
			request.setAttribute("msg", e.getMessage());
		}
		return new ModelAndView("com/ifre/show/biCharts");
	}
	
	@Autowired
	private RuleProdServiceI ruleProdService;
	
	@RequestMapping(params = "biChartsLevel")
	public ModelAndView biChartsLevel(HttpServletRequest request) {
		try {
			//机构过滤-后台实现
			String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
			List<RuleProdEntity> prodList = ruleProdService.findRuleProdList(orgCode);
			
			List<ModelResult> modelResultListByLevel = biService.getApiResultListBy3MonthAndLevel(orgCode);
			request.setAttribute("modelResultListByLevel", modelResultListByLevel);
			//页面图表数据组装
			List<EChaetObject3Month> objectList = getAll(getEChaetElementBy3MonthLevelList(modelResultListByLevel),prodList);
			request.setAttribute("modelResultListBy3MonthLevelAndEChaet", JSON.toJSONString(objectList));
			request.setAttribute("prodList", objectList);
			
		} catch (IfreException e) {
			ifreExceptionService.resolveIfreException(e, Globals.Log_Leavel_exception);
		}
		return new ModelAndView("com/ifre/show/biChartsLevel");
	}
	
	/**
	 * 根据二维表数据，转换为四维图数据（满足系数矩阵格式）
	 * 
	 * @param list
	 * @return
	 */
	private List<EChaetObject3Month> getEChaetElementBy3MonthLevelList(List<ModelResult> list){
		List<EChaetObject3Month> objectList = new ArrayList<>();
		if(null != list ){
			EChaetObject3Month tempObj;
			Map<String, Integer> tempElementMap1,tempElementMap2,tempElementMap3;
			String tempProdId = "",tempMinLevel = "Z",tempMaxLevel = "A";
			Calendar cal = Calendar.getInstance();
			int tempMonths = cal.get(Calendar.YEAR)*12+cal.get(Calendar.MONTH) + 1;
			for (ModelResult modelResult : list) {
				if (!tempProdId.equals(modelResult.getProdId())) {
					//第一次进入时是空，不操作
					if(objectList.size() != 0){
						if(tempMinLevel.toCharArray().length > 1){
							objectList.get(objectList.size()-1).getKeyList().add("欺诈");
							objectList.get(objectList.size()-1).getKeyList().add("高风险");
							objectList.get(objectList.size()-1).getKeyList().add("低风险");
						}else{
							char min = tempMinLevel.toCharArray()[0];
							char max = tempMaxLevel.toCharArray()[0];
							for(char temp = min; temp <= max;temp ++){
								objectList.get(objectList.size()-1).getKeyList().add(String.valueOf(temp));
							}
						}
					}
					if(null == modelResult.getLevel() || modelResult.getLevel().isEmpty()){
						tempMinLevel = "Z";
						tempMaxLevel = "A";
					}else{
						tempMinLevel = modelResult.getLevel();
						tempMaxLevel = modelResult.getLevel();
					}
					
					tempProdId = modelResult.getProdId();
					tempObj = new EChaetObject3Month();
					tempObj.setName(modelResult.getProdName());
					tempObj.setProdId(modelResult.getProdId());
					tempElementMap1 = new HashMap<>();
					tempElementMap2 = new HashMap<>();
					tempElementMap3 = new HashMap<>();
					if(null != modelResult.getLevel() && !modelResult.getLevel().isEmpty()){
						if(tempMonths == (modelResult.getYearNum()*12+modelResult.getMonthNum())){
							tempElementMap3.put(modelResult.getLevel(), modelResult.getNum());
						}else if(tempMonths == (1+modelResult.getYearNum()*12+modelResult.getMonthNum())){
							tempElementMap2.put(modelResult.getLevel(), modelResult.getNum());
						}else if(tempMonths == (2+modelResult.getYearNum()*12+modelResult.getMonthNum())){
							tempElementMap1.put(modelResult.getLevel(), modelResult.getNum());
						}
					}
					tempObj.setElementMap3(tempElementMap3);
					tempObj.setElementMap2(tempElementMap2);
					tempObj.setElementMap1(tempElementMap1);
					objectList.add(tempObj);
				} else {
					if(null != modelResult.getLevel() && !modelResult.getLevel().isEmpty()){
						if(tempMinLevel.compareTo(modelResult.getLevel()) > 0){
							tempMinLevel = modelResult.getLevel();
						}
						if(tempMaxLevel.compareTo(modelResult.getLevel()) < 0){
							tempMaxLevel = modelResult.getLevel();
						}
						//由于获取数据的时候是按照产品排序所有下一个默认添加到最后一个产品
						if(tempMonths == (modelResult.getYearNum()*12+modelResult.getMonthNum())){
							objectList.get(objectList.size()-1).getElementMap3().put(modelResult.getLevel(), modelResult.getNum());
						}else if(tempMonths == (1+modelResult.getYearNum()*12+modelResult.getMonthNum())){
							objectList.get(objectList.size()-1).getElementMap2().put(modelResult.getLevel(), modelResult.getNum());
						}else if(tempMonths == (2+modelResult.getYearNum()*12+modelResult.getMonthNum())){
							objectList.get(objectList.size()-1).getElementMap1().put(modelResult.getLevel(), modelResult.getNum());
						}
					}
				}
			}
		}
		return objectList;
	}
	
	/**
	 * 根据全量的产品，补齐EChaetObject3Month
	 * 
	 * @param list
	 * @param prodList
	 * @return
	 */
	private List<EChaetObject3Month> getAll(List<EChaetObject3Month> list,List<RuleProdEntity> prodList){
		List<EChaetObject3Month> objectList = new ArrayList<>();
		//此处可以优化，暂时这么使用
		int num = list.size(),temp = 0;
		for (RuleProdEntity obj : prodList) {
			for (int i = 0;i< num;i++) {
				temp = i;
				if(obj.getId() != null && obj.getId().equals(list.get(i).getProdId())){
					objectList.add(list.get(i));
					break;
				}
			}
			if(temp + 1 == num){
				objectList.add(new EChaetObject3Month(obj.getName(),obj.getId()));
			}
		}
		return objectList;
	}
	
	/**
	 * 根据二维表数据，转换为三维图数据（满足系数矩阵格式）
	 * 
	 * @param modelResultListByMonth
	 * @param type
	 * @return
	 * @throws IfreException
	 */
	private EChaetObjectList getEChaetElementList(List<ModelResult> modelResultListByMonth,int type) throws IfreException {
		if (modelResultListByMonth == null || modelResultListByMonth.size() == 0) {
			throw new IfreException("没有数据");
		}
		EChaetObjectList eChaetObjectList = new EChaetObjectList();
		
		EChaetObject tempObj;
		Map<String, Integer> elementMap;
		String tempProdId = "";
		int tempMinYear,tempMaxYear = 0,tempMinMonth,tempMaxMonth = 0;
		tempMinYear = modelResultListByMonth.get(0).getYearNum();
		tempMinMonth = modelResultListByMonth.get(0).getMonthNum();
		for (ModelResult modelResult : modelResultListByMonth) {
			if (typeDate == type) {
				if (tempMinYear > modelResult.getYearNum()) {
					tempMinYear = modelResult.getYearNum();
					tempMinMonth = modelResult.getMonthNum();
				}else if(tempMinYear == modelResult.getYearNum() && tempMinMonth > modelResult.getMonthNum()){
					tempMinMonth = modelResult.getMonthNum();
				}
				if (tempMaxYear < modelResult.getYearNum()) {
					tempMaxYear = modelResult.getYearNum();
					tempMaxMonth = modelResult.getMonthNum();
				}else if(tempMaxYear == modelResult.getYearNum() && tempMaxMonth < modelResult.getMonthNum()){
					tempMaxMonth = modelResult.getMonthNum();
				}
			}
			if (!tempProdId.equals(modelResult.getProdId())) {
				tempProdId = modelResult.getProdId();
				tempObj = new EChaetObject();
				tempObj.setName(modelResult.getProdName());
				elementMap = new HashMap<>();
				elementMap.put(getRowData(modelResult, type), modelResult.getNum());
				tempObj.setElementMap(elementMap);
				eChaetObjectList.getObjectList().add(tempObj);
			} else {
				//由于获取数据的时候是按照产品排序所有下一个默认添加到最后一个产品
				eChaetObjectList.getObjectList().get(eChaetObjectList.getObjectList().size()-1).getElementMap().put(getRowData(modelResult, type), modelResult.getNum());
			}
		}
		if (typeDate == type) {
			eChaetObjectList.getKeyList().clear();
			if(tempMinYear < tempMaxYear || (tempMinYear == tempMaxYear && tempMinMonth <= tempMaxMonth)){
				if(tempMinYear == tempMaxYear){
					for (int tempMonth = tempMinMonth; tempMonth <= tempMaxMonth; tempMonth++) {
						eChaetObjectList.getKeyList().add(tempMinYear + "年" + tempMonth + "月");
					}
				}
				for (int tempYear = tempMinYear; tempYear <= tempMaxYear; tempYear++) {
					if(tempYear == tempMinYear){
						for (int tempMonth = tempMinMonth; tempMonth <= 12; tempMonth++) {
							eChaetObjectList.getKeyList().add(tempYear + "年" + tempMonth + "月");
						}
					}else if(tempYear == tempMaxYear){
						for (int tempMonth = 1; tempMonth <= tempMaxMonth; tempMonth++) {
							eChaetObjectList.getKeyList().add(tempYear + "年" + tempMonth + "月");
						}
					}else{
						for (int tempMonth = tempMinMonth; tempMonth <= tempMaxMonth; tempMonth++) {
							eChaetObjectList.getKeyList().add(tempYear + "年" + tempMonth + "月");
						}
					}
				}
			}
		}else if (typeLevel == type) {
			eChaetObjectList.getKeyList().clear();
//			if(){
//				
//			}
			
			//if(modelResultListByMonth.get(0).get){
				eChaetObjectList.getKeyList().add("欺诈");
				eChaetObjectList.getKeyList().add("高风险");
				eChaetObjectList.getKeyList().add("低风险");
			//}
		}
		return eChaetObjectList;
	}
	
	private final int typeDate = 1;
	private final int typeLevel = 2;

	/**
	 * 根据不同的图表横坐标，保存的内容表示的含义不同，临时分类type：1表示日期，2表示等级
	 * 
	 * @param modelResult
	 * @param type
	 * @return
	 * @throws IfreException
	 */
	private String getRowData(ModelResult modelResult, int type) throws IfreException {
		if (typeDate == type) {
			return modelResult.getYearNum() + "年" + modelResult.getMonthNum() + "月";
		} else if (typeLevel == type) {
			return modelResult.getLevel();
		} else {
			throw new IfreException("模型执行指标查询时，图表数据转换，横坐标类型错误");
		}
	}
	
//	/**
//	 * 选择产品变更，获取对应产品的等级分布
//	 * 
//	 * @return
//	 */
//	@RequestMapping(params = "changeProd")
//	@ResponseBody
//	public AjaxJson changeProd(RuleProdEntity ruleProd, HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
//		ruleProd = biService.getEntity(RuleProdEntity.class, ruleProd.getId());
//		j.setMsg(message);
//		return j;
//	}

}
