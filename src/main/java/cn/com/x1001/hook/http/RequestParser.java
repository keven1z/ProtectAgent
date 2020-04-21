package cn.com.x1001.hook.http;

import cn.com.x1001.Agent;
import cn.com.x1001.http.CoyoteRequest;
import cn.com.x1001.utils.Reflection;


/**
 * @author x1001
 * 解析request请求
 */
public class RequestParser {
    public static void parseRequest(Object[] object){
        Object request = object[0];
        if (request == null) return;
        Agent.request = new CoyoteRequest(request);
        Agent.flowManager.onRequestEnter(Agent.request);
    }
}
