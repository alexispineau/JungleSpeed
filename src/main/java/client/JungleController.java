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

    public void wantPlay(String name) {
        model.iWantPlay(port, name);
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
            else ret = model;
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

    public int getNbCards(UUID uuid) {
        int ret = -1;
        try {
            ret = getClient(uuid).getMyNbcard();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int getNbDiscard() {
        return model.getNbDiscard();
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

    public boolean getCurrentPlayer(UUID uuid) {
        boolean ret = false;
        try {
            ret = getClient(uuid).getCurrentPlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    public UUID getID() {
        UUID ret = null;
        try {
            ret = model.getClientID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void main(String[] args) {
        try {
            new JungleController(8100, 1);
            new JungleController(8101, 2);
            new JungleController(8102, 3);
            new JungleController(8103, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
