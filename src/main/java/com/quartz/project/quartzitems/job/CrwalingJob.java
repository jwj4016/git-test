package com.quartz.project.quartzitems.job;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.quartz.project.service.CrwalingService;
import com.quartz.project.service.MailService;
import com.quartz.project.service.ProductInfoService;
import com.quartz.project.service.schedule.ScheduleService;

public class CrwalingJob extends QuartzJobBean implements InterruptableJob{

	@Autowired
	MailService mailService;
	@Autowired
	ScheduleService scheduleService;
	@Autowired
	ProductInfoService productInfoService;
	@Autowired
	CrwalingService crwalingService;
	
	@Override
	public void interrupt() throws UnableToInterruptJobException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		crwalingService.srvCrwalingJob(context);

	}

}
