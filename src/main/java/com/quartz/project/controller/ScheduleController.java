package com.quartz.project.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quartz.project.domain.ScheduleVO;
import com.quartz.project.quartzitems.job.CrwalingJob;
import com.quartz.project.quartzitems.joblistener.QuartzJobListener;
import com.quartz.project.service.schedule.ScheduleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;
	
	@GetMapping("/")
	public String main() {
		return "table";
	}
	
	
	@GetMapping("/startScheduler")
	public String startScheduler() throws SchedulerException, Exception {
		scheduleService.srvStartScheduler(new CrwalingJob());
		return null;
	}
	
	@GetMapping("/standbyScheduler")
	public String standbyScheduler() throws SchedulerException, Exception {
		scheduleService.srvStandByScheduler();
		return null;
	}
	
	
	@GetMapping("/insertSchedule")
	public String insertSchedule(@ModelAttribute ScheduleVO scheduleVO) throws SchedulerException, Exception {
		//job object 를 넘길지 job 이름만 넘길지 고민...
		//job object를 클라이언트 측에서 넘기는 방법 고민.
		//job object를 클라이언트에서 넘기기 힘들기 때문에 일단 job이름만 넘기기.
		//1.job object로 넘기기
//		Class<Job> myJob = (Class<Job>) Class.forName(scheduleVO.getJobName());
//		scheduleService.srvInsertSchedule(scheduleVO, myJob);
		
		//2.job 이름으로 넘기
		scheduleService.srvInsertSchedule(scheduleVO);
		return null;
	}
	
	@GetMapping("/updateSchedule")
	public String updateSchedule(@ModelAttribute ScheduleVO scheduleVO) throws SchedulerException, Exception {
		scheduleService.srvUpdateSchedule(scheduleVO);
		return null;
	}
	
	@GetMapping("/addJobListener")
	public String addJobListener(@ModelAttribute ScheduleVO scheduleVO) throws SchedulerException, Exception {
		scheduleService.srvAddJobListener(scheduleVO, new QuartzJobListener());
		return null;
	}
	
	@ResponseBody
	@GetMapping("/getScheduleList")
	public Map<String, Object> getScheduleList() throws SchedulerException, Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<ScheduleVO> list = scheduleService.srvSelectSchdulList();
		result.put("key", list);
		return result;
	}
	
	@GetMapping("/deleteScheulde")
	public String deleteSchedule(@ModelAttribute ScheduleVO scheduleVO) throws SchedulerException, Exception {
		scheduleService.srvDeleteSchedule(scheduleVO);
		return null;
	}
	

}
