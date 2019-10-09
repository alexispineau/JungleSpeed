package client;

import java.rmi.Remote;
import java.util.ArrayList;

public interface ClientInterface extends Remote {

    public void setNextPlayer(Player player);
    public void setPreviousPlayer(Player player);
    public void dealCards(ArrayList<Card> cards);
    public Card showCard();

}
