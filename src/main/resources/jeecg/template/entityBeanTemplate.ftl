package ${bussiPackage}.${entityPackage};

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**   
 * @Title: Entity
 * @Description: ${ftl_description}
 * @author huzheng
 * @date ${ftl_create_time}
 * @version V1.0   
 *
 */

@SuppressWarnings("serial")
public class ${entityName} implements java.io.Serializable {
	<#list originalColumns as po>
	/**${po.filedComment}*/
	
	private <#if po.optionType == "Y">List<${po.fieldType}><#else>${po.fieldType}</#if> ${po.fieldName};
	</#list>

	    /********** constructors ***********/
    public ${entityName}() {
     
    }
   
    public ${entityName}(<#list originalColumns as po> <#if po.optionType == "Y">List<${po.fieldType}><#else>${po.fieldType}</#if> ${po.fieldName}<#if po_has_next>,</#if></#list>) {
    <#list originalColumns as po>
        this.${po.fieldName} = ${po.fieldName};
    </#list>
    }
	
	<#list originalColumns as po>
	/**
	 *方法: 取得${po.filedComment}
	 *@return: <#if po.optionType == "Y">List<${po.fieldType}><#else>${po.fieldType}</#if> ${po.filedComment}
	 */
	
	public <#if po.optionType == "Y">List<${po.fieldType}><#else>${po.fieldType}</#if> get${po.fieldName?cap_first}(){
		return this.${po.fieldName};
	}

	/**
	 *方法: 设置${po.filedComment}
	 *@param: <#if po.optionType == "Y">List<${po.fieldType}><#else>${po.fieldType}</#if> ${po.filedComment}
	 */
	public void set${po.fieldName?cap_first}(<#if po.optionType == "Y">List<${po.fieldType}><#else>${po.fieldType}</#if> ${po.fieldName}){
		this.${po.fieldName} = ${po.fieldName};
	}
	</#list>
}
