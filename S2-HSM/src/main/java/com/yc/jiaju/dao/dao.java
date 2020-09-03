package com.yc.jiaju.dao;

import java.util.List;
import java.util.Map;

import com.yc.jiaju.util.DBHelper;

public class dao {
	
	public void update() {
		String sql = "update tb_product set pdesc=? where pid=?";
		String sql1 = "select style from tb_product where pid=?";
		for(int i=55;i<247;i++) {
			List<Map<String, Object>> list = new DBHelper().query(sql1, i);
			String style = (String) list.get(0).get("style");
			String pdesc = "【破损补寄】【适合放室内、卧室、书房、餐桌、办公室、客厅、电视柜等地方】【无理由退换货】【包邮】【白色现代简约花瓶】"+"【"+style+"】";
			System.out.println(pdesc);
			new DBHelper().update(sql, pdesc,i);
		}
		
		
		
	}
	
	
	public static void main(String[] args) {
		dao d = new dao();
		d.update();
	}
}
