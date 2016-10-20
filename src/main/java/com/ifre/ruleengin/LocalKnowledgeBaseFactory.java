/**
 * 
 * @author majiang
 *
 */
package com.ifre.ruleengin;

import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;

public class LocalKnowledgeBaseFactory {
	
	 private static KnowledgeBase kbase;
	 /**
	  * 生成KnowledgeBase 单例
	  * @return
	  */
	 public static KnowledgeBase getKnowledgeBaseInstance(){
		 if(kbase == null){
			 kbase = KnowledgeBaseFactory.newKnowledgeBase();
		 }
		 return kbase;
	 }

}

