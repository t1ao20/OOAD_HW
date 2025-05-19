package view;

import model.Shape;
import javax.swing.*;
import java.awt.*;

public class LabelStyleDialog extends JDialog {
    public LabelStyleDialog(JFrame parent, Shape shape) {
        super(parent, "Edit Label Style", true);
        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // ===== 表單元件 =====
        JTextField labelField = new JTextField(shape.getLabel());
//        形狀
        String[] shapeOptions = {"Rect", "Oval"};
        JComboBox<String> shapeCombo = new JComboBox<>(shapeOptions);
        shapeCombo.setSelectedItem(shape.getShapeType());

//      字型
        JComboBox<String> fontCombo = new JComboBox<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        fontCombo.setSelectedItem(shape.labelFont.getFamily());

//      字體大小
        JComboBox<Integer> sizeCombo = new JComboBox<>(new Integer[]{6, 8, 10, 12, 14, 16, 18, 20, 24});
        sizeCombo.setSelectedItem(shape.labelFont.getSize());

////      顏色 - Font
//        JButton colorBtn = new JButton("Choose Color");
//        JLabel colorPreview = new JLabel("■");
//        colorPreview.setForeground(shape.color_bg);
//        colorBtn.addActionListener(e -> {
//            Color newColor = JColorChooser.showDialog(this, "Choose Label Color", shape.color_bg);
//            if (newColor != null) {
//                colorPreview.setForeground(newColor);
//            }
//        });

//      顏色 - BG
//        JLabel colorPreview_BG = new JLabel("■");
        JButton bgColorBtn = new JButton(); // 按鈕作為 preview
        Color defaultColor = shape.color_bg;
        bgColorBtn.setBackground(defaultColor);
        bgColorBtn.setPreferredSize(new Dimension(40, 20)); // 適合當 preview 小色塊

        bgColorBtn.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose BG Color", defaultColor);
            if (newColor != null) {
//                shape.color_bg = newColor;
                bgColorBtn.setBackground(newColor); // 更新 preview
//                colorPreview_BG.setForeground(newColor);
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> {
            dispose();
        });

        JPanel form = new JPanel(new GridLayout(4, 2));
        form.add(new JLabel("Name:"));
        form.add(labelField);
        form.add(new JLabel("Shape:"));
        form.add(shapeCombo);
        form.add(new JLabel("Color:"));
        form.add(bgColorBtn);
        form.add(new JLabel("FontSize:"));
        form.add(sizeCombo);
        JButton okBtn = new JButton("OK");


        okBtn.addActionListener(e -> {
            shape.setLabel(labelField.getText());
            shape.setLabelFont(new Font((String) fontCombo.getSelectedItem(), Font.PLAIN, (Integer) sizeCombo.getSelectedItem()));
            shape.setShapeType((String) shapeCombo.getSelectedItem());
//            shape.setBgColor(colorPreview_BG.getForeground());
            shape.setBgColor(bgColorBtn.getBackground());
            dispose();
        });
        JPanel btnPanel = new JPanel();
        btnPanel.add(cancelBtn);
        btnPanel.add(okBtn);
        add(form, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }
}
