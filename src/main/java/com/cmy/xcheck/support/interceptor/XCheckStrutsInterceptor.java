package com.cmy.xcheck.support.interceptor;

import org.apache.struts2.convention.annotation.InterceptorRef;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@InterceptorRef("xcheck")
public class XCheckStrutsInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -9086221142614357755L;

    // Struts xCheck拦截器
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        /**
        HttpServletRequest req = ServletActionContext.getRequest();
        // 获取请求json参数
        JSONObject jsonParam = (JSONObject) req.getAttribute("jsonParam");

        // action 配置
        ActionConfig config = invocation.getProxy().getConfig();
        Class<?> clz = Class.forName(config.getClassName());
        Method method = clz.getDeclaredMethod(config.getMethodName());
        // 校验方法入口
        CheckResult checkResult = XCheckSupport.check(method, jsonParam);
        if (checkResult.isPass()) {
            return invocation.invoke();
        } else {
            // 如果有异常error页面则进入
            if (hasErrorPage(config)) {
                return "error";
            } else {
                // 否则记入错误信息至action的result属性中返回前台
                ResponseWriter.writeMessage(ServletActionContext.getResponse(),
                        checkResult.getStatus(), checkResult.getErrorMsg());
                return "success";
            }
        }
        */
        return "success";
    }

    /**
     * 如果有制定错误页面路径，则返回页面 否则返回规定格式结果
     * 
     * @param config
     * @return
     */
//    private boolean hasErrorPage(ActionConfig config) {
//        Map<String, ResultConfig> results = config.getResults();
//        ResultConfig resultConfig = results.get("error");
//        if (resultConfig == null) {
//            return false;
//        }
//        if (resultConfig.getLocation() == null) {
//            return false;
//        }
//        return true;
//    }

}
