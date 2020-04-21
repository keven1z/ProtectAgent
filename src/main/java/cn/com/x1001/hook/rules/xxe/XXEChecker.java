package cn.com.x1001.hook.rules.xxe;

import cn.com.x1001.Agent;
import cn.com.x1001.FlowInterface;
import cn.com.x1001.Policy;
import cn.com.x1001.http.AbstractRequest;
import cn.com.x1001.http.CoyoteRequest;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;

/**
 * by x1001 2020/4/8
 * XXE 攻击检测
 */
public class XXEChecker implements FlowInterface {


    public static void check(Object[] args) {
        //如果没有流量，不进入检测逻辑
        if (Agent.request == null) return;
        // name of the entity
        String entityName = args[1].toString();
        Object arg = args[2];
        //过滤[XML]
        if(_check(entityName,arg)) throw new SecurityException("[Agent] XXE Attack Detected");

    }

    /**
     * 检测的核心逻辑
     * @param entityName 实体名称
     * @param arg
     * @return
     */
    private static boolean _check(String entityName,Object arg){
        if ("[xml]".equalsIgnoreCase(entityName)) {
            return false;
        }
        if (arg instanceof XMLInputSource) {
            XMLInputSource source = (XMLInputSource) arg;
            String systemId = source.getSystemId();
            if (systemId == null) {
                return false;
            }
            //检测外部实体
            return checkSystemId(systemId);
        }
        return false;
    }

    /**
     * 检测外部实体
     *
     * @param systemId
     * @return
     */
    private static boolean checkSystemId(String systemId) {
        systemId = systemId.toLowerCase();
        if ((systemId.startsWith("https:") || systemId.startsWith("http:")) && !systemId.endsWith(".dtd")){
            return true;
        }
        if (systemId.startsWith("file:")||systemId.startsWith("gopher")||systemId.startsWith("dict")||systemId.startsWith("data:")|| systemId.startsWith("jar:")){
            return true;
        }
        return false;
    }

    @Override
    public void onRequestEnter(AbstractRequest request) {

    }

    @Override
    public void onResponseStart() {

    }
}
