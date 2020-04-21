package cn.com.x1001;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 触发规则信息类
 */
public class ClassHooker {
    //类名
    private String clazz;
    //方法
    private String method;
    //描述
    private String desc;
    //hook的操作id
    private String code;

    public ClassHooker(String clazz, String method, String desc, String code) {
        this.clazz = clazz;
        this.method = method;
        this.desc = desc;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
