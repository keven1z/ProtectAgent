package cn.com.x1001;

import cn.com.x1001.http.CoyoteRequest;
import cn.com.x1001.http.HttpResponse;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.instrument.Instrumentation;

public class Agent {
    //打印输出
    public static PrintStream out = System.out;
    //策略文件，你可以在任何地方访问
    public static Policy policy = new Policy();
    public static FlowManager flowManager = new FlowManager();
    public static CoyoteRequest request;
    public static HttpResponse response;

    public static void premain(String args, Instrumentation inst) throws IOException {
        /*策略初始化*/
        policy.init();
        /*注册流量接口*/
        flowManager.registerInterface();
        start(inst);
    }

    private static void start(Instrumentation inst) {
        out.println("********************************************************************");
        out.println("*                      Agent for  attacks                          *");
        out.println("********************************************************************");
        /*流量transform*/
        inst.addTransformer(new HTTPTransformer(),true);
        /*漏洞transform*/
        inst.addTransformer(new ProtectTransformer(),true);

        Class[] loadedClasses = inst.getAllLoadedClasses();
        for (Class clazz : loadedClasses) {
            String name = clazz.getName().replace(".", "/");
            if (Agent.policy.isClassMatch(name)) {
                if (inst.isModifiableClass(clazz) && !clazz.getName().startsWith("java.lang.invoke.LambdaForm")) {
                    try {
                        // hook已经加载的类，或者是回滚已经加载的类
                        inst.retransformClasses(clazz);
                    } catch (Throwable t) {
                        t.getStackTrace();
                    }
                }
            }


        }
    }

}
