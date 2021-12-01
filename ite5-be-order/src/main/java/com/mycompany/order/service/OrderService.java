package com.mycompany.order.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.order.dto.OrderItem;
import com.mycompany.order.dto.Orders;
import com.mycompany.order.dto.PaymentMethod;
import com.mycompany.order.dto.Product;
import com.mycompany.order.memberdao.MemberDao;
import com.mycompany.order.orderdao.OrderDao;
import com.mycompany.order.productdao.ProductDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {

	public enum OrderResult {
		SUCCESS, FAIL
	}

	@Resource
	private OrderDao orderDao;
	@Resource
	private MemberDao memberDao;
	@Resource
	private ProductDao productDao;
	
	public Product getOrderdItems(String pid, String pcid) {
		return productDao.getOrderdItems(pid, pcid);
	}

	public List<PaymentMethod> getPaymethodList() {
		return orderDao.getPaymethodList();
	}

	public List<Orders> getOrderList(String mid) {
		return orderDao.getOrderList(mid);
	}

	public List<OrderItem> getOrderItemByOid(String oid) {
		return orderDao.getOrderItemByOid(oid);
	}

	public int cancleOrder(String mid, String oid, String status) {
		return orderDao.cancleOrder(mid, oid, status);
	}

	@Transactional
	public OrderResult addDirectOrder(Orders order, OrderItem orderItem) {
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
	public OrderResult addOrder(Orders order, List<OrderItem> orderItems) {
		try {
			orderDao.addOders(order);
			String oid = order.getOid();
			for (OrderItem oi : orderItems) {
				oi.setOid(oid);
				orderDao.addOderItem(oi);
				// 카트제거
				memberDao.removeCart(order.getMid(), oi.getPsid());
				// 재고감소
			}			
			order.setOusedmileage((int)(order.getOafterprice()*0.05) - order.getOusedmileage());
			memberDao.updateMileage(order);
			// 쿠폰상태변경
			if (!order.getCpid().equals(""))
				memberDao.usingCoupon(order);
			
			return OrderResult.SUCCESS;
		} catch (Exception e) {
			log.info(e.toString());
			log.info("실패?");
			return OrderResult.FAIL;
		}
	}

}
