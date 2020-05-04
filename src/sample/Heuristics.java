package sample;

import java.util.ArrayList;

public class Heuristics {
    
    public  int manhattanDistance(ArrayList<Integer> puzzle, int size) {
        int cost = 0, index = 0;
        int xValue, yValue;
        for (int i=0; i < size; i++)
            for (int j = 0; j < size; j++) {
                xValue = puzzle.get(index) / size;
                yValue = puzzle.get(index) % size;
                cost += Math.abs(i - xValue) + Math.abs(j - yValue);
                index++;
            }

        return cost;
    }

    public  int euclideanDistance(ArrayList<Integer> puzzle, int size) {
        int cost = 0, index = 0;
        int xValue, yValue;
        for (int i=0; i < size; i++)
            for (int j = 0; j < size; j++) {
                xValue = puzzle.get(index) / size;
                yValue = puzzle.get(index) % size;
                cost += Math.sqrt(Math.pow(i - xValue, 2) + Math.pow(j - yValue, 2));
                index++;
            }

        return cost;
    }
}
