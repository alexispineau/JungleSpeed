package client;

public class JungleController {

    private Client model;
    private JungleWindow view;
    private int port;

    public JungleController(int port) {
        try {
            model = new Client(this);
            view = new JungleWindow(this);
            this.port = port;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void wantPlay() {
        model.iWantPlay(port);
    }

    public Card getBottomCard() {
        return model.getBottomCard();
    }

    public void turnCard() {
        model.turnCard();
    }

    public void passMyTurn() {
        try {
            model.passMyturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void takeTotem() {
        model.setIHaveWin(true);
    }

    public void update() {
        view.update();
    }

    public static void main(String[] args) {
        try {
            new JungleController(8100);
            new JungleController(8101);
            new JungleController(8102);
            new JungleController(8103);
        } catch (Exception e) {
            System.out.println("Erreur, n'oubliez pas de spécifier un port en lançant le jeu");
            e.printStackTrace();
        }
    }

}
