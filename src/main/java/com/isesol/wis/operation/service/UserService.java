package com.isesol.wis.operation.service;

import com.isesol.wis.operation.mapper.AdminUserMapper;
import com.isesol.wis.operation.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AdminUserMapper mapper;

    /**
     * 登录获取token
     * @param username
     * @param password
     * @return userId
     */
    public Long login(String username, String password) {
        try {
            User user = mapper.getUser(username, password);
            if (user != null) {
                return user.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
