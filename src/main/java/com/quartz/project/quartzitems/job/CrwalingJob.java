package com.quartz.project.quartzitems.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quartz.project.domain.ScheduleVO;
import com.quartz.project.service.MailService;
import com.quartz.project.service.schedule.ScheduleService;
import com.quartz.project.util.SSLUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CrwalingJob extends QuartzJobBean implements InterruptableJob{

	@Autowired
	MailService mailService;
	@Autowired
	ScheduleService scheduleService;
	
	@Override
	public void interrupt() throws UnableToInterruptJobException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO 실행할 job logic 구현.
		SSLUtil.ignoreSSL();
		
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		List<String> productIdList = new ArrayList<String>();
		productIdList = (List<String>) jobDataMap.get("productIdList");
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
			 * stream으로 처리한다면 덜 dirty한 코드가 만들어질 수도...
			 */
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, String> map = objectMapper.readValue(doc.getElementsByTag("body").get(0).text(),Map.class);
			
			//list에 파싱된 상품정보(map으로 된)를 할당한다.   
			List<?> list = objectMapper.readValue(map.get("data"), List.class);
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
			
			//상품이 있는지 확인하는 flag.
			Boolean isExistent  = false;
			//list에는 각각의 상품정보가 map형태로 담겨있다.
			Map<?, ?> temp = null;
			for(int i=0; i<list.size(); i++) {
				//임시 map에 상품정보 하나를 담는다.
				temp = (Map) list.get(i); 
				if(productIdList.contains(temp.get("product_count").toString()) ) {
					//임시 map에 담긴 상품코드와 jobDataMap에 등록된 상품코드가 일치.
					if((int)temp.get("quantity") > 0) {
						//해당 상품의 수량이 0보다 크다.
						log.info("상품ID = " + temp.get("product_count").toString());
						log.info("상품명 = " + temp.get("product_name").toString());
						log.info("수량 = " + temp.get("quantity").toString());
						//해당 상품의 수량이 0보다 크기 때문에 메일을 발송한다.
//						mai	lService.srvSendMail(null);
						//메일 보내고 나면 해당 잡 삭제해야될듯
						isExistent = true;
					}else {
						log.info("상품ID = " + temp.get("product_count").toString());
						log.info("상품명 = " + temp.get("product_name").toString());
						log.info("수량 = " + temp.get("quantity").toString());
					}
				}
			}
			//검색어에 해당하는 상품 중 quantity가 0보다 큰것이 있을 경우.
			if(isExistent) {
				//해당 잡 삭제
				ScheduleVO scheduleVO = new ScheduleVO();
				scheduleVO.setJobName(context.getJobDetail().getKey().getName());
				scheduleVO.setJobGroup(context.getJobDetail().getKey().getGroup());
				
				scheduleService.srvDeleteSchedule(scheduleVO);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
