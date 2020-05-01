package sample;

import java.util.*;

public class A_STAR {
    private StateInfo stateInfo;

    /**
     * A* Algorithms using Priority Queue
     * @param initialState
     * @param goal
     */
    public State aStar(State initialState, List<Integer> goal, String heuristics) {
        PriorityQueue<State> frontier = new PriorityQueue<>((p1, p2)->{
            //ascending order
            int cost1 = p1.getPath().size() + p1.computeHeuristic(heuristics);
            int cost2 = p2.getPath().size() + p2.computeHeuristic(heuristics);

            return cost1 - cost2;
        });
        HashSet<String> visited = new HashSet<>();

        stateInfo = new StateInfo();
        stateInfo.setStartTime(System.nanoTime());
        frontier.add(initialState);
        while (!frontier.isEmpty())
        {
            var currentState = frontier.poll();
            System.out.println(currentState);
            visited.add(currentState.getGameState().toString());

            if (currentState.isReachedGoal(goal)) {
                stateInfo.setEndTime(System.nanoTime());
                stateInfo.setNodesExpanded(visited.size());
                stateInfo.setPathCost(currentState.getPath().size());
                stateInfo.setSearchDepth(currentState.getPath().size());
                stateInfo.setMaxSearchDepth(currentState.getDepth());
                System.out.println(stateInfo);
                currentState.setStateInfo(stateInfo);
                return currentState;
            }

            for (var children: currentState.expand())
                if (!visited.contains(children.getGameState().toString()) && !frontier.contains(children))
                    frontier.add(children);

        }

        return null;
    }

    /**
     * different implementation for A* using decrease key
     * @param initialState
     * @param goal
     * @param heuristics
     */
    public void aStar_2(State initialState, List<Integer> goal, String heuristics) {
        PriorityQueue<State> frontier = new PriorityQueue<>(Comparator.comparingInt(State::getCostToPath));
        HashSet<String> visited = new HashSet<>();

        stateInfo = new StateInfo();

        initialState.computeHeuristic(heuristics);

        stateInfo.setStartTime(System.nanoTime());

        frontier.add(initialState);
        while (!frontier.isEmpty())
        {
            var currentState = frontier.poll();
            System.out.println(currentState);
            visited.add(currentState.getGameState().toString());

            if (currentState.isReachedGoal(goal)) {
                stateInfo.setEndTime(System.nanoTime());
                stateInfo.setNodesExpanded(visited.size());
                stateInfo.setPathCost(currentState.getPath().size());
                stateInfo.setSearchDepth(currentState.getPath().size());
                stateInfo.setMaxSearchDepth(currentState.getDepth());
                System.out.println(stateInfo);
                return;
            }

            Queue<State> storeQueue = new ArrayDeque<>();
            for (var children: currentState.expand()) {
                children.computeHeuristic(heuristics);

                if (!visited.contains(children.getGameState().toString()) && !frontier.contains(children))
                    frontier.add(children);

                else if (frontier.contains(children)) {
                    if (children.getCostToPath() > currentState.getCost() + children.getHeuristicCost()) {
                        for (var state: frontier)
                            if (state.getGameState().toString().equals(children.getGameState().toString()))
                                storeQueue.add(state);

                        decreaseKey(frontier, storeQueue, currentState);
                    }
                }
            }
        }
    }

    private void decreaseKey(PriorityQueue<State> frontier, Queue<State> storeQueue,State state){
        frontier.remove(storeQueue.peek());
        storeQueue.peek().setCost(state.getCost()+10);
        frontier.add(storeQueue.peek());
    }
}
