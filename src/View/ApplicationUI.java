package View;

import Control.Control;
import Model.Grid;
import Model.Configuration.CustomConfig;
import Model.Configuration.GliderConfig;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ApplicationUI extends Application implements UI {

    private Grid grid;
    private final int CELL_SIZE = 15;
    private Timeline timeline;
    private Control control;

    @Override
    public void start(Stage stage) {
        grid = new Grid(50, 50);
        control = new Control();
        CustomConfig cfg = new CustomConfig();
        cfg.configure(grid);

        Canvas canvas = new Canvas(grid.getWidth() * CELL_SIZE, grid.getHeight() * CELL_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawGrid(gc);

        canvas.setOnMouseClicked(event -> {
            int x = (int) (event.getY() / CELL_SIZE);
            int y = (int) (event.getX() / CELL_SIZE);
            if (x >= 0 && x < grid.getHeight() && y >= 0 && y < grid.getWidth()) {
                boolean currentState = grid.getState(x, y);
                grid.setState(x, y, !currentState);
                drawGrid(gc);
            }
        });

        Button startButton = new Button("Start");
        Button pauseButton = new Button("Pause");
        Button resetButton = new Button("Reset");
        Button clearButton = new Button("Clear");
        Button gliderButton = new Button("Glider Layout");
        Button exitButton = new Button("Exit");

        startButton.setOnAction(e -> startSimulation(gc));
        pauseButton.setOnAction(e -> pauseSimulation());
        resetButton.setOnAction(e -> resetSimulation(gc));
        clearButton.setOnAction(e -> clearGrid(gc));
        gliderButton.setOnAction(e -> applyGliderLayout(gc));
        exitButton.setOnAction(e -> Platform.exit());

        HBox controls = new HBox(10, startButton, pauseButton, resetButton, clearButton, gliderButton, exitButton);
        controls.setId("controls");

        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        root.setBottom(controls);

        Scene scene = new Scene(root, grid.getWidth() * CELL_SIZE, grid.getHeight() * CELL_SIZE + 60);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("Game of Life");
        stage.setScene(scene);
        stage.show();
    }

    private void startSimulation(GraphicsContext gc) {
        if (timeline == null) {
            timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> {
                control.advance(grid);
                drawGrid(gc);
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        } else {
            timeline.play();
        }
    }

    private void pauseSimulation() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    private void resetSimulation(GraphicsContext gc) {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
        grid = new Grid(50, 50);
        CustomConfig cfg = new CustomConfig();
        cfg.configure(grid);
        drawGrid(gc);
    }

    private void clearGrid(GraphicsContext gc) {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
        grid = new Grid(50, 50);
        drawGrid(gc);
    }

    private void applyGliderLayout(GraphicsContext gc) {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
        grid = new Grid(50, 50);
        GliderConfig gliderConfig = new GliderConfig();
        gliderConfig.configure(grid);
        drawGrid(gc);
    }

    @Override
    public void displayGrid(Grid grid) {
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setFill(Color.web("#ecf0f1"));
        gc.fillRect(0, 0, grid.getWidth() * CELL_SIZE, grid.getHeight() * CELL_SIZE);

        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                if (grid.getState(i, j)) {
                    gc.setFill(Color.web("#2c3e50"));
                    gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else {
                    gc.setFill(Color.web("#bdc3c7"));
                    gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        gc.setStroke(Color.web("#34495e"));
        for (int i = 0; i <= grid.getHeight(); i++) {
            gc.strokeLine(0, i * CELL_SIZE, grid.getWidth() * CELL_SIZE, i * CELL_SIZE);
        }
        for (int j = 0; j <= grid.getWidth(); j++) {
            gc.strokeLine(j * CELL_SIZE, 0, j * CELL_SIZE, grid.getHeight() * CELL_SIZE);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}