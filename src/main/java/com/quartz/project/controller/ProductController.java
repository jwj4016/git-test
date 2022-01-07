package com.quartz.project.controller;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quartz.project.domain.ProductInfoVO;
import com.quartz.project.domain.ScheduleVO;
import com.quartz.project.quartzitems.job.CrwalingJob;
import com.quartz.project.quartzitems.joblistener.QuartzJobListener;
import com.quartz.project.service.ProductInfoService;
import com.quartz.project.service.schedule.ScheduleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {
	@Autowired
	private ProductInfoService productInfoService;
	
	/**
	 * productCode, quantity 필수
	 * @param scheduleVO
	 * @return
	 */
	@GetMapping("/insertProductInfo")
	public String insertProductInfo(@ModelAttribute ProductInfoVO productInfoVO) {
		ProductInfoVO pdiVo = new ProductInfoVO();
		pdiVo.setProductCode("testCode");
		pdiVo.setCategoryName1("category1");
		pdiVo.setCategoryName2("category2");
		pdiVo.setCategoryName3("category3");
		pdiVo.setScore("score");
		pdiVo.setSaleCount(0);
		pdiVo.setMinPriceInfo("minPrice");
		pdiVo.setQuantity(0);
		pdiVo.setBrandName("brand name");
		pdiVo.setProductName("product name");
		pdiVo.setWriteTime("2022.01.07 금요일");
		pdiVo.setMainShopId("mainShopId");
		pdiVo.setRegistDate(LocalDateTime.now());
		pdiVo.setUpdateDate(LocalDateTime.now());
		pdiVo.setDeleteDate(LocalDateTime.now());
		pdiVo.setStatus("status code");
		
		productInfoService.srvInsertProductInfo(pdiVo);
		return null;
	}
	
	@GetMapping("/selectProductInfoList")
	public String selectProductInfoList(@ModelAttribute ProductInfoVO productInfoVO) {
		
		productInfoService.srvSelectProductList(productInfoVO);
		return null;
	}
	

}
