import java.util.*;

public class A_STAR {
    private StateInfo stateInfo;

    /**
     * A* Algorithms using Priority Queue
     * @param initialState
     * @param goal
     */
    public void aStar(State initialState, List<Integer> goal, String heuristics) {
        PriorityQueue<State> frontier = new PriorityQueue<>(Comparator.comparingInt(State::getCostToPath));
        HashSet<String> visited = new HashSet<>();

        stateInfo = new StateInfo();

        if (heuristics.equals("manhattan")) initialState.manhattanDistanceCost();
        else initialState.euclideanDistanceCost();

        stateInfo.setStartTime(System.nanoTime());

        frontier.add(initialState);
        while (!frontier.isEmpty())
        {
            var currentState = frontier.poll();
            System.out.println(currentState);
            visited.add(currentState.getGameState().toString());

            //update state info
            stateInfo.setNodesExpanded(visited.size());
            stateInfo.setPathCost(frontier.size());
            stateInfo.setSearchDepth(frontier.size() - 1);

            if (currentState.isReachedGoal(goal)) {
                stateInfo.setEndTime(System.nanoTime());
                System.out.println(stateInfo);
                return;
            }

            Queue<State> storeQueue = new ArrayDeque<>();
            for (var children: currentState.expand()) {
                if (heuristics.equals("manhattan")) children.manhattanDistanceCost();
                else children.euclideanDistanceCost();

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

    public void decreaseKey(PriorityQueue<State> frontier, Queue<State> storeQueue,State state){
        frontier.remove(storeQueue.peek());
        storeQueue.peek().setCost(state.getCost()+10);
        frontier.add(storeQueue.peek());
    }
}
