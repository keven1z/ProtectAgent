package cn.com.x1001.hook.rules.s2032;

import cn.com.x1001.FlowInterface;
import cn.com.x1001.http.AbstractRequest;
import cn.com.x1001.http.CoyoteRequest;
import cn.com.x1001.utils.ArrayUtil;


/**
 * by x1001 2020/4/8
 * S2032 攻击检测
 */
public class S2032Checker implements FlowInterface {
    private static boolean isEnable = false;
    public static final S2032Checker INSTANCE = new S2032Checker();
    /* S2-032 修改memberAccess的方式*/
    private static final String[] BLACK_KEYWORD = {
            "@DEFAULT_MEMBER_ACCESS", "_memberAccess", "allowStaticMethodAccess" };

    public void onSinkInvoke(){
        if(isEnable){
            isEnable = false;
            throw new SecurityException("[Agent] S2032 Attack Detected");
        }
    }

    /**
     * 校验s2032的输入
     */
    public boolean evaluateInput(String key){
        if (key == null) return false;
        if(key.startsWith("method:") && ArrayUtil.isContain(key, BLACK_KEYWORD)){
            return true;
        }
        return false;
    }

    @Override
    public void onRequestEnter(AbstractRequest request) {
        String parameters = request.getParameters();
        if(!isEnable && evaluateInput(parameters)){
            isEnable = true;
        }
    }

    @Override
    public void onResponseStart() {

    }
}
