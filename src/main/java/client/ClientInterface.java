package client;

import java.rmi.Remote;
import java.util.ArrayList;

public interface ClientInterface extends Remote {

    public void setNextPlayer(ClientInterface player);
    public Card getBottomCardOfNextPlayer();
    public int getNbCardOfNextPlayer();
    
    public void setPreviousPlayer(ClientInterface player);
    
    public Card getBottomCard();
    public int getMyNbcard();
    public void setHand(ArrayList<Card> crds);

}
