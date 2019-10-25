package client;

import javax.swing.*;
import java.awt.*;

public class JungleWindow extends JFrame {

    private JPanel junglePanel;
    private JButton totem, pile;

    public JungleWindow() {
        super("Jungle Speed");
        initWindow();
    }

    private void initWindow() {
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        junglePanel = new JPanel();
        setContentPane(junglePanel);
        totem = new JButton("TOTEM"); pile =  new JButton("Pile");
        junglePanel.add(totem); junglePanel.add(pile);
        setVisible(true);
    }

}
