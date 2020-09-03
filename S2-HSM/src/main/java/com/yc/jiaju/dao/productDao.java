package com.yc.jiaju.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.yc.jiaju.po.Samepdt;
import com.yc.jiaju.util.DBHelper;
import com.yc.jiaju.util.MyBatisHelper;

public class productDao {
	DBHelper dbh = new DBHelper();

	public List<Map<String, Object>> queryProduct(int a, int b) {
		String sql = "select * from tb_samepdt where cid=? or cid=?";
		return dbh.query(sql, a, b);
	}
	
	/**
	 * 抗疫商品查询
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Map<String, Object>> queryPagefight(int page, int size) {
		int begin = (page - 1) * size;
		String sql = "select * from tb_samepdt where cid=15 limit ?,?";
		return dbh.query(sql, begin, size);
	}

	//分页查询创意家居
	public List<Map<String, Object>> queryPageidea(int page, int size) {
		int begin = (page - 1) * size;
		String sql = "select * from tb_samepdt where isidea=1 limit ?,?";
		return dbh.query(sql, begin, size);
	}
	//分页查询
	public List<Map<String, Object>> queryPage(int a,int b,int page, int size) {
		int begin = (page - 1) * size;
		String sql = "select * from tb_samepdt where cid=? or cid=? limit ?,?";
		return dbh.query(sql,a,b,begin, size);
	}
	//尾页
	public int countPages(int size,int a,int b) {
		String sql="select * from tb_samepdt where cid=? or cid=?";
		int i = new DBHelper().count(sql,a,b);
		System.out.println(i);
		if (i % size == 0) {
			return i / size;
		} else
			return (i / size) + 1;
	}
	//创意家居分页查询尾页
	public int countPages(int size) {
		String sql="select * from tb_samepdt";
		int i = new DBHelper().count(sql);
		System.out.println(i);
		if (i % size == 0) {
			return i / size;
		} else
			return (i / size) + 1;
	}

}
