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
    private JButton totem, pile, play;
    private JungleController controller;
    private JLabel player, j2, j3, j4, playerCard, j2Card, j3Card, j4Card, j2Restantes, j3Restantes, j4Restantes;
    private JTextField field;
    private UUID playerID;

    public JungleWindow(JungleController controller) throws RemoteException {
        this.frame = new JFrame("Jungle Speed");
        this.controller = controller;
        this.playerID = controller.getID();
        this.initWindow();
    }

    private void initWindow() {
        //this.setExtendedState(MAXIMIZED_BOTH);
        frame.setSize(700, 420);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        frame.setContentPane(mainPanel);

        JPanel playPanel = new JPanel();
        playPanel.setLayout(new GridLayout(3,1));
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        mainPanel.add(playPanel, BorderLayout.WEST);
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        JPanel playerPanel = new JPanel(); playerPanel.setLayout(new GridLayout(1,3));
        JPanel p2Panel = new JPanel(); p2Panel.setLayout(new GridLayout(3,1));
        JPanel p3Panel = new JPanel(); p3Panel.setLayout(new GridLayout(1,3));
        JPanel p4Panel = new JPanel(); p4Panel.setLayout(new GridLayout(3,1));
        gamePanel.add(playerPanel, BorderLayout.SOUTH);
        gamePanel.add(p2Panel, BorderLayout.WEST);
        gamePanel.add(p3Panel, BorderLayout.NORTH);
        gamePanel.add(p4Panel, BorderLayout.EAST);

        player = new JLabel(controller.getName());
        playerPanel.add(player);
        j2 = new JLabel("j2"); j3 = new JLabel("j3"); j4 = new JLabel("j4");
        p2Panel.add(j2); p3Panel.add(j3); p4Panel.add(j4);
        j2Restantes = new JLabel(); j3Restantes = new JLabel(); j4Restantes = new JLabel();
        p2Panel.add(j2Restantes); p3Panel.add(j3Restantes); p4Panel.add(j4Restantes);
        j2Card = new JLabel(); j3Card = new JLabel(); j4Card = new JLabel();
        p2Panel.add(j2Card); p3Panel.add(j3Card); p4Panel.add(j4Card);
        totem = new JButton("TOTEM");
        gamePanel.add(totem, BorderLayout.CENTER);
        play = new JButton("Jouer");
        playPanel.add(play);
        field = new JTextField();
        playPanel.add(field);
        pile =  new JButton(); playerCard = new JLabel("Défausse");
        playerPanel.add(pile); playerPanel.add(playerCard);

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
        this.playerCard.setIcon(new ImageIcon(path));
        pile.setText(controller.getNbCards(playerID)+" cartes restantes");
        playerCard.setText(controller.getNbDiscard()+" cartes");
    }

    private void setCard(JLabel label, Card card) {
        String path = "src/main/resources/nocard.png";
        if (card != null) {
            path = card.getPicPath();
        }
        label.setIcon(new ImageIcon(path));
    }

    private void setCurrentPlayer(JLabel label, boolean current) {
        if (current)
            label.setForeground(Color.BLACK);
        else
            label.setForeground(Color.GRAY);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == play) {
            controller.wantPlay(field.getText());
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
        this.playerCard.setIcon(new ImageIcon("src/main/resources/nocard.png"));
        UUID[] ids = controller.getPlayersIDs();
        this.player.setText(controller.getName());
        this.j2.setText(controller.getName(ids[0]));
        this.j3.setText(controller.getName(ids[1]));
        this.j4.setText(controller.getName(ids[2]));
        update();
    }

    public void update() throws RemoteException {
        System.out.println("  * update()");
        UUID[] ids = controller.getPlayersIDs();
        setCard(controller.getBottomCard());
        setCard(j2Card, controller.getCard(ids[0]));
        setCard(j3Card, controller.getCard(ids[1]));
        setCard(j4Card, controller.getCard(ids[2]));
        setCurrentPlayer(player, controller.getCurrentPlayer(playerID));
        setCurrentPlayer(j2, controller.getCurrentPlayer(ids[0]));
        setCurrentPlayer(j3, controller.getCurrentPlayer(ids[1]));
        setCurrentPlayer(j4, controller.getCurrentPlayer(ids[2]));
        j2Restantes.setText(controller.getNbCards(ids[0])+"Cartes restantes");
        j3Restantes.setText(controller.getNbCards(ids[1])+"Cartes restantes");
        j4Restantes.setText(controller.getNbCards(ids[2])+"Cartes restantes");
    }

}
