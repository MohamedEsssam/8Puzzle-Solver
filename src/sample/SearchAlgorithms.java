package sample;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.System.exit;

public class SearchAlgorithms {
    private DFS dfs = new DFS();
    private BFS bfs = new BFS();
    private A_STAR aStar = new A_STAR();

    public State search(String searchAlgorithm, State initialState,
                       List<Integer> goal, String heuristics){
        switch (searchAlgorithm){
            /**
             * Solve the game using Iterative Method
             */
            case "DFS":
                return dfs.dfs(initialState, goal);

            case "BFS":
                return bfs.bfs(initialState, goal);

            case "A_STAR":
                return aStar.aStar(initialState, goal, (heuristics.equals("manhattan") || heuristics.equals("euclidean"))? heuristics : "manhattan");


            case "A_STAR_2":
                aStar.aStar_2(initialState, goal, (heuristics.equals("manhattan") || heuristics.equals("euclidean"))? heuristics : "manhattan");
                break;
            /**
             * Solve the game using Recursion Method but
             * it goes to StackOverflowError at some cases
             */
            case "DFS_REC":
                dfs.dfsRec(initialState, goal);
                break;
            case "BFS_REC":
                bfs.bfsRec(initialState, goal);
                break;

            default:
                System.out.println("You Enter Wrong Approach");
        }
        return initialState;
    }
}
