import javax.swing.*;

import controller.AppController;
import view.MainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            AppController.init(frame);
            frame.setVisible(true);
        });
    }
}

