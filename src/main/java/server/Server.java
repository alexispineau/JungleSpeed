package server;

import client.Card;
import client.ClientInterface;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import client.ClientInterface;

public class Server implements ServerInterface {
	
	public static int nbMAXPlayerInGame = 4;

    private ArrayList<ClientInterface> clientsInMatchMaking; // fille d'attente pour attente de partie
    int cpt = 0;

    public Server() {
        super();
        clientsInMatchMaking = new  ArrayList<ClientInterface>();
    }

    public void joinGame(String clientPort) throws RemoteException {
    	 try {
    		 // connection avec l'interface du client qui à appelé la méthode joinGame
    		 ClientInterface client = (ClientInterface) Naming.lookup(clientPort);
             System.out.println("SERCER Connexion au clien : "+clientPort);
             // ajout de l'interface client dans une fille d'attente
             this.clientsInMatchMaking.add(client);
             cpt++;
        	// traitement pour les 3 premiers appels
        	if (cpt < nbMAXPlayerInGame) {
        		//try{
        			System.out.println("SERVER Le client : "+clientPort+" entre en MM");
        			wait();
        			System.out.println("SERVER Le client : "+clientPort+" sort en MM");
        		//}
        		//catch(Exception e) {
        		//	e.printStackTrace();
        		//}
        	}
        	// traitement pour le 4 eme appel
        	else {
    	    	ArrayList<ArrayList<Card>> crds = new ArrayList<ArrayList<Card>>(nbMAXPlayerInGame);
    	    	crds = melange(4,nbMAXPlayerInGame);
    	    		
    	    	// initialisation de joueur précédent et suivant pour chaque joueur + définition du joueru courant
    	    	for(int i=0;i<nbMAXPlayerInGame;i++) {
    	    		clientsInMatchMaking.get(i).setNextPlayer(clientsInMatchMaking.get((i+1)%nbMAXPlayerInGame));
    	    		clientsInMatchMaking.get((i+1)%nbMAXPlayerInGame).setPreviousPlayer(clientsInMatchMaking.get(i));
    	    		clientsInMatchMaking.get(i).setHand(crds.get(i));
    		    	if (i == 0) {
    		    		clientsInMatchMaking.get(i).setCurrentPlayer(true);
    		    	}
    	    	}
    	    	cpt = 0;   		
    	    	notifyAll();
    	    	System.out.println("Lancement du jeu vidéal");
        	}
         }
    	 catch (Exception e) {
             e.printStackTrace();
         }
    }     
    
    private static ArrayList<ArrayList<Card>> melange(int cardNumber, int nbPlayer){
    	ArrayList<ArrayList<Card>> res = new ArrayList<ArrayList<Card>>(nbPlayer);
    	ArrayList<Integer> tmpcrd = new ArrayList<Integer>(cardNumber*nbPlayer);
    	for(int i=0;i<nbPlayer;i++) {
    		for(int j=0;j<cardNumber;j++) {
    			tmpcrd.add(j);
    		}
    	}
    	
    	for (int i=0;i<1000;i++) {
    		int nombreAleatoire = (int)(Math.random() * ((cardNumber*nbPlayer)));
    		int nombreAleatoire2 = (int)(Math.random() * ((cardNumber*nbPlayer)));
    		
    		int tmp = tmpcrd.get(nombreAleatoire);
    		tmpcrd.set(nombreAleatoire, tmpcrd.get(nombreAleatoire2));
    		tmpcrd.set(nombreAleatoire2,tmp);

    	}
    	
    	for(int i=0;i<nbPlayer;i++) {
    		ArrayList<Card> tabCardForOnePlayer = new ArrayList<Card>();
    		res.add(tabCardForOnePlayer);
    		for(int j=i*cardNumber;j<cardNumber*(i+1);j++) {
    			res.get(i).add(new Card (tmpcrd.get(j),""));
    		}
    	}
    	
    	for(int i=0;i<nbPlayer;i++) {
    		System.out.print("tab number : "+ (i+1) +" --> ");
    		for(int j=0;j<cardNumber;j++) {
    			System.out.print(res.get(i).get(j).getId() + " ,");
    		}
    		System.out.println();
    	}
    	return res;
    }

    public void takeTheTotem(ClientInterface client) throws RemoteException {
        //TODO
		 boolean estValide =false;
            ClientInterface gagnant = null;
            ClientInterface perdant = null;
            Card carte = null;

            for(ClientInterface c : clientsInMatchMaking) {

                    if(client.getBottomCard().getId() == c.getBottomCard().getId() && client != c) {
                        gagnant = client;
                        perdant = c;
                        estValide = true;
                        break;
                    }
                    else {
                        gagnant = c;
                        perdant = client;
                    }
            }

            if(estValide) {
                while(gagnant.getPlayerStack().size() != 0) {
                    carte = gagnant.getPlayerStack().get(gagnant.getPlayerStack().size()-1);
                    gagnant.getPlayerStack().remove(gagnant.getPlayerStack().size()-1);
                    perdant.getPlayerStack().add(carte);
                }
            } else {
                for(ClientInterface c : clientsInMatchMaking) {
                    if(c != client) {
                        carte = c.getPlayerStack().get(gagnant.getPlayerStack().size()-1);
						gagnant.getPlayerStack().remove(gagnant.getPlayerStack().size()-1);
                        client.getPlayerStack().add(carte);
                    }
                }
            }

    }
    
    public String test() {
    	return "Test de qualité";
    }

    public static void main(String[] args) {
        try {
            String name = "//127.0.0.1:8090/Server";
            LocateRegistry.createRegistry(8090);
            ServerInterface server = new Server();
            ServerInterface stub =
                    (ServerInterface) UnicastRemoteObject.exportObject(server, 0);
            Naming.rebind(name,stub);
            System.out.println("Bravo le serveur a été démarré avec succès lol");
            
            melange(4,3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
