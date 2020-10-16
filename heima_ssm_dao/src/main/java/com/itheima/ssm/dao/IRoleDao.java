package com.itheima.ssm.dao;
import	java.security.Permission;
import com.itheima.ssm.domain.Role;
import org.apache.ibatis.annotations.*;

import javax.xml.bind.annotation.XmlAnyAttribute;
import	java.util.List;

public interface IRoleDao {
//    //根据用户id查询出所有对应的角色
//    @Select("select * from role where id in (select roleId from users_role where userId=#{userId})")
//    List<Role> findRoleByUserId(String userId) throws Exception;

    //根据用户id查询出所有对应的角色
    @Select("select * from role where id in (select roleId from users_role where userId=#{userId})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,many = @Many(select = "com.itheima.ssm.dao.IPermissionDao.findPermissionByRoleId"))
    })
    public List<Role> findRoleByUserId(String userId) throws Exception;


    /**
     * 查询所有的角色信息
     * @return
     */
    @Select("select * from role")
    List<Role> findAll();

    /**
     * 保存用户角色信息
     */
    //@Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    void save(Role role);

    /**
     * 查询角色详情
     * @param id
     * @return
     */
    @Select("select * from role where id=#{id}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType =java.util.List.class,many = @Many(select = "com.itheima.ssm.dao.IPermissionDao.findPermissionByRoleId"))
    })
    Role findById(String id);

    /**
     * 删除角色信息  在删除的时候需要在角色权限中间表中查询权限的集合然后将权限删除后方能删除角色信息
     * @param id
     */
    @Delete("delete from role where id=#{id}")
    void deleteRole(String id);

    /**
     * 删除角色用户中间表中的角色信息
     * @param id
     */
    //@Delete("delete * from users_role where roleId=#{id}")
    @Delete("delete from users_role where roleId=#{id}")
    void deleteRole_User(String id);

    /**
     * 删除角色权限中间表中的角色信息
     * @param id
     */
    @Delete("delete from role_permission where roleId=#{id}")
    void deleteRole_Permission(String id);
}
