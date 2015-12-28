package com.neustar.reservations.services.impl;

import java.math.BigDecimal;

public class PriceFactory {

	public static BigDecimal getPrice(int rowNum) {
		// TODO Auto-generated method stub
		BigDecimal price = BigDecimal.valueOf(-1.0);
		switch (rowNum)  {
		case 1:
			price = BigDecimal.valueOf(250.00);
			break;
		case 2:
			price = BigDecimal.valueOf(150.05);
			break;
		case 3:
			price = BigDecimal.valueOf(100.10);
			break;
		default :
			price = BigDecimal.valueOf(250.00);
			break;
		}
		return price;
	}
	

}
