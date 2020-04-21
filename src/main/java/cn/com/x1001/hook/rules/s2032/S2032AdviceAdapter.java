package cn.com.x1001.hook.rules.s2032;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

/**
 * @author x1001
 * S2-032方法修改
 * 2020/4/8
 */
public class S2032AdviceAdapter extends AdviceAdapter {
    private static final Type S2032Type = Type.getType(S2032Checker.class);
//    Method method = new Method("evaluateInput", "(Ljava/lang/String;)V");
    Method method = new Method("onSinkInvoke", "()V");
    /**
     * Creates a new {@link AdviceAdapter}.
     *
     * @param api    the ASM API version implemented by this visitor. Must be one
     *               of {@link Opcodes#ASM4} or {@link Opcodes#ASM5}.
     * @param mv     the method visitor to which this adapter delegates calls.
     * @param access the method's access flags (see {@link Opcodes}).
     * @param name   the method's name.
     * @param desc   the method's descriptor (see {@link Type Type}).
     */
    public S2032AdviceAdapter(int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc);
    }

//    @Override
//    protected void onMethodEnter() {
//        getStatic(S2032Type,"INSTANCE",S2032Type);
//        loadArg(0);
//        invokeVirtual(S2032Type,method);
//    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if("setMethod".equals(name)){
            getStatic(S2032Type,"INSTANCE",S2032Type);
            invokeVirtual(S2032Type,method);
        }
        super.visitMethodInsn(opcode,owner, name, desc, itf);
    }
}
