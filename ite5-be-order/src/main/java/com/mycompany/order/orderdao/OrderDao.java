package com.mycompany.order.orderdao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.order.dto.OrderItem;
import com.mycompany.order.dto.Orders;
import com.mycompany.order.dto.PaymentMethod;

@Mapper
public interface OrderDao {
	public List<PaymentMethod> getPaymethodList();
	public List<Orders> getOrderList(String mid);
	public List<OrderItem> getOrderItemByOid(String oid);
}
