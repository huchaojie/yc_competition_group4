package com.yc.jiaju.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.yc.jiaju.po.Orders;
import com.yc.jiaju.util.DBHelper;

/**
 * 
 * ∂©µ•±Ìdao
 *
 */
public class ordersDao {
	public void addorder(String uid,String aid,String total,String pay,String oids) {
		
		String sql="insert into tb_orders values(null,?,?,0,?,now(),?,?)";
		new DBHelper().update(sql, uid,aid,total,pay,oids);
		/*
		 * String sql1="update tb_product set stock=stock-? where pid=?"; new
		 * DBHelper().update(sql1, count,pid);
		 */
	}
	public void addorderitem(String uid,String pid) {
		
		String sql="INSERT INTO tb_orderitem SELECT\n" +
				"	NULL,\n" +
				"	a.count,\n" +
				"	b.total,\n" +
				"	a.pid,\n" +
				"	(\n" +
				"		SELECT\n" +
				"			max(oid)\n" +
				"		FROM\n" +
				"			tb_orders\n" +
				"		WHERE\n" +
				"			uid = ?\n" +
				"	),\n" +
				"	0\n" +
				"FROM\n" +
				"	tb_cart a,\n" +
				"	(\n" +
				"		SELECT\n" +
				"			SUM(a.count * b.price) total,\n" +
				"			a.count,\n" +
				"			a.cid\n" +
				"		FROM\n" +
				"			tb_cart a,\n" +
				"			tb_product b\n" +
				"		WHERE\n" +
				"			a.pid = b.pid\n" +
				"		AND a.pid = ?\n" +
				"		GROUP BY\n" +
				"			a.pid\n" +
				"	) b\n" +
				"WHERE\n" +
				"	a.cid = b.cid\n" +
				"AND a.pid = ?";
		new DBHelper().update(sql, uid,pid,pid);
		
		/*
		 * String sql1="update tb_product set stock=stock-? where pid=?"; new
		 * DBHelper().update(sql1, count,pid);
		 */
	}
	public void addorderitem2(String uid,String pid,String count) {
		
		String sql="INSERT INTO tb_orderitem SELECT\n" +
				"	NULL,\n" +
				"	?,\n" +
				"	a.total,\n" +
				"	?,\n" +
				"	(\n" +
				"		SELECT\n" +
				"			max(oid)\n" +
				"		FROM\n" +
				"			tb_orders\n" +
				"		WHERE\n" +
				"			uid = ?\n" +
				"	),\n" +
				"	0\n" +
				"FROM\n" +
				"(\n" +
				"	SELECT\n" +
				"		SUM(? * price) total\n" +
				"	FROM\n" +
				"		tb_product\n" +
				"	WHERE\n" +
				"		pid = ?\n" +
				"	GROUP BY\n" +
				"		pid\n" +
				") a";
		new DBHelper().update(sql, count,pid,uid,count,pid);
		
		/*
		 * String sql1="update tb_product set stock=stock-? where pid=?"; new
		 * DBHelper().update(sql1, count,pid);
		 */
	}
	public void addeval(String uid) {
		String sql="INSERT INTO tb_evaluate\n" +
				"VALUES\n" +
				"	(\n" +
				"		NULL,\n" +
				"		?,\n" +
				"		(\n" +
				"			SELECT\n" +
				"				max(id)\n" +
				"			FROM\n" +
				"				tb_orderitem\n" +
				"		),\n" +
				"		0,\n" +
				"		NULL,\n" +
				"		now()\n" +
				"	)";
		new DBHelper().update(sql,uid);
	}
	public void cutcart(String pid) {
		String sql="delete from tb_cart where pid=?";
		new DBHelper().update(sql,pid);
	}
	public void cutproductstock(String pid) {
		String sql="select count from tb_cart where pid=?";
		List<Map<String,Object>> list =new DBHelper().query(sql, pid);
		for (Map<String, Object> map : list) {
			int count = (int) map.get("count");
			String sql1="update tb_product set stock=stock-? where pid=?"; 
			new DBHelper().update(sql1, count,pid); 
		}
	}
	public void cutproductstock2(String pid,String count) {
		
			String sql1="update tb_product set stock=stock-? where pid=?"; 
			new DBHelper().update(sql1, count,pid); 
		
	}
	public List<Map<String,Object>> listorders(String uid){
		
			String sql1="SELECT\n" +
					"	b. NAME,\n" +
					"	a.oid,\n" +
					"	a.oids,\n" +
					"	a.statu,\n" +
					"	a.total,\n" +
					"	a.createtime\n" +
					"FROM\n" +
					"	tb_orders a,\n" +
					"	tb_addr b\n" +
					"WHERE\n" +
					"	a.aid = b.aid\n" +
					"and a.uid = ?\n" +
					"AND 1 = 1";
			List<Map<String,Object>> list= new DBHelper().query(sql1,uid);
			for (Map<String, Object> map : list) {
				int oid=(int) map.get("oid");
				String sql="SELECT\n" +
						"	b.pname,\n" +
						"	b.image,\n" +
						"	b.price,\n" +
						"	b.style,\n" +
						"	a.count,\n" +
						"	a.total,\n" +
						"	a.estatu\n" +
						"FROM\n" +
						"	tb_orderitem a,\n" +
						"	tb_product b\n" +
						"WHERE\n" +
						"	a.pid = b.pid\n" +
						"AND oid = ?";
				List<Map<String,Object>> p =new DBHelper().query(sql, oid);
				//System.out.println(p);
				map.put("p", p);
			}
			return list;
		
	}
	public List<Map<String,Object>> search(String oids){
		
		/*
		 * String sql1="SELECT\n" + "	b. NAME,\n" + "	a.oid,\n" + "	a.oids,\n" +
		 * "	a.statu,\n" + "	a.total,\n" + "	a.createtime\n" + "FROM\n" +
		 * "	tb_orders a,\n" + "	tb_addr b\n" + "WHERE\n" + "	a.aid = b.aid\n" +
		 * "AND  1=1 "; List<Object> params = new ArrayList<Object>(); if (oids != null
		 * && oids.trim().isEmpty() == false) { sql1 +=
		 * " and oids like concat('%',?,'%')"; params.add(oids); }
		 * List<Map<String,Object>> list= new DBHelper().query(sql1,params.toArray());
		 * for (Map<String, Object> map : list) { int oid=(int) map.get("oid"); String
		 * sql="SELECT\n" + "	b.pname,\n" + "	b.image,\n" + "	b.price,\n" +
		 * "	b.style,\n" + "	a.count,\n" + "	a.total,\n" + "	a.estatu\n" + "FROM\n" +
		 * "	tb_orderitem a,\n" + "	tb_product b\n" + "WHERE\n" + "	a.pid = b.pid\n"
		 * + "AND oid = ? ";
		 * 
		 * List<Map<String,Object>> p =new DBHelper().query(sql,oid);
		 * System.out.println(p); map.put("p", p); }
		 */
		String sql="select oids from tb_orders where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		 if (oids != null && oids.trim().isEmpty() == false) { 
			 sql +=" and oids like concat('%',?,'%')"; 
			 params.add(oids);
		 }
		 List<Map<String,Object>> p =new DBHelper().query(sql,params.toArray());
		return p;
	
	}
