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
        try {
            String name = "//127.0.0.1:8090/Server";
            LocateRegistry.createRegistry(8090);
            ServerInterface server = new Server();
            ServerInterface stub =
                    (ServerInterface) UnicastRemoteObject.exportObject(server, 0);
            System.out.println("Bravo le serveur a été démarré avec succès lol");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
