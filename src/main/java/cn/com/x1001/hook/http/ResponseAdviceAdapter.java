package cn.com.x1001.hook.http;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

public class ResponseAdviceAdapter extends AdviceAdapter {
//    Method method = new Method("evaluateInput", "(Ljava/lang/String;)V");
    Method method = new Method("parseResponse", "(Ljava/lang/Object;)V");

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
    public ResponseAdviceAdapter(int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }

    @Override
    protected void onMethodEnter() {
        loadThis();
        dup();
        swap();
        invokeStatic(Type.getType(ResponseParser.class),method);
        super.onMethodEnter();
    }
}
