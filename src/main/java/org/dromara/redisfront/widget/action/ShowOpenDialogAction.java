package org.dromara.redisfront.widget.action;

import org.dromara.quickswing.ui.app.AppAction;
import org.dromara.redisfront.widget.MainWidget;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ShowOpenDialogAction extends AppAction<MainWidget> {

    public ShowOpenDialogAction(MainWidget app, String key) {
        super(app, key);
    }

    @Override
    public void handleAction(ActionEvent e) {
        System.out.println("Show Open Dialog");
    }

    @Override
    public KeyStroke getKeyStroke() {
        return KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
    }
}
