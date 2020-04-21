package cn.com.x1001;

import java.util.ArrayList;
import java.util.List;

/**
 * @author x1001
 * 策略文件
 */
public class Policy {

    // 以 （漏洞名称，hook点）存入map
    private List<ClassHooker> hookers = new ArrayList<>();

    /**
     * 初始化策略
     */
    public void init() {
        addToPolicy();
    }

    /**
     * 增加防护的hook点
     */
    private void addToPolicy() {
        addHook(HookCodes.S2037,"com/opensymphony/xwork2/DefaultActionInvocation","invokeAction",null);

        addHook(HookCodes.S2032,"org/apache/struts2/dispatcher/mapper/DefaultActionMapper$2$1","execute","(Ljava/lang/String;Lorg/apache/struts2/dispatcher/mapper/ActionMapping;)V");
//        addHook(HookCodes.S2045,"org/apache/struts2/dispatcher/multipart/JakartaMultiPartRequest","parse",null, S2045Worker.INSTANCE);
        addHook(HookCodes.XXE,"com.sun.org.apache.xerces.internal.impl.XMLEntityManager".replace(".","/"),"setupCurrentEntity","(ZLjava.lang.String;Lcom.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;ZZ)Ljava.lang.String;".replace(".","/"));
    }
    /**
     * 增加HOOK点
     * @param clazz  类名
     * @param method 方法名
     * @param desc 方法描述
     */
    private void addHook(String code,String clazz, String method, String desc){
        ClassHooker classHooker = new ClassHooker(clazz, method, desc, code);
        this.hookers.add(classHooker);
    }
    /**
     * 类名是否匹配
     * @param className
     * @return
     */
    public boolean isClassMatch(String className){
        for (ClassHooker classHooker :hookers){
            String replace = classHooker.getClazz().replace(".", "/");
            if(replace.equals(className)) return true;
        }
        return false;
    }

    /**
     * 根据className获取hook点code
     */
    public String getHookCode(String className){
        for (ClassHooker classHooker:hookers){
            String clazz = classHooker.getClazz();
            if(clazz.equals(className)) return classHooker.getCode();
        }
        return null;
    }

    /**
     * 匹配当前hook点的方法
     */
    public boolean isMethodMatch(String code,String method){
        if (code == null || method == null) return false;
        for (ClassHooker classHooker:hookers){
            if(code.equals(classHooker.getCode())) return method.equals(classHooker.getMethod());
        }
        return false;
    }
    /**
     * 匹配当前hook点的方法
     */
    public boolean isDescMatch(String code,String desc){
        if (code == null || desc == null) return false;
        for (ClassHooker classHooker:hookers){
            if(code.equals(classHooker.getCode())) return desc.equals(classHooker.getDesc());
        }
        return false;
    }
    /**
     * 匹配方法和描述
     */
    public boolean isMethodAndDescMatch(String type,String method,String desc){
        return isMethodMatch(type,method) && isDescMatch(type,desc);
    }

    public List<ClassHooker> getHookers() {
        return hookers;
    }
}