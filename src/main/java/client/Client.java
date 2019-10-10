package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Stack;

public class Client extends UnicastRemoteObject implements ClientInterface {

    private Player nextPlayer;
    private Player previousPlayer;
    private Stack<Card> hand;
    private Card card;

    public Client() throws RemoteException {

    }

    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public void setPreviousPlayer(Player player) {
        this.previousPlayer = previousPlayer;
    }

    public void setHand(Stack<Card> hand) {
        this.hand = (Stack<Card>)hand.clone();
    }

    public Card getCard() {
        return this.card;
    }

    public void turnCard() {
        this.card = this.hand.pop();
        //TODO Notify other players
    }

}
