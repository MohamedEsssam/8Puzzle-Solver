public class Heuristics {
    public static int manhattanDistance(State currentState) {
        int cost = 0, index = 0;
        int xValue, yValue;
        for (int i=0; i < currentState.getGameState().size(); i++)
            for (int j = 0; j < currentState.getGameState().size(); j++) {
                xValue = currentState.getGameState().get(index) / currentState.getGameState().size();
                yValue = currentState.getGameState().get(index) % currentState.getGameState().size();
                cost += Math.abs(i - xValue) + Math.abs(j - yValue);
                index++;
            }

        return cost;
    }

    public static int euclideanDistance(State currentState) {
        int cost = 0, index = 0;
        int xValue, yValue;
        for (int i=0; i < currentState.getGameState().size(); i++)
            for (int j = 0; j < currentState.getGameState().size(); j++) {
                xValue = currentState.getGameState().get(index) / currentState.getGameState().size();
                yValue = currentState.getGameState().get(index) % currentState.getGameState().size();
                cost += Math.sqrt(Math.pow(i - xValue, 2) + Math.pow(j - yValue, 2));
                index++;
            }

        return cost;
    }
}
