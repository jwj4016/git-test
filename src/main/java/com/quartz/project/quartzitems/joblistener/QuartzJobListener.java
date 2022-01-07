package com.quartz.project.quartzitems.joblistener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.quartz.project.domain.ScheduleVO;
import com.quartz.project.service.schedule.ScheduleService;

// TODO 클래스명 변경 예정 -> CrwalingJobListener로. 
public class QuartzJobListener extends JobListenerSupport {
	@Autowired
	ScheduleService scheduleService;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}
	
	/**
	 * job이 실행되고 난 후 호출되는 메소드.
	 */
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		System.out.println("잡 리스너 테스트 중.");

		Boolean isProductDataExistent = false;
		isProductDataExistent = (Boolean) context.get("isProductDataExistent");
		
		if(isProductDataExistent) {
			//해당 잡 삭제
			ScheduleVO scheduleVO = new ScheduleVO();
			scheduleVO.setJobName(context.getJobDetail().getKey().getName());
			scheduleVO.setJobGroup(context.getJobDetail().getKey().getGroup());
			
			try {
				scheduleService.srvDeleteSchedule(scheduleVO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		// TODO Auto-generated method stub
		//잡 실패 시
		//	실패 결과 이메일 발송.
		super.jobExecutionVetoed(context);
	}
	
	
	
	

}
