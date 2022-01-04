package com.quartz.project.domain;

import java.util.List;

import org.quartz.JobDataMap;
import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class ScheduleVOTEMP {

	/**
	 * serialVersionUID
	 */
//	private static final long serialVersionUID = -5844507306076140135L;

	/** 배치ID TN_BATCH, TN_schedule */
	private String batchId;
	
	/** 배치명 TN_BATCH */
	private String batchNm;
	
	private String jobSeCode;
	
	private String jobSeCodeNm;
	
	/** 설정파일명 */
	private String setupFileNm;
	
	/** 일정번호 TN_schedule */
	private String scheduleNo;
	
	/** 일정명 TN_schedule */
	private String scheduleNm;
	
	/** 실행주기 TN_schedule */
	private String executCycle;
	
	/** 실행일정일 TN_schedule */
	private String executScheduleDate;
	
	/** 실행일정시 TN_schedule */
	private String executScheduleHour;
	
	/** 실행일정분 TN_schedule */
	private String executScheduleMinute;
	
	/** 실행일정초 TN_schedule */
	private String executScheduleSecond;
	
	/** 최초등록자ID */
	private String frstRegisterId;
	
	/** 최초등록시점 */
	private String frstRegistPnttm;
	
	/** 최종수정자ID */
	private String lastUpdusrId;
	
	/** 최종수정시점 */
	private String lastUpdtPnttm;
	
	/** 실행일정 변환 값 */
	private String strExecutCycle;
	
	/** 크론 형식 표현 값 */
	private String cronExpression;
	
	/** 금일실행 여부 */
	private String executYn;
	
	/** 일정 파라미터 목록 */
	private List paramtrList;
	
	/** JobDataMap */
	private JobDataMap jobDataMap;

	/**
	 * scheduleVO의 실행일정을 CRON EXPRESSION 형태로 출력한다
	 * @return String
	 */
	public String getCronExpression()
	{
		cronExpression = "";
		
		cronExpression = executScheduleSecond + " " + executScheduleMinute + " " + executScheduleHour;
		
		/*
		 * executCycle:	A = 매일
		 * 				B = 매주
		 * 				C = 매월
		 * 				D = 매년
		 * 				E = 1회실행
		 *              F = 매시 몇분 마다
		 */
		if ("A".equals(executCycle))
		{
			cronExpression += " * * ?";
		}
		else if ("B".equals(executCycle))
		{
			cronExpression += " ? * ";
			
			if ('1' == executScheduleDate.charAt(0))
			{
				cronExpression += "SUN,";
			}
			if ('1' == executScheduleDate.charAt(1))
			{
				cronExpression += "MON,";
			}
			if ('1' == executScheduleDate.charAt(2))
			{
				cronExpression += "TUE,";
			}
			if ('1' == executScheduleDate.charAt(3))
			{
				cronExpression += "WED,";
			}
			if ('1' == executScheduleDate.charAt(4))
			{
				cronExpression += "THU,";
			}
			if ('1' == executScheduleDate.charAt(5))
			{
				cronExpression += "FRI,";
			}
			if ('1' == executScheduleDate.charAt(6))
			{
				cronExpression += "SAT,";
			}
			
			if (StringUtils.hasText(cronExpression) && ',' == cronExpression.charAt(cronExpression.length() - 1))
			{
				cronExpression = cronExpression.substring(0, cronExpression.length() -1);
			}
		}
		else if ("C".equals(executCycle))
		{
			cronExpression += " " + executScheduleDate.substring(6) + " * ?";
		}
		else if ("D".equals(executCycle))
		{
			cronExpression += " " + executScheduleDate.substring(6) + " " + executScheduleDate.substring(4, 6) + " ?";
		}
		else if ("E".equals(executCycle))
		{
			cronExpression += " " + executScheduleDate.substring(6) + " " + executScheduleDate.substring(4, 6) + " ? " + executScheduleDate.substring(0, 4);
		}
		else if ("F".equals(executCycle))/*매시 몇문마다*/
		{
			cronExpression = "0 0/" + executScheduleMinute + " * * * ?";
		}
		
		initStrExecutCycle();
		
		return cronExpression;
	}
	
	

	private void initStrExecutCycle()
	{
		strExecutCycle = "";
		
		strExecutCycle = executScheduleHour + "시 " + executScheduleMinute + "분 " + executScheduleSecond + "초";
		
		/*
		 * executCycle:	A = 매일
		 * 				B = 매주
		 * 				C = 매월
		 * 				D = 매년
		 * 				E = 1회실행
		 */
		if ("A".equals(executCycle))
		{
			strExecutCycle = "매일 " + strExecutCycle;
		}
		else if ("B".equals(executCycle))
		{
			strExecutCycle += " ? * ";
			
			String strWeek = "";
			
			if ('1' == executScheduleDate.charAt(0))
			{
				strWeek += "일요일,";
			}
			if ('1' == executScheduleDate.charAt(1))
			{
				strWeek += "월요일,";
			}
			if ('1' == executScheduleDate.charAt(2))
			{
				strWeek += "화요일,";
			}
			if ('1' == executScheduleDate.charAt(3))
			{
				strWeek += "수요일,";
			}
			if ('1' == executScheduleDate.charAt(4))
			{
				strWeek += "목요일,";
			}
			if ('1' == executScheduleDate.charAt(5))
			{
				strWeek += "금요일,";
			}
			if ('1' == executScheduleDate.charAt(6))
			{
				strWeek += "토요일,";
			}
			
			if (StringUtils.hasText(strWeek) && ',' == strWeek.charAt(strWeek.length() - 1))
			{
				strWeek = strWeek.substring(0, strWeek.length() -1);
			}
			
			strExecutCycle = "매주 " + strWeek + " " + strExecutCycle;
		}
		else if ("C".equals(executCycle))
		{
			strExecutCycle = "매월 " + executScheduleDate.substring(6) + "일 " + strExecutCycle;
		}
		else if ("D".equals(executCycle))
		{
			strExecutCycle = "매년 " + executScheduleDate.substring(4, 6) + "월 " + executScheduleDate.substring(6) + "일 " + strExecutCycle;
		}
		else if ("E".equals(executCycle))
		{
			strExecutCycle = executScheduleDate.substring(0, 4) + "년 " + executScheduleDate.substring(4, 6) + "월 " + executScheduleDate.substring(6) + "일 " + strExecutCycle;
		}
		else if ("F".equals(executCycle))
		{
			strExecutCycle = "매시 " + executScheduleMinute + "분 간격";
		}
	}

}
