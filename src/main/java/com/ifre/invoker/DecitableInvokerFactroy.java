/**
 * 
 * @author majiang
 *
 */
package com.ifre.invoker;

import com.ifre.invoker.impl.AntifraudInvoker;
import com.ifre.invoker.impl.DefaultInvoker;
import com.ifre.invoker.impl.ScoreCardInvoker;
/**
 * 
 * @author majiang
 *
 */
public class DecitableInvokerFactroy {
	
	private static DecitableInvokerFactroy factory = new DecitableInvokerFactroy();
	
	private static DecitabInvokerI invoker;
	
	public static final int FQZ = 71;
	public static final int WY = 25;
	public static final int SC = 31;
	public static final int ZNMX = 42;
	public static DecitabInvokerI create(int type){
		if(FQZ == type){
			invoker = new AntifraudInvoker();
		}else if(WY == type){
			invoker = new ScoreCardInvoker();
		}else{
			invoker = new DefaultInvoker();
		}
		return invoker;
		
	}

	public DecitableInvokerFactroy newInstantce(){
		return factory;
	}
}
