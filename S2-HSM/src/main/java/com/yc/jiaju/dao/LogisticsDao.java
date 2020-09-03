package com.yc.jiaju.dao;

import com.yc.jiaju.util.DBHelper;

public class LogisticsDao {
	public void insert(String oid,String company,String lids,String name,String phone,String addr) {
		String sql="select * from tb_logistics where oid=?";
		
		if(new DBHelper().query(sql, oid).size()==0) {
			sql="insert into tb_logistics values(null,?,?,?,?,?,?)";
			new DBHelper().update(sql, company,lids,name,phone,addr,oid);
		}else {
			sql="update tb_logistics set company=?,lids=? where oid=? ";
			new DBHelper().update(sql, company,lids,oid);
		}
		sql="update tb_orders set statu=4 where oid=?";
		new DBHelper().update(sql, oid);
	}
	/*
	 * public void update(String oid,String name,String phone,String addr) { String
	 * sql="select * from tb_logistics where oid=?"; System.out.println(new
	 * DBHelper().query(sql, oid)==null); if(new DBHelper().query(sql,
	 * oid).size()==0) {
	 * sql="insert into tb_logistics values(null,null,null,?,?,?,?)"; new
	 * DBHelper().update(sql, name,phone,addr,oid); }else {
	 * sql="update tb_logistics set name=?,phone=?,addr=? where oid=? "; new
	 * DBHelper().update(sql, name,phone,addr,oid); }
	 * 
	 * }
	 */
}
