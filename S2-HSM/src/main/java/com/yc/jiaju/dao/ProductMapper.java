package com.yc.jiaju.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yc.jiaju.po.Product;



public interface ProductMapper {
	/**
	 * @Selectע���Ĭ��ʹ��resultType��ӳ����Ϊ
	 * @return
	 */
	@Select("select * from tb_samepdt  where cid=#{a} or cid= #{b} limit #{begin},#{size} ")
	//@Results�����<resultMap>   @Result()�����<result>
	List<Product> queryPage(@Param("a") Integer a,@Param("b") Integer b,@Param("begin") Integer begin,@Param("size") Integer size);
	
	@Select("select * from tb_samepdt  where cid=15 limit #{begin},#{size} ")
	
	List<Product> queryPagefight(@Param("begin") Integer begin,@Param("size") Integer size);
	
	@Select("select * from tb_samepdt where isidea=1 limit #{begin},#{size} ")
	
	List<Product> queryPageidea(@Param("begin") Integer begin,@Param("size") Integer size);
}
