package com.yc.jiaju.dao;


import java.util.List;
import java.util.Map;

import com.yc.jiaju.util.DBHelper;

public class UserDao {

	public void insert(String email,String name,String head,String upass) {
		String sql = "insert into tb_user values(null,?,?,1,null,?,?,0)";
		new DBHelper().update(sql, name,upass,head,email);
	}
	
	//uid�޸�ͷ��
	public void changeimg(int uid,String head) {
		String sql = "update tb_user set head=? where uid=?";
		new DBHelper().update(sql, head , uid);
	}
	
	//���email�Ƿ����
	public boolean checkemail(String email) {
		String sql = "select * from tb_user where email=?";
		return new DBHelper().count(sql, email)>0;
	}
	
	//���email�Ƿ����
	public int checkuid(String email) {
		String sql = "select uid from tb_user where email=?";
		List<Map<String,Object>> list = new DBHelper().query(sql, email);
		if(list.size()==0) {
			return 0;
		}else {
			return (int) list.get(0).get("uid");
		}
	}
	
	//����Ƿ��и��û�
	public boolean querylogin(String email,String upass) {
		String sql = "select * from tb_user where email=? and upass=?";
		return new DBHelper().count(sql, email,upass)>0;
	}
	
	//email�޸�����
	public void changepwd(String email,String upass) {
		String sql = "update tb_user set upass=? where email=?";
		new DBHelper().update(sql, upass , email);
	}
	
	//uid�޸�����
		public void changepwd(int uid,String upass) {
			String sql = "update tb_user set upass=? where uid=?";
			new DBHelper().update(sql, upass , uid);
		}
	
	//�޸�����
	public void updata(int uid,String uname,String sex,String birth) {
		String sql = "update tb_user set uname=?,sex=?,birth=? where uid=?";
		new DBHelper().update(sql, uname , sex,birth,uid);
	}
	
	//��¼ʱ��email��ѯuser
	public Map<String,Object> selectByLogin(String email,String upass) {
		String sql = "select * from tb_user where email=? and upass=?";
		DBHelper dbh = new DBHelper();
		List<Map<String,Object>> list = dbh.query(sql, email, upass);
		if(list.size()==0) {
			return null;
		}else {
			Map<String,Object> user = list.get(0);
			String sql1="update tb_user set statu=1 where uid=?";
			new DBHelper().update(sql1, user.get("uid"));
			return user;
		}
	}
	
	//��uid��ѯ
	public Map<String,Object> selectByLogin(int uid) {
		String sql = "select * from tb_user where uid=?";
		DBHelper dbh = new DBHelper();
		List<Map<String,Object>> list = dbh.query(sql, uid);
		if(list.size()==0) {
			return null;
		}else {
			Map<String,Object> user = list.get(0);
			return user;
		}
	}
	
	public static void main(String[] args) {
		//System.out.println(String.valueOf(new Date().getTime()).substring(8,13));
	}
	
}
