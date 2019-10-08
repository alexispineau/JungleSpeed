package server;

import client.Client;

import java.rmi.Remote;

public interface ServerInterface extends Remote {

    public void joinGame(Client client);
    public void takeTheTotem(Client client);

}
