package com.ifre.util.encrypte;

public class CustomClassTest {

/*	public static void main(String[] args) {
        IfreClassLoader loader = new IfreClassLoader(Thread.currentThread().getContextClassLoader());
      //  Thread.currentThread().setContextClassLoader(loader);
        try {
			Class<?> clazzScoreModelObj = loader.loadClass("com.ifre.modelproj.defaultcards.xlfq.ScoreModelObj");
			Object instanceScoreModelObj = clazzScoreModelObj.newInstance();
			int SINA_INSTALMENT = 27;
			Map<String, String> defaultMap = new HashMap<String, String>();
			// 36 + 50 + 49 + 48 + 55 + 51 + 52 + 42 + 48 + 41 + 42 + 50
			defaultMap.put("city", "2");				//绍兴市、深圳市
			defaultMap.put("bankName", "5");			//中国建设银行
			defaultMap.put("initPayRatio", "0.2");		//首付比例
			defaultMap.put("loanMonth", "10");			// 贷款期数 
			defaultMap.put("relationship", "1");		//亲属关系
			defaultMap.put("position", "1");			//职位
			defaultMap.put("industry", "-99");			//行业类型
			defaultMap.put("sex", "1");					//性别
			defaultMap.put("inComeDebtRatio", "1.65");	//收入负债比(个人月收入/月还金额) 
			defaultMap.put("creditAmount", "6000");		//贷款金额(元)
			defaultMap.put("workYear", "6");			//工作年限(年)
			defaultMap.put("cityMob", "2");				//手机归属地于进件城市是否一致			
			
			LoanApplication defaultCardsObj =  new LoanApplication();
			defaultCardsObj.setType(SINA_INSTALMENT);
			Class<?> clazzLoanApplication = loader.loadClass("com.ifre.modelproj.defaultcards.xlfq.LoanApplication");
			Object instanceLoanApplication = clazzLoanApplication.newInstance();
			clazzLoanApplication.getMethod("setType", int.class ).invoke(instanceLoanApplication, 27);
			
			 Object[] _args = new Object[]{defaultMap, defaultCardsObj};
			 Class<?>[] paramsType = new Class[]{Object.class,  LoanApplication.class};
			 
			 Object _instanceLoanApplication = clazzScoreModelObj.getMethod("getPricingObj",paramsType ).invoke(instanceScoreModelObj, _args);
			 int totalscore = (int)clazzLoanApplication.getMethod("getTotalScore").invoke(_instanceLoanApplication);
			 int trimScore = (int)clazzLoanApplication.getMethod("getTrimScore").invoke(_instanceLoanApplication);
			 String ranking = (String)clazzLoanApplication.getMethod("getRanking").invoke(_instanceLoanApplication);
			
			 defaultCardsObj = (LoanApplication)_instanceLoanApplication;
			 System.out.println("totalsocore = " + defaultCardsObj.getTotalScore());
			 System.out.println("trimScore = " + defaultCardsObj.getTrimScore());
			 System.out.println("ranking = " + defaultCardsObj.getRanking());
			 System.out.println("defaultRatio = " + defaultCardsObj.getDefaultRatio());			 
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
	}*/

}
