package view;

import model.Shape.Shape;
import model.Shape.ShapeType;

import javax.swing.*;
import java.awt.*;

public class LabelStyleDialog extends JDialog {
    public LabelStyleDialog(JFrame parent, Shape shape) {
        super(parent, "Edit Label Style", true);
        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // ===== 表單元件 =====
        JTextField labelField = new JTextField(shape.getLabel().getText());
//        形狀
        JComboBox<ShapeType> shapeCombo = new JComboBox<>(ShapeType.values());
        shapeCombo.setSelectedItem(shape.getLabel().getShapeType());

//      字型
        JComboBox<String> fontCombo = new JComboBox<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        fontCombo.setSelectedItem(shape.getLabel().getFont().getFamily());

//      字體大小
        JComboBox<Integer> sizeCombo = new JComboBox<>(new Integer[]{6, 8, 10, 12, 14, 16, 18, 20, 24});
        sizeCombo.setSelectedItem(shape.getLabel().getFont().getSize());

//      顏色 - BG
        JButton bgColorBtn = new JButton(); // 按鈕作為 preview
        Color defaultColor = shape.getLabel().getBackgroundColor();
        bgColorBtn.setBackground(defaultColor);
        bgColorBtn.setPreferredSize(new Dimension(40, 20)); // 適合當 preview 小色塊

        bgColorBtn.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose BG Color", defaultColor);
            if (newColor != null) {
                bgColorBtn.setBackground(newColor); // 更新 preview
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
            shape.getLabel().setText(labelField.getText());
            shape.getLabel().setFont(new Font((String) fontCombo.getSelectedItem(), Font.PLAIN, (Integer) sizeCombo.getSelectedItem()));
            shape.getLabel().setShapeType((ShapeType) shapeCombo.getSelectedItem());
            shape.getLabel().setBackgroundColor(bgColorBtn.getBackground());
            dispose();

        });
        JPanel btnPanel = new JPanel();
        btnPanel.add(cancelBtn);
        btnPanel.add(okBtn);
        add(form, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }
}
