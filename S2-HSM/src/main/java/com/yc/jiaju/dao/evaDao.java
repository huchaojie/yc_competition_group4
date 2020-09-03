package com.yc.jiaju.dao;

import java.util.List;
import java.util.Map;

import com.yc.jiaju.util.DBHelper;

public class evaDao {
	public List<Map<String,Object>> query(String uid){
		String sql="select \n" +
				"c.image,\n" +
				"c.pname,\n" +
				"c.style,\n" +
				"c.eva,\n" +
				"b.total/b.count price,\n" +
				"b.estatu,\n" +
				"a.star,\n" +
				"a.content ,"+
				"a.eid "+
				"from \n" +
				"tb_evaluate a,\n" +
				"tb_orderitem b,\n" +
				"tb_product c\n" +
				"where\n" +
				"a.oitid=b.id\n" +
				"and b.pid=c.pid\n" +
				"and a.uid=?\n" +
				"and b.oid in (\n" +
				"select oid from tb_orders where statu =3\n" +
				")";
		return new DBHelper().query(sql,uid);
	}
	public void save(String eid,String star,String content) {
		String sql="update  tb_evaluate set star=? , content=?,createtime=now() where eid=?";
		new DBHelper().update(sql, star,content,eid);
	}
	public void updateestatu(String eid) {
		String sql="update tb_orderitem set estatu=1 where id=(\n" +
				"select oitid\n" +
				"from tb_evaluate\n" +
				"where eid =?\n" +
				")";
		new DBHelper().update(sql,eid);
	}
	public void changeproducteva(String eid) {
		String sql="update  \n" +
				"tb_product \n" +
				"set eva=eva+1 \n" +
				"where pid=(\n" +
				"select b.pid\n" +
				"from tb_evaluate a,\n" +
				"tb_orderitem b\n" +
				"where a.oitid =b.id\n" +
				"and a.eid = ?\n" +
				")";
		new DBHelper().update(sql, eid);
	}
	public static void main(String[] args) {
		evaDao e = new evaDao();
		System.out.println(e.query("1"));
	}
	public List<Map<String,Object>> queryByPid(String sid){
		String sql="select a.uname,\n" +
				"a.head,d.star,d.eid,\n" +
				"d.content,d.createtime,c.style\n" +
				"from tb_user a,\n" +
				"tb_orderitem b,\n" +
				"tb_product c,\n" +
				"tb_evaluate d\n" +
				"where a.uid=d.uid\n" +
				"and  d.oitid = b.id\n" +
				"and  b.pid = c.pid\n" +
				"and  b.estatu=1\n" +
				"and  c.pid in (\n" +
				"select pid\n" +
				"from tb_product\n" +
				"where sid= ?\n" +
				")";
		
		return new DBHelper().query(sql, sid);
		
		
	}
}
