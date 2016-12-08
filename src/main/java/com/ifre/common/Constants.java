package com.ifre.common;

import java.io.File;

/**
 * 常量类
 * @author 王传圣
 */
public class Constants {
			
	/** ********************* 业务数据状态 *********************************** */
	public static final String FIRST_TRIAL_APPLY				= "A01";							//预审申请 
	public static final String FIRST_TRIAL_APPROVE				= "A02";							//预审通过
	public static final String FIRST_TRIAL_REFUSE               = "A03";                            //预审拒绝
	public static final String RECHECK_APPLY                    = "B01";                            //终审申请
	public static final String RECHECK_PASS                     = "B02";                            //终审通过
	public static final String RECHECK_REFUSE                   = "B03";                            //终审拒绝
	public static final String MORTGAGE_APPLY                   = "C01";                            //抵押登记
	public static final String MORTGAGE_APPROVE                 = "C02";                            //已抵押
	public static final String LOAN_STATUS_APPLY                = "F01";						    //放款申请
	public static final String LOAN_STATUS_PASS                 = "F02";						    //放款通过
	public static final String LOAN_STATUS_REFUSE               = "F03";						    //放款拒绝
	public static final String REPAYMENT_NORMAL                 = "H01";							//正常还款 
	public static final String REPAYMENT_OVERDUE 				= "H02";							//逾期        
	public static final String REPAYMENT_SQUARE				    = "H03"; 							//结清           
	public static final String REPAYMENT_CLAIMS 			    = "H04";							//理赔
	public static final String REPAYMENT_COMMUTING				= "H05";							//代偿
	public static final String REPAYMENT_COMMUTING_OVER		    = "H06";							//代偿结束
	public static final String REPAYMENT_OTHER  				= "H99";							//其他
	public static final String ERROR_XT         				= "E01";							//信托人工反馈异常数据
	public static final String ERROR_WD         				= "E02";							//微贷已重新提交异常数据
	
	/** ********************* 文件状态 *********************************** */
	public static final int APPLY_FILE_UPLOAD					= 1;                                //已上传贷款申请文件
	public static final int LOAN_FILE_DOWNLOAD					= 2;                                //已下载放款结果文件
	public static final int REPAYMENT_FILE_DOWNLOAD             = 3;                                //已下载成功扣款文件
	
	/** ********************* 文件类型 *********************************** */
	public static final String FILE_TYPE_APPLY                  = "1";                              //贷款申请文件
	public static final String FILE_TYPE_LOAN                   = "2";                              //放款结果文件
	public static final String FILE_TYPE_REPAYMENT              = "3";                              //成功扣款文件
	public static final String FILE_TYPE_PLAN                   = "4";                              //还款计划文件
	
	/** ********************* 业务类型 *********************************** */
	public static final short LOAN_APPLY						= 1;                               	//放款申请
	public static final short LOAN_RESULT						= 2;                               	//放款结果
	public static final short DEDUCT_RESULT             		= 3;                               	//扣款结果
	public static final short LOAN_APPLY_Processed              =14;                               	//放款处理完成
	public static final short LOAN_RESULT_Processed             =24;                               	//放款结果处理完成
	public static final short DEDUCT_RESULT_Processed           =34;                               	//扣款结果处理完成
	
	/** ********************* 共享服务接口业务类型 *********************************** */
	public static final String SHARED_TYPE_FIRST_TRIAL			= "firstTrial";                     //初审
	public static final String SHARED_TYPE_FINAL_TRIAL			= "finalTrial";                     //终审
	
	public static final String FAIL                             = "FAIL";
	public static final String SUCCESS                          = "SUCCESS";
	public static final String STATUS                           = "status";
	public static final String MSG                              = "msg";
	/**      合并数据             **/
	public static final Integer mergerData = 9999;
	
    /** The Constant ANTIFRAUD. */
    public static final int ANTIFRAUD = 71;
    
    
    public static final String START = "1";  //初始化完成
    public static final String PASS_TEST = "2"; //经过测试
    public static final String PUBLISH = "3";  //发布成功
    public static final String PACKAGE = "5"; //打包成功
    public static final String RE_PASS_TEST = "4"; //发布后再编辑
    public static final String RULE_TEMP_TYPE = "fqz"; //规则模板类型
    public static final String MODEL_TEMP_TYPE = "scorecards"; //模型模板类型
    public static final String NEW_TEMP_TYPE = "newCard"; //新评分卡类型
    public static final String RULE_INTF_URL = "/saas_deis/shared/brmsShared/api/callModelFire?jsonData="; //规则模板接口
    public static final String MODEL_INTF_URL = "/saas_deis/shared/brmsShared/api/callModelFire?jsonData="; //模型模板接口
    public static final Integer ACTIVATE = 1;  //激活
    public static final Integer INACTIVATE = 0; //关闭
    
    public static final String BACKUP_PATH = File.separator+"home"+File.separator+"backup";
}