package controller.listener;

import view.*;
import model.Shape.Shape;
import model.Shape.Composite;

import javax.swing.*;
import java.util.List;

public class MenuBarListener {
    private final JFrame parentFrame;
    private final CanvasPanel canvas;
    private final AppMenuBar menuBar;

    public MenuBarListener(JFrame parentFrame, CanvasPanel canvas, AppMenuBar menuBar) {
        this.parentFrame = parentFrame;
        this.canvas = canvas;
        this.menuBar = menuBar;
    }

    public void bind() {
        // label
        menuBar.getLabelItem().addActionListener(e -> {
            List<Shape> selected = canvas.getSelectedShapes();
            if (selected.size() == 1 && !(selected.get(0) instanceof Composite)) {
                new LabelStyleDialog(parentFrame, selected.get(0)).setVisible(true);
                canvas.repaint();
            } else if (selected.isEmpty() || selected.size() != 1) {
                JOptionPane.showMessageDialog(parentFrame, "請選取一個圖形。", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parentFrame, "不適用於Composite物件。", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        menuBar.getGroupItem().addActionListener(e -> canvas.groupSelectedShapes());
        menuBar.getUngroupItem().addActionListener(e -> canvas.ungroupSelectedShape());
    }
}
