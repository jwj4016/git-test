package com.quartz.project.config;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SampleJob extends QuartzJobBean implements InterruptableJob{

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO 실행할 job logic 구현.
		System.out.println("샘플잡 테스트 수행중.");
		//1.검색어를 받아서 해당 검색어로 url 구성.
		//2.url 크롤링 시작.
		//3.크롤링 한 데이터 파싱.
		//4.파싱한 데이터 저장.
		//5.원하는 결과일 경우 메일 보내기
	}

}
