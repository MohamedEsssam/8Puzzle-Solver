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
    private int size;
    private StateInfo stateInfo;
    private Heuristics heuristics = new Heuristics();

    public State(ArrayList<Integer> State, int indexOfBlank, int depth, State parent, int size) {
        this.gameState = State;
        this.indexOfBlank = indexOfBlank;
        this.depth = depth;
        this.parent = parent;
        this.size = size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < this.size; i++) {
            builder.append("|");
            for (int j = 0; j < this.size; j++) {
                builder.append(" " + this.gameState.get(i * this.size + j).toString() + "");
            }
            builder.append("|\n");;
        }

        return "Current State =\n"+"-".repeat(this.size * 2 + 2)+"\n" + builder.toString().replace("0"," ")+"-".repeat(this.size * 2 + 2)+"\n";
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
            cost = heuristics.euclideanDistance(this.gameState, size);

        else if (heuristic.equalsIgnoreCase("manhattan"))
            cost = heuristics.manhattanDistance(this.gameState, size);

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

    private int getIndex(int num) {
        return this.gameState.indexOf(num);
    }

    private int getRow(int num) {
        int n = this.size;
        int indexOfZero = this.getIndex(num);

        return indexOfZero / n ;
    }

    private int getCol(int num) {
        int n = this.size;
        int indexOfZero = this.getIndex(num);

        return indexOfZero % n;
    }

    private boolean canMoveLeft() {
        return this.getCol(0) != 0;
    }
    private State goLeft() {
        if (!canMoveLeft())
            return null;

        int target = this.indexOfBlank - 1;
        ArrayList<Integer> puzzleState = new ArrayList<>(this.getGameState());
        swap(puzzleState, target, this.indexOfBlank);

        return new State(puzzleState, target, this.depth++, this, size);
    }

    private boolean canMoveRight() {
        return this.getCol(0) != this.size - 1;
    }
    private State goRight() {
        if(!canMoveRight())
            return null;

        int target = this.getIndex(0) + 1;
        ArrayList<Integer> puzzleState = new ArrayList<>(this.getGameState());
        swap(puzzleState, target, this.indexOfBlank);

        return new State(puzzleState, target, this.depth++, this, size);
    }

    private boolean canMoveUp() {
        return this.getRow(0) != 0;
    }
    private State goUp() {
        if(!canMoveUp())
            return null;

        int target = this.indexOfBlank - size;
        ArrayList<Integer> puzzleState = new ArrayList<>(this.getGameState());
        swap(puzzleState, target, this.indexOfBlank);

        return new State(puzzleState, target, this.depth++, this, size);
    }

    private boolean canMoveDown() {
        return this.getRow(0)!= this.size - 1;
    }
    private State goDown() {
        if(!canMoveDown())
            return null;

        int target = this.indexOfBlank + size;
        ArrayList<Integer> puzzleState = new ArrayList<>(this.getGameState());
        swap(puzzleState, target, this.indexOfBlank);

        return new State(puzzleState, target, this.depth++, this, size);
    }

    private void swap(ArrayList<Integer> state, int firstIdx, int secondIdx){
        var temp = state.get(firstIdx);
        state.set(firstIdx, state.get(secondIdx));
        state.set(secondIdx, temp);
    }

    /**
     * Return ArrayList of state based on the blank moves
     * there are four possible movement up, down, left, right
     * so the max return states is 4 new states if it made 4 movements
     * @return
     */
    public ArrayList<State> expand() {
        children = new ArrayList<>();

        if (goLeft() != null)
            children.add(goLeft());

        if (goRight() != null)
            children.add(goRight());

        if (goUp() != null)
            children.add(goUp());

        if (goDown() != null)
            children.add(goDown());

        return children;
    }
}


