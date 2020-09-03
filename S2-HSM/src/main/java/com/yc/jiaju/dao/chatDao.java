package com.yc.jiaju.dao;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.yc.jiaju.util.DBHelper;

public class chatDao {
	public Map<String,Object> query(String uid) {
		String sql="select * from tb_chat where uid=?";
		if(new DBHelper().query(sql, uid).size()!=0) {
			return new DBHelper().query(sql, uid).get(0);
		}else {
			List<Map<String,Object>> list=new DBHelper().query("select id from tb_manager ");
			int id= new Random().nextInt(list.size());
			
			String sql1="insert into tb_chat values(null,?,?,'[]',0)";
			new DBHelper().update(sql1, uid,list.get(id).get("id"));
			sql1="select * from tb_chat where uid=?";
			return new DBHelper().query(sql1, uid).get(0);
		}
		
	}
	public void insert(String uid,String content) {
		String sql="update tb_chat set content=?,mxx=mxx+1 where uid=?";
		new DBHelper().update(sql,content, uid);
	}
	public Map<String,Object> mlogin(String email,String password) {
		String sql="select * from tb_manager where email=? and password=?";
		return new DBHelper().query(sql,email, password).get(0);
	}
	public List<Map<String,Object>> mquerypeople(String mid) {
		String sql="select b.uname,b.head,b.statu,a.uid from tb_chat a,tb_user b where a.uid=b.uid and mid=?";
		return new DBHelper().query(sql, mid);
	}
	public Map<String,Object> mquery(String mid,String uid) {
		String sql="select * from tb_chat where mid=? and uid=?";
		return new DBHelper().query(sql, mid,uid).get(0);
	}
	public void minsert(String uid,String content) {
		String sql="update tb_chat set content=? where uid=?";
		new DBHelper().update(sql,content, uid);
	}
	public void resetxx(String uid) {
		String sql="update tb_chat set mxx=0 where uid=?";
		new DBHelper().update(sql, uid);
	}
}
