package server;

import java.rmi.*;

public class Main {
    public static void main(String[] args) {
        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }
        } catch (Exception e) {
            //e.printStrackTrace(System.out);
        }
    }


}
