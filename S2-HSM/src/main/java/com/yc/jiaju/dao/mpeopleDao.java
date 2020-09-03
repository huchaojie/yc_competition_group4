package com.yc.jiaju.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.yc.jiaju.po.manager;
import com.yc.jiaju.util.DBHelper;

public class mpeopleDao {
	public List<Map<String, Object>> query(manager sp, int page, int size) {
		String where = "";
		List<Object> params = new ArrayList<>();
		if (sp.getName() != null && sp.getName().trim().isEmpty() == false) {
			// where += " and sname like '%"+sp.getSname()+"%'";
			where += " and name like ?";
			params.add("%" + sp.getName().trim() + "%");
		}
		if (sp.getImage() != null && sp.getImage().trim().isEmpty() == false) {
			
				where += " and image like ?";
				params.add("%"+sp.getImage().trim()+"%");
			
		}

		
		int begin = (page - 1) * size;
		params.add(begin);
		params.add(size);
		String sql = "select * from tb_manager where id!=1 " + where + " limit ?,?";
		return new DBHelper().query(sql, params.toArray());
	}
	public int queryp(manager sp) {
		String where = "";
		List<Object> params = new ArrayList<>();
		if (sp.getName() != null && sp.getName().trim().isEmpty() == false) {
			// where += " and sname like '%"+sp.getSname()+"%'";
			where += " and name like ?";
			params.add("%" + sp.getName().trim() + "%");
		}
		if (sp.getImage() != null && sp.getImage().trim().isEmpty() == false) {
			
				where += " and image like ?";
				params.add("%"+sp.getImage().trim()+"%");
			
		}
		String sql = "select * from tb_manager where id!=1" + where;
		return new DBHelper().count(sql, params.toArray());
	}
	public void insert(manager mg) {
		String sql=" insert into tb_manager values(null,?,?,?,?,0)";
		new DBHelper().update(sql, mg.getName(),mg.getImage(),mg.getPassword(),mg.getEmail());
	}
	public void update(manager mg) {
		String sql=" update tb_manager set name=?,image=?,password=?,email=? where id=?";
		new DBHelper().update(sql, mg.getName(),mg.getImage(),mg.getPassword(),mg.getEmail(),mg.getId());
	}
	public void delete(int id) {
		String sql=" delete from tb_manager where id=?";
		new DBHelper().update(sql,id);
	}
}
