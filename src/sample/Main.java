package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private int index = 0;
    private SearchAlgorithms searchAlgorithms;
    private StateInfo stateInfo;
    private State game;
    private ArrayList<State> path;

    @Override
    public void start(Stage primaryStage) throws Exception{
        searchAlgorithms = new SearchAlgorithms();
        stateInfo = new StateInfo();

        Pane pane = new Pane();
        GridPane gridPane = new GridPane();
        GridPane puzzle = new GridPane();
        puzzle.setPadding(new Insets(20,20,20,20));
        gridPane.setPadding(new Insets(20,10,20,400));

        for (int i = 0; i < 9; i++) {
            Rectangle rectangle = new Rectangle();
            rectangle.setWidth(100);
            rectangle.setHeight(100);
            rectangle.setFill(Color.rgb(17, 186, 216));
            rectangle.setId("rectangle");
            StackPane stack = new StackPane();
            stack.getChildren().add(rectangle);
            puzzle.add(stack, (i%3), i/3);
            puzzle.setHgap(3);
            puzzle.setVgap(3);
        }

        TextField input = new TextField();
        input.setPromptText("Enter Puzzle");
        TextField goal = new TextField();
        goal.setPromptText("Enter Goal");

        ChoiceBox chooseAlgorithm = new ChoiceBox<String>(
                FXCollections.observableArrayList("BFS", "DFS", "A*"));
        Platform.runLater(() -> {
            SkinBase<ChoiceBox<String>> skin = (SkinBase<ChoiceBox<String>>) chooseAlgorithm.getSkin();
            for (Node child : skin.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    if (label.getText().isEmpty()) {
                        label.setText("Choose Algorithm");
                    }
                    return;
                }
            }
        });

        ChoiceBox chooseHeuristic = new ChoiceBox<String>(
                FXCollections.observableArrayList("manhattan", "euclidean"));
        chooseHeuristic.setValue("");
        Platform.runLater(() -> {
            SkinBase<ChoiceBox<String>> skin = (SkinBase<ChoiceBox<String>>) chooseHeuristic.getSkin();
            for (Node child : skin.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    if (label.getText().isEmpty()) {
                        label.setText("Choose Heuristic");
                    }
                    return;
                }
            }
        });
        chooseHeuristic.setDisable(true);

        Label nodesExpand = new Label("Nodes Expand:");
        Label nodesExpandVal = new Label("0");
        nodesExpandVal.setStyle("-fx-text-fill:rgb(17, 186, 216);");

        Label pathCost = new Label("Path Cost:");
        Label pathCostVal = new Label("0");
        pathCostVal.setStyle("-fx-text-fill:rgb(17, 186, 216);");


        Label searchDepth = new Label("Search Depth:");
        Label searchDepthVal = new Label("0");
        searchDepthVal.setStyle("-fx-text-fill:rgb(17, 186, 216);");


        Label runningTime = new Label("Running Time:");
        Label runningTimeVal = new Label("0");
        runningTimeVal.setStyle("-fx-text-fill:rgb(17, 186, 216);");


        Button run = new Button("Run");
        Button reset = new Button("Reset");
        Button next = new Button("Next");
        Button prev = new Button("Previous");
        reset.setDisable(true);
        next.setDisable(true);
        prev.setDisable(true);



        next.setOnAction(event->{
            System.out.println(++index);
            updatePuzzle(puzzle, path.get(index).getGameState());
            prev.setDisable(false);
            if (index == path.size() - 1)
                next.setDisable(true);
        });

        prev.setOnAction(event->{
            System.out.println(--index);
            updatePuzzle(puzzle, path.get(index).getGameState());
            next.setDisable(false);

            if (index == 0) {
                prev.setDisable(true);
                return;
            }
        });

        reset.setOnAction(event -> {
            index = 0;
            prev.setDisable(true);
            next.setDisable(false);
            updatePuzzle(puzzle, path.get(0).getGameState());
        });

        run.setOnAction(event -> {
            index = 0;
            prev.setDisable(true);

            String approach =  chooseAlgorithm.getValue().toString().replace("A*","A_STAR");
            String heuristic =  chooseHeuristic.getValue().toString();
            ArrayList<Integer> puzzleState = toArr(input.getText());
            List<Integer> target = toArr(goal.getText());

            updatePuzzle(puzzle, puzzleState);
            reset.setDisable(false);
            next.setDisable(false);

            var state = new State(puzzleState, puzzleState.indexOf(0),0, null);
            game = searchAlgorithms.search(approach, state, target, heuristic);
            path = game.getPath();
            nodesExpandVal.setText(Integer.toString(game.getStateInfo().getNodesExpanded()));
            searchDepthVal.setText(Integer.toString(game.getStateInfo().getSearchDepth()));
            pathCostVal.setText(Integer.toString(game.getStateInfo().getPathCost()));
            runningTimeVal.setText(game.getStateInfo().getRunningTime());
        });

        chooseAlgorithm.setOnAction(actionEvent -> {
            if (chooseAlgorithm.getSelectionModel().getSelectedItem().equals("A*")) {
                chooseHeuristic.setDisable(false);
                chooseHeuristic.setValue("manhattan");
            }

            else {
                chooseHeuristic.setDisable(true);
                chooseHeuristic.setValue("");
            }

        });

        gridPane.add(input, 0,4);
        gridPane.setHgap(5);
        gridPane.add(goal, 1,4);
        gridPane.setVgap(8);

        gridPane.add(chooseAlgorithm, 0, 5);
        gridPane.setHgap(5);
        gridPane.add(chooseHeuristic, 1, 5);
        gridPane.add(run, 2,5);
        gridPane.setVgap(8);

        gridPane.add(nodesExpand, 0, 6);
        gridPane.add(nodesExpandVal, 1, 6);
        gridPane.add(searchDepth, 0, 7);
        gridPane.add(searchDepthVal, 1,7);
        gridPane.add(pathCost,0,8);
        gridPane.add(pathCostVal,1,8);
        gridPane.add(runningTime,0,9);
        gridPane.add(runningTimeVal,1,9);

        gridPane.add(next, 0, 11);
        gridPane.add(prev, 1,11);
        gridPane.add(reset, 2, 11);

        gridPane.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        pane.getChildren().addAll(puzzle, gridPane);
        primaryStage.setScene(new Scene(pane));
        primaryStage.setTitle("8-Puzzle-Solver");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private ArrayList<Integer> toArr(String st){
        ArrayList<Integer> arr = new ArrayList<>();
        for (char ch : st.toCharArray())
            arr.add(Character.getNumericValue(ch));

        return arr;
    }

    private void updatePuzzle(GridPane gridPane, ArrayList<Integer>puzzle){
        for (int i = 0; i < puzzle.size(); i++) {
            Label label = new Label(Integer.toString(puzzle.get(i)).replace("0",""));
            label.setStyle("-fx-font-weight : bold; -fx-font-family:\"Arial Narrow\";-fx-font-size:20;");
            Rectangle rectangle = new Rectangle();
            rectangle.setWidth(100);
            rectangle.setHeight(100);
            rectangle.setFill(Color.rgb(17, 186, 216));
            rectangle.setId("rectangle");
            StackPane stack = new StackPane();
            stack.getChildren().addAll(rectangle, label);
            gridPane.add(stack, (i%3), i/3);
            gridPane.setHgap(3);
            gridPane.setVgap(3);
        }
    }
}