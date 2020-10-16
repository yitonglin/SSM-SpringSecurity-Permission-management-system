package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Member;
import org.apache.ibatis.annotations.Select;

public interface IMemberDao {

    /**
     * 根据会员（member）id查询信息
     * @param id
     * @return
     * @throws Exception
     */
    @Select("select * from member where id = #{id}")
    public Member findById(String id)throws Exception;
}
