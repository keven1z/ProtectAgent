package cn.com.x1001;


import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;

public class ResolveClassController {
    public static void onResolveClass(Object[] args, String className) {
        if (Policy.XXE.equals(Agent.policy.getVulnName(className))) {
            if (args == null || args.length == 0) {
                return;
            }
            // name of the entity
            String entityName = args[1].toString();
            Object arg = args[2];
            //过滤[XML]
            if ("[xml]".equalsIgnoreCase(entityName)) {
                return;
            }
            if (arg instanceof XMLInputSource) {
                XMLInputSource source = (XMLInputSource) arg;
                String systemId = source.getSystemId();
                if (systemId == null) {
                    return;
                }
                if (checkSystemId(systemId)) {
                    throw new SecurityException("[XXE ATTACK] entity:" + systemId);
                }
            }
        }
    }

    /**
     * 检测外部实体
     *
     * @param systemId
     * @return
     */
    private static boolean checkSystemId(String systemId) {
        systemId = systemId.toLowerCase();
        if ((systemId.startsWith("https:") || systemId.startsWith("http:")) && !systemId.endsWith(".dtd")){
            return true;
        }
        if (systemId.startsWith("file:")||systemId.startsWith("jar:")||systemId.startsWith("gopher")||systemId.startsWith("dict")||systemId.startsWith("data:")){
            return true;
        }
        return false;
    }
}
