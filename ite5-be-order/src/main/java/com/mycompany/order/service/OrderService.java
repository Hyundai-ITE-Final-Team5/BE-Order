package com.mycompany.order.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.order.dto.OrderItem;
import com.mycompany.order.dto.Orders;
import com.mycompany.order.dto.PaymentMethod;
import com.mycompany.order.orderdao.OrderDao;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class OrderService {
	
	public enum OrderResult {
		SUCCESS,
		FAIL
	}
	
	@Resource OrderDao orderDao;
	
	public List<PaymentMethod> getPaymethodList(){
		return orderDao.getPaymethodList();
	}
	public List<Orders> getOrderList(String mid){
		return orderDao.getOrderList(mid);
	}
	
	public List<OrderItem> getOrderItemByOid(String oid){
		return orderDao.getOrderItemByOid(oid);
	}
	
	public int cancleOrder(String oid, String status) {
		return orderDao.cancleOrder(oid, status);
	}
	
	@Transactional
	public OrderResult addDirectOrder(Orders order, OrderItem orderItem){
		try {
			orderDao.addOders(order);
			orderDao.addOderItem(orderItem);
			
			return OrderResult.SUCCESS;
		} catch (Exception e) {
			log.info("실패라고?");
			return OrderResult.FAIL;
		}
	}
	
	@Transactional
	public OrderResult addOrder(Orders order, List<OrderItem> orderItems){
		try {
			int orderResult = orderDao.addOders(order);
			log.info(orderResult + " ");
			for(OrderItem oi : orderItems) {
				int orderItemResult = orderDao.addOderItem(oi);
				log.info(orderItemResult + " ");
			}
			
			return OrderResult.SUCCESS;
		} catch (Exception e) {
			log.info("실패라고?");
			return OrderResult.FAIL;
		}
	}
}
