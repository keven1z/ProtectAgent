package cn.com.x1001.hook.rules.s2032;

import cn.com.x1001.Agent;
import cn.com.x1001.CodeClassHook;
import cn.com.x1001.HookCodes;
import cn.com.x1001.Policy;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class S2032Hook extends CodeClassHook {


    @Override
    protected String getCode() {
        return HookCodes.S2032;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions, MethodVisitor mv) {
        if (Agent.policy.isMethodAndDescMatch(getCode(), name, desc)) {
            return new S2032AdviceAdapter(Opcodes.ASM5, mv, access, name, desc);
        }
        return mv;
    }

}
