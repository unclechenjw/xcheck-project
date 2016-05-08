package com.cmy.xcheck.util;

import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.support.annotation.Check;

import java.util.Map;

/**
 * 会话有效检查
 * Created by Kevin72c on 2016/5/2.
 */
public class SessionCheck {

//  @Resource
//  private static final JedisManager jm = SysContext.getBean("jedisManager", JedisManager.class);

    /**
     * 验证用户是否登录
     */
    private static boolean verifySessionUser(Check check, Map<String, String> requestParam,
                                             XResult cr) {
        // 判断用户是否登录
        if (check.required()) {
            // 如果会话令牌为空
            String sessionToken = requestParam.get("sessionToken");
            if (Validator.isEmpty(sessionToken)) {
                cr.failure("用户未登录");
                cr.setStatus(1100); //TODO 未登录状态需要修改
                return false;
            }

            // redis获取用户信息
//            SessionUser sessionUser = (SessionUser) jm.getObject(
//                    CommonKeyEnum.token_app_user, requestParam.get("userID"));
            // 或者会话令牌不匹配，提示用户未登录
//            if (sessionUser == null
//                    || !sessionUser.getSessionToken().equals(sessionToken)) { // token不匹配
//                cr.setErrorMsg("用户未登录");
//                cr.setStatus(100);
//                return false;
//            }
        }
        return true;
    }
}
