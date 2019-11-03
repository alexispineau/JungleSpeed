package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientInterface extends Remote {

	// méthode nul juste pour tester
	public int getClientID() throws RemoteException;
	public String testClient(String text) throws RemoteException;
    public void setNextPlayer(ClientInterface player ) throws RemoteException;
    // Moi je pense qu'il faut supprimer cette méthode^^
    public void setPreviousPlayer(ClientInterface player) throws RemoteException;
    public Card getBottomCardOfNextPlayer() throws RemoteException;
    public boolean getCurrentPlayer() throws RemoteException;
    public void setCurrentPlayer(boolean cur) throws RemoteException;
    public int getNbCardOfNextPlayer() throws RemoteException;
    public Card getBottomCard() throws RemoteException;
    public int getMyNbcard() throws RemoteException;
    public void setHand(ArrayList<Card> crds) throws RemoteException;
	public ArrayList<Card> getPlayerStack() throws RemoteException;
	public void passMyturn() throws RemoteException;
	
	public ClientInterface getNextPlayerInterface() throws RemoteException;
	public ClientInterface getPreviousPlayerInterface() throws RemoteException;
	public ClientInterface getThirdClientInterface() throws RemoteException;
	public ArrayList<Card> getPlayerDeck() throws RemoteException;



}
