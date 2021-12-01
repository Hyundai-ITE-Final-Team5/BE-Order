package com.mycompany.order.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.order.dto.OrderItem;
import com.mycompany.order.dto.Orders;
import com.mycompany.order.dto.PaymentMethod;
import com.mycompany.order.dto.Product;
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

	@RequestMapping("/directorder/{psid}")
	public Map<String, String> directOrder(HttpServletRequest request, Orders order, OrderItem orderItem) {
		log.info("실행");

		Map<String, String> map = new HashMap();

		String mid = null;

		if (!request.getHeader("Authorization").equals("")) {
			String jwt = request.getHeader("Authorization").substring(7);
			Claims claims = JWTUtil.validateToken(jwt);
			mid = JWTUtil.getMid(claims);
		}

		if (mid == null) {
			map.put("result", "돌아가");
			return map;
		}

		order.setMid(mid);
		order.setOid(new Date().getTime() + "_" + mid);// 어떻게 정할까요
		order.setOstatus("주문완료");

		orderItem.setOid(order.getOid());

		OrderResult result = orderService.addDirectOrder(order, orderItem);

		map.put("result", result.toString());

		return map;
	}

	@PostMapping("/carttoorder")
	public Map<String, Object> carttoorder(HttpServletRequest request, @RequestBody Orders order) {
		log.info("실행");
		String mid = null;
		Map<String, Object> map = new HashMap();

		if (!request.getHeader("Authorization").equals("")) {
			String jwt = request.getHeader("Authorization").substring(7);
			Claims claims = JWTUtil.validateToken(jwt);
			mid = JWTUtil.getMid(claims);
		}

		if (mid == null) {
			map.put("result", "돌아가");
			return map;
		}

		order.setMid(mid);
		order.setOid(new Date().getTime() + "_" + mid);// 어떻게 정할까요
		order.setOstatus("주문완료");
		
		if (order.getOtel() == null) {
			order.setOtel("");
		}
		
		log.info(order.toString());

		OrderResult result = orderService.addOrder(order, order.getItems());

		map.put("oid", order.getOid());

		return map;
	}

	@RequestMapping("/orderlist")
	public List<Orders> orderlist(HttpServletRequest request) {
		log.info("실행");

		String mid = null;
		

		if (!request.getHeader("Authorization").equals("")) {
			String jwt = request.getHeader("Authorization").substring(7);
			Claims claims = JWTUtil.validateToken(jwt);
			mid = JWTUtil.getMid(claims);
		}

		List<Orders> orderList = new ArrayList();
		List<Orders> orders = orderService.getOrderList(mid);

		for (Orders order : orders) {
			List<OrderItem> items = orderService.getOrderItemByOid(order.getOid());
			order.setItems(items); //주문된 상품들 조회
			
			//psid로 상품 정보 채우기
			for(OrderItem item: items) {
				String[] pids = item.getPsid().split("_");
				String pid = pids[0];
				String pcid = pids[0] + "_" + pids[1];
				
				Product pd = orderService.getOrderdItems(pid, pcid);
				pd.setPsize(pids[2]);
				item.setItemInfo(pd);
			}
			
			orderList.add(order);
		}
		
		return orderList;
	}

	@GetMapping("/cancelorder/{oid}")
	public Map<String, Object> cancelorder(HttpServletRequest request, @PathVariable String oid) {
		log.info("실행");

		String mid = null;
		Map<String, Object> map = new HashMap();

		if (!request.getHeader("Authorization").equals("")) {
			String jwt = request.getHeader("Authorization").substring(7);
			Claims claims = JWTUtil.validateToken(jwt);
			mid = JWTUtil.getMid(claims);
		}

		if (mid == null) {
			map.put("result", "돌아가");
			return map;
		}

		int result = orderService.cancleOrder(mid, oid, "주문취소");

		map.put("result", result);

		return map;
	}
}
