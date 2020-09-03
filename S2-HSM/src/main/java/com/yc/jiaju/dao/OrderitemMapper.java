package com.yc.jiaju.dao;

import java.util.List;

import com.yc.jiaju.po.Orderitem;



public interface OrderitemMapper {
	List<Orderitem> selectAll();
	
	Orderitem selectById(int id);
	
	int insert(Orderitem cg);
	
	int update(Orderitem cg);
	
	int delete(int id);
}
