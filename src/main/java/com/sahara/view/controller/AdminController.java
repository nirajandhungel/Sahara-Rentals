package com.sahara.view.controller;

import com.sahara.view.components.Footer;

import javafx.scene.layout.BorderPane;

public class AdminController {

    public BorderPane getView() {
        BorderPane adminView = new BorderPane();

        // Footer Section
        adminView.setBottom(new Footer());

        return adminView;
    }
}
