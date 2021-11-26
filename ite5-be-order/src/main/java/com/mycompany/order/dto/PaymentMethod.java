package com.mycompany.order.dto;

import lombok.Data;

@Data
public class PaymentMethod {
	String pmcode;
	String pmcompany;
	int pmmethod;
}
