package cn.com.x1001;

import cn.com.x1001.hook.rules.s2032.S2032Hook;
import cn.com.x1001.hook.rules.s2037.S2037Hook;
import cn.com.x1001.hook.rules.xxe.XXEHook;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

public class ProtectTransformer implements ClassFileTransformer {
    private  Set<CodeClassHook> hooks;
    public ProtectTransformer(){
        hooks = new HashSet<>();
        addHook(new S2032Hook());
        addHook(new S2037Hook());
        addHook(new XXEHook());
//        addHook(new S2045Hook());
    }

    private void addHook(CodeClassHook requestHook) {
        this.hooks.add(requestHook);
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if(className == null){
            return classfileBuffer;
        }
        try{
            return doTransform(loader,className,classBeingRedefined,protectionDomain,classfileBuffer);
        }
        catch (Exception e){
            return classfileBuffer;
        }
    }
    private byte[] doTransform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer){
        for(CodeClassHook codeClassHook:hooks){
            if(codeClassHook.isClassMatched(className)){
                return codeClassHook.transformClass(classfileBuffer);
            }
        }
        return classfileBuffer;
    }

}
