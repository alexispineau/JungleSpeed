package server;

import client.Client;
import client.ClientInterface;

import java.rmi.Remote;

public interface ServerInterface extends Remote {

    public void joinGame(ClientInterface client);
    public void takeTheTotem(ClientInterface client);

}
