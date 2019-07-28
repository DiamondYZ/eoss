package com.isesol.wis.operation.interceptor;

import com.isesol.api.rest.annoations.RestAuthority;
import com.isesol.api.rest.cgi.interceptor.prepare.AbstractRestRequestPrepareInterceptor;
import com.isesol.api.rest.cgi.utils.RestServiceMapping;
import com.isesol.api.rest.model.base.RestError;
import com.isesol.api.rest.model.base.RestRequest;
import com.isesol.api.rest.model.base.RestResponse;
import com.isesol.api.system.constant.RestErrorCode;
import com.isesol.arch.common.utils.PropertyFileUtil;
import com.isesol.arch.common.utils.ThreadContextHolder;
import com.isesol.wis.operation.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class WisLocalAuthInterceptor extends AbstractRestRequestPrepareInterceptor {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static List<RestAuthority> SET_USER_AUTHS = new ArrayList<>();

    @Autowired
    private TokenService tokenService;

    static{

        SET_USER_AUTHS.add(RestAuthority.ANY);
        SET_USER_AUTHS.add(RestAuthority.NORMAL_USER);

    }

    @Override
    protected boolean execute() {
        try{
            // 权限拦截
            RestServiceMapping serviceMapping=(RestServiceMapping) ThreadContextHolder.getAttr("serviceMapping");
            String cmd=(String)ThreadContextHolder.getAttr("cmd");
            String userToken=(String)ThreadContextHolder.getAttr("userToken");
            RestResponse response=(RestResponse)ThreadContextHolder.getAttr("response");
            RestRequest restRequest=(RestRequest)ThreadContextHolder.getAttr("restRequest");

            RestAuthority authority = serviceMapping.getAuthority();

            logger.debug("命令 {} 的权限配置 {}", cmd, authority);

            String apiEnv = PropertyFileUtil.get("api.env");
            if (StringUtils.isNotBlank(apiEnv) && apiEnv.equalsIgnoreCase("local")) {

                return false;
            }

            if(authority.equals(RestAuthority.NONE)){
                return false;
            }

            boolean maybeNeedUser = StringUtils.isNotBlank(userToken);

            if (SET_USER_AUTHS.contains(authority) || maybeNeedUser){

                // token为空的情况
                if (StringUtils.isBlank(userToken)){

                    response.setStatusCode(403);
                    response.setError(new RestError(RestErrorCode.NEED_TOKEN.getCode(), RestErrorCode.NEED_TOKEN.getMessage()));

                    return true;
                }

                try{
                    response.getAuth().put("tokenValid", true);
                    if (tokenService.verify(userToken) == null) {
                        response.setStatusCode(403);
                        response.setError(new RestError(RestErrorCode.TOKEN_INVALID.getCode(), RestErrorCode.TOKEN_INVALID.getMessage()));
                        if (restRequest.isDebug()){
                            response.setDebugInfo("token invalid");
                        }
                        // 设置token是否有效
                        response.getAuth().put("tokenValid", false);
                        return true;
                    }
                } catch (Exception e){
                    LOGGER.error("TokenVerifyService error", e);
                    response.setStatusCode(500);
                    response.setError(new RestError(RestErrorCode.SYSTEM_ERROR.getCode(), RestErrorCode.SYSTEM_ERROR.getMessage()));
                    return true;
                }
            }

        } catch (Exception e){

            LOGGER.error("RestAuthPrepareInterceptor error", e);
        }

        return false;
    }

    @Override
    public String getName() {
        return "权限拦截器";
    }
}
