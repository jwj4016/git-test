package com.quartz.project.exception;

import org.json.simple.JSONObject;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalException {
	
	@ExceptionHandler(DuplicateEmailException.class)
	public JSONObject duplicateEmailException() {
		return ErrorResponse.JsonErrorResponse(400, "중복된 이메일 입니다.");
	}
	
	@ExceptionHandler(SchedulerException.class)
	public JSONObject schedulerExceptionException() {
		return ErrorResponse.JsonErrorResponse(400, "quart error");
	}
	
	@ExceptionHandler(Exception.class)
	public JSONObject ExceptionException() {
		return ErrorResponse.JsonErrorResponse(400, "error");
	}
	
	

}
