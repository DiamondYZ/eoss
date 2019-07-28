package com.isesol.wis.operation.service;

import com.isesol.wis.core.service.UserService;
import com.isesol.wis.core.vo.cnc.MemberUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CoreUserService implements UserService {

    @Override
    public Map<String, Object> login(String username, String password, Object... objs) {
        return null;
    }

    @Override
    public Map<String, Object> logout(String token) {
        return null;
    }

    @Override
    public String getCurrentOwner() {
        return null;
    }

    @Override
    public Long getCurrentUserId() {
        return null;
    }

    @Override
    public Map<String, Object> synchronizeUser(String memberName, String password, String owner) {
        return null;
    }

    @Override
    public String getCurrentUserInfo(String property) {
        return null;
    }

    @Override
    public Map<String, Object> verifyToken(String token, Object... objs) {
        return null;
    }

    @Override
    public boolean getUserLicense(String owner, String userId) {
        return false;
    }

    @Override
    public MemberUser getUsersDataForMachineHead(String account, String password, String equSerialNo) {
        return null;
    }

    @Override
    public boolean updatePassword(String memberCode, String oldPassword, String newPassword) {
        return false;
    }

    @Override
    public boolean resetPassword(String memberCode, String newPassword) {
        return false;
    }

    @Override
    public boolean getOwnerValidterm(String owner) {
        return false;
    }

    @Override
    public boolean validPassword(String account, String password) {
        return false;
    }
}
