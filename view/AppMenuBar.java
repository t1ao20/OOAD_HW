//package view;
//
//import javax.swing.*;
//import model.Composite;
//import model.Shape;
//import java.util.List;
//
//public class AppMenuBar extends JMenuBar {
//
//    public AppMenuBar(JFrame parent, CanvasPanel canvas) {
//        JMenu fileMenu = new JMenu("File");
//        JMenu editMenu = new JMenu("Edit");
//
//        // Label
//        JMenuItem labelItem = new JMenuItem("Label");
//        labelItem.addActionListener(e -> {
//            List<Shape> selected = canvas.getSelectedShapes();
//            if (selected.size() == 1 && !(selected.get(0) instanceof Composite)) {
//                new LabelStyleDialog(parent, selected.get(0)).setVisible(true);
//                canvas.repaint();
//            } else if (selected.isEmpty() || selected.size() != 1) {
//                JOptionPane.showMessageDialog(parent, "請選取一個圖形。", "提示", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(parent, "不適用於Composite物件。", "提示", JOptionPane.INFORMATION_MESSAGE);
//            }
//        });
//
//        // Group / Ungroup
//        JMenuItem groupItem = new JMenuItem("Group");
//        JMenuItem ungroupItem = new JMenuItem("Ungroup");
//        groupItem.addActionListener(e -> canvas.groupSelectedShapes());
//        ungroupItem.addActionListener(e -> canvas.ungroupSelectedShape());
//
//        editMenu.add(labelItem);
//        editMenu.add(groupItem);
//        editMenu.add(ungroupItem);
//
//        add(fileMenu);
//        add(editMenu);
//    }
//}

package view;

import javax.swing.*;

public class AppMenuBar extends JMenuBar {
    private final JMenuItem labelItem = new JMenuItem("Label");
    private final JMenuItem groupItem = new JMenuItem("Group");
    private final JMenuItem ungroupItem = new JMenuItem("Ungroup");

    public AppMenuBar() {
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        editMenu.add(labelItem);
        editMenu.add(groupItem);
        editMenu.add(ungroupItem);

        add(fileMenu);
        add(editMenu);
    }

    public JMenuItem getLabelItem() {
        return labelItem;
    }

    public JMenuItem getGroupItem() {
        return groupItem;
    }

    public JMenuItem getUngroupItem() {
        return ungroupItem;
    }
}
