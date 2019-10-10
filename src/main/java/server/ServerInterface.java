package server;

import client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    public void joinGame(ClientInterface client) throws RemoteException;
    public void takeTheTotem(ClientInterface client) throws RemoteException;

}
