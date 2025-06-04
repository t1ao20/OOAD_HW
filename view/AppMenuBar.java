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
