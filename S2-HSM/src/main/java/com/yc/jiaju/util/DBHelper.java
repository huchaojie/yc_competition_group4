package com.yc.jiaju.util;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

//import oracle.sql.TIMESTAMP;

/**
 * 	�������װ
 * 		ʵ���� 	==> 	��
 * 		һ������    	==>  	һ����¼
 * 		����		==>		�ֶ�
 * 				ӳ��
 * 	���������ѯ
 * 		����sql���Ķ�̬��ƴ������
 * 		1. 	where 1=1 ==> ���õĲ�ѯ����, ����ƴ��sql���
 * 		2. 	ʹ��List���ϱ����ѯ����
 * 
 * 	��������
 * 		Date  ==>  ������	==> java.sql.Date	---> ���� java.util.Date
 * 		datetime   ������ʱ����  ==> java.sql.Timestamp --> ���� java.util.Date
 * 		timestamp
 * 
 * 		java.sql.Date.valueOf("yyyy-MM-dd"); "20203-3-25";
 * 
 * 	��ҳ��ѯ
 * 
 * 	�������
 * 
 * 	DBHelper ��װ : �� jdbc �Ĵ���
 * 	1.��������	ֻ����һ�� ==> ��̬
 * 	2.��ȡ����
 * 	3.�������
 * 	4.ִ�����
 * 	5.�ر�����
 * 
 * 	�������� ==> �ظ�   ==> ���� ==> ���뾫��
 * 
 * 	DBHelper ����ִ�� sql ��� ���ض�Ӧ�Ľ��
 * 	1. ��ɾ��  ==>  executeUpdate  ==> ����: ����  int , ��ɾ�ĵ�����
 * 	2. ��ѯ     ==>  executeQuery	 ==> ����:ResultSet, 
 * 		��Ϊ��Ҫ�ر�, Ӧ�÷���ʵ����, ����Map����
 * 
 */
public class DBHelper {
	
	// ���干�õ����Ӷ���
	private Connection conn;
	
	private boolean isAutoCommit = true;

