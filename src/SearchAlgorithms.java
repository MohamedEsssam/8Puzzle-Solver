import java.util.*;

import static java.lang.System.exit;

public class SearchAlgorithms {

    /**
     * Solve the game using Iterative Method
     */

    /**
     * DFS using stack
     * @param initialState
     * @param goal
     */
    public void dfs(State initialState, List<Integer> goal) {
        if (initialState == null)
            return;

        HashSet<String> visited = new HashSet();

        Stack<State> stateStack = new Stack<>();
        stateStack.push(initialState);
        while (!stateStack.isEmpty()) {
            var currentState = stateStack.pop();
            System.out.println(currentState);
            visited.add(currentState.getGameState().toString());

            if (isReachedGoal(currentState, goal)) {
                System.out.println("GOAL REACHED");
                return;
            }

            for (var children : currentState.expand())
                if (!visited.contains(children.getGameState().toString()))
                    stateStack.push(children);
        }
    }

    /**
     * BFS using queue
     * @param initialState
     * @param goal
     */
    public void bfs(State initialState, List<Integer> goal) {
        if (initialState == null)
            return;

        HashSet<String> visited = new HashSet<>();

        Queue<State> frontier = new ArrayDeque<>();
        frontier.add(initialState);
        while(!frontier.isEmpty()){
            var currentState = frontier.poll();
            visited.add(currentState.getGameState().toString());

            if(isReachedGoal(currentState, goal)){
                System.out.println(currentState);
                System.out.println("GOAL REACHED");
                return;
            }

            System.out.println(currentState);
            for (var children : currentState.expand())
                if(!visited.contains(children.getGameState().toString()))
                    frontier.add(children);
        }
    }

    /**
     * A Star
     * @param initialState
     * @param goal
     * @param heuristics
     */
    public void aStar(State initialState, List<Integer> goal, String heuristics){
        PriorityQueue<State> frontier = new PriorityQueue<>();
        HashSet<String> visited = new HashSet<>();

        frontier.add(initialState);
    }

    /**
     * Solve the game using Recursion Method but
     * it goes to StackOverflowError at some cases
     */

    /**
     * BFS
     * @param initialState
     * @param goal
     */
    public void bfsRec(State initialState, List<Integer> goal){
        bfsRec(initialState, goal, new ArrayDeque(), new HashSet());
    }

    /**
     * DFS
     * @param initialState
     * @param goal
     */
    public void dfsRec(State initialState, List<Integer> goal){
        dfsRec(initialState, goal, new HashSet());
    }

    private void bfsRec(State initialState, List<Integer> goal,
                        Queue<State> frontier, Set<String> visited){
        if (initialState == null)
            return;

        frontier.add(initialState);

        var currentState = frontier.poll();
        System.out.println(currentState);

        if (isReachedGoal(currentState, goal)){
                System.out.println("Goal is reached");
                exit(0);
            }

        for (var children : currentState.expand())
            if (!visited.contains(children.getGameState().toString()))
            {
                visited.add(children.getGameState().toString());
                frontier.add(children);
            }

        if (frontier.isEmpty())
            return;

        bfsRec(frontier.peek(), goal,frontier, visited);
    }

    private void dfsRec(State initialState, List<Integer> goal,
                        Set<String> visited){
        if (initialState == null)
            return;

        if (isReachedGoal(initialState, goal)){
            System.out.println(initialState);
            System.out.println("Goal is reached");
            exit(0);
        }

        System.out.println(initialState);
        visited.add(initialState.getGameState().toString());

        for (var children : initialState.expand())
            if (!visited.contains(children.getGameState().toString()))
                dfsRec(children, goal, visited);
    }

    /**
     * To check if current state is reach to goal or not
     * @param currentState
     * @param goal
     * @return
     */
    private boolean isReachedGoal(State currentState, List<Integer> goal){
        return Arrays.equals(currentState.getGameState().toArray(), goal.toArray());
    }
}
