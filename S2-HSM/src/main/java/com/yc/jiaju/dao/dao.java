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
			String pdesc = "�����𲹼ġ����ʺϷ����ڡ����ҡ��鷿���������칫�ҡ����������ӹ�ȵط������������˻����������ʡ�����ɫ�ִ���Լ��ƿ��"+"��"+style+"��";
			System.out.println(pdesc);
			new DBHelper().update(sql, pdesc,i);
		}
		
		
		
	}
	
	
	public static void main(String[] args) {
		dao d = new dao();
		d.update();
	}
}
