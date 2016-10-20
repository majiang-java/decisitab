/**
 * 
 * @author majiang
 *
 */
package com.ifre.ruleengin;

import javax.annotation.PostConstruct;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class RuleConfig {
	
	private static KieContainer kieContainer;
	private static KieSession kieSession;
	
	@PostConstruct
	public void init(){
		kieContainer = KieServices.Factory.get().getKieClasspathContainer();
	}
	
	
	public static void getSession(String str){
		kieSession = kieContainer.newKieSession();
	}
	


}
