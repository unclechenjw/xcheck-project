package com.cmy.xcheck.util;

import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XCheckContext;
import com.cmy.xcheck.util.item.XCheckItem;
import com.cmy.xcheck.util.item.XCheckItemLogic;
import com.cmy.xcheck.util.item.XCheckItemSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.*;

@Component
public class XMessageBuilder {

    @Autowired
    private XCheckContext xCheckContext;
    private Map<String, String> GLOBAL_FIELD_ALIAS;

    public XMessageBuilder() {
        System.out.println();
    }

    /**
     * 语言环境
     */
    private Properties properties;

    @PostConstruct
    public void init() {
        /** 加载语言配置 */
        properties = new Properties();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            // 国际化支持
//            if ("".equals(locale)) {
//                inputStream = XMessageBuilder.class.getClassLoader().getResourceAsStream(
//                        "com/cmy/xcheck/config/check_messages_CN.properties");
//            } else {
//                inputStream = XMessageBuilder.class.getClassLoader().getResourceAsStream(
//                        "com/cmy/xcheck/config/check_messages_EN.properties");
//            }
            inputStream = XMessageBuilder.class.getClassLoader().getResourceAsStream(
                    "com/cmy/xcheck/config/check_messages_CN.properties");
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            properties.load(inputStreamReader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /** 加载全局字段别名 */
        GLOBAL_FIELD_ALIAS = new HashMap<>();
        String filedAlias = xCheckContext.getFiledAlias();
        try {

            if (Validator.isNotEmpty(filedAlias)) {
                String[] split = filedAlias.replace(" ", "").split(",");
                for (String alias : split) {
                    String[] aliasSplit = alias.split("=");
                    GLOBAL_FIELD_ALIAS.put(aliasSplit[0], aliasSplit[1]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("字段别名设置正确，各位为field=alias多个用逗号分隔");
        }
    }

    public String buildMsg(String field, XBean xBean,
                           XCheckItemSimple.FormulaItem formulaItem, XCheckItemSimple checkItem) {
        if (!xBean.isHint() && !xCheckContext.isErrorMessageDisplay()) {
            return getProperty("ParameterError");
        }
        if (checkItem.getMessage() != null) {
            return checkItem.getMessage();
        } else {
            String baseMsg = getProperty(formulaItem.getMethodAbbr());
            String filedAlias = getFiledAlias(field, xBean.getFieldAlias());
            return filedAlias + MessageFormat.format(baseMsg, formulaItem.getArgument());
        }
    }

    public String buildMsg(String field, String methodAbbr, XBean xBean,
                           XCheckItem checkItem) {
        if (!xBean.isHint() && !xCheckContext.isErrorMessageDisplay()) {
            return getProperty("ParameterError");
        }
        if (checkItem.getMessage() != null) {
            return checkItem.getMessage();
        } else {
            String baseMsg = getProperty(methodAbbr);
            String filedAlias = getFiledAlias(field, xBean.getFieldAlias());
            return filedAlias + baseMsg;
        }
    }

    private String getFiledAlias(String field, Map<String, String> fieldAliasMap) {
        String alias = field;
        if (fieldAliasMap != null) {
            String fieldAlias = fieldAliasMap.get(field);
            if (fieldAlias != null) {
                alias = fieldAlias;
            } else {
                String globalFieldAlias = GLOBAL_FIELD_ALIAS.get(field);
                if (globalFieldAlias != null) {
                    alias = globalFieldAlias;
                }
            }
        }
        return alias;
    }


    public String getProperty(String key) {
        return properties.get(key).toString();
    }

    /**
     * logic formula error message build
     */
    public String buildLogicErrorMessage(XBean xBean, XCheckItemLogic xCheckItem) {
        String left = getFiledAlias(xCheckItem.getLeftField(), xBean.getFieldAlias());
        String right = getFiledAlias(xCheckItem.getRightField(), xBean.getFieldAlias());
        return left + getProperty(xCheckItem.getComparisonOperator()) + right;
    }



}
