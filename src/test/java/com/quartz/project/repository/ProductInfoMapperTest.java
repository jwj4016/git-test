package com.quartz.project.repository;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.quartz.project.domain.ProductInfoVO;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
public class ProductInfoMapperTest {
	@Autowired
	private ProductInfoMapper productInfoMapper;
	
	private ProductInfoVO pdiVo;

	@Before
	public void setUp() {
		pdiVo = new ProductInfoVO();
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
	}
	@Test
	public void testInsertProductInfo() {
		System.out.println("test start");
		productInfoMapper.insertProductInfo(pdiVo);
		System.out.println("test completed");
		
		
		
		fail("Not yet implemented");
	}

}
