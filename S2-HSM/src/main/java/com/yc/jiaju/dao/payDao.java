package com.yc.jiaju.dao;

import java.util.List;
import java.util.Map;

import com.yc.jiaju.util.DBHelper;

public class payDao {
	public List<Map<String,Object>> listProductFromCart(String uid ,String pid) {
		String sql="select a.pid,a.pname ,a.price,a.image,a.style,b.count from tb_product a,tb_cart b where a.pid=b.pid and b.uid=? and b.pid=?";
		return new DBHelper().query(sql, uid,pid);
	}
	public List<Map<String,Object>> listProduct(String pid) {
		String sql="SELECT\n" +
				"	pid,\n" +
				"	pname,\n" +
				"	price,\n" +
				"	image,\n" +
				"	style\n" +
				"FROM\n" +
				"	tb_product \n" +
				"WHERE\n" +
				"	pid =?";
		return new DBHelper().query(sql, pid);
	}
}
