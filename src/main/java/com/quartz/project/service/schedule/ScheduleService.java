package com.quartz.project.service.schedule;

import java.util.List;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.quartz.CronScheduleBuilder.cronSchedule;

import com.quartz.project.config.CrwalingJob;
import com.quartz.project.domain.ScheduleVO;
import com.quartz.project.repository.ScheduleMapper;

@Service
public class ScheduleService {
	@Autowired
	private Scheduler scheduler;
	@Autowired
	private ScheduleMapper scheduleMapper;
	private final String PACKAGE_ROOT = "com.quartz.project.config.";
	
	/**
	 * 스케줄러 시작 메소드.
	 * @throws SchedulerException
	 * @throws Exception
	 */
	public void srvStartScheduler(Job job) throws SchedulerException,Exception {
		scheduler.start();
	}
	
	/**
	 * 스케줄러를 standby로 전환.(트리거가 발동하지 않는 상태.)
	 * @throws SchedulerException
	 * @throws Exception
	 */
	public void srvStandByScheduler() throws SchedulerException, Exception {
		if(scheduler != null) {
			scheduler.standby();
		}
	}
	

	/**
	 * 스케줄 리스트 조회 메소드.
	 * @return List<ScheduleVO> scheduleList
	 * @throws Exception
	 */
	public List<ScheduleVO> srvSelectSchdulList() throws Exception{
		List<ScheduleVO> scheduleList = null;
		scheduleList = scheduleMapper.selectScheduleList();
		return scheduleList;
	}
	
	/**
	 * 스케줄을 스케줄러에 등록하는 메소드.
	 * @param scheduleVO
	 * @throws SchedulerException
	 * @throws Exception
	 */
	public void srvInsertSchedule(ScheduleVO scheduleVO, Class<Job> job) throws SchedulerException, Exception{
		JobDetail jobDetail;
		CronTrigger cronTrigger;
		
		/**
		 * STEP1. jobDetail object 생성.
		 */
		jobDetail = JobBuilder.newJob(job)
				.withIdentity(scheduleVO.getScheduleNo())
				.storeDurably()
//                .usingJobData(scheduleVO.getJobDataMap())
				.build();	
		
		/**
		 * STEP2. cronTrigger object 생성.
		 */
		cronTrigger = TriggerBuilder.newTrigger()
			    .withIdentity(scheduleVO.getScheduleNo())
			    .withSchedule(cronSchedule(scheduleVO.getCronExpression()))
			    .forJob(scheduleVO.getScheduleNo())
			    .build();
		
		
		/**
		 * STEP3. 스케줄러에 job 등록.
		 */
		scheduler.scheduleJob(jobDetail, cronTrigger);
	}
	
	public void srvInsertSchedule(ScheduleVO scheduleVO) throws SchedulerException, Exception{
		JobDetail jobDetail;
		CronTrigger cronTrigger;
		
		
		Class<Job> myJob = (Class<Job>) Class.forName(PACKAGE_ROOT+scheduleVO.getJobName());
		
		/**
		 * STEP1. jobDetail object 생성.
		 */
		jobDetail = JobBuilder.newJob(myJob)
				.withIdentity(scheduleVO.getScheduleNo())
				.storeDurably()
//                .usingJobData(scheduleVO.getJobDataMap())
				.build();	
		
		/**
		 * STEP2. cronTrigger object 생성.
		 */
		cronTrigger = TriggerBuilder.newTrigger()
			    .withIdentity(scheduleVO.getScheduleNo())
			    .withSchedule(cronSchedule(scheduleVO.getCronExpression()))
			    .forJob(scheduleVO.getScheduleNo())
			    .build();
		
		
		/**
		 * STEP3. 스케줄러에 job 등록.
		 */
		scheduler.scheduleJob(jobDetail, cronTrigger);
	}
	
	/**
	 * JobListener 등록 메서드
	 * @param scheduleVO
	 * @param jobListener
	 * @throws Exception
	 */
	public void srvAddJobListener(ScheduleVO scheduleVO, JobListener jobListener) throws SchedulerException,Exception{
		//job identity(jobKey)에 해당하는 job에 jobListener를 연결한다. 
		JobKey jobKey = new JobKey(scheduleVO.getScheduleNo());
		scheduler.getListenerManager().addJobListener(jobListener, KeyMatcher.keyEquals(jobKey));
	}
	
	public void srvDeleteSchedule(ScheduleVO scheduleVO) throws SchedulerException,Exception {
		scheduler.deleteJob(JobKey.jobKey(scheduleVO.getScheduleNo()));
	}
	
	public void srvUpdateSchedule(ScheduleVO scheduleVO) throws SchedulerException,Exception {
		srvDeleteSchedule(scheduleVO);
//		srvInsertSchedule(scheduleVO, new CrwalingJob());
		srvInsertSchedule(scheduleVO);
	}
	

}
