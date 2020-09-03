package com.yc.jiaju.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisHelper {
	private static SqlSessionFactory sqlSessionFactory;
	//¶¯Ì¬¿é
	static{
		try {
			String resource = "mybatis.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
			
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
	}
	public static SqlSession openSession() {
		return sqlSessionFactory.openSession();
	}
}
