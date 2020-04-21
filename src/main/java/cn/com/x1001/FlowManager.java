package cn.com.x1001;

import cn.com.x1001.hook.rules.s2032.S2032Checker;
import cn.com.x1001.hook.rules.s2037.S2037Checker;
import cn.com.x1001.hook.rules.xxe.XXEChecker;
import cn.com.x1001.http.AbstractRequest;
import cn.com.x1001.http.CoyoteRequest;

import java.util.LinkedList;
import java.util.List;

/**
 * 流量控制类
 */
public class FlowManager {
    private static List<FlowInterface> flows = new LinkedList<>();

    /**
     * 注册流量接口
     */
    public void registerInterface(){
        flows.add(new S2032Checker());
        flows.add(new S2037Checker());
        flows.add(new XXEChecker());
    }
    public void onRequestEnter(AbstractRequest request){
        if (request == null) return;
        for (FlowInterface flowInterface:flows){
            flowInterface.onRequestEnter(request);
        }
    }
}
