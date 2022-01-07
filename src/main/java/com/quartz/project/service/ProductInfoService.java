package com.quartz.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quartz.project.domain.ProductInfoVO;
import com.quartz.project.repository.ProductInfoMapper;

@Service
public class ProductInfoService {
	@Autowired
	private ProductInfoMapper productInfoMapper;
	
	/**
	 * 상품 정보 저장 메소드
	 * @param productInfoVO
	 * (productCode, quantity) - 필수
	 */
	public void srvInsertProductInfo(ProductInfoVO productInfoVO) {
		
		productInfoMapper.insertProductInfo(productInfoVO);
	}
	
	/**
	 * 상품 리스트 출력 메소드
	 * @param productInfoVO
	 * @return
	 */
	public List<ProductInfoVO> srvSelectProductList(ProductInfoVO productInfoVO) {
		
		return productInfoMapper.selectProductInfoList(productInfoVO); 
	}

}
