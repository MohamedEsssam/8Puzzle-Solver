import java.util.*;
import static java.lang.System.exit;

public class BFS {
    private StateInfo stateInfo;
    private int maxDepth = 0;

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
            stateInfo.setNodesExpanded(visited.size()-1);
            //stateInfo.setPathCost();

            if(currentState.isReachedGoal(goal)){
                stateInfo.setEndTime(System.nanoTime());
                stateInfo.setPathCost(currentState.costOfPath(currentState));
                System.out.println(currentState);
                System.out.println(stateInfo);
                return;
            }

            System.out.println(currentState);
            for (var children : currentState.expand())
                if(!visited.contains(children.getGameState().toString())) {
                    frontier.add(children);
                    if (children.getDepth() > this.maxDepth) {
                        this.maxDepth = children.getDepth();
                        stateInfo.setSearchDepth(this.maxDepth);
                    }
                }
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
