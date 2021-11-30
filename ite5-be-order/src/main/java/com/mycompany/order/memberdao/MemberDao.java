package com.mycompany.order.memberdao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.order.dto.Member;
import com.mycompany.order.dto.Orders;

@Mapper
public interface MemberDao {
	public Member getMemberInfo(String mid);
	public int removeCart(String mid, String psid);
	public int updateMileage(Orders order);
	public int usingCoupon(Orders order);
}
