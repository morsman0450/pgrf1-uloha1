package view;

import javax.swing.*;

public class Window extends JFrame {

    private final Panel panel;

    public Window(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Window");

        panel = new Panel();
        add(panel);
        setVisible(true);
        pack();

        panel.setFocusable(true);
        panel.grabFocus();
    }

    public Panel getPanel() {
        return panel;
    }
}
