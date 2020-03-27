package cn.com.x1001;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class XXETransformer implements ClassFileTransformer {

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className == null || !Agent.policy.isClassMatch(className)) {
            return classfileBuffer;
        }
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter( ClassWriter.COMPUTE_FRAMES);
        XXEClassVisitor xxeClassVisitor = new XXEClassVisitor(classWriter,className);
        classReader.accept(xxeClassVisitor, ClassReader.EXPAND_FRAMES);
        Agent.out.println("Add Hook point:"+className);
        return classWriter.toByteArray();
    }

}
