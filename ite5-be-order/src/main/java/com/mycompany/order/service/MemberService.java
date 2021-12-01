package com.mycompany.order.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.order.dto.Member;
import com.mycompany.order.memberdao.MemberDao;

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
