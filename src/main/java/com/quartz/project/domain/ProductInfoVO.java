package com.quartz.project.domain;

import lombok.Data;

@Data
public class ProductInfoVO extends CommonVO {
	long productInfoNo;
	String productCode;						//상품ID
	String categoryName1;					//카테고리 1
	String categoryName2;					//카테고리 2
	String categoryName3;					//카테고리 3
	String score;							//
	int saleCount;							//판매량
	String minPriceInfo;					//
	int quantity;							//판매 수량
	String brandName;						//브랜드명
	String productName;						//상품명
	String writeTime;						//
	String mainShopId;                      //
}
