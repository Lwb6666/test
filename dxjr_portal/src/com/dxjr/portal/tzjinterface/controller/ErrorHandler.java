package com.dxjr.portal.tzjinterface.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dxjr.portal.tzjinterface.entity.ErrorResponse;
import com.dxjr.portal.tzjinterface.exception.PlatformException;


/**
 * @author WangQianJin
 * @title 捕捉投之家异常
 * @date 2016年3月23日
 */
@ControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler(value = PlatformException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse tzjNewInterfaceError(PlatformException exception) {
        return new ErrorResponse(exception.getCode(), 
        		exception.getMessage() == null ? "" : exception.getMessage());
    }
	
//	@ExceptionHandler(value = Throwable.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    public ErrorResponse intervalServerError(Throwable exception) {
//        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
//        		exception.getMessage() == null ? "" : exception.getMessage());
//    }
}