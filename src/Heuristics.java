public class Heuristics {
    private final int size = 3; 
    public  int manhattanDistance(State currentState) {
        int cost = 0, index = 0;
        int xValue, yValue;
        for (int i=0; i < size; i++)
            for (int j = 0; j < size; j++) {
                xValue = currentState.getGameState().get(index) / size;
                yValue = currentState.getGameState().get(index) % size;
                cost += Math.abs(i - xValue) + Math.abs(j - yValue);
                index++;
            }

        return cost;
    }

    public  int euclideanDistance(State currentState) {
        int cost = 0, index = 0;
        int xValue, yValue;
        for (int i=0; i < size; i++)
            for (int j = 0; j < size; j++) {
                xValue = currentState.getGameState().get(index) / size;
                yValue = currentState.getGameState().get(index) % size;
                cost += Math.sqrt(Math.pow(i - xValue, 2) + Math.pow(j - yValue, 2));
                index++;
            }

        return cost;
    }
}
