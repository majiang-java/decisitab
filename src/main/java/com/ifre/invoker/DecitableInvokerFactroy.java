/**
 * 
 * @author majiang
 *
 */
package com.ifre.invoker;

import com.ifre.invoker.impl.AccessInvoker;
import com.ifre.invoker.impl.AntifraudInvoker;
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
	public static final int ZR = 50;
	public static DecitabInvokerI create(int type){
		if(FQZ == type){
			invoker = new AntifraudInvoker();
		}else if(WY == type){
			invoker = new ScoreCardInvoker();
		}else if(ZR == type){
			invoker = new AccessInvoker();
		}else{
			invoker = new ScoreCardInvoker();
		}
		return invoker;
		
	}

	public DecitableInvokerFactroy newInstantce(){
		return factory;
	}
}
