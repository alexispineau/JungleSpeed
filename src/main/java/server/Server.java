package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import client.ClientInterface;

public class Server implements ServerInterface {

    private ArrayList<ClientInterface> clients;

    public Server() {
        super();
    }

    public void joinGame(ClientInterface client) throws RemoteException {
        this.clients.add(client);
        //TODO Do not add client if the game has started
    }

    public void takeTheTotem(ClientInterface client) throws RemoteException {
        //TODO
    }

    public static void main(String[] args) {
        System.setProperty("java.security.policy","file:/home/alexis/Master/JungleSpeed/JungleSpeed/security.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Server";
            ServerInterface server = new Server();
            ServerInterface stub =
                    (ServerInterface) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
