package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CanvasPanel canvas = new CanvasPanel();
    private final ModeButtonPanel buttonPanel;
    private final AppMenuBar menuBar;

    public MainFrame() {
        super("Workflow Editor");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        buttonPanel = new ModeButtonPanel(new String[]{"SELECT", "ASSOCIATION", "GENERALIZATION", "COMPOSITION", "RECT", "OVAL"});
        menuBar = new AppMenuBar();

        add(buttonPanel, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
        setJMenuBar(menuBar);
    }

    public CanvasPanel getCanvas() {
        return canvas;
    }


    public ModeButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public AppMenuBar getAppMenuBar() {
        return menuBar;
    }
}
