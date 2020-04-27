import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class State {
    private ArrayList<Integer> gameState;
    ArrayList<State> children;
    private int indexOfBlank;

    @Override
    public String toString() {
        return "Current State =" + gameState ;
    }

    public State(ArrayList<Integer> State, int indexOfBlank) {
        this.gameState = State;
        this.indexOfBlank = indexOfBlank;
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

    /**
     * Return ArrayList of state based on the black move
     * there are four possible movement up, down, left, right
     * so the max return state is 4 new state if it made 4 movement
     * @return
     */
    public ArrayList<State> expand() {
        children = new ArrayList<>();

        if (goLeft(this) != null)
            children.add(goLeft(this));

        if (goRight(this) != null)
            children.add(goRight(this));

        if (goUp(this) != null)
            children.add(goUp(this));

        if (goDown(this) != null)
            children.add(goDown(this));

        return children;
    }

    private State goLeft(State currentState) {
        if (currentState.indexOfBlank % 3 == 0 &&
                currentState.indexOfBlank - 1 < 0)
            return null;

        int target = currentState.indexOfBlank - 1;
        ArrayList<Integer> puzzleState = new ArrayList<>(currentState.getGameState());
        swap(puzzleState, target, currentState.indexOfBlank);

        return new State(puzzleState, target);
    }

    private State goRight(State currentState) {
         final List<Integer> cantGoRight = Arrays.asList(2, 5, 8);

        if(cantGoRight.contains(currentState.indexOfBlank))
            return null;

        int target = currentState.indexOfBlank + 1;
        ArrayList<Integer> puzzleState = new ArrayList<>(currentState.getGameState());
        swap(puzzleState, target, currentState.indexOfBlank);

        return new State(puzzleState, target);
    }

    private State goUp(State currentState) {
        if(currentState.indexOfBlank <= 2)
            return null;

        int target = currentState.indexOfBlank - 3;
        ArrayList<Integer> puzzleState = new ArrayList<>(currentState.getGameState());
        swap(puzzleState, target, currentState.indexOfBlank);

        return new State(puzzleState, target);
    }

    private State goDown(State currentState) {
        if(currentState.indexOfBlank >= 6)
            return null;

        int target = currentState.indexOfBlank + 3;
        ArrayList<Integer> puzzleState = new ArrayList<>(currentState.getGameState());
        swap(puzzleState, target, currentState.indexOfBlank);

        return new State(puzzleState, target);
    }

    private void swap(ArrayList<Integer> state, int firstIdx, int secondIdx){
        var temp = state.get(firstIdx);
        state.set(firstIdx, state.get(secondIdx));
        state.set(secondIdx, temp);
    }
}
