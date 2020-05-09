package com.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import java.net.InetAddress;
import java.util.List;
import java.util.Set;

public class MBeanUtils {
    
    final static Logger logger = LoggerFactory.getLogger(MBeanUtils.class.getName());
    
    private static String ip;
    
    private static Integer port;
    
    public static void init() throws Exception {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress().toString();
            
            MBeanServer mBeanServer = null;
            List<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(null);
            mBeanServer = mBeanServers.get(0);        
            Set<ObjectName> objectNames = mBeanServer.queryNames(new ObjectName("Catalina:type=Connector,*"), null);
            for (ObjectName objectName : objectNames) {
                String protocol = (String) mBeanServer.getAttribute(objectName, "protocol");
                if (protocol.equals("HTTP/1.1")) {
                    port = (Integer) mBeanServer.getAttribute(objectName, "port");
                    break;
                }
            }
        } catch (Exception ex) {
            logger.error("初始化IP和Port失败", ex);
            throw ex;
        }
    }

    public static String getIp() throws Exception {
        if (ip == null) {
            init();
        }
        return ip;
    }

    public static Integer getPort() throws Exception {
        if (port == null) {
            init();
        }
        return port;
    }
}
