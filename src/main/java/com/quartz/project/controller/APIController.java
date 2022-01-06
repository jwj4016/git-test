package com.quartz.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quartz.project.domain.ScheduleVO;
import com.quartz.project.service.schedule.ScheduleService;

@RestController
@RequestMapping("/api")
public class APIController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@GetMapping("/schedule")
	public Map<String, Object> getScheduleList() throws SchedulerException, Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<ScheduleVO> list = scheduleService.srvSelectSchdulList();
		result.put("key", list);
		return result;
	}

}
