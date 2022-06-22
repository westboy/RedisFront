package com.redisfront.ui.form;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.redisfront.util.Fn;

import javax.swing.*;
import java.awt.*;

/**
 * ConnectForm
 *
 * @author Jin
 */
public class _NoneForm {
    private JPanel contentPanel;
    private JLabel logoLabel;
    private JLabel newLabel;
    private JLabel openLabel;
    private JLabel newKeyLabel;
    private JLabel openKeyLabel;

    private static _NoneForm noneForm;

    public static _NoneForm getInstance() {
        if (Fn.isNull(noneForm)) {
            noneForm = new _NoneForm();
        }
        return noneForm;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public _NoneForm() {
        $$$setupUI$$$();
        newLabel.putClientProperty("FlatLaf.styleClass", "h3");
        newKeyLabel.putClientProperty("FlatLaf.styleClass", "h3");
        openLabel.putClientProperty("FlatLaf.styleClass", "h3");
        openKeyLabel.putClientProperty("FlatLaf.styleClass", "h3");
    }

    private void createUIComponents() {
        logoLabel = new JLabel(new FlatSVGIcon("svg/redis_64.svg"));
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(3, 4, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPanel.add(panel1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        newLabel = new JLabel();
        Font newLabelFont = UIManager.getFont("CheckBox.font");
        if (newLabelFont != null) newLabel.setFont(newLabelFont);
        newLabel.setText("新建连接");
        panel1.add(newLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newKeyLabel = new JLabel();
        newKeyLabel.setText("Ctrl+A");
        panel1.add(newKeyLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openLabel = new JLabel();
        openLabel.setText("打开连接");
        panel1.add(openLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openKeyLabel = new JLabel();
        openKeyLabel.setText("Ctrl+S");
        panel1.add(openKeyLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        contentPanel.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        contentPanel.add(spacer2, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        contentPanel.add(spacer3, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        logoLabel.setText("");
        contentPanel.add(logoLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        contentPanel.add(spacer4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

}