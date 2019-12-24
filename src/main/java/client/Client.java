package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Observable;
import java.util.UUID;

import server.Server;
import server.ServerInterface;

public class Client extends UnicastRemoteObject implements ClientInterface {

    private UUID clientID; // Client Unique ID
	private ServerInterface server; // server interface for communication
    public ClientInterface nextPlayer; // Client next player interface for communication
    public ClientInterface previousPlayer; // Client previous player interface for communication
    private ArrayList<Card> playerStack; // Unplayed cards
    private ArrayList<Card> discardStack; // played cards
    private Card card; // Card at the bottom of discardStack
    private boolean currentPlayer; // True if this is the current Player
    private boolean iHaveWin = false; // True if i have win the game
    private ArrayList<JungleListener> listeners;
    private String name; // The player Name
    
    // CLient constructor
    public Client(String name) throws RemoteException {
        this.listeners = new ArrayList<JungleListener>();
        this.discardStack = new ArrayList<Card>();
        this.name = name;
    }
    
    // getter List
    public UUID getClientID() throws RemoteException {return clientID;}
	public ClientInterface getNextPlayerInterface() throws RemoteException { return nextPlayer;}
	public ClientInterface getPreviousPlayerInterface() throws RemoteException { return previousPlayer;}
	public ClientInterface getThirdClientInterface() throws RemoteException { return nextPlayer.getNextPlayerInterface();}
	public boolean getIHaveWin() {return iHaveWin;}
	public void setIHaveWin(boolean bl) {iHaveWin = bl;}
	public String getName() {return this.name;}
	public boolean getCurrentPlayer() {return this.currentPlayer;}
	public int getMyNbcard() {return playerStack.size();}
    public int getNbDiscard() {return discardStack.size();}
    public ArrayList<Card> getPlayerStack() {return this.discardStack;}
	public ArrayList<Card> getPlayerDeck() {return this.playerStack;}
	
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
	
	//SetterList
	 public void setNextPlayer(ClientInterface nextPlayer) {this.nextPlayer = nextPlayer;}
	 public void setPreviousPlayer(ClientInterface previousPlayer) {this.previousPlayer = previousPlayer;}
	 public void setCurrentPlayer(boolean cur) {this.currentPlayer = cur;}
	 
	 public void setHand(ArrayList<Card> hand) {
	        this.playerStack = (ArrayList<Card>)hand.clone();
	        try {
	            for (JungleListener l : listeners) {
	                l.startGame();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	 public String testClient(String text) {return text;}

    
	 // Take on card of playerStack and put them on discardStack
    public void turnCard() {
    	if(this.currentPlayer) {
    		this.discardStack.add(this.playerStack.remove(this.playerStack.size()-1));
    	}
    	this.passMyturn();
    	System.out.println("*** turnCard() : on va update les listeners");
    	updateListeners();
    }
    
    // pass the hand to the next player
    private void passMyturn() {
    	if(this.currentPlayer) {
    		this.currentPlayer = false;
    		try{
                this.nextPlayer.setCurrentPlayer(true);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
    	}
    }

	public void gameOver() {

    }
	
	// remove the last Card of the discardStack
	public void removeCardFromStack() {
        if(this.playerStack.size() > 0) {
            System.out.println("La taille de la stack"+this.discardStack.size());
            this.discardStack.remove(this.discardStack.size()-1);
        }

    }
	
	// add a Card at the bootom of playerStack
    public void addCardFromStack(Card card) {
        this.playerStack.add(card);
    }
    
	// Send a signal to the Serve, for say that this Client Want play
	public void iWantPlay(int localPort, String nom) {
		try {
		    if (!nom.equals("")) this.name = nom;
			clientID = UUID.randomUUID();
			// server connection
            server = (ServerInterface) Naming.lookup("//127.0.0.1:8090/Server");
            System.out.println("Interface server récuppérée");
            
            // mise à disposition de l'interface du client
            String name = "//127.0.0.1:"+localPort+"/Client";
            LocateRegistry.createRegistry(localPort);
            ClientInterface client = this;
            /*ClientInterface stub =
                    (ClientInterface) UnicastRemoteObject.exportObject(client, 0);*/
            Naming.rebind(name,client);
            System.out.println("Client"+" "+localPort+""+ "lancé");
            
            //call the joinGame method from server to start the game
            server.joinGame(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	// Send to the Server that The client take the JungleSpeed Totem
	public void takeTotem() {
        try {
            server.takeTheTotem(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void addListener(JungleListener listener) throws RemoteException {
        System.out.println("  + addListener("+listener+") : ajout du listener dans la liste");
        System.out.println(listeners.size());
        this.listeners.add(listener);
        System.out.println(listeners.size());
        System.out.println(" /+");
    }

    public void updateListeners() {
        System.out.println(" ** updateListeners()");
        try {
            for (JungleListener l : listeners) {
                System.out.println(" ** updateListeners() : on va update un listener");
                l.update();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) throws RemoteException {
		try {
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
