package com.yc.jiaju.dao;

import org.apache.ibatis.annotations.Select;

import com.yc.jiaju.po.Addr;

public interface AddrMapper {

	@Select("select * from tb_addr where aid=#{aid}")
	Addr selectByAid(int aid);
}
