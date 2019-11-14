package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

public interface ClientInterface extends Remote {

	// méthode nul juste pour tester
	public UUID getClientID() throws RemoteException;
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
	public void addListener(JungleListener listener) throws RemoteException;
    public void removeCardFromStack() throws RemoteException;
    public void addCardFromStack(Card card) throws RemoteException;
    public void updateListeners() throws RemoteException;
    public void gameOver() throws RemoteException;
	public ClientInterface getNextPlayerInterface() throws RemoteException;
	public ClientInterface getPreviousPlayerInterface() throws RemoteException;
	public ClientInterface getThirdClientInterface() throws RemoteException;
	public ArrayList<Card> getPlayerDeck() throws RemoteException;
	public boolean getIHaveWin() throws RemoteException;
	public void setIHaveWin(boolean bl) throws RemoteException;
	public String getName() throws RemoteException;

}
