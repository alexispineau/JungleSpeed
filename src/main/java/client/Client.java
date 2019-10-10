package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Stack;

public class Client extends UnicastRemoteObject implements ClientInterface {

    private Player nextPlayer;
    private Player previousPlayer;
    private Stack<Card> playerStack;
    private Stack<Card> discardStack;
    private Card card;

    public Client() throws RemoteException { }

    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public void setPreviousPlayer(Player previousPlayer) {
        this.previousPlayer = previousPlayer;
    }

    public void setHand(Stack<Card> hand) {
        this.playerStack = (Stack<Card>)hand.clone();
    }

    public Card getCard() {
        return this.card;
    }

    public void turnCard() {
        this.discardStack.push(this.card);
        this.card = this.playerStack.pop();
        //TODO Notify other players
    }

}
