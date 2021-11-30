package com.mycompany.order.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Orders {
	String oid;
	String ozipcode;
	String oaddress1;
	String oaddress2;
	String oreceiver;
	String ophone;
	String otel;
	String omemo;
	String oemail;
	int ousedmileage;
	int obeforeprice;
	int oafterprice;
	String ostatus;
	String mid;
	String pmcode;
	Date odate;
	String cpid;
	List<OrderItem> items;
}
