package client;

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
            ClientInterface c = model.getNextPlayerInterface();
            c.addListener(listener);
            c = c.getNextPlayerInterface();
            c.addListener(listener);
            c = c.getNextPlayerInterface();
            c.addListener(listener);
        } catch (Exception e) {

        }
    }

    public Card getBottomCard() {
        return model.getBottomCard();
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
