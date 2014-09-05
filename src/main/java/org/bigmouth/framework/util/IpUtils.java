package org.bigmouth.framework.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

public class IpUtils {

    /**
     * 获取本地主机上绑定的所有IP地址
     * 
     * @return 本地主机上绑定的所有IP地址
     * @author Gallen Chu
     */
    public static final Set<String> getLocalIps() {
        Set<String> ips = new LinkedHashSet<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces != null && interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses != null && addresses.hasMoreElements()) {
                    InetAddress addresse = addresses.nextElement();
                    String ip = addresse.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        ips.add(ip);
                    }
                }
            }
        }
        catch (SocketException e) {
        }
        return ips;
    }

}