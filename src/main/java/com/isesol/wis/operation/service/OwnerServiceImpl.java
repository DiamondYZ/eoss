package com.isesol.wis.operation.service;

import com.google.common.collect.Lists;
import com.isesol.wis.core.service.OwnerService;
import com.isesol.wis.core.vo.SynchronizeEquipmentVo;
import com.isesol.wis.core.vo.authority.OwnerModule;
import com.isesol.wis.core.vo.user.OwnerInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OwnerServiceImpl implements OwnerService{
    @Override
    public List<String> getAllOwners() {
        return Lists.newArrayList("1");
    }

    @Override
    public boolean isNotCompanyOpen(String owner) {
        return false;
    }

    @Override
    public List<SynchronizeEquipmentVo> getExternalDevices(String owner) {
        return null;
    }

    @Override
    public Map<String, Object> getCompanyByOwner(String owner) {
        return null;
    }

    @Override
    public List<OwnerInfo> getOwnerInfoByToken(String token) {
        return null;
    }

    @Override
    public List<OwnerModule> getOwnerModules(String owner) {
        return null;
    }
}
