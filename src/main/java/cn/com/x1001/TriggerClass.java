package cn.com.x1001;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 触发规则信息类
 */
public class TriggerClass {
    //类名
    private String clazz;
    //方法加描述
    private Set<String> methodDesc;

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Set<String> getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String md) {
        if(methodDesc == null){
            methodDesc = new HashSet<String>();
        }
        methodDesc.add(md);
    }
}
