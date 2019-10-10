package client;

import java.rmi.Remote;
import java.util.Stack;

public interface ClientInterface extends Remote {

    public void setNextPlayer(Player player);
    public void setPreviousPlayer(Player player);
    public void setHand(Stack<Card> hand);
    public Card getCard();

}
