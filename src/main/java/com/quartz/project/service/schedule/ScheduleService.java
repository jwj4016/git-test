package com.quartz.project.service.schedule;

import java.util.List;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Matcher;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.stereotype.Service;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import com.quartz.project.domain.ScheduleVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
	
	private final Scheduler scheduler;
	
	//서비스가 context에 등록된 후 자동 실행
//	@PostConstruct
	/**
	 * 스케줄러 시작 메소드.
	 * @throws SchedulerException
	 * @throws Exception
	 */
	public void srvStartScheduler(Job job) throws SchedulerException,Exception {
		/**
		 * STEP1.스케줄 리스트를 받아온다. 
		 */
		List<ScheduleVO> scheduleList = this.srvSelectSchdulList();
		
		/**
		 * STEP2.받아온 스케줄 리스트를 스케줄러에 등록한다.
		 */
		for (ScheduleVO schedule : scheduleList)
		{
			srvInsertSchedule(schedule,job);
		}
		
		/**
		 * STEP3. 스케줄러 시작.
		 */
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
//		scheduleList = scheduleDAO.selectSchdulList();
		return scheduleList;
	}
	
	/**
	 * 스케줄을 스케줄러에 등록하는 메소드.
	 * @param scheduleVO
	 * @throws SchedulerException
	 * @throws Exception
	 */
	public void srvInsertSchedule(ScheduleVO scheduleVO, Job job) throws SchedulerException, Exception{
		JobDetail jobDetail;
		CronTrigger cronTrigger;
		
		/**
		 * STEP1. jobDetail object 생성.
		 */
		jobDetail = JobBuilder.newJob(job.getClass())
				.withIdentity(scheduleVO.getScheduleNo())
				.storeDurably()
                .usingJobData(scheduleVO.getJobDataMap())
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
	public void srvAddJobListener(ScheduleVO scheduleVO, JobListener jobListener) throws Exception{
		//job identity(jobKey)에 해당하는 job에 jobListener를 연결한다. 
		JobKey jobKey = new JobKey(scheduleVO.getScheduleNo());
		scheduler.getListenerManager().addJobListener(jobListener, KeyMatcher.keyEquals(jobKey));
	}
	

}
