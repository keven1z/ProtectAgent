package cn.com.x1001;

import cn.com.x1001.hook.http.HTTPClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Set;

public class HTTPTransformer implements ClassFileTransformer {
    private  Set<CodeClassHook> hooks;


    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if(className == null){
            return classfileBuffer;
        }
        try{
            ClassReader classReader = new ClassReader(classfileBuffer);
            ClassWriter classWriter = new ClassWriter(classReader,ClassWriter.COMPUTE_FRAMES);
            HTTPClassVisitor httpClassVisitor = new HTTPClassVisitor(classWriter, className);
            classReader.accept(httpClassVisitor, ClassReader.EXPAND_FRAMES);
            return classWriter.toByteArray();
        }
        catch (Exception e){
            return classfileBuffer;
        }
    }

}
