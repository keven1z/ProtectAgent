package cn.com.x1001.hook.rules.s2037;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

/**
 * @author x1001
 * S2-037方法修改
 * 2020/4/10
 */
public class S2037AdviceAdapter extends AdviceAdapter {
    private static final Type S2037Type = Type.getType(S2037Checker.class);
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
    public S2037AdviceAdapter(int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if("getValue".equals(name)){
            getStatic(S2037Type,"INSTANCE",S2037Type);
            invokeVirtual(S2037Type,method);
        }
        super.visitMethodInsn(opcode,owner, name, desc, itf);
    }
}
