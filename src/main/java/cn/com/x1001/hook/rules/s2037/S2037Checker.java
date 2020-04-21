package cn.com.x1001.hook.rules.s2037;

import cn.com.x1001.FlowInterface;
import cn.com.x1001.http.AbstractRequest;
import cn.com.x1001.utils.ArrayUtil;
/**
 * @author x1001
 * 2020/4/21
 * S2037 攻击检测
 */
public class S2037Checker implements FlowInterface {
    private static boolean isEnable = false;
    public static final S2037Checker INSTANCE = new S2037Checker();
    /* S2-037 修改memberAccess的方式*/
    private static final String[] BLACK_KEYWORD = {
            "@DEFAULT_MEMBER_ACCESS", "_memberAccess", "allowStaticMethodAccess" };

    public void onSinkInvoke(){
        if(isEnable){
            isEnable = false;
            throw new SecurityException("[Agent] S2037 Attack Detected");
        }
    }

    /**
     * 校验s2037的输入
     */
    public boolean evaluateInput(String key){
        if (key == null) return false;
        if(ArrayUtil.isContain(key, BLACK_KEYWORD)){
            return true;
        }
        return false;
    }

    @Override
    public void onRequestEnter(AbstractRequest request) {
        String parameters = request.getRequestURI();
        if(!isEnable && evaluateInput(parameters)){
            isEnable = true;
        }
    }

    @Override
    public void onResponseStart() {
    }
}
