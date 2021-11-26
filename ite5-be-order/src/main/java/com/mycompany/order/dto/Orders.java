package com.mycompany.order.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Orders {
	String oid;
	String ozipcode;
	String oaddress;
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
	Object oitems;
}
