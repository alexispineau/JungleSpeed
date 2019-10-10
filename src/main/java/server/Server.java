package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import client.ClientInterface;

public class Server extends UnicastRemoteObject implements ServerInterface {

    public Server() throws RemoteException { }

    public void joinGame(ClientInterface client) {
        //TODO
    }

    public void takeTheTotem(ClientInterface client) {
        //TODO
    }

}
