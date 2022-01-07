package com.quartz.project.domain;



import java.util.List;

import org.quartz.JobDataMap;

import lombok.Data;

@Data
public class ScheduleVO {
	private String scheduleNo;
	//searchKeyword, productCodeList 포함.
	private String searchKeyword;
	private List<String> productCodeList;
	
	private JobDataMap jobDataMap;
	
	private String jobClassName;
	private String schedName;
	private String triggerName;
	private String triggerGroup;
	private String jobName;
	private String jobGroup;
	private String description;
	private long nextFireTime;
	private long prevFireTime;
	private long priority;
	private String triggerState;
	private String triggerType;
	private long startTime;
	private long endTime;
	private String calendarName;
	private long misfireInstr;
//	private Object jobData;
	
	/** 실행일정 변환 값 */
	private String strExecutCycle;
	
	/** 크론 형식 표현 값 */
	private String cronExpression;
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
	
	public JobDataMap getJobDataMap() {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("searchKeyword", this.searchKeyword);
		jobDataMap.put("productCodeList", this.productCodeList);
		return jobDataMap;
	}
	
//	public String getCronExpression()
//	{
//		cronExpression = "";
//		
//		cronExpression = executScheduleSecond + " " + executScheduleMinute + " " + executScheduleHour;
//		
//		/*
//		 * executCycle:	A = 매일
//		 * 				B = 매주
//		 * 				C = 매월
//		 * 				D = 매년
//		 * 				E = 1회실행
//		 *              F = 매시 몇분 마다
//		 */
//		if ("A".equals(executCycle))
//		{
//			cronExpression += " * * ?";
//		}
//		else if ("B".equals(executCycle))
//		{
//			cronExpression += " ? * ";
//			
//			if ('1' == executScheduleDate.charAt(0))
//			{
//				cronExpression += "SUN,";
//			}
//			if ('1' == executScheduleDate.charAt(1))
//			{
//				cronExpression += "MON,";
//			}
//			if ('1' == executScheduleDate.charAt(2))
//			{
//				cronExpression += "TUE,";
//			}
//			if ('1' == executScheduleDate.charAt(3))
//			{
//				cronExpression += "WED,";
//			}
//			if ('1' == executScheduleDate.charAt(4))
//			{
//				cronExpression += "THU,";
//			}
//			if ('1' == executScheduleDate.charAt(5))
//			{
//				cronExpression += "FRI,";
//			}
//			if ('1' == executScheduleDate.charAt(6))
//			{
//				cronExpression += "SAT,";
//			}
//			
//			if (StringUtils.hasText(cronExpression) && ',' == cronExpression.charAt(cronExpression.length() - 1))
//			{
//				cronExpression = cronExpression.substring(0, cronExpression.length() -1);
//			}
//		}
//		else if ("C".equals(executCycle))
//		{
//			cronExpression += " " + executScheduleDate.substring(6) + " * ?";
//		}
//		else if ("D".equals(executCycle))
//		{
//			cronExpression += " " + executScheduleDate.substring(6) + " " + executScheduleDate.substring(4, 6) + " ?";
//		}
//		else if ("E".equals(executCycle))
//		{
//			cronExpression += " " + executScheduleDate.substring(6) + " " + executScheduleDate.substring(4, 6) + " ? " + executScheduleDate.substring(0, 4);
//		}
//		else if ("F".equals(executCycle))/*매시 몇문마다*/
//		{
//			cronExpression = "0 0/" + executScheduleMinute + " * * * ?";
//		}
//		
//		initStrExecutCycle();
//		
//		return cronExpression;
//	}
//	
//	
//
//	private void initStrExecutCycle()
//	{
//		strExecutCycle = "";
//		
//		strExecutCycle = executScheduleHour + "시 " + executScheduleMinute + "분 " + executScheduleSecond + "초";
//		
//		/*
//		 * executCycle:	A = 매일
//		 * 				B = 매주
//		 * 				C = 매월
//		 * 				D = 매년
//		 * 				E = 1회실행
//		 */
//		if ("A".equals(executCycle))
//		{
//			strExecutCycle = "매일 " + strExecutCycle;
//		}
//		else if ("B".equals(executCycle))
//		{
//			strExecutCycle += " ? * ";
//			
//			String strWeek = "";
//			
//			if ('1' == executScheduleDate.charAt(0))
//			{
//				strWeek += "일요일,";
//			}
//			if ('1' == executScheduleDate.charAt(1))
//			{
//				strWeek += "월요일,";
//			}
//			if ('1' == executScheduleDate.charAt(2))
//			{
//				strWeek += "화요일,";
//			}
//			if ('1' == executScheduleDate.charAt(3))
//			{
//				strWeek += "수요일,";
//			}
//			if ('1' == executScheduleDate.charAt(4))
//			{
//				strWeek += "목요일,";
//			}
//			if ('1' == executScheduleDate.charAt(5))
//			{
//				strWeek += "금요일,";
//			}
//			if ('1' == executScheduleDate.charAt(6))
//			{
//				strWeek += "토요일,";
//			}
//			
//			if (StringUtils.hasText(strWeek) && ',' == strWeek.charAt(strWeek.length() - 1))
//			{
//				strWeek = strWeek.substring(0, strWeek.length() -1);
//			}
//			
//			strExecutCycle = "매주 " + strWeek + " " + strExecutCycle;
//		}
//		else if ("C".equals(executCycle))
//		{
//			strExecutCycle = "매월 " + executScheduleDate.substring(6) + "일 " + strExecutCycle;
//		}
//		else if ("D".equals(executCycle))
//		{
//			strExecutCycle = "매년 " + executScheduleDate.substring(4, 6) + "월 " + executScheduleDate.substring(6) + "일 " + strExecutCycle;
//		}
//		else if ("E".equals(executCycle))
//		{
//			strExecutCycle = executScheduleDate.substring(0, 4) + "년 " + executScheduleDate.substring(4, 6) + "월 " + executScheduleDate.substring(6) + "일 " + strExecutCycle;
//		}
//		else if ("F".equals(executCycle))
//		{
//			strExecutCycle = "매시 " + executScheduleMinute + "분 간격";
//		}
//	}
}
