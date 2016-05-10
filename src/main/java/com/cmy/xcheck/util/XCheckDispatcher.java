package com.cmy.xcheck.util;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.handler.ValidationHandler;
import com.cmy.xcheck.util.handler.XFactory;

import java.util.List;
import java.util.Map;

/**
 * 校验器\回话校验执行器调取
 * Created by Kevin72c on 2016/5/1.
 */
public class XCheckDispatcher {

    public static void execute(XBean xBean, Map<String, String[]> requestParam, XResult xResult) {
        if (xBean.isRequire()) {
            if (!verifySessionUser(requestParam, xResult)) {
                return;
            }
        }
        List<XCheckItem> checkItems = xBean.getCheckItems();
        // 遍历表达式
        for (XCheckItem checkItem : checkItems) {
            ValidationHandler handler = XFactory.getCheckHandler(checkItem);
            handler.validate(xBean, checkItem, xResult, requestParam);
            if (xResult.isNotPass()) {
                return;
            }
        }
    }

    /**
     * 校验用户是否登录
     * @param requestParam
     * @param xResult
     * @return
     */
    private static boolean verifySessionUser(Map<String, String[]> requestParam,
                                             XResult xResult) {
        // 如果会话令牌为空
        String[] sessionToken = requestParam.get("sessionToken");
        if (sessionToken == null || Validator.isEmpty(sessionToken[0])) {
            xResult.failure("用户未登录");
            xResult.setStatus(1100); //TODO 未登录状态需要修改
            return false;
        }

//        // redis获取用户信息
//        SessionUser sessionUser = (SessionUser) jm.getObject(
//                CommonKey.Token_App_User, jsonParam.getString("userID"));
//        // 或者会话令牌不匹配，提示用户未登录
//        if (sessionUser == null
//                || !sessionUser.getSessionToken().equals(sessionToken)) { // token不匹配
//            cr.setErrorMsg("用户未登录");
//            cr.setStatus(100);
//            return false;
//        }
        return true;
    }

}