	/**
	 * 	��Ĵ����:
	 * 	��̬��: 
	 * 		static {}
	 * 		�ص�: �����౻���ص������ʱ,ִ��һ��(  ����: import ��)
	 * 	ʵ����
	 * 		{}
	 * 		�ص�: ���ڶ��󱻴���ʱִ��һ��, �ڹ��췽��ǰ
	 * 	�鲻�Ƿ���,�����׳��������쳣
	 */
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// �쳣ת�� ==> �쳣��
			// δ������������쳣�����³������ֹ
			RuntimeException re = new RuntimeException("���ݿ���������ʧ��!", e);
			throw re;
		}
	}
	
	/**
	 * ʹ�� isAutoCommit �����Ƿ��Զ��ύ
	 * 
	 * ������Զ��ύ, ����ζ��ÿ��ִ�� update ������Ҫ��ȡ�µ�����, ��ִ��֮��ر�����
	 * ����, ���ر�����
	 * @param isAutoCommit  �Զ��ύ  true
	 */
	public DBHelper(boolean isAutoCommit) {
		this.isAutoCommit = isAutoCommit;
		if(isAutoCommit == false) {
			conn = openConnection();
		}
	}
	
	/**
	 * JDBC ����Ĭ�����Զ��ύ, Ҳ����ÿ��ִ������ɾ�Ķ����Զ��ύ
	 * �޲εĹ��췽��, ����ע�͵���
	 */
	public DBHelper() {
		// �ڹ��췽���д�������
		//conn = openConnection();
	}
	
	// �ر�����
	public void closeConnection() {
		IOHelper.close(conn);
	}
	
	// �������Ӷ���
	public Connection getConn() {
		return conn;
	}

	/**
	 * 	��ȡ����
	 * @return
	 */
	public Connection openConnection() {
		String url = "jdbc:mysql://127.0.0.1:3306/s2-plhy-jiaju?useUnicode=true&characterEncoding=UTF-8"; // ���ݿ�ĵ�ַ
		String user = "root"; // ���ݵ��û�
		String password = "a";
		try {
			if(isAutoCommit) {
				return DriverManager.getConnection(url, user, password);
			} else {
				if(conn == null) {
					// ��ֹ�Զ��ύ
					conn = DriverManager.getConnection(url, user, password);
					conn.setAutoCommit(isAutoCommit);
				}
				return conn;
			}
		} catch (SQLException e) {
			throw new RuntimeException("��ȡ���ݿ�����ʧ��!", e);
		}
	}

	/**
	 * 	ִ���޸����ݿ�����
	 * sql = "update emp set ename = ? where empno=?"
	 * update(sql,2,3,)
	 * @param sql		ִ�е�sql���
	 * @param params	�ɱ��������
	 * @return
	 */
	public int update(String sql, Object... params) {
		try {
			// ÿ�ζ���ͨ��open������ȡ����
			conn = openConnection();
			System.out.println("SQL: " + sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			// alrt + /
			System.out.println("����: " + Arrays.toString(params));
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("ִ��SQL���ʧ��!", e);
		} finally {
			if(isAutoCommit == true) {
				IOHelper.close(conn);
			}
		}
	}

	/**
	 * 	ִ�в�ѯ���
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> query(String sql, Object... params) {
		try {
			conn = openConnection();
			System.out.println("SQL: " + sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			// alrt + /
			System.out.println("����: " + Arrays.toString(params));
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ResultSet rs = ps.executeQuery();

			// ��ȡ�����Ԫ���ݶ���, Ԫ(Meta)����(data): �������ݵ�����
			ResultSetMetaData rsmd = rs.getMetaData();
			// �������ؽ������
			List<Map<String, Object>> ret = new ArrayList<>();
			while (rs.next()) {
				// ���� map ����
				/**
				 * 1. HashMap   	�����ظ�
				 * 2 LinkedHashMap, �����ظ�
				 * 3. TreeMap 		�����ظ�
				 */
				Map<String, Object> row = new LinkedHashMap<>();
				// ��ȡÿһ���ֶ�ֵ, ���õ�һ��map��
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String columnName = rsmd.getColumnName(i + 1);
					Object columnValue = rs.getObject(columnName);
					/**
					 * �жϵ�ǰ����ֵ������, ����� oracle ��TIMESTAMP, ��ô����ʹ��
					 * rs.getTimestamp ����ȡֵ
					 */
					/*if(columnValue!= null && columnValue instanceof TIMESTAMP) {
						columnValue = rs.getTimestamp(columnName);
					}*/
					row.put(columnName, columnValue);
				}
				// �� map ��ӵ� ret ��
				ret.add(row);
			}
			return ret;
		} catch (SQLException e) {
			throw new RuntimeException("ִ��SQL���ʧ��!", e);
		} finally {
			if(isAutoCommit == true) {
				IOHelper.close(conn);
			}
		}
	}
	
	/**
	 * 	����ֵ�������ǿɱ������, ���еļ���==> ������
	 * 	query ��������� ���ͷ���	: �﷨�Ķ���: �ڷ���ǰ�� <E>
	 * 
	 * @param sql
	 * @param cls		�����, ��ʾ E ��������, Java ���似��
	 * @param params
	 * @return
	 */
	public <E> List<E> query(String sql, Class<E> cls, Object... params) {
		try {
			conn = openConnection();
			System.out.println("SQL: " + sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			// alrt + /
			System.out.println("����: " + Arrays.toString(params));
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ResultSet rs = ps.executeQuery();

			// ��ȡ�����Ԫ���ݶ���, Ԫ(Meta)����(data): �������ݵ�����
			ResultSetMetaData rsmd = rs.getMetaData();
			// �������ؽ������
			List<E> ret = new ArrayList<>();
			while (rs.next()) {
				// ���� ʵ����󼯺�( ͨ��������ƴ���ʵ�����  == new ʵ����()   )
				E e;
				try {
					e = cls.newInstance();
				} catch (Exception e2) {
					// �쳣ת��
					throw new RuntimeException(e2);
				}
				
				// ͨ�������������ֵ������
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					try {
						// ���ݵ�ǰ�������Ҷ�Ӧ������
						String columnName = rsmd.getColumnName(i+1); // ID, NAME, AUTHER ...
						columnName = columnName.toLowerCase(); // תСд
						// ��ȡ���ඨ�������(����˽��)
						Field field = cls.getDeclaredField(columnName);
						// ��ȡ��ǰ�е�ֵ
						/**
						 *  ID ==> JDBC �������� : BigDecimal ��ʵ�� ��ʾ�����С������
						 *  	  	    ʵ��������: Long
						 *  .getType ��ȡ���Ե�����  ==> LONG  String  Integer
						 */
						// Ҫת������ֵ
						Object destValue = null;
						if(field.getType().equals(Long.class)) {
							destValue = rs.getLong(i+1);
						} else if(field.getType().equals(Integer.class)) {
							destValue = rs.getInt(i+1);
						} else if(field.getType().equals(Double.class)) {
							destValue = rs.getDouble(i+1);
						} else if(field.getType().equals(Byte.class)) {
							destValue = rs.getByte(i+1);
						} else if(field.getType().equals(Boolean.class)) {
							destValue = rs.getBoolean(i+1);
						// ���������������������
						} else if(field.getType().equals(Timestamp.class)) {
							// ����timestamp�ֶ�ֵ�ķ���
							destValue = rs.getTimestamp(i+1);
							/**
							 * 	����δ�жϵ�����,���������
							 */
						} else {
							// ʹ��Ĭ�ϵ�����
							destValue = rs.getObject(i+1);
						}
						// ����ǿ�Ʒ���˽������
						field.setAccessible(true);
						// ��ֵ���õ���������
						field.set(e, destValue);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				// �� map ��ӵ� ret ��
				ret.add(e);
			}
			return ret;
		} catch (SQLException e) {
			throw new RuntimeException("ִ��SQL���ʧ��!", e);
		} finally {
			if(isAutoCommit == true) {
				IOHelper.close(conn);
			}
		}
	}

	/**
	 * 	��ҳ��ѯ: ���� 
	 * @param sql
	 * @param pageNumber	�ڼ�ҳ	=>  1,2,3 ����0
	 * @param pageSize		ÿҳ����	=>  3,4 ����0
	 * @param params		�ɱ��������: ֻ�ܷ������һ������λ��
	 * @return
	 */
	public List<Map<String, Object>> queryPage(String sql, int pageNumber, int pageSize, Object... params) {
		// ����˳��: ԭ�������� , ��ֹ����, ��ʼ����

		// number = 1, size = 5; => begin = 1, end =5
		// number = 2, size = 5; => begin = 6, end = 10
		// number = 2, size = 5; => begin = 11, end = 15

		int begin = (pageNumber - 1) * pageSize + 1;
		int end = pageNumber * pageSize;
		// �����²�������
		Object[] newParams = new Object[params.length + 2];

		// ����ĸ���: 1,���ø���, 2��¡���� , 3 system.arraycopy
		System.arraycopy(params, 0, newParams, 0, params.length);

		// ���µĲ����������2��Ԫ�ظ�ֵ
		newParams[newParams.length - 2] = end;
		newParams[newParams.length - 1] = begin;

		sql = "select *\n" + "  from (select a.*, rownum rn\n" + "          from (" + sql + ") a\n"
				+ "         where rownum <= ?)\n" + " where rn >= ? ";

		// ���� query ��ѯ����
		return query(sql, newParams);
	}

	/**
	 * 	��ҵ: �뷵�ظ��������������
	 * @param sql
	 * @param params
	 * @return
	 */
	public int count(String sql, Object... params) {
		// select * from emp where ename like '%A%'
		// return query(sql, params).size();
		// �Ӳ�ѯ => select count(*) cnt from (select * from emp where ename like
		// '%A%') ;
		sql = "select count(*) cnt from (" + sql + ") a";
		Object cnt = query(sql, params).get(0).get("cnt");
		// Object ==> int   ǿ������ת�� ==> ����ƥ��    String =>  int
		// int ret = (int) cnt; // ע��ʧ�� cnt ������δ֪ ??   Integer Long BigDecimal ��ʵ��
		int ret = Integer.valueOf("" + cnt);
		
		return ret;
	}

	/**
	 * 	��ҵ: ���ؽ������, ��һ��,��һ�е�ֵ
	 * 	����: select count(*) from emp;
	 * @return
	 */
	public Object getValue(String sql, Object...params) {
		List<Map<String,Object>> list = query(sql,params);
		Map<String,Object> row = list.get(0);
		for( Entry<String,Object> entry : row.entrySet() ) {
			return entry.getValue();
		}
		return null;
	}

	public static void main(String[] args) {

		DBHelper dbhelper = new DBHelper();

		// params = {1,2}
		// dbhelper.update("update dept set dname=? where deptno=?", "������", 60);
		// params = {1,2,3}
		// dbhelper.update("update dept set dname=?,loc=? where deptno=?",
		// "������", "����", 60);
		// params = {}
		// dbhelper.update("update dept set dname='������',loc='ʦԺ' where
		// deptno=60");
		System.out.println("=========================");
		List<Map<String, Object>> list = dbhelper.query("select * from emp");
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

		System.out.println("=========================");
		list = dbhelper.query("select * from emp where ename like ?", "%S%");
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

		System.out.println("=========================");
		list = dbhelper.query("select * from emp where ename like ? " + " and sal > ? ", "%S%", 1250);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

		System.out.println("==========1====5===========");
		list = dbhelper.queryPage("select * from emp", 1, 5);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

		System.out.println("==========2====5===========");
		list = dbhelper.queryPage("select * from emp", 2, 5);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

		System.out.println("==========3====5===========");
		list = dbhelper.queryPage("select * from emp", 3, 5);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

		System.out.println("==========1====5====7521=======");
		list = dbhelper.queryPage("select * from emp where empno > ? ", 1, 5, 7521);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}

}
