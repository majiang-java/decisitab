package com.ifre.util;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;



/**
 * 定时任务工具类
 */
public class QuartzUtil {

	/**
     * 启动任务
     * @param  name 名称(任务名称&触发器名称) 
     * @param  classpath 待触发的类路径
     * @param  cronExpression cron表达式
     * @param  jobDataMap 任务调度参数集
     * @return void
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
	public static void startJob(String name, String classpath,String cronExpression,JobDataMap jobDataMap) throws Exception {
    	
    	// 创建一个Scheduler
        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler sched = schedFact.getScheduler();
        
        // 创建一个JobDetail，指明name，groupname，以及具体的Job类名，
        // 该Job负责定义需要执行任务
        Class cla =  Class.forName(classpath);
        JobDetail jobDetail = new JobDetail(name,name,cla);
        jobDetail.setJobDataMap(jobDataMap);
        
        // 创建一个每周触发的Trigger，指明星期几几点几分执行
        CronTrigger trigger = new CronTrigger(name, name); 
        trigger.setCronExpression(cronExpression); 
        
        // 用scheduler将JobDetail与Trigger关联在一起，开始调度任务
        sched.scheduleJob(jobDetail, trigger);  
      
        sched.start();
    }
    
    /**
     * 停止任务
     * @param  name 名称(任务名称&触发器名称) 
     * @return void
     * @throws Exception 
     */
	public static void stopJob(String name) throws Exception {
    	
		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler sched = schedFact.getScheduler();
        sched.deleteJob(name,name);
    }
   
}