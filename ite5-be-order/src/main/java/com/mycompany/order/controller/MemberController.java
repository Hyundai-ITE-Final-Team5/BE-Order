package com.mycompany.order.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.order.dto.Member;
import com.mycompany.order.security.JWTUtil;
import com.mycompany.order.service.MemberService;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {
	
	@Resource
	private MemberService memberService;
	
	@RequestMapping("/info")
	public Map<String, Object> info(HttpServletRequest request) {
		log.info("실행");
		Map<String, Object> map = new HashMap();
		
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
		
		Member member = memberService.getMemberInfo(mid);
		
		map.put("memberInfo", member);
		
		return map;
	}

}
