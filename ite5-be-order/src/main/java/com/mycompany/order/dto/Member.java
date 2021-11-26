package com.mycompany.order.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Member {
	private String mid;
	private String mpassword;
	private String mname;
	private String memail;
	private String mphone;
	private String mtel;
	private String mzipcode;
	private String maddress1;
	private String maddress2;
	private Date mbirth;
	private int mgender;
	private String mrefid;
	private String mlogintype;
	private int mtosno;
	private int menabled;
	private String mrole;
	private int mgrade;
	private int mmileage;
}
