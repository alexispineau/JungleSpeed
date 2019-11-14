package client;

import jdk.nashorn.internal.runtime.ECMAException;

import java.util.ArrayList;
import java.util.UUID;

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
            System.out.println(" ++ addListener("+model.getName()+") : next");
            model.getNextPlayerInterface().addListener(listener);
            System.out.println(" ++ addListener("+model.getName()+") : third");
            model.getThirdClientInterface().addListener(listener);
            System.out.println(" ++ addListener("+model.getName()+") : previous");
            model.getPreviousPlayerInterface().addListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Card getBottomCard() {
        return model.getBottomCard();
    }

    public UUID[] getPlayersIDs() {
        UUID[] ret = new UUID[3];
        try {
            ret[0] = model.getNextPlayerInterface().getClientID();
            ret[1] = model.getThirdClientInterface().getClientID();
            ret[2] = model.getPreviousPlayerInterface().getClientID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private ClientInterface getClient(UUID uuid) {
        ClientInterface ret = null;
        try {
            if (model.getNextPlayerInterface().getClientID().equals(uuid))
                ret = model.getNextPlayerInterface();
            else if (model.getThirdClientInterface().getClientID().equals(uuid))
                ret = model.getThirdClientInterface();
            else if (model.getPreviousPlayerInterface().getClientID().equals(uuid))
                ret = model.getPreviousPlayerInterface();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Card getCard(UUID uuid) {
        Card ret = null;
        try {
            ret = getClient(uuid).getBottomCard();
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

    public String getName(UUID uuid) {
        String ret = null;
        try {
            ret = getClient(uuid).getName();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public boolean getCurrentPlayer() {
        return model.getCurrentPlayer();
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
