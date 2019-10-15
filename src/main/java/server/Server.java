package server;

import client.Card;
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
    
    private Card[][] melange(int cardNumber, int nbPlayer){
    	ArrayList<Integer> tmpcrd = new ArrayList<Integer>(cardNumber*4);
    	for(int i=0;i<4;i++) {
    		for(int j=0;j<cardNumber;j++) {
    			tmpcrd.set(i*cardNumber+j, j);
    		}
    	}
    	
    	for (int i=0;i<1000;i++) {
    		 Math.random();
    	}
    	return null;
    }

    public void takeTheTotem(ClientInterface client) throws RemoteException {
        //TODO
    }

    public static void main(String[] args) {
        try {
            String name = "//127.0.0.1:8091/Server";
            LocateRegistry.createRegistry(8091);
            ServerInterface server = new Server();
            ServerInterface stub =
                    (ServerInterface) UnicastRemoteObject.exportObject(server, 0);
            System.out.println("Bravo le serveur a été démarré avec succès lol");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
