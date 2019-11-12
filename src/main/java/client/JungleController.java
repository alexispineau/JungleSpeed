package client;

import jdk.nashorn.internal.runtime.ECMAException;

import java.util.ArrayList;

public class JungleController {

    private Client model;
    private JungleWindow view;
    private int port;

    public JungleController(int port, int n) {
        try {
            model = new Client("Joueur "+n);
            view = new JungleWindow(this);
            model.addListener(view);
            this.port = port;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void wantPlay() {
        model.iWantPlay(port);
    }

    public void addListener(JungleListener listener) {
        try {
            model.getNextPlayerInterface().addListener(listener);
            model.getThirdClientInterface().addListener(listener);
            model.getPreviousPlayerInterface().addListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Card getBottomCard() {
        return model.getBottomCard();
    }

    public Card[] getOthersCards() {
        Card[] ret = new Card[3];
        try {
            ret[0] = model.getNextPlayerInterface().getBottomCard();
            ret[1] = model.getThirdClientInterface().getBottomCard();
            ret[2] = model.getPreviousPlayerInterface().getBottomCard();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int getNbCards() {
        return model.getMyNbcard();
    }

    public void turnCard() {
        model.turnCard();
    }

    public void takeTotem() {
        model.takeTotem();
    }

    public String getName() {
        return model.getName();
    }

    public String[] getOthersName() {
        String[] ret = new String[3];
        try {
            ret[0] = model.getNextPlayerInterface().getName();
            ret[1] = model.getThirdClientInterface().getName();
            ret[2] = model.getPreviousPlayerInterface().getName();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void main(String[] args) {
        try {
            System.out.println("Création des clients");
            new JungleController(8100, 1);
            new JungleController(8101, 2);
            new JungleController(8102, 3);
            new JungleController(8103, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
