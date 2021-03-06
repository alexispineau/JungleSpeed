package server;

import client.Card;
import client.Client;
import client.ClientInterface;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;

import client.ClientInterface;

public class Server implements ServerInterface {
	
	public static int nbMAXPlayerInGame = 4;

    private ArrayList<ClientInterface> clientsInMatchMaking; // fille d'attente pour attente de partie
    int cpt = 0;

    public Server() {
        super();
        // Initialisation of the Match Making ArrayList
        clientsInMatchMaking = new  ArrayList<ClientInterface>();
    }

    public synchronized void joinGame(String clientAdress) throws RemoteException {
    	 try {
    		 // connection with the client interface which called the joinGame method
    		 ClientInterface client = (ClientInterface) Naming.lookup(clientAdress);
             System.out.println("SERVER Connexion au clien : "+clientAdress);
             // adding the client interface to a queue
             this.clientsInMatchMaking.add(client);
             cpt++;
        	// treatment for the first 3 calls
        	if (cpt < nbMAXPlayerInGame) {
        			System.out.println("SERVER Le client : "+clientAdress+" entre en Match Making");
        	}
        	// treatment for the 4th call
        	else {
    	    	ArrayList<ArrayList<Card>> crds = new ArrayList<ArrayList<Card>>(nbMAXPlayerInGame);
    	    	crds = melange(17,nbMAXPlayerInGame);
    	    		
    	    	// initialization of previous and next player for each player + definition of the current player
    	    	for(int i=0;i<nbMAXPlayerInGame;i++) {
    	    		clientsInMatchMaking.get(i).setNextPlayer(clientsInMatchMaking.get((i+1)%nbMAXPlayerInGame));
    	    		clientsInMatchMaking.get((i+1)%nbMAXPlayerInGame).setPreviousPlayer(clientsInMatchMaking.get(i));
    	    		System.out.println("The player " + clientsInMatchMaking.get(i).getClientID() +" has for next player " + clientsInMatchMaking.get((i+1)%nbMAXPlayerInGame).getClientID());
    	    		System.out.println("The player " + clientsInMatchMaking.get((i+1)%nbMAXPlayerInGame).getClientID() +" has for previous player " + clientsInMatchMaking.get(i).getClientID());

    	    		if (i == 0) {
    		    		clientsInMatchMaking.get(i).setCurrentPlayer(true);
    		    	}
    	    	}
    	    	for (int i=0;i<nbMAXPlayerInGame;i++) {
					clientsInMatchMaking.get(i).setHand(crds.get(i));
				}
    	    	cpt = 0;   		
    	    	System.out.println("Lancement de la partie");
        	}
         }
    	 catch (Exception e) {
             e.printStackTrace();
         }
    }     
    
    // Shuffle then distribute the cards according to the number of players
    private static ArrayList<ArrayList<Card>> melange(int cardNumber, int nbPlayer){
    	ArrayList<ArrayList<Card>> res = new ArrayList<ArrayList<Card>>(nbPlayer);
    	ArrayList<Integer> tmpcrd = new ArrayList<Integer>(cardNumber*nbPlayer);
    	for(int i=0;i<nbPlayer;i++) {
    		for(int j=0;j<cardNumber;j++) {
    			tmpcrd.add(j);
    		}
    	}
    	
    	// Shuffle all Cards
    	for (int i=0;i<1000;i++) {
    		int nombreAleatoire = (int)(Math.random() * ((cardNumber*nbPlayer)));
    		int nombreAleatoire2 = (int)(Math.random() * ((cardNumber*nbPlayer)));
    		
    		int tmp = tmpcrd.get(nombreAleatoire);
    		tmpcrd.set(nombreAleatoire, tmpcrd.get(nombreAleatoire2));
    		tmpcrd.set(nombreAleatoire2,tmp);

    	}
    	// Distribute the cards according to the number of players
    	for(int i=0;i<nbPlayer;i++) {
    		ArrayList<Card> tabCardForOnePlayer = new ArrayList<Card>();
    		res.add(tabCardForOnePlayer);
    		for(int j=i*cardNumber;j<cardNumber*(i+1);j++) {
    			res.get(i).add(new Card (tmpcrd.get(j),"src/main/resources/carte"+(tmpcrd.get(j)+1)+".png"));
    		}
    	}
    	return res;
    }

    public void takeTheTotem(ClientInterface client) throws RemoteException {
    	
    	ArrayList<ClientInterface> otherPlayers = new ArrayList<ClientInterface>();
    	otherPlayers.add(client.getPreviousPlayerInterface());
    	otherPlayers.add(client.getNextPlayerInterface());
    	otherPlayers.add(client.getThirdClientInterface());
    	
    	boolean clientGagnant = false;

    	boolean estValide = false;
    	ClientInterface gagnant = null;
    	ClientInterface perdant = null;
    	Card carte = null;

    	// definition of winner and loser
		for(ClientInterface c : otherPlayers) {
		 	int id1 = client.getBottomCard().getId();
		 	int id2 = c.getBottomCard().getId();
		 	System.out.println(id1 + " " + id2);
		 	if(id1 == id2) {
		 		clientGagnant = true;
                gagnant = client;
                perdant = c;
		 	}
		}
		if (!clientGagnant) {
         	perdant = client;
		}
            
         // exchange cards
		if(clientGagnant) {
			if(gagnant.getPlayerDeck().size() == 0) {
				for(int i =0; i < 50; i++) {
					System.out.println("LE "+gagnant.getName()+" A GAGNE !!!!!");
					client.gameOver();
					client.getPreviousPlayerInterface().gameOver();
					client.getNextPlayerInterface().gameOver();
					client.getThirdClientInterface().gameOver();
				}


			}
			else {
				while(gagnant.getPlayerStack().size() > 0) {
					carte = gagnant.getPlayerStack().get(gagnant.getPlayerStack().size()-1);
					gagnant.removeCardFromStack();
					perdant.addCardFromStack(carte);
					gagnant.updateListeners();
				}
			}

		} else {
         	for(ClientInterface c : otherPlayers) {
         		if(c != client) {
         			if(c.getPlayerDeck().size() == 0) {
						for(int i =0; i < 50; i++) {
							System.out.println("LE "+c.getName()+" A GAGNE !!!!!");
							client.gameOver();
							client.getPreviousPlayerInterface().gameOver();
							client.getNextPlayerInterface().gameOver();
							client.getThirdClientInterface().gameOver();
						}
					}
					else {
						while(c.getPlayerStack().size() > 0) {
							carte = c.getPlayerStack().get(c.getPlayerStack().size()-1);
							c.removeCardFromStack();
							perdant.addCardFromStack(carte);
							perdant.updateListeners();
						}
					}

            	}
         	}
		}
            
		// we check if there is a winner or not
		otherPlayers.add(client);
		for(ClientInterface c : otherPlayers) {
			if (c.getMyNbcard() <= 0)
	       		// send a message to other players
	            c.setIHaveWin(true);
		}
    }

    public static void main(String[] args) {
    	// Initialization of the RMI interface of the server and provision on a port
        try {
            String name = "//127.0.0.1:8090/Server";
            LocateRegistry.createRegistry(8090);
            ServerInterface server = new Server();
            ServerInterface stub =
                    (ServerInterface) UnicastRemoteObject.exportObject(server, 0);
            Naming.rebind(name,stub);
            System.out.println("Bravo le serveur a été démarré avec succès");
            
            melange(17,3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
