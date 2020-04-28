import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;

public class BFS {
    private StateInfo stateInfo;

    /**
     * BFS using queue
     * @param initialState
     * @param goal
     */
    public void bfs(State initialState, List<Integer> goal) {
        if (initialState == null)
            return;

        stateInfo = new StateInfo();

        HashSet<String> visited = new HashSet<>();

        stateInfo.setStartTime(System.nanoTime());

        Queue<State> frontier = new ArrayDeque<>();
        frontier.add(initialState);
        while(!frontier.isEmpty()){
            var currentState = frontier.poll();
            visited.add(currentState.getGameState().toString());

            //update state info
            stateInfo.setNodesExpanded(visited.size());
            stateInfo.setPathCost(frontier.size());
            stateInfo.setSearchDepth(frontier.size() - 1);

            if(currentState.isReachedGoal(goal)){
                stateInfo.setEndTime(System.nanoTime());
                System.out.println(currentState);
                System.out.println(stateInfo);
                return;
            }

            System.out.println(currentState);
            for (var children : currentState.expand())
                if(!visited.contains(children.getGameState().toString()))
                    frontier.add(children);
        }
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
