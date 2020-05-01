package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class State {
    private ArrayList<Integer> gameState;
    private ArrayList<State> children;
    private int indexOfBlank;
    private int cost;
    private int heuristicCost;
    private int depth;
    private State parent;
    private StateInfo stateInfo;
    private Heuristics heuristics = new Heuristics();

    public State(ArrayList<Integer> State, int indexOfBlank, int depth, State parent) {
        this.gameState = State;
        this.indexOfBlank = indexOfBlank;
        this.depth = depth;
        this.parent = parent;
    }

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

    public ArrayList<Integer> getGameState() {
        return gameState;
    }

    public int getDepth() {
        return this.depth;
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

    public StateInfo getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(StateInfo stateInfo) {
        this.stateInfo = stateInfo;
    }

    public boolean isReachedGoal(List<Integer> goal){
        return Arrays.equals(this.gameState.toArray(), goal.toArray());
    }

    public int computeHeuristic(String heuristic) {
        int cost = 0;

        if (heuristic.equalsIgnoreCase("euclidean"))
            cost = heuristics.euclideanDistance(this);

        else if (heuristic.equalsIgnoreCase("manhattan"))
            cost = heuristics.manhattanDistance(this);

        this.heuristicCost = cost;

        return cost;
    }

    public int getCostToPath() {
        return this.cost + this.heuristicCost;
    }

    public ArrayList<State> getPath() {
        ArrayList<State> path = new ArrayList<>();
        var currentPuzzle = this;
        while (currentPuzzle != null) {
            path.add(currentPuzzle);
            currentPuzzle = currentPuzzle.parent;
        }
        Collections.reverse(path);

        return path;
    }

    /**
     * Return ArrayList of state based on the blank moves
     * there are four possible movement up, down, left, right
     * so the max return states is 4 new states if it made 4 movements
     * @return
     */
    public ArrayList<State> expand() {
        children = new ArrayList<>();

        if (goUp() != null)
            children.add(goUp());

        if (goDown() != null)
            children.add(goDown());

        if (goLeft() != null)
            children.add(goLeft());

        if (goRight() != null)
            children.add(goRight());

        return children;
    }

    private State goLeft() {
        if (this.indexOfBlank % 3 == 0 &&
                this.indexOfBlank - 1 < 0)
            return null;

        int target = this.indexOfBlank - 1;
        ArrayList<Integer> puzzleState = new ArrayList<>(this.getGameState());
        swap(puzzleState, target, this.indexOfBlank);

        return new State(puzzleState, target, this.depth++, this);
    }

    private State goRight() {
         final List<Integer> cantGoRight = Arrays.asList(2, 5, 8);

        if(cantGoRight.contains(this.indexOfBlank))
            return null;

        int target = this.indexOfBlank + 1;
        ArrayList<Integer> puzzleState = new ArrayList<>(this.getGameState());
        swap(puzzleState, target, this.indexOfBlank);

        return new State(puzzleState, target, this.depth++, this);
    }

    private State goUp() {
        if(this.indexOfBlank <= 2)
            return null;

        int target = this.indexOfBlank - 3;
        ArrayList<Integer> puzzleState = new ArrayList<>(this.getGameState());
        swap(puzzleState, target, this.indexOfBlank);

        return new State(puzzleState, target, this.depth++, this);
    }

    private State goDown() {
        if(this.indexOfBlank >= 6)
            return null;

        int target = this.indexOfBlank + 3;
        ArrayList<Integer> puzzleState = new ArrayList<>(this.getGameState());
        swap(puzzleState, target, this.indexOfBlank);

        return new State(puzzleState, target, this.depth++, this);
    }

    private void swap(ArrayList<Integer> state, int firstIdx, int secondIdx){
        var temp = state.get(firstIdx);
        state.set(firstIdx, state.get(secondIdx));
        state.set(secondIdx, temp);
    }
}