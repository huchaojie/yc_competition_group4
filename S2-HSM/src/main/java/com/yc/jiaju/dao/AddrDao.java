package com.yc.jiaju.dao;

import java.util.List;
import java.util.Map;

import com.yc.jiaju.util.DBHelper;

public class AddrDao {

	public void insert(int uid,String name,String addr,String phone,String dft,String code) {
		String sql = "insert into tb_addr values(null,?,?,?,?,?,?)";
		new DBHelper().update(sql, uid,name,addr,phone,dft,code);
	}
	
	public void deldft() {
		String sql = "select * from tb_addr where dft=1";
		List<Map<String, Object>> user = new DBHelper().query(sql);
		int aid = (int) user.get(0).get("aid");
		String sql1 = "update tb_addr set dft=0 where aid=?";
		new DBHelper().update(sql1, aid);
	}
	
	public void updft(int aid) {
		deldft();
		String sql = "update tb_addr set dft=1 where aid=?";
		new DBHelper().update(sql, aid);
	}
	
	public List<Map<String,Object>> selectadd(int uid) {
		String sql = "select * from tb_addr where uid=?";
		DBHelper dbh = new DBHelper();
		List<Map<String,Object>> list = dbh.query(sql, uid);
		if(list.size()==0) {
			return null;
		}else {
			return list;
		}
	}
	
	public void deladd(int aid) {
		String sql = "delete from tb_addr where aid=?";
		new DBHelper().update(sql, aid);
	}
	
}
