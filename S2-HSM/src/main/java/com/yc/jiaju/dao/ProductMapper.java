package com.yc.jiaju.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yc.jiaju.po.Product;



public interface ProductMapper {
	/**
	 * @Select注解会默认使用resultType的映射行为
	 * @return
	 */
	@Select("select * from tb_samepdt  where cid=#{a} or cid= #{b} limit #{begin},#{size} ")
	//@Results替代了<resultMap>   @Result()替代了<result>
	List<Product> queryPage(@Param("a") Integer a,@Param("b") Integer b,@Param("begin") Integer begin,@Param("size") Integer size);
	
	@Select("select * from tb_samepdt  where cid=15 limit #{begin},#{size} ")
	
	List<Product> queryPagefight(@Param("begin") Integer begin,@Param("size") Integer size);
	
	@Select("select * from tb_samepdt where isidea=1 limit #{begin},#{size} ")
	
	List<Product> queryPageidea(@Param("begin") Integer begin,@Param("size") Integer size);
}