public List<Map<String,Object>> searchByPname(String pname){
		
		String sql1="SELECT\n" +
				"	oids\n" +
				"FROM\n" +
				"	tb_orders\n" +
				"WHERE\n" +
				"	oid IN (\n" +
				"		SELECT\n" +
				"			oid\n" +
				"		FROM\n" +
				"			tb_orderitem a,\n" +
				"			tb_product b\n" +
				"		WHERE\n" +
				"			a.pid = b.pid\n" +
				"		AND 1 = 1\n";
		List<Object> params = new ArrayList<Object>();
		
		if (pname != null && pname.trim().isEmpty() == false) {
			sql1 += " and b.pname like concat('%',?,'%'))";
			params.add(pname);
		}else {
			sql1 +=")";
		}
			
		List<Map<String,Object>> list= new DBHelper().query(sql1,params.toArray());
		
		return list;
	
	}
public Map<String,Object> listorderxq(String oid){
	
	String sql1="SELECT\n" +
			"	b. NAME,\n" +
			"	b.phone,\n" +
			"	b.addr,\n" +
			"	a.oid,\n" +
			"	a.oids,\n" +
			"	a.total,\n" +
			"	a.statu,\n" +
			"	a.createtime\n" +
			"FROM\n" +
			"	tb_orders a,\n" +
			"	tb_addr b\n" +
			"WHERE\n" +
			"	a.aid = b.aid\n" +
			"AND a.oid = ?";
	List<Map<String,Object>> list= new DBHelper().query(sql1,oid);
	Map<String, Object> map =new HashMap<String, Object>();
		
		String sql="SELECT\n" +
				"	b.pname,\n" +
				"	b.image,\n" +
				"	b.price,\n" +
				"	b.style,\n" +
				"	a.count,\n" +
				"	a.total,\n" +
				"	a.estatu\n" +
				"FROM\n" +
				"	tb_orderitem a,\n" +
				"	tb_product b\n" +
				"WHERE\n" +
				"	a.pid = b.pid\n" +
				"AND oid = ?";
		List<Map<String,Object>> p =new DBHelper().query(sql, oid);
		
		map.put("list",list);
		map.put("p", p);
	
	return map;

	}
	public void changestatu(String statu,String oid) {
		String sql="update tb_orders set statu=? where oid=?";
		new DBHelper().update(sql,statu, oid);
	}
	public void changestatuto1(String oids) {
		String sql="update tb_orders set statu=1 where oids=?";
		new DBHelper().update(sql, oids);
	}

	/*≤‚ ‘
	 * public static void main(String[] args) { ordersDao odao = new ordersDao();
	 * List<Map<String,Object>> list=odao.listorders("0"); System.out.println(list);
	 * }
	 */
	public List<Map<String,Object>> payByOid(String oid,String uid) {
		if (oid != null && oid.trim().isEmpty() == false) {
			String sql="SELECT\n" +
					"	oids,\n" +
					"	total\n" +
					"FROM\n" +
					"	tb_orders\n" +
					"WHERE\n" +
					"	oid = ?";
			return new DBHelper().query(sql, oid);
		}else {
			String sql="SELECT\n" +
					"	a.oids,\n" +
					"	a.total\n" +
					"FROM\n" +
					"	tb_orders a\n" +
					"WHERE\n" +
					"	a.oid =(\n" +
					"SELECT max(oid)\n" +
					"FROM\n" +
					"	tb_orders where uid=?\n" +
					")";
			return new DBHelper().query(sql,uid);
		}
	}
	public List<Map<String,Object>> querym(String statu,String time,String oids,String uname,String name,String phone, int page,int size){
		
		int begin=(page-1)*size;
		List<Object> params =new ArrayList<Object>();
		String where ="";
		if(oids!=null&&oids.trim().isEmpty()==false) {
			where+=" and a.oids like ? ";
			params.add("%"+oids.trim()+"%");
		}
		if(uname!=null&&uname.trim().isEmpty()==false) {
			where+=" and b.uname like ? ";
			params.add("%"+uname.trim()+"%");
		}
		if(name!=null&&name.trim().isEmpty()==false) {
			where+=" and c.name like ? ";
			params.add("%"+name.trim()+"%");
		}
		if(phone!=null&&phone.trim().isEmpty()==false) {
			where+=" and c.phone like ? ";
			params.add("%"+phone.trim()+"%");
		}
		if(statu!=null&&statu.trim().isEmpty()==false) {
			where+=" and a.statu = ? ";
			params.add(statu);
		}
		if(time!=null&&time.trim().isEmpty()==false) {
			if(time.equals("1")) {
				where+=" and DATEDIFF(a.createtime,NOW())=0";
			}else if(time.equals("2")) {
				where+=" and DATEDIFF(a.createtime,NOW())=-1";
			}else if(time.equals("3")) {
				where+=" and  YEARWEEK(date_format(a.createtime,'%Y-%m-%d')) = YEARWEEK(now())";
			}else if(time.equals("4")) {
				where+=" and YEARWEEK(date_format(a.createtime,'%Y-%m-%d')) = YEARWEEK(now())-1";
			}else if(time.equals("5")) {
				where+=" and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(a.createtime)";
			}else if(time.equals("6")) {
				where+=" and DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(a.createtime)";
			}else if(time.equals("7")) {
				where+=" and DATE_FORMAT( a.createtime, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )";
			}else if(time.equals("8")) {
				where+=" and PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( a.createtime, '%Y%m' ) ) =1";
			}else if(time.equals("9")) {
				where+=" and QUARTER(a.createtime)=QUARTER(now())";
			}else if(time.equals("10")) {
				where+=" and QUARTER(a.createtime)=QUARTER(DATE_SUB(now(),interval 1 QUARTER))";
			}else if(time.equals("11")) {
				where+=" and YEAR(a.createtime)=YEAR(NOW())";
			}else if(time.equals("12")) {
				where+=" and year(a.createtime)=year(date_sub(now(),interval 1 year))";
			}
			
		}
		params.add(begin);
		params.add(size);
		String sql="SELECT\n" +
				"	aa.*,d.mname,d.mid\n" +
				"FROM\n" +
				"	(\n" +
				"		SELECT\n" +
				"			a.uid,\n" +
				"			a.oid,\n" +
				"			a.oids,\n" +
				"			a.statu,\n" +
				"			b.uname,\n" +
				"			c.name,\n" +
				"			c.addr,\n" +
				"			c.phone,\n" +
				"			a.createtime,\n" +
				"			a.total\n" +
				"		FROM\n" +
				"			tb_orders a,\n" +
				"			tb_user b,\n" +
				"			tb_addr c\n" +
				"		WHERE\n" +
				"			a.uid = b.uid\n" +
				"		AND a.aid = c.aid"+where+") aa\n" +
						"LEFT OUTER JOIN (\n" +
						"	SELECT\n" +
						"		b. NAME mname,\n" +
						"		a.mid,\n" +
						"		a.uid\n" +
						"	FROM\n" +
						"		tb_chat a,\n" +
						"		tb_manager b\n" +
						"	WHERE\n" +
						"		a.mid = b.id\n" +
						") d ON (aa.uid = d.uid) limit ?,?";
		return new DBHelper().query(sql ,params.toArray());
	}
	public int queryp(String statu,String time,String oids,String uname,String name,String phone){
		List<Object> params = new ArrayList<Object>();
		String where ="";
		
		if(oids!=null&&oids.trim().isEmpty()==false) {
			where+=" and a.oids like ? ";
			params.add("%"+oids.trim()+"%");
		}
		if(uname!=null&&uname.trim().isEmpty()==false) {
			where+=" and b.uname like ? ";
			params.add("%"+uname.trim()+"%");
		}
		if(name!=null&&name.trim().isEmpty()==false) {
			where+=" and c.name like ? ";
			params.add("%"+name.trim()+"%");
		}
		if(phone!=null&&phone.trim().isEmpty()==false) {
			where+=" and c.phone like ? ";
			params.add("%"+phone.trim()+"%");
		}
		if(statu!=null&&statu.trim().isEmpty()==false) {
			where+=" and a.statu = ? ";
			params.add(statu);
		}
		if(time!=null&&time.trim().isEmpty()==false) {
			if(time.equals("1")) {
				where+=" and DATEDIFF(a.createtime,NOW())=0";
			}else if(time.equals("2")) {
				where+=" and DATEDIFF(a.createtime,NOW())=-1";
			}else if(time.equals("3")) {
				where+=" and  YEARWEEK(date_format(a.createtime,'%Y-%m-%d')) = YEARWEEK(now())";
			}else if(time.equals("4")) {
				where+=" and YEARWEEK(date_format(a.createtime,'%Y-%m-%d')) = YEARWEEK(now())-1";
			}else if(time.equals("5")) {
				where+=" and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(a.createtime)";
			}else if(time.equals("6")) {
				where+=" and DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(a.createtime)";
			}else if(time.equals("7")) {
				where+=" and DATE_FORMAT( a.createtime, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )";
			}else if(time.equals("8")) {
				where+=" and PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( a.createtime, '%Y%m' ) ) =1";
			}else if(time.equals("9")) {
				where+=" and QUARTER(a.createtime)=QUARTER(now())";
			}else if(time.equals("10")) {
				where+=" and QUARTER(a.createtime)=QUARTER(DATE_SUB(now(),interval 1 QUARTER))";
			}else if(time.equals("11")) {
				where+=" and YEAR(a.createtime)=YEAR(NOW())";
			}else if(time.equals("12")) {
				where+=" and year(a.createtime)=year(date_sub(now(),interval 1 year))";
			}
			
		}
		String sql="SELECT\n" +
				"	a.oids,\n" +
				"	b.uname,\n" +
				"	c.name,\n" +
				"	c.addr,\n" +
				"	c.phone,\n" +
				"	a.createtime,\n" +
				"	a.total\n" +
				"FROM\n" +
				"	tb_orders a,\n" +
				"	tb_user b,\n" +
				"	tb_addr c\n" +
				"WHERE\n" +
				"	a.uid = b.uid\n" +
				"AND a.aid = c.aid"+where;
		return new DBHelper().count(sql,params.toArray());
	}
	public List<Map<String,Object>> queryoit(String oid){
		String sql="SELECT\n" +
				"	a.id,\n" +
				"	a.count,\n" +
				"	a.total,\n" +
				"	b.pname\n" +
				"FROM\n" +
				"	tb_orderitem a,\n" +
				"	tb_product b\n" +
				"WHERE\n" +
				"	a.pid = b.pid\n" +
				"AND a.oid =?";
		return new DBHelper().query(sql, oid);
	}
	public Map<String,Object> querytotal(String time,String oids,String uname,String name,String phone){
		List<Object> params = new ArrayList<Object>();
		String where ="";
		
		if(oids!=null&&oids.trim().isEmpty()==false) {
			where+=" and a.oids like ? ";
			params.add("%"+oids.trim()+"%");
		}
		if(uname!=null&&uname.trim().isEmpty()==false) {
			where+=" and b.uname like ? ";
			params.add("%"+uname.trim()+"%");
		}
		if(name!=null&&name.trim().isEmpty()==false) {
			where+=" and c.name like ? ";
			params.add("%"+name.trim()+"%");
		}
		if(phone!=null&&phone.trim().isEmpty()==false) {
			where+=" and c.phone like ? ";
			params.add("%"+phone.trim()+"%");
		}
		if(time!=null&&time.trim().isEmpty()==false) {
			if(time.equals("1")) {
				where+=" and DATEDIFF(a.createtime,NOW())=0";
			}else if(time.equals("2")) {
				where+=" and DATEDIFF(a.createtime,NOW())=-1";
			}else if(time.equals("3")) {
				where+=" and  YEARWEEK(date_format(a.createtime,'%Y-%m-%d')) = YEARWEEK(now())";
			}else if(time.equals("4")) {
				where+=" and YEARWEEK(date_format(a.createtime,'%Y-%m-%d')) = YEARWEEK(now())-1";
			}else if(time.equals("5")) {
				where+=" and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(a.createtime)";
			}else if(time.equals("6")) {
				where+=" and DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(a.createtime)";
			}else if(time.equals("7")) {
				where+=" and DATE_FORMAT( a.createtime, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )";
			}else if(time.equals("8")) {
				where+=" and PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( a.createtime, '%Y%m' ) ) =1";
			}else if(time.equals("9")) {
				where+=" and QUARTER(a.createtime)=QUARTER(now())";
			}else if(time.equals("10")) {
				where+=" and QUARTER(a.createtime)=QUARTER(DATE_SUB(now(),interval 1 QUARTER))";
			}else if(time.equals("11")) {
				where+=" and YEAR(a.createtime)=YEAR(NOW())";
			}else if(time.equals("12")) {
				where+=" and year(a.createtime)=year(date_sub(now(),interval 1 year))";
			}
			
		}
		String sql="select sum(d.total) total from"+
				"(SELECT\n" +
				"	a.total\n" +
				"FROM\n" +
				"	tb_orders a,\n" +
				"	tb_user b,\n" +
				"	tb_addr c\n" +
				"WHERE\n" +
				"	a.uid = b.uid\n" +
				"AND a.aid = c.aid and a.statu=3 "+where+") d";
		return new DBHelper().query(sql,params.toArray()).get(0);
	}
	public void qq() {
		Calendar cal = Calendar.getInstance();
		 int year = cal.get(Calendar.YEAR);
		System.out.println(year);
		
	}
	public Map<String,Object> wuliu(String oid) {
		String sql="select company,lids from tb_logistics where oid = ?";
		return new DBHelper().query(sql, oid).get(0);
		
	}
	public static void main(String[] args) {
		ordersDao q=new ordersDao();
		q.qq();
	}
	
}
