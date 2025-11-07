package com.example.task_manager_beta;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application
{

    public static final String FXML_TO_LOAD = "hello-view.fxml";
    public static final String STAGE_TITLE = "Maintenance Task Manager";
    public static final int STAGE_WIDTH = 1075;
    public static final int STAGE_HEIGHT = 700;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(FXML_TO_LOAD));
        Scene scene = new Scene(fxmlLoader.load(), STAGE_WIDTH, STAGE_HEIGHT);
        stage.setTitle(STAGE_TITLE);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}