package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Stack;

public class Client extends UnicastRemoteObject implements ClientInterface {

    private ClientInterface nextPlayer;
    private ClientInterface previousPlayer;
    private Stack<Card> playerStack;
    private Stack<Card> discardStack;
    private Card card;

    public Client() throws RemoteException { }

    public void setNextPlayer(ClientInterface nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public void setPreviousPlayer(ClientInterface previousPlayer) {
        this.previousPlayer = previousPlayer;
    }

    public Card getBottomCardOfNextPlayer() {
        //TODO
        return null;
    }

    public int getNbCardOfNextPlayer() {
        //TODO
        return -1;
    }

    public Card getBottomCard() {
        return this.card;
    }

    public int getMyNbcard() {
        //TODO
        return -1;
    }

    public void setHand(Stack<Card> hand) {
        this.playerStack = (Stack<Card>)hand.clone();
    }

    public void turnCard() {
        this.discardStack.push(this.card);
        this.card = this.playerStack.pop();
        //TODO Notify other players
    }

}
