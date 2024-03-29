package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        final List<Integer> goal = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
        SearchAlgorithms search = new SearchAlgorithms();
        ArrayList<Integer> puzzleState = new ArrayList<>();

        puzzleState.add(8);
        puzzleState.add(6);
        puzzleState.add(4);
        puzzleState.add(2);
        puzzleState.add(1);
        puzzleState.add(3);
        puzzleState.add(5);
        puzzleState.add(7);
        puzzleState.add(0);

        var state = new State(puzzleState, puzzleState.indexOf(0),0, null, 3);

        var puzzle =search.search("A_STAR", state, goal,"manhattan");
    }
}
