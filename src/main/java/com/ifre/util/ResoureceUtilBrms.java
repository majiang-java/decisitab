/**
*@Project Name：saas_brms
*@Package Name：com.ifre.util
*@File Name：ResoureceUtilBrms.java
*@Date：2016年10月17日-上午9:43:19
*@Copyright (c)2016 www.ifre.com.cn Inc. All rights reserved.
*@Attention：本内容仅限于数信互融科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
*/

package com.ifre.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.util.ResourceUtil;

import com.ifre.entity.brms.VarType;
import com.ifre.entity.brms.VarTypegroup;

/**
*@Class Name：ResoureceUtilBrms
*@Class Description：<br />
*@Creator：caojianmiao
*@Modifier：
*@Modify Date：2016年10月17日 上午9:43:19
*@Description：
*/

public class ResoureceUtilBrms extends ResourceUtil {

	/**
	 * 缓存模型变量分组【缓存】
	 */
	public static Map<String, VarTypegroup> allVarTypegroups = new HashMap<String,VarTypegroup>();
	
	/**
	 * 缓存字典【缓存】
	 */
	public static Map<String, List<VarType>> allVarTypes = new HashMap<String,List<VarType>>();

}
