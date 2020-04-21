package cn.com.x1001.hook.rules.s2037;

import cn.com.x1001.Agent;
import cn.com.x1001.CodeClassHook;
import cn.com.x1001.HookCodes;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class S2037Hook extends CodeClassHook {

    @Override
    protected String getCode() {
        return HookCodes.S2037;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions, MethodVisitor mv) {
        if (Agent.policy.isMethodMatch(getCode(), name)) {
            return new S2037AdviceAdapter(Opcodes.ASM5, mv, access, name, desc);
        }
        return mv;
    }

}
