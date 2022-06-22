package com.redisfront.ui.base;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * BaseDialog
 *
 * @author Jin
 */
public abstract class AbstractDialog<T> extends JDialog {
    protected Consumer<T> callback;

    public AbstractDialog(Frame owner, Consumer<T> callback) {
        super(owner);
        this.callback = callback;
    }

    public AbstractDialog(Frame owner) {
        super(owner);
    }
}