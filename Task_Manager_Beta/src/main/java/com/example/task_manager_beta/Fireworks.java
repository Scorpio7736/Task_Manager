package com.example.task_manager_beta;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Fireworks {

    private static final int NUMBER_OF_FIREWORKS_PARTICLES = 35;
    private static final double FULLSCREEN_ANCHOR_WIDTH = 0.0;
    private static final double FIREWORK_PADDING = 10;
    private static final double FIREWORK_SIZE_ALL_DONE = 300;
    private static final int FIREWORK_COUNT_ALL_DONE = 3;
    private static final int FIREWORK_ALL_DONE_LABEL_DISPLAY_SECONDS = 2;
    private static final double OPACITY_HIDDEN = 0;
    private static final double OPACITY_VISIBLE = 1;

    public static void launch(AnchorPane root, MouseEvent mouseEvent){
        launch(
                root,
                mouseEvent.getSceneX(),
                mouseEvent.getSceneY(),
                100,
                0);
    }

    public static void launchAllDone(AnchorPane root){
        double fireworkOffset = FIREWORK_SIZE_ALL_DONE / 2 + FIREWORK_PADDING;
        for(int i = 1; i <= FIREWORK_COUNT_ALL_DONE; i++){
            double x = StrongRandom.getDouble(fireworkOffset, root.getWidth() - fireworkOffset);
            double y = StrongRandom.getDouble(fireworkOffset, root.getHeight() - fireworkOffset);
            launch(root, x, y, FIREWORK_SIZE_ALL_DONE, i);
        }

        StackPane pane = new StackPane();
        root.getChildren().add(pane);
        AnchorPane.setTopAnchor(pane, FULLSCREEN_ANCHOR_WIDTH);
        AnchorPane.setBottomAnchor(pane, FULLSCREEN_ANCHOR_WIDTH);
        AnchorPane.setLeftAnchor(pane, FULLSCREEN_ANCHOR_WIDTH);
        AnchorPane.setRightAnchor(pane, FULLSCREEN_ANCHOR_WIDTH);

        Label label = new Label();
        label.setText("All Done!");
        label.setStyle("-fx-font-size: 75pt; -fx-font-weight: bold; -fx-text-fill: #222222; -fx-padding: 0 0 10 0;");
        pane.getChildren().add(label);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(pane.opacityProperty(), OPACITY_HIDDEN)),
                new KeyFrame(Duration.seconds(FIREWORK_COUNT_ALL_DONE),
                        new KeyValue(pane.opacityProperty(), OPACITY_VISIBLE)),
                new KeyFrame(Duration.seconds(FIREWORK_COUNT_ALL_DONE + FIREWORK_ALL_DONE_LABEL_DISPLAY_SECONDS),
                        new KeyValue(pane.opacityProperty(), OPACITY_VISIBLE))
        );

        timeline.setOnFinished(e->root.getChildren().remove(pane));
        timeline.play();

    }

    public static void launch(AnchorPane root, double centerX, double centerY, double fireworkMaxSize, double delay) {
        for (int i = 0; i < NUMBER_OF_FIREWORKS_PARTICLES; i++) {
            Circle particle = new Circle(3, Color.hsb(StrongRandom.getDouble(0, 360), 1, 1));
            particle.setCenterX(centerX);
            particle.setCenterY(centerY);
            particle.setOpacity(OPACITY_HIDDEN);
            root.getChildren().add(particle);

            double angle = StrongRandom.getDouble(0, 2 * Math.PI);
            double distance = fireworkMaxSize + StrongRandom.getDouble(0, 50);
            double targetX = centerX + distance * Math.cos(angle);
            double targetY = centerY + distance * Math.sin(angle);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(delay),
                            new KeyValue(particle.centerXProperty(), centerX),
                            new KeyValue(particle.centerYProperty(), centerY),
                            new KeyValue(particle.opacityProperty(), OPACITY_VISIBLE)),
                    new KeyFrame(Duration.seconds(delay + 1),
                            new KeyValue(particle.centerXProperty(), targetX),
                            new KeyValue(particle.centerYProperty(), targetY),
                            new KeyValue(particle.opacityProperty(), OPACITY_HIDDEN))
            );

            timeline.setOnFinished(e -> root.getChildren().remove(particle));
            timeline.play();

        }

    }
}
