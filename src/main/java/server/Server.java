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

    private ArrayList<ClientInterface> clients;
    int cpt = 0;

    public Server() {
        super();
    }

    public void joinGame(ClientInterface client) throws RemoteException {
        
    	
    	this.clients.add(client);
    	cpt++;
    	
    	if (cpt < 4) {
    		try{
    			wait();
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    	else {
    		clients.get(0).setNextPlayer(clients.get(1));
    		clients.get(0).setNextPlayer(clients.get(1));
    		clients.get(1).setNextPlayer(clients.get(2));
    		clients.get(2).setNextPlayer(clients.get(3));
    		clients.get(3).setNextPlayer(clients.get(0));
    		
    		clients.get(0).setPreviousPlayer(clients.get(1));
    		clients.get(1).setPreviousPlayer(clients.get(2));
    		clients.get(2).setPreviousPlayer(clients.get(3));
    		clients.get(3).setPreviousPlayer(clients.get(0));
    		
    		/*clients.get(0).setHand(crds);
    		clients.get(0).setHand(crds);
    		clients.get(0).setHand(crds);
    		clients.get(0).setHand(crds);*/
    		
    		cpt = 0;
    		
    		notifyAll();
    	}
    
        
        
    }
    
    private static ArrayList<ArrayList<Card>> melange(int cardNumber, int nbPlayer){
    	ArrayList<ArrayList<Card>> res = new ArrayList<ArrayList<Card>>(nbPlayer);
    	ArrayList<Integer> tmpcrd = new ArrayList<Integer>(cardNumber*nbPlayer);
    	for(int i=0;i<nbPlayer;i++) {
    		for(int j=0;j<cardNumber;j++) {
    			tmpcrd.add(j);
    			System.out.print("-"+tmpcrd.get((i*cardNumber)+j));
    		}
    	}
    	System.out.println();
    	
    	for (int i=0;i<1000;i++) {
    		int nombreAleatoire = (int)(Math.random() * ((cardNumber*nbPlayer)));
    		int nombreAleatoire2 = (int)(Math.random() * ((cardNumber*nbPlayer)));
    		
    		int tmp = tmpcrd.get(nombreAleatoire);
    		tmpcrd.set(nombreAleatoire, tmpcrd.get(nombreAleatoire2));
    		tmpcrd.set(nombreAleatoire2,tmp);

    	}
    	
    	for(int i=0;i<nbPlayer;i++) {
    		for(int j=0;j<cardNumber;j++) {
    			tmpcrd.add(j);
    			System.out.print("*"+tmpcrd.get((i*cardNumber)+j));
    		}
    	}
    	System.out.println();
    	
    	for(int i=0;i<nbPlayer;i++) {
    		ArrayList<Card> tabCardForOnePlayer = new ArrayList<Card>(cardNumber);
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
