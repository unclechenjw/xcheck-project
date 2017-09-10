package com.c.j.w.xcheck.util;

import com.c.j.w.xcheck.support.XConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class XHelper {

    @Autowired(required = false)
    private XConfiguration xCheckProperties;
    private Map<String, String> GLOBAL_FIELD_ALIAS = new HashMap<>();

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
            inputStream = XHelper.class.getClassLoader().getResourceAsStream(
                    "com/c/j/w/xcheck/config/check_messages_CN.properties");
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
        String filedAlias = xCheckProperties.getFiledAlias();
        try {
            if (StringUtil.isNotEmpty(filedAlias)) {
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

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getAlias(String field, Map<String, String> fieldAliasMap) {
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


}
