package com.yc.jiaju.dao;

import java.util.List;
import java.util.Map;

import com.yc.jiaju.util.DBHelper;

public class addressDao {
	public List<Map<String,Object>> queryadd(String uid) {
		String sql="select * from tb_addr where uid=?";
		return new DBHelper().query(sql,uid);
	}
	public void setdft(String aid,String uid) {
		String sql="update tb_addr set dft=0 where dft = 1 and uid=?";
		new DBHelper().update(sql,uid);
		String sql1="update tb_addr set dft=1 where aid = ? and uid=?";
		new DBHelper().update(sql1, aid,uid);
	}
	public void delete(String aid) {
		String sql="delete from tb_addr where aid=?";
		new DBHelper().update(sql, aid);
	}
	public void change(String name,String phone,String addr,String sheng,String shi,String xian,String aid) {
		String sql="update tb_addr set name=?,phone=?,addr=?,sheng=?,shi=?,xian=? where aid = ? ";
		new DBHelper().update(sql, name,phone,addr,sheng,shi,xian,aid);
	}
	public void insert(String name,String phone,String addr,String sheng,String shi,String xian,String uid) {
		String sql="insert into tb_addr values(null,?,?,?,?,0,?,?,?)";
		new DBHelper().update(sql,uid, name,addr,phone,sheng,shi,xian);
	}
}
