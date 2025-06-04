package controller.listener;

import view.CanvasPanel;
import model.Shape;
import utils.Mode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ButtonListener {
    private final CanvasPanel canvas;
    private final List<JButton> buttons;

    public ButtonListener(CanvasPanel canvas, List<JButton> buttons) {
        this.canvas = canvas;
        this.buttons = buttons;
    }

    public void bind() {
        for (JButton btn : buttons) {
            btn.addActionListener(getListener(btn));
        }
    }

    private ActionListener getListener(JButton btn) {
        return e -> {
            for (JButton other : buttons) {
                other.setBackground(null);
                other.setForeground(null);
            }
            btn.setBackground(Color.BLACK);
            btn.setForeground(Color.WHITE);

            canvas.setMode(Mode.valueOf(btn.getText()));
            for (Shape s : canvas.getSelectedShapes()) {
                s.setSelected(false);
            }
            canvas.repaint();
        };
    }
}
