package sample;

public class Heuristics {
    private final int rowLen = 3, colLen = 3;

    public  int manhattanDistance(State currentState) {
        int cost = 0, index = 0;
        int xValue, yValue;
        for (int i=0; i < rowLen; i++)
            for (int j = 0; j < colLen; j++) {
                xValue = currentState.getGameState().get(index) / rowLen;
                yValue = currentState.getGameState().get(index) % colLen;
                cost += Math.abs(i - xValue) + Math.abs(j - yValue);
                index++;
            }

        return cost;
    }

    public  int euclideanDistance(State currentState) {
        int cost = 0, index = 0;
        int xValue, yValue;
        for (int i=0; i < rowLen; i++)
            for (int j = 0; j < colLen; j++) {
                xValue = currentState.getGameState().get(index) / rowLen;
                yValue = currentState.getGameState().get(index) % colLen;
                cost += Math.sqrt(Math.pow(i - xValue, 2) + Math.pow(j - yValue, 2));
                index++;
            }

        return cost;
    }
}
