import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicReference;

public class A_STAR {
    private int startTime, endTime, nodesExpanded;

    /**
     * A Star
     * @param initialState
     * @param goal
     */
    public void aStar(State initialState, List<Integer> goal, String heuristics) {
    }

    public void decreaseKey(PriorityQueue<State> queue, State state){
        queue.remove(state);
        state.setCost(state.getCost()+1);
        queue.add(state);
    }
}
