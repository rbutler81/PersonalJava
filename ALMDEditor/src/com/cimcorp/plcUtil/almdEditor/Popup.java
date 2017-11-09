package com.cimcorp.plcUtil.almdEditor;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popup {
	public static void display(String t) {
		Stage popupwindow = new Stage();

		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("AB Code");

		Label l1 = new Label("Copy / paste the following into Logix:");

		TextArea text = new TextArea(t);
		text.setPrefHeight(400);
		text.setPrefWidth(500);
		text.setWrapText(true);
		text.setEditable(false);

		Button button1 = new Button("Close");

		button1.setOnAction(e -> popupwindow.close());

		VBox layout = new VBox(10);

		layout.getChildren().addAll(l1, text, button1);

		layout.setAlignment(Pos.CENTER);

		Scene scene1 = new Scene(layout, 700, 480);

		popupwindow.setScene(scene1);

		popupwindow.showAndWait();

	}
}
