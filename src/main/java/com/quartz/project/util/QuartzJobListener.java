package com.quartz.project.util;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

public class QuartzJobListener extends JobListenerSupport {

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
		//잡 실패 시
		//	실패 결과 이메일 발송.
		//잡 성공 시
		//  성공 결과 이메일 발송.
    }
	

}
