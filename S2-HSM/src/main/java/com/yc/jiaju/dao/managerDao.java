package com.yc.jiaju.dao;

import java.util.*;

import com.yc.jiaju.po.Product;
import com.yc.jiaju.po.Samepdt;
import com.yc.jiaju.util.DBHelper;



public class managerDao {
	DBHelper dbh = new DBHelper();

	/*
	 * public boolean login(String name, String pwd) { String sql =
	 * "select * from tb_manager where name=? and password=?"; return dbh.count(sql,
	 * name, pwd) > 0; }
	 */
	public Map<String, Object> login(String name, String pwd) {
		String sql = "select * from tb_manager where name=? and password=?";
		List<Map<String, Object>> list = dbh.query(sql, name, pwd); // list.size()返回列表中元素个数
		if (list.size() == 0) {
			return null;
		} else {
			Map<String, Object> manager = list.get(0);
			return manager;
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param sp
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Map<String, Object>> queryProduct(Samepdt sp, int page, int size) {
		String where = "";
		List<Object> params = new ArrayList<>();
		if (sp.getSname() != null && sp.getSname().trim().isEmpty() == false) {
			// where += " and sname like '%"+sp.getSname()+"%'";
			where += " and sname like ?";
			params.add("%" + sp.getSname() + "%");
		}
		if (sp.getCid() != null) {
			if (sp.getCid() != 0) {
				where += " and a.cid = ?";
				params.add(sp.getCid());
			}
		}

		if (sp.getIsnew() != null) {
			where += " and isnew = ?";
			params.add(sp.getIsnew());
		}

		int begin = (page - 1) * size;
		params.add(begin);
		params.add(size);
		String sql = "SELECT sid,sname,image,a.cid,b.cname,isnew,price\n" +
				",isidea FROM `tb_samepdt` a,tb_category b WHERE a.cid=b.cid and 1=1" + where + " order by sid limit ?,?";
		return dbh.query(sql, params.toArray());
	}

	public int lastPage(Samepdt sp) {
		String where = "";
		List<Object> params = new ArrayList<>();
		if (sp.getSname() != null && sp.getSname().trim().isEmpty() == false) {
			where += " and sname like ?";
			params.add("%" + sp.getSname() + "%");
		}
		if (sp.getCid() != null) {
			if (sp.getCid() != 0) {
				where += " and cid = ?";
				params.add(sp.getCid());
			}
		}
		if (sp.getIsnew() != null) {
			where += " and isnew = ?";
			params.add(sp.getIsnew());
		}
		String sql = "select null from tb_samepdt where 1=1" + where;
		return dbh.count(sql, params.toArray());
	}

	// 新增
	public void insert(Samepdt sp) {
		String sql = "insert into tb_samepdt values(null,?,?,?,?,?,?)";
		dbh.update(sql, sp.getSname(), sp.getImage(), sp.getCid(), sp.getIsnew(), sp.getPrice(), sp.getIsidea());
	}

	// 分类
	public List<?> query() {
		// String sql="select DISTINCT(b.cname) from tb_samepdt a,tb_category b where a.cid=b.cid";"select distinct(cid) from tb_samepdt"
		String sql = "select * from tb_category where pid is not null";
		return dbh.query(sql);
	}

	// getcid
	/*
	 * public List<Map<String,Object>> getcid(Samepdt sp){ String
	 * sql="SELECT cid FROM tb_category WHERE cname=?"; return dbh.query(sql,
	 * sp.getCid()); }
	 */
	public void update(Samepdt sp) {
		String sql = "update tb_samepdt set " + "sname=?," + "image=?," + "cid=?," + "isnew=?," + "price=?,"
				+ "isidea=?" + " where sid=?";
		dbh.update(sql, sp.getSname(), sp.getImage(), sp.getCid(), sp.getIsnew(), sp.getPrice(), sp.getIsidea(),
				sp.getSid());
	}

	public void delete(int sid) {
		String sql = "delete from tb_samepdt where sid=?";
		dbh.update(sql, sid);
	}
	public List<Map<String, Object>> queryBySid(String sid) {
		String sql="select * from tb_product where sid=?";
		return new DBHelper().query(sql, sid);
	}
	public void insertp(Product pt) {
		String sql = "insert into tb_product values(null,?,?,?,?,?,?,now(),?,?,0)";
		dbh.update(sql, pt.getPname(), pt.getPrice(), pt.getSid(), pt.getPdesc(), pt.getImage(), pt.getStyle(),pt.getHot(),pt.getStock());
	}
	public void updatep(Product pt) {
		String sql = "update tb_product set Pname=?,image=?,pdesc=?,image=?,price=?,style=?,hot=?,stock=? where pid=?";
		dbh.update(sql, pt.getPname(), pt.getImage(), pt.getPdesc(), pt.getImage(), pt.getPrice(), pt.getStyle(),
				pt.getHot(),pt.getStock(),pt.getPid());
	}
	public void deletep(String pid) {
		String sql = "delete from tb_product where pid=?";
		dbh.update(sql, pid);
	}
}