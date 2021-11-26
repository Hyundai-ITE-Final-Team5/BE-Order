package com.mycompany.order.dto;

import lombok.Data;

@Data
public class OrderItem {
	String psid;
	String oid;
	int oicount;
	int oitotalprice;
}
