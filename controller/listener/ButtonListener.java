package controller.listener;

import view.CanvasPanel;
import model.Shape.Shape;
import utils.Mode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ButtonListener {
    private final CanvasPanel canvas;
    private final List<JButton> buttons;
    private JButton selectedButton = null;

    public ButtonListener(CanvasPanel canvas, List<JButton> buttons) {
        this.canvas = canvas;
        this.buttons = buttons;
    }

    public void bind() {
        for (JButton btn : buttons) {
            for (ActionListener al : btn.getActionListeners()) {
                btn.removeActionListener(al);
            }
            btn.addActionListener(getListener(btn));
        }
    }

    private ActionListener getListener(JButton btn) {
        return e -> SwingUtilities.invokeLater(() -> {
            if (selectedButton != null) {
                selectedButton.setBackground(null);
                selectedButton.setForeground(null);
            }

            btn.setBackground(Color.BLACK);
            btn.setForeground(Color.WHITE);
            selectedButton = btn;

            canvas.setMode(Mode.valueOf(btn.getText()));
            for (Shape s : canvas.getSelectedShapes()) {
                s.setSelected(false);
            }
            canvas.repaint();
        });
    }

}
