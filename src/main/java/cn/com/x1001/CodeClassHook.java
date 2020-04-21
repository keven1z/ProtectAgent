package cn.com.x1001;

import org.objectweb.asm.*;

public abstract class CodeClassHook {
    protected abstract String getCode();
    private String[] interfaces;
    public  boolean isClassMatched(String className){
        return getCode().equals(Agent.policy.getHookCode(className));
    }

    public String[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String[] interfaces) {
        this.interfaces = interfaces;
    }

    public abstract MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions, MethodVisitor mv);

    public byte[] transformClass(byte[] classfileBuffer) {
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader,ClassWriter.COMPUTE_FRAMES);
        CodeClassVisitor codeClassVisitor = new CodeClassVisitor(classWriter, this);
        classReader.accept(codeClassVisitor, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    private static class CodeClassVisitor extends ClassVisitor{
        private CodeClassHook codeClassHook;
        public CodeClassVisitor(ClassVisitor cv,CodeClassHook codeClassHook) {
            super(Opcodes.ASM5, cv);
            this.codeClassHook = codeClassHook;
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
            this.codeClassHook.setInterfaces(interfaces);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor localMethodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
            MethodVisitor methodVisitor = codeClassHook.visitMethod(access, name, desc, signature, exceptions,localMethodVisitor);
            if (methodVisitor == null) methodVisitor = localMethodVisitor;
            return methodVisitor;
        }
    }

}
