package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JungleListener extends Remote {

    public void startGame() throws RemoteException;
    public void update() throws RemoteException;

}
