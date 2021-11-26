package com.mycompany.order.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.order.dto.Member;
import com.mycompany.order.dto.OrderItem;
import com.mycompany.order.dto.Orders;
import com.mycompany.order.dto.PaymentMethod;
import com.mycompany.order.memberdao.MemberDao;
import com.mycompany.order.orderdao.OrderDao;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class MemberService {
	
	@Resource
	private MemberDao memberDao;
	
	public Member getMemberInfo(String mid) {
		return memberDao.getMemberInfo(mid);
	}
	
}
