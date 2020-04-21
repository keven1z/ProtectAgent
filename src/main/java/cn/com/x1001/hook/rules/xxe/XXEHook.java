package cn.com.x1001.hook.rules.xxe;

import cn.com.x1001.Agent;
import cn.com.x1001.CodeClassHook;
import cn.com.x1001.HookCodes;
import cn.com.x1001.hook.rules.s2037.S2037AdviceAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class XXEHook extends CodeClassHook {

    @Override
    protected String getCode() {
        return HookCodes.XXE;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions, MethodVisitor mv) {
        if (Agent.policy.isMethodAndDescMatch(getCode(), name,desc)) {
            return new XXEAdviceAdapter(Opcodes.ASM5, mv, access, name, desc);
        }
        return mv;
    }

}
