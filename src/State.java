import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class State {
    private ArrayList<Integer> gameState;
    ArrayList<State> children;
    private int indexOfBlank;
    private int cost;
    private int heuristicCost;
    private int depth;
    private State parent;
    private Heuristics heuristics = new Heuristics();

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < gameState.size(); i++) {
            if (i % 3 == 0)
                builder.append("\n|");

            builder.append(gameState.get(i).toString()+" ");
            if (i == 2 || i == 8 || i == 5)
                builder.append("|");
        }

        return "Current State =\n--------" + builder.toString().replace("0"," ")+"\n--------";
    }

    public State(ArrayList<Integer> State, int indexOfBlank, int depth, State parent) {
        this.gameState = State;
        this.indexOfBlank = indexOfBlank;
        this.depth = depth;
        this.parent = parent;
    }

    public ArrayList<Integer> getGameState() {
        return gameState;
    }

    public void setGameState(ArrayList<Integer> State) {
        this.gameState = State;
    }

    public int getIndexOfBlank() {
        return indexOfBlank;
    }

    public void setIndexOfBlank(int indexOfBlank) {
        this.indexOfBlank = indexOfBlank;
    }

    public ArrayList<State> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<State> children) {
        this.children = children;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public void manhattanDistanceCost(){
        this.heuristicCost = heuristics.manhattanDistance(this);
    }

    public void euclideanDistanceCost(){
        this.heuristicCost = heuristics.euclideanDistance(this);
    }

    public int getCostToPath() {
        return this.cost + this.heuristicCost;
    }

    public boolean isReachedGoal(List<Integer> goal){
        return Arrays.equals(this.gameState.toArray(), goal.toArray());
    }

    public int costOfPath(State currentState) {
        ArrayList<State> fullPath = new ArrayList<>();
        fullPath.add(currentState);

        var nextState = currentState.parent;
        while (nextState != null) {
            fullPath.add(nextState);
            nextState = nextState.parent;
        }
        return fullPath.size() - 1;
    }

    /**
     * Return ArrayList of state based on the blank moves
     * there are four possible movement up, down, left, right
     * so the max return states is 4 new states if it made 4 movements
     * @return
     */
    public ArrayList<State> expand() {
        children = new ArrayList<>();

        if (goUp(this) != null)
            children.add(goUp(this));

        if (goDown(this) != null)
            children.add(goDown(this));

        if (goLeft(this) != null)
            children.add(goLeft(this));


        if (goRight(this) != null)
            children.add(goRight(this));

        return children;
    }

    private State goLeft(State currentState) {
        if (currentState.indexOfBlank % 3 == 0 &&
                currentState.indexOfBlank - 1 < 0)
            return null;

        int target = currentState.indexOfBlank - 1;
        ArrayList<Integer> puzzleState = new ArrayList<>(currentState.getGameState());
        swap(puzzleState, target, currentState.indexOfBlank);

        return new State(puzzleState, target, depth++, this);
    }

    private State goRight(State currentState) {
         final List<Integer> cantGoRight = Arrays.asList(2, 5, 8);

        if(cantGoRight.contains(currentState.indexOfBlank))
            return null;

        int target = currentState.indexOfBlank + 1;
        ArrayList<Integer> puzzleState = new ArrayList<>(currentState.getGameState());
        swap(puzzleState, target, currentState.indexOfBlank);

        return new State(puzzleState, target, depth++, this);
    }

    private State goUp(State currentState) {
        if(currentState.indexOfBlank <= 2)
            return null;

        int target = currentState.indexOfBlank - 3;
        ArrayList<Integer> puzzleState = new ArrayList<>(currentState.getGameState());
        swap(puzzleState, target, currentState.indexOfBlank);

        return new State(puzzleState, target, depth++, this);
    }

    private State goDown(State currentState) {
        if(currentState.indexOfBlank >= 6)
            return null;

        int target = currentState.indexOfBlank + 3;
        ArrayList<Integer> puzzleState = new ArrayList<>(currentState.getGameState());
        swap(puzzleState, target, currentState.indexOfBlank);

        return new State(puzzleState, target, depth++, this);
    }

    private void swap(ArrayList<Integer> state, int firstIdx, int secondIdx){
        var temp = state.get(firstIdx);
        state.set(firstIdx, state.get(secondIdx));
        state.set(secondIdx, temp);
    }
}
