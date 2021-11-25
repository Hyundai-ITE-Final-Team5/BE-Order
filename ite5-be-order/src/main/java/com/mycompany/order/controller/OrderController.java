package com.mycompany.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.order.dto.OrderItem;
import com.mycompany.order.dto.Orders;
import com.mycompany.order.dto.PaymentMethod;
import com.mycompany.order.security.JWTUtil;
import com.mycompany.order.service.OrderService;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
	
	@Resource
	private OrderService orderService;
	
	
	@RequestMapping("/paymethodList")
	public Map<String, Object> paymethodList() {
		log.info("실행");
		
		List<PaymentMethod> pmList = orderService.getPaymethodList();
		
		Map<String, Object> map = new HashMap();
		
		map.put("pmList", pmList);
		
		return map;
	}
	
	@PostMapping("/directorder/{psid}")
	public void directOrder(HttpServletRequest request, Orders order) {
		log.info("실행");
		String mid = null;

		if (request.getHeader("Authorization") != null) {
			String jwt = request.getHeader("Authorization").substring(7);
			Claims claims = JWTUtil.validateToken(jwt);
			mid = JWTUtil.getMid(claims);
		}
		
		order.setMid(mid);
		
	}
	
	@RequestMapping("/orderlist")
	public Map<String, Object> orderlist(HttpServletRequest request) {
		log.info("실행");
		
		String mid = null;

		if (request.getHeader("Authorization") != null) {
			String jwt = request.getHeader("Authorization").substring(7);
			Claims claims = JWTUtil.validateToken(jwt);
			mid = JWTUtil.getMid(claims);
		}
		
		List<Orders> orders = orderService.getOrderList(mid);
		
		Map<String, Object> itemList = new HashMap();
		
		for(Orders order: orders) {
			
			List<OrderItem> item = orderService.getOrderItemByOid(order.getOid());
			
			itemList.put(order.getOid(), item);
		}
		
		return itemList;
	}
}
