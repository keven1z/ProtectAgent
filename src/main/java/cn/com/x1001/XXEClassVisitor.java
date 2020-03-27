package cn.com.x1001;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class XXEClassVisitor extends ClassVisitor {
    private String className;
    public XXEClassVisitor(ClassVisitor cv,String className) {
        super(Opcodes.ASM5, cv);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        if(Agent.policy.isMDMatch(name,desc)){
            return new XXEAdviceAdapter(api,methodVisitor,access,name,desc,className);
        }
        return methodVisitor;
    }
}
