package com.redisfront.ui.dialog;

import com.formdev.flatlaf.FlatClientProperties;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.redisfront.RedisFrontApplication;
import com.redisfront.constant.Enum;
import com.redisfront.exception.RedisFrontException;
import com.redisfront.model.ConnectInfo;
import com.redisfront.ui.component.AbstractDialog;
import com.redisfront.util.FunUtil;
import com.redisfront.util.LettuceUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class AddKeyDialog extends AbstractDialog<String> {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField keyNameField;
    private JComboBox<String> keyTypeComboBox;
    private JTextArea keyValueField;
    private JTextField setScoreField;
    private JTextField hashKeyField;
    private JTextField streamField;
    private JLabel scoreLabel;
    private JLabel hashKeyLabel;
    private JLabel streamLabel;
    private JSpinner ttlSpinner;

    private final ConnectInfo connectInfo;

    public static void showAddDialog(ConnectInfo connectInfo, Consumer<String> addSuccessFun) {
        var openConnectDialog = new AddKeyDialog(connectInfo, addSuccessFun);
        openConnectDialog.setSize(new Dimension(500, 280));
        openConnectDialog.setLocationRelativeTo(RedisFrontApplication.frame);
        openConnectDialog.pack();
        openConnectDialog.setVisible(true);
    }

    public AddKeyDialog(ConnectInfo connectInfo, Consumer<String> addSucFun) {
        super(RedisFrontApplication.frame);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("添加");
        this.connectInfo = connectInfo;
        this.callback = addSucFun;
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        keyNameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "请输入键名");

        for (Enum.KeyTypeEnum typeEnum : Enum.KeyTypeEnum.values()) {
            keyTypeComboBox.addItem(typeEnum.typeName());
        }
        scoreLabel.setVisible(false);
        hashKeyLabel.setVisible(false);
        streamLabel.setVisible(false);
        hashKeyField.setVisible(false);
        setScoreField.setVisible(false);
        streamField.setVisible(false);
        ttlSpinner.setValue(-1);
        ttlSpinner.addChangeListener(e -> {
            if (((Integer) ttlSpinner.getValue()) < 0) {
                ttlSpinner.setValue(-1);
            }

            if (((Integer) ttlSpinner.getValue()) == 0) {
                ttlSpinner.setValue(1);
            }

        });

        keyTypeComboBox.addActionListener(e -> {
            String selectItem = (String) keyTypeComboBox.getSelectedItem();
            if (FunUtil.equal(Enum.KeyTypeEnum.HASH.typeName(), selectItem)) {
                hashKeyField.setVisible(true);
                hashKeyLabel.setVisible(true);

                setScoreField.setVisible(false);
                scoreLabel.setVisible(false);

                streamField.setVisible(false);
                streamLabel.setVisible(false);

            } else if (FunUtil.equal(Enum.KeyTypeEnum.STREAM.typeName(), selectItem)) {
                hashKeyField.setVisible(false);
                hashKeyLabel.setVisible(false);

                setScoreField.setVisible(false);
                scoreLabel.setVisible(false);

                streamField.setVisible(true);
                streamLabel.setVisible(true);

            } else if (FunUtil.equal(Enum.KeyTypeEnum.ZSET.typeName(), selectItem)) {
                hashKeyField.setVisible(false);
                hashKeyLabel.setVisible(false);

                setScoreField.setVisible(true);
                scoreLabel.setVisible(true);

                streamLabel.setVisible(false);
                streamField.setVisible(false);
            } else {
                hashKeyField.setVisible(false);
                hashKeyLabel.setVisible(false);

                setScoreField.setVisible(false);
                scoreLabel.setVisible(false);

                streamField.setVisible(false);
                streamLabel.setVisible(false);
            }
        });

    }

    private void validParam() {
        var selectItem = (String) keyTypeComboBox.getSelectedItem();

        if (FunUtil.isEmpty(keyNameField.getText())) {
            keyNameField.requestFocus();
            throw new RedisFrontException("参数校验失败", false);
        }

        if (FunUtil.equal(Enum.KeyTypeEnum.HASH.typeName(), selectItem) && FunUtil.isEmpty(hashKeyField.getText())) {
            hashKeyField.requestFocus();
            throw new RedisFrontException("参数校验失败", false);
        }

        if (FunUtil.equal(Enum.KeyTypeEnum.STREAM.typeName(), selectItem) && FunUtil.isEmpty(streamField.getText())) {
            streamField.requestFocus();
            throw new RedisFrontException("参数校验失败", false);
        }

        if (FunUtil.equal(Enum.KeyTypeEnum.ZSET.typeName(), selectItem) && FunUtil.isEmpty(setScoreField.getText())) {
            setScoreField.requestFocus();
            throw new RedisFrontException("参数校验失败", false);
        }

        if (FunUtil.isEmpty(keyValueField.getText())) {
            keyValueField.requestFocus();
            throw new RedisFrontException("参数校验失败", false);
        }
    }

    private void onOK() {

        validParam();
        var key = keyNameField.getText();
        var value = keyValueField.getText();
        var ttl = ((Integer) ttlSpinner.getValue());

        var selectItem = (String) keyTypeComboBox.getSelectedItem();
        if (FunUtil.equal(Enum.KeyTypeEnum.HASH.typeName(), selectItem) && FunUtil.isEmpty(hashKeyField.getText())) {
            LettuceUtil.run(connectInfo, redisCommands -> {
                redisCommands.hset(key, hashKeyField.getText(), keyValueField.getText());
            });
        } else if (FunUtil.equal(Enum.KeyTypeEnum.STREAM.typeName(), selectItem) && FunUtil.isEmpty(streamField.getText())) {

        } else if (FunUtil.equal(Enum.KeyTypeEnum.ZSET.typeName(), selectItem) && FunUtil.isEmpty(setScoreField.getText())) {

        } else {
            LettuceUtil.run(connectInfo, redisCommands -> {
                if (ttl > 0) {
                    redisCommands.setex(key, ttl, value);
                } else {
                    redisCommands.set(key, value);
                }
            });
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
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
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(7, 2, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        keyNameField = new JTextField();
        panel3.add(keyNameField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("TTL");
        panel3.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ttlSpinner = new JSpinner();
        panel3.add(ttlSpinner, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keyTypeComboBox = new JComboBox();
        contentPane.add(keyTypeComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keyValueField = new JTextArea();
        contentPane.add(keyValueField, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        setScoreField = new JTextField();
        contentPane.add(setScoreField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        hashKeyField = new JTextField();
        contentPane.add(hashKeyField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        streamField = new JTextField();
        contentPane.add(streamField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        scoreLabel = new JLabel();
        scoreLabel.setText("分数");
        contentPane.add(scoreLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        hashKeyLabel = new JLabel();
        hashKeyLabel.setText("键名");
        contentPane.add(hashKeyLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("键名");
        contentPane.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("类型");
        contentPane.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        streamLabel = new JLabel();
        streamLabel.setText("ID");
        contentPane.add(streamLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("值");
        contentPane.add(label4, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}