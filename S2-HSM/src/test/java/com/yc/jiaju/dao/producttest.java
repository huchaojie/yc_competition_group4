package com.yc.jiaju.dao;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.yc.jiaju.util.MyBatisHelper;

public class producttest {
	
	@Test
	public void test() {
		SqlSession session = MyBatisHelper.openSession();
		ProductMapper p = session.getMapper(ProductMapper.class);
		int a=12;
		int b=0;
		int begin=0;
		int size = 9;
		p.queryPage(a, b, begin, size);
	}
}
