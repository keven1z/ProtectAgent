package cn.com.x1001.hook.http;

import cn.com.x1001.utils.ArrayUtil;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPClassVisitor extends ClassVisitor {
    private String className;
    private boolean isRequestClassName= false;
    private boolean isResponseClassName= false;
    private static final String REQUEST_HOOK = "org/apache/coyote/Adapter";
    private static final String RESPONSE_HOOK = "org/apache/coyote/Response";
    public HTTPClassVisitor(ClassVisitor cv, String className) {
        super(Opcodes.ASM5, cv);
        this.className = className;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        if (REQUEST_HOOK.equals(className)|| ArrayUtil.isContain(REQUEST_HOOK,interfaces)){
            isRequestClassName = true;
        }
        if(RESPONSE_HOOK.equals(className)){
            isResponseClassName = true;
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor localMethodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        if (isRequestClassName && "service".equals(name)){
            return new RequestAdviceAdapter(api, localMethodVisitor, access, name, desc);
        }
        if(isResponseClassName && "doWrite".equals(name)){

        }
        return localMethodVisitor;
    }
}
