package com.mycompany.order.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.order.dto.OrderItem;
import com.mycompany.order.dto.Orders;
import com.mycompany.order.dto.PaymentMethod;
import com.mycompany.order.security.JWTUtil;
import com.mycompany.order.service.OrderService;
import com.mycompany.order.service.OrderService.OrderResult;

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
	public Map<String, String> directOrder(HttpServletRequest request, Orders order, OrderItem orderItem) {
		log.info("실행");
		
		Map<String, String> map = new HashMap();
		
		String mid = null;

		if (!request.getHeader("Authorization").equals("")) {
			String jwt = request.getHeader("Authorization").substring(7);
			Claims claims = JWTUtil.validateToken(jwt);
			mid = JWTUtil.getMid(claims);
		}

		if(mid == null) {
			 map.put("result", "돌아가");
			return map;
		}

		order.setMid(mid);
		order.setOid(new Date().getTime() + "_" + mid);//어떻게 정할까요
		order.setOstatus("주문완료");
		
		orderItem.setOid(order.getOid());
		
		OrderResult result = orderService.addDirectOrder(order, orderItem);

		map.put("result", result.toString());
		
		return map;
	}

	@PostMapping("/carttoorder")
	public Map<String, Object> order(HttpServletRequest request, Orders order, List<OrderItem> orderItems) {
		log.info("실행");
		String mid = null;
		Map<String, Object> map = new HashMap();

		if (!request.getHeader("Authorization").equals("")) {
			String jwt = request.getHeader("Authorization").substring(7);
			Claims claims = JWTUtil.validateToken(jwt);
			mid = JWTUtil.getMid(claims);
		}

		if(mid == null) {
			 map.put("result", "돌아가");
			return map;
		}

		order.setMid(mid);
		order.setOid(new Date().getTime() + "_" + mid);//어떻게 정할까요
		order.setOstatus("주문완료");
		
		for(OrderItem oi : orderItems) {
			oi.setOid(order.getOid());
		}
		
		OrderResult result = orderService.addOrder(order, orderItems);
		map.put("result", result);

		return map;
	}

	@RequestMapping("/orderlist")
	public Map<String, Object> orderlist(HttpServletRequest request) {
		log.info("실행");

		String mid = null;
		Map<String, Object> itemList = new HashMap();

		if (!request.getHeader("Authorization").equals("")) {
			String jwt = request.getHeader("Authorization").substring(7);
			Claims claims = JWTUtil.validateToken(jwt);
			mid = JWTUtil.getMid(claims);
		}
		
		if(mid == null) {
			itemList.put("result", "돌아가");
			return itemList;
		}

		List<Orders> orders = orderService.getOrderList(mid);

		for (Orders order : orders) {

			List<OrderItem> item = orderService.getOrderItemByOid(order.getOid());

			itemList.put(order.getOid(), item);
		}

		return itemList;
	}
	
	@PutMapping("/cancleorder/{oid}")
	public Map<String, Object> cancleorder(HttpServletRequest request, @PathVariable String oid) {
		log.info("실행");

		String mid = null;
		Map<String, Object> map = new HashMap();

		if (!request.getHeader("Authorization").equals("")) {
			String jwt = request.getHeader("Authorization").substring(7);
			Claims claims = JWTUtil.validateToken(jwt);
			mid = JWTUtil.getMid(claims);
		}
		
		if(mid == null) {
			map.put("result", "돌아가");
			return map;
		}
		
		int result = orderService.cancleOrder(oid, "주문취소");
		
		map.put("result", result);

		return map;
	}
}
