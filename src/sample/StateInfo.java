package sample;

import java.util.concurrent.TimeUnit;

public class StateInfo {
    private int pathCost;
    private int nodesExpanded;
    private int searchDepth;
    private int maxSearchDepth;
    private double startTime;
    private double endTime;
    private String  runningTime;

    @Override
    public String toString() {
        return  "****************** GAME SOLVED ******************"+
                "\nNodes Expanded = " + nodesExpanded +
                "\nCost Of Path = " + pathCost +
                "\nSearch Depth = " + searchDepth +
                "\nMax Search Depth = " + maxSearchDepth +
                "\nRunning Time = "+ String.format("%.3f",(this.endTime - this.startTime)/1_000_000_000)+" Sec";
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public int getNodesExpanded() {
        return nodesExpanded;
    }

    public void setNodesExpanded(int nodesExpanded) {
        this.nodesExpanded = nodesExpanded;
    }

    public int getSearchDepth() {
        return searchDepth;
    }

    public void setSearchDepth(int searchDepth) {
        this.searchDepth = searchDepth;
    }

    public int getMaxSearchDepth() {
        return maxSearchDepth;
    }

    public void setMaxSearchDepth(int maxSearchDepth) {
        this.maxSearchDepth = maxSearchDepth;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getRunningTime() {
        return String.format("%.3f",(this.endTime - this.startTime)/1_000_000_000)+" Sec";
    }

}
