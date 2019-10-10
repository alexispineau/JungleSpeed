package server;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;

public class Main {
    
    public static void main(String[] args) {
        String serverAdress = "";

        try {
            LocateRegistry.createRegistry(8090);
            Server server = new Server();
            Naming.bind(serverAdress, server);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
