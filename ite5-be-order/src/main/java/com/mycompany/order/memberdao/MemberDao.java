package com.mycompany.order.memberdao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.order.dto.Member;

@Mapper
public interface MemberDao {
	public Member getMemberInfo(String mid);
}
