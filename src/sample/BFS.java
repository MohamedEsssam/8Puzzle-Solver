package sample;

import java.util.*;
import static java.lang.System.exit;

public class BFS {
    private StateInfo stateInfo;

    /**
     * BFS using queue
     * @param initialState
     * @param goal
     */
    public State bfs(State initialState, List<Integer> goal) {
        if (initialState == null)
            return null;

        stateInfo = new StateInfo();

        HashSet<String> visited = new HashSet<>();

        stateInfo.setStartTime(System.nanoTime());

        Queue<State> frontier = new ArrayDeque<>();
        frontier.add(initialState);
        while(!frontier.isEmpty()){
            var currentState = frontier.poll();
            visited.add(currentState.getGameState().toString());

            if(currentState.isReachedGoal(goal)){
                stateInfo.setEndTime(System.nanoTime());
                stateInfo.setNodesExpanded(visited.size()-1);
                stateInfo.setPathCost(currentState.getPath().size());
                stateInfo.setSearchDepth(currentState.getPath().size());
                stateInfo.setMaxSearchDepth(currentState.getDepth());
                System.out.println(currentState);
                System.out.println(stateInfo);
                currentState.setStateInfo(stateInfo);
                return currentState;
            }

            System.out.println(currentState);
            for (var children : currentState.expand())
                if(!visited.contains(children.getGameState().toString()))
                    frontier.add(children);
        }

        return null;
    }

    /**
     * BFS
     * @param initialState
     * @param goal
     */
    public void bfsRec(State initialState, List<Integer> goal){
        bfsRec(initialState, goal, new ArrayDeque(), new HashSet());
    }

    private void bfsRec(State initialState, List<Integer> goal,
                        Queue<State> frontier, Set<String> visited){
        if (initialState == null)
            return;

        frontier.add(initialState);

        var currentState = frontier.poll();
        System.out.println(currentState);

        if (currentState.isReachedGoal(goal)){
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
}
