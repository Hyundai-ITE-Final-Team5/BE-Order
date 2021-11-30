package com.mycompany.order.dto;

import lombok.Data;

@Data
public class Product {
	private String bname;
	private String pid;
	private String pname;
	private int pstatus;
	private int pcprice;
	private String pcimg1;
	private String pcid;
}