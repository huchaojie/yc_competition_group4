package com.yc.jiaju.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import com.yc.jiaju.po.Orders;



public interface OrdersMapper {
	
	@Insert("insert into tb_orders values(null,#{uid},#{aid},#{statu},#{total},now(),#{pay},#{oids})")
	@Options(useGeneratedKeys = true,keyProperty = "oid" ,keyColumn = "oid")
	int insert(Orders od);
	
	
}
