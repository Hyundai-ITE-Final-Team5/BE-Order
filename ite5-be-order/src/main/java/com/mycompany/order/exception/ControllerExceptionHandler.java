package com.mycompany.order.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.standard.expression.GenericTokenExpression;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
	
	@ExceptionHandler
	public void handleOtherException(Exception e, 
			HttpServletResponse response) throws IOException {
		log.info(e.getMessage());
		e.printStackTrace();
		response.sendError(500);
	}
	
	@ExceptionHandler
	public void handleBadCredentialsException(BadCredentialsException e, 
			HttpServletResponse response) throws IOException {
		log.info(e.getMessage());
		response.sendError(401);
	}
	
	@ExceptionHandler
	public void handleAuthorizationException(AuthorizationServiceException e, 
			HttpServletResponse response) throws IOException {
		log.info(e.getMessage());
		response.sendError(403);
	}
	
	@ExceptionHandler
	public void handleExpiredJwtException(ExpiredJwtException e, 
			HttpServletResponse response) throws IOException {
		log.info(e.getMessage());
		response.sendError(403);
	}
}

