package com.isesol.wis.operation.controller;

import com.google.common.collect.Maps;
import com.isesol.api.rest.annoations.RestAuthority;
import com.isesol.api.rest.annoations.RestServiceClass;
import com.isesol.api.rest.annoations.RestServiceMethod;
import com.isesol.wis.authority.manage.vo.MemberLoginVo;
import com.isesol.wis.core.common.constant.CommonConstants;
import com.isesol.wis.core.common.msg.RestResponse;
import com.isesol.wis.core.vo.baseVo.BaseRequestVo;
import com.isesol.wis.operation.service.TokenService;
import com.isesol.wis.operation.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
@RestServiceClass(url = "/account")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @RestServiceMethod(url = "/login", authority= RestAuthority.NONE, requestType = BaseRequestVo.class)
    public RestResponse<Map<String, Object>> login(@RequestBody BaseRequestVo<MemberLoginVo> vo) {
        MemberLoginVo voTemp = vo.getEntity(MemberLoginVo.class);
        RestResponse<Map<String, Object>> resp = new RestResponse<>();
        String username = voTemp.getUsername();
        String password = voTemp.getPassword();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            logger.error("empty username or password or owner, username:{}, password:{}, owner:{}", username, password);
            resp.setStatus(CommonConstants.EX_FORM_VALIDATE_REQUIRED);
            resp.setMessage("仅作为开发调试：用户名或密码或租户为空");
            return resp;
        }

        // 校验用户是否存在
        Long userId = userService.login(username, password);
        if (userId == null) {
            resp.setStatus(CommonConstants.EX_WIS_USER_LOGIN);
            resp.setMessage("仅作为开发调试：用户不存在");
            return resp;
        }
        String token = tokenService.generate(userId);
        Map<String, Object> result = Maps.newHashMap();
        result.put("token", token);
        resp.setResult(result);
        return resp;
    }

    @RestServiceMethod(url = "/test", authority= RestAuthority.ANY)
    public boolean test() {
        return true;
    }
}
