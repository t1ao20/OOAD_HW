package controller;

import controller.listener.ButtonListener;
import controller.listener.MenuBarListener;
import view.*;

public class AppController {
    public static void init(MainFrame frame) {
        CanvasPanel canvas = frame.getCanvas();
        ModeButtonPanel buttonPanel = frame.getButtonPanel();
        AppMenuBar menuBar = frame.getAppMenuBar();

        new ButtonListener(canvas, buttonPanel.getButtons()).bind();
        new MenuBarListener(frame, canvas, menuBar).bind();
    }
}
