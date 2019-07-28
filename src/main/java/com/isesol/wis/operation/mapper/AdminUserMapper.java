package com.isesol.wis.operation.mapper;

import com.isesol.wis.operation.vo.User;
import com.isesol.wis.mybatis.util.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;


/**
 * 管理用户
 */
public interface AdminUserMapper extends BaseMapper<User> {

    @Select("select * from users where account=#{account} and owner=#{owner}")
    @Results(
        @Result(property = "certificatePhotoPath", column = "certificate_photo_path")
    )
    User getUserId(@Param("account") String account, @Param("owner") String owner);

    @Select("select * from admin_users where account=#{account} and password=md5(#{password}) and del_flag=0 limit 1")
    User getUser(@Param("account") String account, @Param("password") String password);
    

    
    
}