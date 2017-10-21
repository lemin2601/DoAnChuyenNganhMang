package lib;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

public class Lib {

    public static String getMyIp() {
        try {
            Enumeration<NetworkInterface> NICs = NetworkInterface.getNetworkInterfaces();
            while (NICs.hasMoreElements() == true) {
                NetworkInterface NIC = NICs.nextElement();
                Enumeration<InetAddress> IPs = NIC.getInetAddresses();
                while (IPs.hasMoreElements() == true) {
                    InetAddress IP = IPs.nextElement();
                    if (IP instanceof java.net.Inet4Address) {
                        if (IP.toString().startsWith("/192")) {
                            return IP.toString().substring(1);
                        }
                    }
                }
            }
        } catch (SocketException e4) {
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(Lib.getMyIp());
    }
}
