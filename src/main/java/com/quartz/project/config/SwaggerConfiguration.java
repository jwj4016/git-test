//package com.quartz.project.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfiguration {
//	
//	@Bean
//	public Docket swaagerApi() {
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
//				.apis(RequestHandlerSelectors.basePackage("com.quartz.project.controller"))
//				.paths(PathSelectors.any())
//				.build()
//				.useDefaultResponseMessages(false);
//				
//	}
//	
//	private ApiInfo swaggerInfo() {
//		return new ApiInfoBuilder().title("Spring API Documentation")
//				.description("엡 개발시 사용되는 서버 API에 대한 연동 문서입니다.")
//				.license("").licenseUrl("").version("1").build();
//		
//	}
//
//}
