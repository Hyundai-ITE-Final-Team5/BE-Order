package com.mycompany.order.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.order.dto.OrderItem;
import com.mycompany.order.dto.Orders;
import com.mycompany.order.dto.PaymentMethod;
import com.mycompany.order.orderdao.OrderDao;

@Service
public class OrderService {
	
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
}
