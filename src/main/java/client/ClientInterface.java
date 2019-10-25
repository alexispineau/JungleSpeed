package client;

import java.rmi.Remote;
import java.util.ArrayList;

public interface ClientInterface extends Remote {

    public void setNextPlayer(ClientInterface player);
    // Moi je pense qu'il faut supprimer cette méthode^^
    public void setPreviousPlayer(ClientInterface player);
    public Card getBottomCardOfNextPlayer();
    public boolean getCurrentPlayer();
    public void setCurrentPlayer(boolean cur);
    public int getNbCardOfNextPlayer();
    public Card getBottomCard();
    public int getMyNbcard();
    public void setHand(ArrayList<Card> crds);
	public ArrayList<Card> getPlayerStack();

}
