package com.redisfront.ui.form;

import javax.swing.*;
import java.awt.*;

public class _DatabaseForm {
    private JPanel contentPanel;

    public static _DatabaseForm dashboardForm;

    public static _DatabaseForm getInstance() {
        if (dashboardForm == null) {
            dashboardForm = new _DatabaseForm();
        }
        return dashboardForm;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 0));
        final JSplitPane splitPane1 = new JSplitPane();
        contentPanel.add(splitPane1, BorderLayout.CENTER);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        splitPane1.setLeftComponent(panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        splitPane1.setRightComponent(panel2);
        final JToolBar toolBar1 = new JToolBar();
        panel2.add(toolBar1, BorderLayout.WEST);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

}