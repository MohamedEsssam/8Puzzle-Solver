package sample;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import static java.lang.System.exit;

public class DFS {
    private StateInfo stateInfo;

    /**
     * DFS using stack
     * @param initialState
     * @param goal
     */
    public State dfs(State initialState, List<Integer> goal) {
        if (initialState == null)
            return null;

        stateInfo = new StateInfo();

        HashSet<String> visited = new HashSet();

        stateInfo.setStartTime(System.nanoTime());

        Stack<State> stateStack = new Stack<>();
        stateStack.push(initialState);
        while (!stateStack.isEmpty()) {
            var currentState = stateStack.pop();
            System.out.println(currentState);
            visited.add(currentState.getGameState().toString());

            if (currentState.isReachedGoal(goal)) {
                stateInfo.setEndTime(System.nanoTime());
                stateInfo.setNodesExpanded(visited.size() - 1);
                stateInfo.setPathCost(currentState.getPath().size());
                stateInfo.setSearchDepth(currentState.getPath().size());
                stateInfo.setMaxSearchDepth(currentState.getDepth());
                System.out.println(stateInfo);
                currentState.setStateInfo(stateInfo);
                return currentState;
            }

            for (var children : currentState.expand())
                if(!visited.contains(children.getGameState().toString()))
                    stateStack.push(children);
        }

        return null;
    }

    /**
     * DFS Recursive
     * @param initialState
     * @param goal
     */
    public void dfsRec(State initialState, List<Integer> goal){
        dfsRec(initialState, goal, new HashSet());
    }

    private void dfsRec(State initialState, List<Integer> goal,
                        Set<String> visited){
        if (initialState == null)
            return;

        if (initialState.isReachedGoal(goal)){
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
}
