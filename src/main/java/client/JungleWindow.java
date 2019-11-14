package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public class JungleWindow extends UnicastRemoteObject implements JungleListener, ActionListener {

    private JFrame frame;
    private JButton totem, pile, play, discard;
    private JungleController controller;
    private JLabel player, j2, j3, j4, j2Card, j3Card, j4Card;

    public JungleWindow(JungleController controller) throws RemoteException {
        frame = new JFrame("Jungle Speed");
        this.controller = controller;
        this.initWindow();
    }

    private void initWindow() {
        //this.setExtendedState(MAXIMIZED_BOTH);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        frame.setContentPane(mainPanel);

        JPanel playPanel = new JPanel();
        playPanel.setLayout(new GridLayout(2,1));
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        mainPanel.add(playPanel, BorderLayout.WEST);
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        JPanel playerPanel = new JPanel(); playerPanel.setLayout(new GridLayout(1,3));
        JPanel p2Panel = new JPanel(); p2Panel.setLayout(new GridLayout(2,1));
        JPanel p3Panel = new JPanel(); p3Panel.setLayout(new GridLayout(1,2));
        JPanel p4Panel = new JPanel(); p4Panel.setLayout(new GridLayout(2,1));
        gamePanel.add(playerPanel, BorderLayout.SOUTH);
        gamePanel.add(p2Panel, BorderLayout.WEST);
        gamePanel.add(p3Panel, BorderLayout.NORTH);
        gamePanel.add(p4Panel, BorderLayout.EAST);

        player = new JLabel(controller.getName());
        playerPanel.add(player);
        j2 = new JLabel("j2"); j3 = new JLabel("j3"); j4 = new JLabel("j4");
        p2Panel.add(j2); p3Panel.add(j3); p4Panel.add(j4);
        j2Card = new JLabel(); j3Card = new JLabel(); j4Card = new JLabel();
        p2Panel.add(j2Card); p3Panel.add(j3Card); p4Panel.add(j4Card);
        totem = new JButton("TOTEM");
        gamePanel.add(totem, BorderLayout.CENTER);
        play = new JButton("Jouer");
        playPanel.add(play);
        pile =  new JButton(); discard = new JButton("Défausse");
        playerPanel.add(pile); playerPanel.add(discard);

        play.addActionListener(this);
        totem.addActionListener(this);
        pile.addActionListener(this);

        frame.setVisible(true);
    }

    private void setCard(Card card) {
        String path = "src/main/resources/nocard.png";
        if (card != null) {
            path = card.getPicPath();
        }
        this.discard.setIcon(new ImageIcon(path));
        int n = controller.getNbCards();
        this.discard.setText(n+" cartes");
    }

    private void setOtherCard(JLabel label, Card card) {
        String path = "src/main/resources/nocard.png";
        if (card != null) {
            path = card.getPicPath();
        }
        label.setIcon(new ImageIcon(path));
    }

    private void setCurrentPlayer() {

    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == play) {
            controller.wantPlay();
        }
        else if (event.getSource() == pile) {
            controller.turnCard();
        }
        else if (event.getSource() == totem) {
            controller.takeTotem();
        }
    }

    public void startGame() throws RemoteException {
        System.out.println("+++ startGame() : cc");
        this.controller.addListener(this);
        this.pile.setIcon(new ImageIcon("src/main/resources/carteverso.png"));
        this.discard.setIcon(new ImageIcon("src/main/resources/nocard.png"));
        UUID[] ids = controller.getPlayersIDs();
        this.j2.setText(controller.getName(ids[0]));
        this.j3.setText(controller.getName(ids[1]));
        this.j4.setText(controller.getName(ids[2]));
    }

    public void update() throws RemoteException {
        System.out.println("  * update()");
        pile.setText(controller.getNbCards()+" cartes restantes");
        setCard(controller.getBottomCard());
        UUID[] ids = controller.getPlayersIDs();
        setOtherCard(j2Card, controller.getCard(ids[0]));
        setOtherCard(j3Card, controller.getCard(ids[1]));
        setOtherCard(j4Card, controller.getCard(ids[2]));
    }

}
