package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Observable;

import server.Server;
import server.ServerInterface;

public class Client extends UnicastRemoteObject implements ClientInterface {

	public int clientID;
	private ServerInterface server;
    public ClientInterface nextPlayer;
    public ClientInterface previousPlayer;
    private ArrayList<Card> playerStack; // Cartes non joués
    private ArrayList<Card> discardStack; // Cartes joués
    private Card card;
    private boolean currentPlayer; // vrai si ce client est le joueur courant
    private boolean iHaveWin = false;
    private ArrayList<JungleListener> listeners;
    
    public int getClientID() throws RemoteException {return clientID;}
	public ClientInterface getNextPlayerInterface() throws RemoteException { return nextPlayer;}
	public ClientInterface getPreviousPlayerInterface() throws RemoteException { return previousPlayer;}
	public ClientInterface getThirdClientInterface() throws RemoteException { return nextPlayer.getNextPlayerInterface();}
	public boolean getIHaveWin() {return iHaveWin;}
	public void setIHaveWin(boolean bl) {iHaveWin = bl;}

    public Client() throws RemoteException {
        this.listeners = new ArrayList<JungleListener>();
        this.discardStack = new ArrayList<Card>();
    }
    public String testClient(String text) {return text;}

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
        Card ret = null;
        try {
            ret = this.nextPlayer.getBottomCard();
        }
        catch(RemoteException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int getNbCardOfNextPlayer() {
        int ret = -1;
        try {
            ret =  this.nextPlayer.getMyNbcard();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Card getBottomCard() {
        Card ret = null;
        if (!discardStack.isEmpty()) {
            ret = this.discardStack.get(this.discardStack.size()-1);
        }
        return ret;
    }

    public int getMyNbcard() {
        return playerStack.size();
    }

    public void setHand(ArrayList<Card> hand) {
        this.playerStack = (ArrayList<Card>)hand.clone();
        for (JungleListener l : listeners) {
            l.startGame();
        }
    }

    public void turnCard() {
    	if(this.currentPlayer) {
    		this.discardStack.add(this.playerStack.remove(this.playerStack.size()-1));
    	}

    	updateListeners();
        //TODO Notify other players
    }
    
    // passe la main au joueur suivant
    public void passMyturn() throws RemoteException {
    	if (this.currentPlayer) {
    		this.currentPlayer = false;
    		try{
    			this.nextPlayer.setCurrentPlayer(true);
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    	updateListeners();
    }

	public ArrayList<Card> getPlayerStack() {return this.discardStack;}
	public ArrayList<Card> getPlayerDeck() {return this.playerStack;}
	
	// Envoie un signal au serveur pour signifier que je veux jouer
	public void iWantPlay(int localPort) {
		try {
			clientID = localPort;
			// connection au serveur
            server = (ServerInterface) Naming.lookup("//127.0.0.1:8090/Server");
            System.out.println("Interface server récuppérée");
            System.out.println(server.test());
            
            // mise à disposition de l'interface du client
            String name = "//127.0.0.1:"+localPort+"/Client";
            LocateRegistry.createRegistry(localPort);
            ClientInterface client = this;
            /*ClientInterface stub =
                    (ClientInterface) UnicastRemoteObject.exportObject(client, 0);*/
            Naming.rebind(name,client);
            System.out.println("Client"+" "+localPort+""+ "lancé");
            
            //Appel à la méthode joinGame du serveur pour commencer une partie
            server.joinGame(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public void addListener(JungleListener jl) {
        this.listeners.add(jl);
    }

    private void updateListeners() {
        for (JungleListener l : listeners) {
            l.update();
        }
    }
	
	public static void main(String[] args) throws RemoteException {
		/*
        try {
            ServerInterface server = (ServerInterface) Naming.lookup("//127.0.0.1:8090/Server");
            System.out.println("CLient lancé");
            System.out.println(server.test());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
		try {/*
			Client firstClient = new Client();
			firstClient.IWantPlay(8100);
			
			Client secondClient = new Client();
			secondClient.IWantPlay(8101);
			
			Client thirdClient = new Client();
			thirdClient.IWantPlay(8102);
			
			Client fourClient = new Client();
			fourClient.IWantPlay(8103);
			
			System.out.println(firstClient.previousPlayer.testClient("Test de com avec le previous"));
			System.out.println(firstClient.nextPlayer.testClient("test de com avec le next"));*/
			ArrayList<Integer> test = new ArrayList<Integer>();
			for (int i=0;i<10;i++) { test.add(i); }
			System.out.println("Taille du tableau test après 10 add : "+test.size());
			test.remove(test.size()-1);
			System.out.println("Taille du tableau test après 10 add puis un remove : "+test.size());

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
