package com.quartz.project.config;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quartz.project.util.SSLUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CrwalingJob extends QuartzJobBean implements InterruptableJob{

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO 실행할 job logic 구현.
		SSLUtil.ignoreSSL();
		
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		
//		String homeURL = "https://www.ddakpet.com";
//		Response response;
		try {
//			response = Jsoup.connect(homeURL).execute();
//			
//			String jsessionid = response.cookie("JSESSIONID");
			
			/**
			 * 1.검색어를 받아서 해당 검색어로 url 구성 request.
			 */
			Document doc = Jsoup.connect("https://www.ddakpet.com/petmama/shop/search/new-search")
					.data("search_value",jobDataMap.getString("searchKeyword"))
					.data("filter1","000")
					.data("filter2","000")
					.data("filter3","000")
					.data("filter4","000")
//					.cookie("JSESSIONID", jsessionid)
					.header("Host", "www.ddakpet.com")
					.ignoreContentType(true)
					.post();
			
			/**
			 * 2.받아온 데이터 파싱.
			 */
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, String> map = objectMapper.readValue(doc.getElementsByTag("body").get(0).text(),Map.class);
			List list = objectMapper.readValue(map.get("data"), List.class);
//			Stream<Map> stream = list.stream();
//			stream.forEach(num -> {
//				if(num.containsValue(jobDataMap.getString("productId"))) {
//					if((Integer)num.get("quantity")>0) {
//						log.debug("quantity = " + num.get("quantity").toString());
//					}else {
//						log.debug("quantity = " + num.get("quantity").toString());
//					}
//				}
//			});
			Map temp = null;
			for(int i=0; i<list.size(); i++) {
				temp = (Map) list.get(i); 
				if(jobDataMap.get("productId").toString().equals(temp.get("product_count")) ) {
					//jobdatampa에 등록된 상품코드와 해당 데이터의 상품코드가 일치.
					if((int)temp.get("quantity") > 0) {
						//해당 상품의 수량이 0보다 크다.
						log.debug("상품ID = " + temp.get("product_count").toString());
						log.debug("상품명 = " + temp.get("product_name").toString());
						log.debug("수량 = " + temp.get("quantity").toString());
					}
				}
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		//2.url 크롤링 시작.
		//3.크롤링 한 데이터 파싱.
		//4.파싱한 데이터 저장.
		//5.원하는 결과일 경우 메일 보내기
	}

}
