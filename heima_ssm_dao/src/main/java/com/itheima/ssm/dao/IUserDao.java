package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IUserDao {

    /**
     * 根据用户名查询数据库用户信息  用于登录使用
     * @param username
     * @return
     * @throws Exception
     */
    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id = true,property ="id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "email",column = "email"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.itheima.ssm.dao.IRoleDao.findRoleByUserId"))
    })
    UserInfo findByName(String username) throws Exception;

    /**
     * 查询所有的用户
     * @return
     */
    @Select("select * from users")
    List<UserInfo> findAll() throws Exception;

    /**
     * 添加用户
     * @param userInfo
     */
    @Insert("insert into users(email,username,password,phoneNum,status) values(#{email},#{username},#{password},#{phoneNum},#{status})")
    void save(UserInfo userInfo);

    /**
     * 根据用户ID查询用户的所有信息 包括角色及权限
     * @param id
     * @return
     */
    @Select("select * from users where id=#{id}")
    @Results({
            @Result(id = true,property ="id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "email",column = "email"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.itheima.ssm.dao.IRoleDao.findRoleByUserId"))
    })
    UserInfo findById(String id) throws Exception;

    /**
     * 首先从users_role中间表中查询出userid所有的角色  然后从role表中not in 即不存在的角色展示出来  用于用户选择
     * @param id
     * @return
     */
    @Select("select * from role where id not in(select roleId from users_role where userId=#{id})")
    List<Role> findUserByIdAndAllRole(String id);

    /**
     * 插入选择到的角色
     * @param userId
     * @param roleIds
     */
    @Insert("insert into users_role(userId,roleId) values(#{userId},#{roleIds})")
    void addRoleToUser(@Param("userId") String userId, @Param("roleIds") String roleIds);//此处写@Param是因为此处的参数不是对象，无法用#{}封装
}
