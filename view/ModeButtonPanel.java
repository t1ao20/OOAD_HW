//package view;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.*;
//import java.util.List;
//
//import view.CanvasPanel;
//import utils.Mode;
//import model.Shape;
//
//public class ModeButtonPanel extends JPanel {
//
//    public ModeButtonPanel(CanvasPanel canvas) {
//        setLayout(new GridLayout(6, 1));
//        String[] modes = {"SELECT", "ASSOCIATION", "GENERALIZATION", "COMPOSITION", "RECT", "OVAL"};
//
//        List<JButton> buttons = new ArrayList<>();
//        for (String modeName : modes) {
//            JButton btn = new JButton(modeName);
//            buttons.add(btn);
//
//            btn.addActionListener(e -> {
//                // Reset all buttons
//                for (JButton b : buttons) {
//                    b.setBackground(null);
//                    b.setForeground(null);
//                }
//                // Set active
//                btn.setBackground(Color.BLACK);
//                btn.setForeground(Color.WHITE);
//                canvas.setMode(Mode.valueOf(modeName));
//
//                // Unselect shapes
//                for (Shape s : canvas.getSelectedShapes()) {
//                    s.setSelected(false);
//                }
//                canvas.repaint();
//            });
//
//            add(btn);
//        }
//    }
//}

package view;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ModeButtonPanel extends JPanel {
    private final List<JButton> buttons = new ArrayList<>();

    public ModeButtonPanel(String[] modeNames) {
        setLayout(new GridLayout(modeNames.length, 1));

        for (String modeName : modeNames) {
            JButton btn = new JButton(modeName);
            buttons.add(btn);
            add(btn);
        }
    }

    public List<JButton> getButtons() {
        return buttons;
    }
}
