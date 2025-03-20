package com.sahara.view.util;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ViewUtil {
    public static HBox createFormRow(String labelText, Control field) {
        HBox row = new HBox(10);
        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        Label label = new Label(labelText);
        label.setMinWidth(80);
        row.getChildren().addAll(label, field);
        return row;
    }
}
