package com.quartz.project.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.quartz.project.domain.ScheduleVO;

@Mapper
public interface ScheduleMapper {
	List<ScheduleVO> selectScheduleList();
}
