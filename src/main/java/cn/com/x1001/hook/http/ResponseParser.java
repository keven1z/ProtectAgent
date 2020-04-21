package cn.com.x1001.hook.http;

import cn.com.x1001.Agent;
import cn.com.x1001.http.HttpResponse;

public class ResponseParser {

    public static void parseResponse(Object object){

        Agent.response = new HttpResponse(object);
        Agent.request = null;
    }
}
