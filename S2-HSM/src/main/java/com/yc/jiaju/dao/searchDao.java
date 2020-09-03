package com.yc.jiaju.dao;

import java.util.List;
import java.util.Map;
import com.yc.jiaju.util.DBHelper;

public class searchDao {
	DBHelper dbh=new DBHelper();
	public List<Map<String,Object>> search(String sname) {
		//String sql="select * from tb_samepdt where sname like '%"+sname+"%'";
		//return dbh.query(sql);
		String sql="select * from tb_samepdt where sname like ?";
		return dbh.query(sql,"%"+sname+"%");
	}
	/*
	 * public List<Map<String,Object>> searchAll() { String
	 * sql="select * from tb_samepdt"; return dbh.query(sql); }
	 */
	public List<Map<String,Object>> searchSort(int a,int b,String bywhat,String obj,String orderby,int page,int size){
		int begin = (page - 1) * size;
		String sql="SELECT * FROM tb_samepdt where cid=? or cid=? ORDER BY price "+orderby+" limit ?,?";
		return dbh.query(sql,a,b,begin,size);
	}
	//搜索页面商品排序
	public List<Map<String,Object>> searchSort(String sname,String bywhat,String orderby){
		String sql="select * from tb_samepdt where sid in(\n" +
				"SELECT sid from tb_samepdt where sname like ?\n" +
				")ORDER BY price "+orderby;
		return dbh.query(sql,"%"+sname+"%");
	}
	//搜索页新增商品查询
	public List<Map<String,Object>> searchIsNew(String sname){
		String sql="select * from tb_samepdt where sname like ? and isnew=1";
		return dbh.query(sql,"%"+sname+"%");
	}
	//新增商品查询
	public List<Map<String,Object>> searchIsNew(int page,int size,int a,int b){
		int begin = (page - 1) * size;
		String sql="select * from tb_samepdt where cid=? or cid=? isnew=1 limit ?,?";
		return dbh.query(sql,a,b,begin,size);
	}
	//尾页
	public int countPages(int size,int a,int b) {
		String sql="select a.pid from tb_product a join tb_samepdt b on a.sid=b.sid where b.cid=? or b.cid=?";
		int i = new DBHelper().count(sql,a,b);
		System.out.println(i);
		if (i % size == 0) {
			return i / size;
		} else
			return (i / size) + 1;
	}
	
}