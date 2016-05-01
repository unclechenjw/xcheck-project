package com.cmy.xcheck.support;

import com.cmy.xcheck.config.XMessageBuilder;
import com.cmy.xcheck.support.annotation.Check;
import com.cmy.xcheck.util.Validator;

import java.util.List;
import java.util.Map;

/**
 * 校验处理器
 * Created by Kevin72c on 2016/5/1.
 */
public class XCheckHandler {

    /**
     * 校验对象处理
     *
     * @param xBean
     * @param requestParam
     */
    public static void handle(XBean xBean, Map<String, String[]> requestParam, XResult xResult) {
        if (xBean.isRequire()) {

        }
        handle_(xBean, requestParam, xResult);
    }

    private static void handle_(XBean xBean, Map<String, String[]> requestParam, XResult xResult) {
        XBean.CheckItem[] checkItems = xBean.getCheckItems();
        // 遍历表达式
        for (XBean.CheckItem checkItem : checkItems) {
            List<XBean.FormulaItem> formulaItems = checkItem.getFormulaItems();
            // 遍历公式
            for (XBean.FormulaItem formulaItem : formulaItems) {
                // 遍历校验字段
                for (String field : checkItem.getFields()) {
                    String[] values = requestParam.get(field);

                    if (values == null) {
                        // 允许为空结束当前校验
                        if (checkItem.isNullable()) {
                            continue;
                        } else {
                            String message = XMessageBuilder.buildMsg(field, "CanNotBeNull", xBean, checkItem);
                            xResult.failure(message);
                            return;
                        }
                    }

                    // 遍历校验字段值
                    for (String value : values) {
                        if (checkItem.isNullable() && Validator.isEmpty(value)) {
                            // 允许空,字段值为空跳过当前校验
                            continue;
                        }
                        Boolean calculate = formulaItem.calculate(value);
                        if (!calculate) {
                            String message = XMessageBuilder.buildMsg(field, xBean, formulaItem, checkItem);
                            xResult.failure(message);
                            return;
                        }
                    }
                }
            }
        }
    }

//  @Resource
//  private static final JedisManager jm = SysContext.getBean("jedisManager", JedisManager.class);

    /**
     * 验证用户是否登录
     *
     * @param check
     * @param requestParam
     * @param cr
     * @return
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
