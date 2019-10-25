package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import server.Server;
import server.ServerInterface;

public class Client extends UnicastRemoteObject implements ClientInterface {

	private ServerInterface server;
    private ClientInterface nextPlayer;
    private ClientInterface previousPlayer;
    private ArrayList<Card> playerStack;
    private ArrayList<Card> discardStack;
    private Card card;
    private boolean currentPlayer;

    public Client() throws RemoteException { }

    public void setNextPlayer(ClientInterface nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public void setPreviousPlayer(ClientInterface previousPlayer) {
        this.previousPlayer = previousPlayer;
    }
    
    public boolean getCurrentPlayer() {
    	return this.currentPlayer;
    }
    public void setCurrentPlayer(boolean cur) {
    	this.currentPlayer = cur;
    }

    public Card getBottomCardOfNextPlayer() {
        //TODO
        return null;
    }

    public int getNbCardOfNextPlayer() {
        //TODO
        return -1;
    }

    public Card getBottomCard() {
        return this.card;
    }

    public int getMyNbcard() {
        return playerStack.size();
    }

    public void setHand(ArrayList<Card> hand) {
        this.playerStack = (ArrayList<Card>)hand.clone();
    }

    public void turnCard() {
        this.discardStack.add(this.card);
        for(Card s : this.playerStack) {
            if(this.card == s) {
                this.playerStack.remove(s);
            }
        }

        //TODO Notify other players
    }

	public ArrayList<Card> getPlayerStack() {return this.discardStack;}
	
	// Envoie un signal au serveur pour signifier que je veux jouer
	public void IWantPlay(int localPort) {
		try {
			// connection au serveur
            server = (ServerInterface) Naming.lookup("//127.0.0.1:8090/Server");
            System.out.println("Interface server récuppérée");
            System.out.println(server.test());
            
            // mise à disposition de l'interface du client
            String name = "//127.0.0.1:"+localPort+"/Client";
            LocateRegistry.createRegistry(localPort);
            ClientInterface client = this;
            ClientInterface stub =
                    (ClientInterface) UnicastRemoteObject.exportObject(client, 0);
            Naming.rebind(name,stub);
            System.out.println("Client lancé");
            
            //Appel à la méthode joinGame du serveur pour commencer une partie
            server.joinGame(name);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
        try {
            ServerInterface server = (ServerInterface) Naming.lookup("//127.0.0.1:8090/Server");
            System.out.println("CLient lancé");
            System.out.println(server.test());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
