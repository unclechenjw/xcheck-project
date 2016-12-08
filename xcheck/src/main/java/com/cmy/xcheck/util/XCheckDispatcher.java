package com.cmy.xcheck.util;

import com.cmy.xcheck.support.XCheckHandlerAdapter;
import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.support.XResult;
import com.cmy.xcheck.util.handler.ValidationHandler;
import com.cmy.xcheck.util.handler.XFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 校验器\会话校验执行器调取
 * Created by Kevin72c on 2016/5/1.
 */
@Component
public class XCheckDispatcher {

    @Autowired(required = false)
    private XCheckHandlerAdapter xCheckHandlerAdapter;
    @Autowired
    private XFactory xFactory;

    public void execute(XBean xBean, Map<String, String[]> requestParam, XResult xResult) {
        if (xBean.isRequire()) {
            if (!xCheckHandlerAdapter.verifySession(requestParam)) {
                xResult.setStatus(XResult.XCHECK_SESSION_EXPIRE);
                return;
            }
        }
        List<XCheckItem> checkItems = xBean.getCheckItems();
        // 遍历表达式
        for (XCheckItem checkItem : checkItems) {
            ValidationHandler handler = xFactory.getCheckHandler(checkItem);
            handler.validate(xBean, checkItem, xResult, requestParam);
            if (xResult.isNotPass()) {
                return;
            }
        }
    }

    /**
     * 检查校验响应是否正确配置
     */
    @PostConstruct
    public void checkEnv() {
        if (xCheckHandlerAdapter == null) {
            throw new RuntimeException("XCheckHandlerAdapter unimplemented");
        }
    }

}
