import model.Composite;
import view.CanvasPanel;
import utils.Mode;
import model.Shape;
import view.LabelStyleDialog;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Workflow Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        CanvasPanel canvas = new CanvasPanel();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1));

        String[] buttons = {"SELECT", "ASSOCIATION", "GENERALIZATION", "COMPOSITION", "RECT", "OVAL"};
        List<JButton> buttonList = new ArrayList<>();
        for (String b : buttons) {
            JButton btn = new JButton(b);
            buttonList.add(btn);
            btn.addActionListener(e -> {
                // reset all button colors
                for (JButton otherBtn : buttonList) {
                    otherBtn.setBackground(null);
                    otherBtn.setForeground(null);
                }
                // set current button to black background
                btn.setBackground(Color.BLACK);
                btn.setForeground(Color.WHITE);
                canvas.setMode(Mode.valueOf(b));

                // unselect all shapes
                for (Shape s : canvas.getSelectedShapes()) {
                    s.setSelected(false);
                }
                canvas.repaint();
            });
            buttonPanel.add(btn);
        }

        frame.getContentPane().add(BorderLayout.WEST, buttonPanel);
        frame.getContentPane().add(BorderLayout.CENTER, canvas);
        frame.setVisible(true);

//        Label

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenuItem labelEdit = new JMenuItem("Label");

        labelEdit.addActionListener(e -> {
            List<Shape> selected = canvas.getSelectedShapes();
            if (selected != null && selected.size()==1 && !(selected.get(0) instanceof Composite)) {
                new LabelStyleDialog(frame, selected.get(0)).setVisible(true);
                canvas.repaint();
            } else if (selected == null || selected.size()!=1){
                JOptionPane.showMessageDialog(frame, "請選取一個圖形。", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (selected.get(0) instanceof Composite){
                JOptionPane.showMessageDialog(frame, "不適用於Composite物件。", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        editMenu.add(labelEdit);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        frame.setJMenuBar(menuBar);

//        Group / UnGroup
        JMenuItem groupItem = new JMenuItem("Group");
        JMenuItem ungroupItem = new JMenuItem("Ungroup");
        groupItem.addActionListener(e -> canvas.groupSelectedShapes());
        ungroupItem.addActionListener(e -> canvas.ungroupSelectedShape());

        editMenu.add(groupItem);
        editMenu.add(ungroupItem);


    }
}
