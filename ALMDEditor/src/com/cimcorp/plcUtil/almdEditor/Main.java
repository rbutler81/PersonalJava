package com.cimcorp.plcUtil.almdEditor;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {

	static Controller mainController;
	static DescController descController;

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
			Parent root = loader.load();
			mainController = (Controller) loader.getController();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("ALMD Editor");

			FXMLLoader loader2 = new FXMLLoader(getClass().getResource("DescDetails.fxml"));
			Parent root2 = loader2.load();
			descController = (DescController) loader2.getController();
			Scene scene2 = new Scene(root2);
			Stage descDetails = new Stage();
			primaryStage.getIcons().add(new Image("file:icon.png"));
			descDetails.setTitle("Description Details");
			descDetails.setScene(scene2);
			descDetails.initOwner(primaryStage.getScene().getWindow());
			descDetails.initModality(Modality.WINDOW_MODAL);
			descDetails.setResizable(false);
			descDetails.setAlwaysOnTop(true);
			descDetails.initStyle(StageStyle.UTILITY);

			mainController.setDescStage(descDetails);
			mainController.setDescController(descController);

			descController.setStage(descDetails);
			descController.setMainController(mainController);

			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {

		launch(args);

	}
}
