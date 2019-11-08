package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JungleWindow extends JFrame implements JungleListener, ActionListener {

    private JPanel mainPanel, cardsPanel, totemPanel;
    private JButton totem, pile, play, defausse, pass;
    private JungleController controller;
    private JLabel j1, j2, j3;

    public JungleWindow(JungleController controller) {
        super("Jungle Speed");
        this.controller = controller;
        this.initWindow();
    }

    private void initWindow() {
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new GridLayout(1,3));
        totemPanel = new JPanel();
        totemPanel.setLayout(new BorderLayout());
        this.setContentPane(mainPanel);
        mainPanel.add(cardsPanel, BorderLayout.SOUTH);
        mainPanel.add(totemPanel, BorderLayout.CENTER);

        j1 = new JLabel("j1"); j2 = new JLabel("j2"); j3 = new JLabel("j3");
        totemPanel.add(j1, BorderLayout.WEST);
        totemPanel.add(j2, BorderLayout.NORTH);
        totemPanel.add(j3, BorderLayout.EAST);

        totem = new JButton("TOTEM");
        play = new JButton("Jouer");
        pile =  new JButton(); defausse = new JButton("Défausse");
        pass = new JButton("Passer tour");

        play.addActionListener(this);
        totem.addActionListener(this);
        pile.addActionListener(this);
        pass.addActionListener(this);

        totemPanel.add(totem, BorderLayout.CENTER);
        mainPanel.add(play, BorderLayout.WEST);
        cardsPanel.add(pass); cardsPanel.add(pile); cardsPanel.add(defausse);

        this.setVisible(true);
    }

    private void setCard(Card card) {
        String path = "src/main/resources/carteverso.png";
        if (card != null) {
            card.getPicPath();
        }
        this.pile.setIcon(new ImageIcon(path));
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == play) {
            controller.wantPlay();
        }
        else if (event.getSource() == pile) {
            controller.turnCard();
        }
        else if (event.getSource() == pass) {
            controller.passMyTurn();
        }
        else if (event.getSource() == totem) {
            controller.takeTotem();
        }
    }

    public void update() {
        System.out.println("*** Coucou mdr ");
        setCard(controller.getBottomCard());
    }

}
