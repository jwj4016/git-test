package com.quartz.project;


import java.io.IOException;
import java.time.LocalDateTime;

import com.quartz.project.domain.ProductInfoVO;
import com.quartz.project.service.ProductInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUtil {
	public static void main(String[] args) throws IOException {
		System.out.println("test start");
		ProductInfoService productInfoService = new ProductInfoService();
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
		System.out.println("test completed");
		
	}

}
