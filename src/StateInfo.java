import java.util.concurrent.TimeUnit;

public class StateInfo {
    private int pathCost;
    private int nodesExpanded;
    private int searchDepth;
    private long startTime;
    private long endTime;

    @Override
    public String toString() {
        return  "****************** GAME SOLVED ******************"+
                "\nCost Of Path = " + pathCost +
                "\nNodes Expanded = " + nodesExpanded +
                "\nSearch Depth = " + searchDepth +
                "\nRunning Time = "+ TimeUnit.SECONDS.convert((this.endTime - this.startTime), TimeUnit.NANOSECONDS)+" Sec";
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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
