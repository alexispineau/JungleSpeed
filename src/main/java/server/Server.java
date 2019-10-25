package server;

import client.Card;
import client.ClientInterface;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import client.ClientInterface;

public class Server implements ServerInterface {
	
	public static int nbMAXPlayerInGame = 4;

    private ArrayList<ClientInterface> clients;
    int cpt = 0;

    public Server() {
        super();
    }

    public void joinGame(ClientInterface client) throws RemoteException {
        
    	this.clients.add(client);
    	cpt++;
    	
    	if (cpt < nbMAXPlayerInGame) {
    		try{
    			wait();
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    		
    	ArrayList<ArrayList<Card>> crds = new ArrayList<ArrayList<Card>>(nbMAXPlayerInGame);
    	crds = melange(4,nbMAXPlayerInGame);
    		
    	for(int i=0;i<nbMAXPlayerInGame;i++) {
    		clients.get(i).setNextPlayer(clients.get((i+1)%nbMAXPlayerInGame));
	    	clients.get((i+1)%nbMAXPlayerInGame).setPreviousPlayer(clients.get(i));
	    	clients.get(i).setHand(crds.get(i));
	    	if (i == 0) {
	    		clients.get(i).setCurrentPlayer(true);
	    	}
    	}
    	cpt = 0;   		
    	notifyAll();
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

            for(ClientInterface c : clients) {

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
                for(ClientInterface c : clients) {
                    if(c != client) {
                        carte = c.getPlayerStack().get(gagnant.getPlayerStack().size()-1);
						gagnant.getPlayerStack().remove(gagnant.getPlayerStack().size()-1);
                        client.getPlayerStack().add(carte);
                    }
                }
            }

    }

    public static void main(String[] args) {
        try {
            String name = "//127.0.0.1:8090/Server";
            LocateRegistry.createRegistry(8090);
            ServerInterface server = new Server();
            ServerInterface stub =
                    (ServerInterface) UnicastRemoteObject.exportObject(server, 0);
            System.out.println("Bravo le serveur a été démarré avec succès lol");
            melange(4,3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
