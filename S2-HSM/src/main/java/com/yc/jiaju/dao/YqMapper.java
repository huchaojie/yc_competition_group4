package com.yc.jiaju.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Select;


import com.yc.jiaju.po.Yq;



public interface YqMapper {
	
	@Select("select * from tb_yq")
	List<Yq> query();
	
	@Insert("insert into tb_yq values (null,#{province},#{city},#{citycode})")
	int insert(Yq yq);
	
	@Delete("delete from tb_yq where citycode=#{citycode}")
	int qxsx(Yq yq);
}
