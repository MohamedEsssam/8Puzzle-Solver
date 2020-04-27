import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final List<Integer> goal = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
        SearchAlgorithms search = new SearchAlgorithms();
        ArrayList<Integer> puzzleState = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            puzzleState.add(i);
        }
        Collections.shuffle(puzzleState);

        var state = new State(puzzleState, puzzleState.indexOf(0));

        search.bfs(state, goal);
        }
}
