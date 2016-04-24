package com.cmy.xcheck.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
/**
 * @Description: 
 * 杭州动享互联网技术有限公司
 * @author chenjw
 * @date 2016年4月5日 下午2:40:31
 */
public class ResponseWriter {

    /**
     * 输出数据
     * @param response
     * @param status
     * @param message
     */
    public static void writeMessage(ServletResponse response,
            int status, String message) {
        PrintWriter writer = null;
        try {
            response.setContentType("application/json; charset=utf-8");
            writer = response.getWriter();
            writer.write(getContent(status, message));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    private static String getContent(int status, String message) {
        return "{\"status\":" + status + ",\"message\":\"" + message +"\"}";
    }
}
