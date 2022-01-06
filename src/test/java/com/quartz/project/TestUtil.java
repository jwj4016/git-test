package com.quartz.project;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quartz.project.util.SSLUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUtil {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		SSLUtil.ignoreSSL();
		
//		String homeURL = "https://www.ddakpet.com";
//		Response response;
		try {
//			response = Jsoup.connect(homeURL).execute();
			
//			String jsessionid = response.cookie("JSESSIONID");
			
			Document doc = Jsoup.connect("https://www.ddakpet.com/petmama/shop/search/new-search")
					.data("search_value","로얄캐닌")
					.data("filter1","000")
					.data("filter2","000")
					.data("filter3","000")
					.data("filter4","000")
//					.cookie("JSESSIONID", jsessionid)
					.header("Host", "www.ddakpet.com")
					.ignoreContentType(true)
					.post();
			
			
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, String> map = objectMapper.readValue(doc.getElementsByTag("body").get(0).text(),Map.class);
			List list = objectMapper.readValue(map.get("data"), List.class);
			//product_count & quantity & product_name
//			log.debug(list.toString());
//			Stream<Map> stream = list.stream();
//			stream.forEach(num -> {
//				if(num.containsValue("2-15438")) {
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
				log.debug("상품ID = " + temp.get("product_count").toString());
				log.debug("수량 = " + temp.get("quantity").toString());
				log.debug("상품명 = " + temp.get("product_name").toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
