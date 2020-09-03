package com.yc.jiaju.dao;



import com.yc.jiaju.util.DBHelper;

public class mygxinDao {
	public int querygopay(String uid) {
		String sql="SELECT * from tb_orders \n" +
				"where statu=0 and uid=?" ;
		return new DBHelper().count(sql,uid);
		//System.out.println(map);
	}
	public int querygetp(String uid) {
		String sql="SELECT * from tb_orders \n" +
				"where (statu=1 or statu=4)  and uid=?";
		return new DBHelper().count(sql,uid);
		//System.out.println(map);
	}
	public int queryeva(String uid) {
		String sql="select b.pid\n" +
				"from tb_orders a,\n" +
				"tb_orderitem b\n" +
				"where a.oid =b.oid\n" +
				"and b.estatu=0 and a.statu=3 and a.uid=?";
		return new DBHelper().count(sql,uid);
		//System.out.println(map);
	}
}
