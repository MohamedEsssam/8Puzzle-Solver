import java.util.concurrent.TimeUnit;

public class StateInfo {
    private int pathCost;
    private int nodesExpanded;
    private int searchDepth;
    private double startTime;
    private double endTime;

    @Override
    public String toString() {
        return  "****************** GAME SOLVED ******************"+
                "\nCost Of Path = " + pathCost +
                "\nNodes Expanded = " + nodesExpanded +
                "\nSearch Depth = " + searchDepth +
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
}
