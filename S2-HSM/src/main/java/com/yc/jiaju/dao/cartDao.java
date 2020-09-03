package com.yc.jiaju.dao;

import java.util.List;
import java.util.Map;

import com.yc.jiaju.util.DBHelper;

/**
 * 
 * ¹ºÎï³µdao
 *
 */
public class cartDao {
	public void insert(String uid,String pid,String count) {
		String sql="select * from tb_cart where uid=? and pid=? ";
		List<Map<String,Object>> list= new DBHelper().query(sql, uid,pid);
		if(list.size()==0) {
			String sql1="insert into tb_cart values(null,?,?,?)";
			new DBHelper().update(sql1, uid,pid,count);
		}else {
			String sql2="update tb_cart set count=count+? where uid=? and pid=?";
			new DBHelper().update(sql2, count,uid,pid);
		}
		
		
	}
	public List<Map<String,Object>> query(String uid) {
		String sql="select a.sid,a.pid,a.pname ,a.price,a.image,a.style,b.count from tb_product a,tb_cart b where a.pid=b.pid and b.uid=? ";
		return new DBHelper().query(sql, uid);
	
	}
	public void changecount(String uid,String pid,String count) {
		String sql="update tb_cart set count=? where uid=? and pid=?";
		new DBHelper().update(sql, count,uid,pid);
	
	}
	public void deleteproduct(String uid,String pid) {
		String sql="delete from tb_cart where uid=? and pid=?";
		new DBHelper().update(sql, uid,pid);
	}
}
