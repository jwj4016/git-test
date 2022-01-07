package com.quartz.project.repository;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.quartz.project.domain.ProductInfoVO;

@Mapper
public interface ProductInfoMapper {
	 public void insertProductInfo(ProductInfoVO productInfoVo);
	 public List<ProductInfoVO> selectProductInfoList(ProductInfoVO productInfoVo);

}
