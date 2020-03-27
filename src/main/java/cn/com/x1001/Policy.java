package cn.com.x1001;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Policy {
    public static String XXE = "xxe";
    // 以 （漏洞名称，hook点）存入map
    private  ConcurrentHashMap<String, TriggerClass> vulnHashMap = new ConcurrentHashMap<String, TriggerClass>();

    /**
     * 初始化策略
     */
    public void init() {
        addToPolicy();
    }

    /**
     * 增加XXE防护的hook点
     */
    private void addToPolicy() {
        TriggerClass triggerClass = new TriggerClass();
        triggerClass.setClazz("com.sun.org.apache.xerces.internal.impl.XMLEntityManager");
        triggerClass.setMethodDesc("setupCurrentEntity(ZLjava.lang.String;Lcom.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;ZZ)Ljava.lang.String;".replace(".","/"));
        vulnHashMap.put(XXE,triggerClass);
    }

    /**
     * 类名是否匹配
     * @param className
     * @return
     */
    public boolean isClassMatch(String className){
        for (TriggerClass triggerClass:vulnHashMap.values()){
            String replace = triggerClass.getClazz().replace(".", "/");
            if(replace.equals(className)) return true;
        }
        return false;
    }

    /**
     * 匹配方法和描述
     */
    public boolean isMDMatch(String method,String desc){
        String md = method+desc;
        for (TriggerClass triggerClass:vulnHashMap.values()){
            Set<String> methodDescs = triggerClass.getMethodDesc();
            for(String methodDesc:methodDescs){
                if(methodDesc.equals(md)) return true;
            }
        }
        return false;
    }

    /**
     * 根据className获取漏洞名称
     * @param className
     * @return
     */
    public String getVulnName(String className){
        for (Map.Entry<String,TriggerClass> entry:vulnHashMap.entrySet()){
            TriggerClass triggerClass = entry.getValue();
            String replace = triggerClass.getClazz().replace(".", "/");
            if(replace.equals(className)) return entry.getKey();
        }
        return null;
    }
}