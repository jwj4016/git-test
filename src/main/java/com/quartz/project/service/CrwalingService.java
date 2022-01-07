package com.quartz.project.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quartz.project.domain.ProductInfoVO;
import com.quartz.project.domain.ScheduleVO;
import com.quartz.project.service.schedule.ScheduleService;
import com.quartz.project.util.SSLUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrwalingService {
	@Autowired
	ProductInfoService productInfoService;
	@Autowired
	ScheduleService scheduleService;
	
	@SuppressWarnings("unchecked")
	public void srvCrwalingJob(JobExecutionContext context) {
		// TODO 실행할 job logic 구현.
		
		/**
		 * STEP1. ssl 무시
		 */
		SSLUtil.ignoreSSL();
		
		/**
		 * STEP2. JobExecutionContext에서 jobDataMap 객체 가져오기.
		 */
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		
		try {
			/**
			 * STEP3. client에게 target(쇼핑몰)에서 검색할 검색어를 받아서, 해당 검색어로 url 구성 후 request.
			 */
			Document doc = Jsoup.connect("https://www.ddakpet.com/petmama/shop/search/new-search")
					.data("search_value",jobDataMap.getString("searchKeyword"))
					.data("filter1","000")
					.data("filter2","000")
					.data("filter3","000")
					.data("filter4","000")
					.header("Host", "www.ddakpet.com")
					.ignoreContentType(true)
					.post();
			
			/**
			 * STEP4. 받아온 데이터 파싱.
			 * stream으로 처리한다면 덜 dirty한 코드가 만들어질 수도...
			 */
			ObjectMapper objectMapper = new ObjectMapper();
			//받아온 DATA를 JOSN
			Map<String, String> map = objectMapper.readValue(doc.getElementsByTag("body").get(0).text(), Map.class);
			List<?> list = objectMapper.readValue(map.get("data"), List.class);
//					Stream<Map> stream = list.stream();
//					stream.forEach(num -> {
//						if(num.containsValue(jobDataMap.getString("productCode"))) {
//							if((Integer)num.get("quantity")>0) {
//								log.debug("quantity = " + num.get("quantity").toString());
//							}else {
//								log.debug("quantity = " + num.get("quantity").toString());
//							}
//						}
//					});
			
			/**
			 * STEP5. 파싱한 데이터에서 상품 정보를 확인한다.(target 상품이 품절인지 아닌지 확인.)
			 */
			//상품 수량이 0보다 큰지 확인하기 위한 flag
			boolean isProductQuantityBiggerThanZero = false;
			
			//상품 정보 저장을 위한 파라미터 VO 객체 생성.
			//jobDataMap에 넣은 ProductInfoVO 객체를 전달해서 ProductInfoVO객체가 중복 생성되는 것을 방지한다.
			//job이 실행되고 나면 가비지 컬렉터가 지우지 않을까??
//			ProductInfoVO productInfoVO = (ProductInfoVO) jobDataMap.get("productInfoVO");
			ProductInfoVO productInfoVO = new ProductInfoVO();

			//jobDataMap(key:"productCodeList")에는 client 측에서 요청한 '검색하고자 하는 상품 코드 리스트'가 들어있다.
			List<String> productCodeList = new ArrayList<String>();
			productCodeList = (List<String>) jobDataMap.get("productCodeList");
			
			//list에는 각각의 상품정보가 map형태로 담겨있다.
			Map<?, ?> temp = null;
			for(int i=0; i<list.size(); i++) {
				//임시 map에 상품정보를 하나씩 담는다.
				temp = (Map<String, String>) list.get(i); 
				if(productCodeList.contains(temp.get("product_count").toString()) ) {
					//크롤링한 상품정보의 상품코드(temp)와 client가 요청한 상품코드(productCodeList)가 일치할 경우.
					if((int)temp.get("quantity") > 0) {
						//크롤링한 상품의 수량이 0보다 클 경우.
						
						//해당 상품 정보를 전달하기 위한 파라미터 구성.
						productInfoVO.setProductCode(temp.get("product_count").toString());
						productInfoVO.setCategoryName1(temp.get("category_name1").toString());
						productInfoVO.setCategoryName2(temp.get("category_name2").toString());
						productInfoVO.setCategoryName3(temp.get("category_name3").toString());
						productInfoVO.setScore(temp.get("score").toString());
						productInfoVO.setSaleCount((int)temp.get("sale_count"));
						productInfoVO.setMinPriceInfo(temp.get("min_price_info").toString());
						productInfoVO.setQuantity((int)temp.get("quantity"));
						productInfoVO.setBrandName(temp.get("brand_name").toString());
						productInfoVO.setProductName(temp.get("product_name").toString());
						productInfoVO.setWriteTime(temp.get("write_time").toString());
						productInfoVO.setMainShopId(temp.get("main_shop_id").toString());
						//enum으로 리팩토링 가능??
						productInfoVO.setStatus("1");
						
						log.info("PRODUCT INFO = ", productInfoVO.toString());
						
						//상품 정보 db 저장.
						productInfoService.srvInsertProductInfo(productInfoVO);
						
						//상품이 품절이 아님을 알리기 위한 메일 발송.
//						mailService.srvSendMail(null);
						

						/**
						 * joblistener를 사용할 경우
						 */
						//메일을 보내고 난 후 해당 잡을 삭제한다.
						//삭제하지 않을 경우 지속적으로 메일이 발송된다.
						//삭제하지 않고 중단하는게 나은가?
						//상품 존재 유무를 알리는 flag 값을 context에 넣어 전달한다.
//						context.put("isProductDataExistent",true);
						/**
						 * end
						 */
						
						isProductQuantityBiggerThanZero = true;
					}
				}
			}
			
			
			/**
			 * STEP6. 상품이 있을 경우에 해당 job을 삭제한다.
			 * job을 삭제하지 않을 경우 지속적으로 메일이 발송되는 문제가 발생한다.
			 */
			if(isProductQuantityBiggerThanZero) {
				//job을 삭제하기 위한 파라미터 구성.
				ScheduleVO scheduleVO = new ScheduleVO();
				scheduleVO.setJobName(context.getJobDetail().getKey().getName());
				scheduleVO.setJobGroup(context.getJobDetail().getKey().getGroup());
				
				//job 삭제.
				scheduleService.srvDeleteSchedule(scheduleVO);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
