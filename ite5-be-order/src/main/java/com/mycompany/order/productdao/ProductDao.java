package com.mycompany.order.productdao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.order.dto.Product;

@Mapper
public interface ProductDao {
	public Product getOrderdItems(String pid, String pcid);
}
