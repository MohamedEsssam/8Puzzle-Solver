const PriorityQueue = require("js-priority-queue");

export default class Puzzle {
  constructor(
    boardSize = 3,
    arrangement,
    parentPuzzle = undefined,
    target,
    depth = 0
  ) {
    this.boardSize = boardSize;
    this.arrangement = arrangement;
    this.parentPuzzle = parentPuzzle;
    this.target = target;
    this.depth = depth;
  }

  getIndex(num) {
    return [this.arrangement.indexOf(num), this.target.indexOf(num)];
  }

  getRow(num) {
    const n = Number(this.boardSize);
    const indexOfZero = this.getIndex(num);

    return [
      Number.parseInt(indexOfZero[0] / n),
      Number.parseInt(indexOfZero[1] / n),
    ];
  }
  getCol(num) {
    const n = Number(this.boardSize);
    const indexOfZero = this.getIndex(num);

    return [
      Number.parseInt(indexOfZero[0] % n),
      Number.parseInt(indexOfZero[1] % n),
    ];
  }

  expand() {
    let children = [];

    const moveLeft = this.moveLeft();
    if (moveLeft) children.unshift(moveLeft);

    const moveRight = this.moveRight();
    if (moveRight) children.unshift(moveRight);

    const moveUp = this.moveUp();
    if (moveUp) children.unshift(moveUp);

    const moveDown = this.moveDown();
    if (moveDown) children.unshift(moveDown);

    return children;
  }

  swap(arrangement, idx1, idx2) {
    let temp = arrangement[idx1];
    arrangement[idx1] = arrangement[idx2];
    arrangement[idx2] = temp;
  }

  canMoveUp() {
    return this.getRow("0")[0] !== 0;
  }

  moveUp() {
    if (!this.canMoveUp()) return;

    let target = this.getIndex("0")[0] - this.boardSize;
    let arrangementClone = [...this.arrangement];
    this.swap(arrangementClone, target, this.getIndex("0")[0]);

    return new Puzzle(
      this.boardSize,
      arrangementClone,
      this,
      this.target,
      this.depth + 1
    );
  }

  canMoveDown() {
    return this.getRow("0")[0] !== this.boardSize - 1;
  }

  moveDown() {
    if (!this.canMoveDown()) return;

    let target = this.getIndex("0")[0] + this.boardSize;
    let arrangementClone = [...this.arrangement];
    this.swap(arrangementClone, target, this.getIndex("0")[0]);

    return new Puzzle(
      this.boardSize,
      arrangementClone,
      this,
      this.target,
      this.depth + 1
    );
  }

  canMoveRight() {
    return this.getCol("0")[0] !== this.boardSize - 1;
  }

  moveRight() {
    if (!this.canMoveRight()) return;

    let target = this.getIndex("0")[0] + 1;
    let arrangementClone = [...this.arrangement];
    this.swap(arrangementClone, target, this.getIndex("0")[0]);

    return new Puzzle(
      this.boardSize,
      arrangementClone,
      this,
      this.target,
      this.depth + 1
    );
  }

  canMoveLeft() {
    return this.getCol("0")[0] !== 0;
  }

  moveLeft() {
    if (!this.canMoveLeft()) return;

    let target = this.getIndex("0")[0] - 1;
    let arrangementClone = [...this.arrangement];
    this.swap(arrangementClone, target, this.getIndex("0")[0]);

    return new Puzzle(
      this.boardSize,
      arrangementClone,
      this,
      this.target,
      this.depth + 1
    );
  }

  print() {
    console.log("-".repeat(this.boardSize * 2 + 5));

    for (let i = 0; i < this.boardSize; i++) {
      let row = "";
      row += "|";
      for (let j = 0; j < this.boardSize; j++) {
        row += " ";
        row += this.arrangement[i * this.boardSize + j];
        row += " ";
      }
      row += "|";
      console.log(row);
    }

    console.log("-".repeat(this.boardSize * 2 + 5));
  }

  hash() {
    return this.arrangement.toString();
  }

  dfs() {
    let stack = [];
    let visited = {};

    stack.push(this);
    while (stack.length !== 0) {
      let currentPuzzle = stack.pop();
      console.log("Expanding");
      currentPuzzle.print();
      visited[currentPuzzle.hash()] = currentPuzzle;

      if (currentPuzzle.isGoalState()) {
        // Reached goal state

        currentPuzzle.expanded = Object.values(visited).map(
          (v) => v.arrangement
        );

        return currentPuzzle;
      }

      const children = currentPuzzle.expand();
      children.forEach((child) => {
        if (!visited[child.hash()]) stack.push(child);
      });
    }
  }

  bfs() {
    let queue = [];
    let visited = {};

    queue.unshift(this);
    while (queue.length !== 0) {
      let currentPuzzle = queue.pop();
      console.log("Expanding");
      currentPuzzle.print();
      visited[currentPuzzle.hash()] = currentPuzzle;

      if (currentPuzzle.isGoalState()) {
        // Reached goal state

        currentPuzzle.expanded = Object.values(visited).map(
          (v) => v.arrangement
        );

        return currentPuzzle;
      }

      const children = currentPuzzle.expand();
      children.forEach((child) => {
        if (!visited[child.hash()]) queue.unshift(child);
      });
    }
  }

  ast(heuristic) {
    const pq = new PriorityQueue({
      comparator: function (p1, p2) {
        const cost1 = p1.depth + p1.heuristicCost(heuristic);
        const cost2 = p2.depth + p2.heuristicCost(heuristic);

        return cost1 - cost2;
      },
    });
    let visited = {};

    pq.queue(this);
    while (pq.length !== 0) {
      const currentPuzzle = pq.dequeue();
      visited[currentPuzzle.hash()] = currentPuzzle;

      console.log("Expanding");
      currentPuzzle.print();

      if (currentPuzzle.isGoalState()) {
        // Reached goal state

        currentPuzzle.expanded = Object.values(visited).map(
          (v) => v.arrangement
        );

        return currentPuzzle;
      }

      const children = currentPuzzle.expand();
      children.forEach((child) => {
        if (!visited[child.hash()]) pq.queue(child);
      });
    }
  }

  heuristicCost(heuristic) {
    switch (heuristic) {
      case "manhattan":
        return this.manhattanDist();

      case "euclidean":
        return this.euclideanDist();

      default:
        break;
    }
  }

  manhattanDist() {
    let cost = 0;
    for (let i = 0; i < this.arrangement.length; i++) {
      const currentCellX = this.getCol(this.arrangement[i])[0];
      const goalX = this.getCol(this.arrangement[i])[1];

      const currentCellY = this.getRow(this.arrangement[i])[0];
      const goalY = this.getRow(this.arrangement[i])[1];

      cost += Math.abs(currentCellX - goalX) + Math.abs(currentCellY - goalY);
    }
    return cost;
  }

  euclideanDist() {
    let cost = 0;
    for (let i = 0; i < this.arrangement.length; i++) {
      const currentCellX = this.getCol(this.arrangement[i])[0];
      const goalX = this.getCol(this.arrangement[i])[1];

      const currentCellY = this.getRow(this.arrangement[i])[0];
      const goalY = this.getRow(this.arrangement[i])[1];

      cost += Math.sqrt(
        Math.pow(currentCellX - goalX, 2) + Math.pow(currentCellY - goalY, 2)
      );
    }
    return cost;
  }

  isGoalState() {
    for (let i = 0; i < this.arrangement.length; i++) {
      if (this.arrangement[i] !== this.target[i]) return false;
    }

    return this.arrangement.length === this.target.length;
  }

  getPath() {
    let path = [];

    let currentPuzzle = this;
    while (currentPuzzle) {
      path.push(currentPuzzle.arrangement);
      currentPuzzle = currentPuzzle.parentPuzzle;
    }

    return path;
  }

  getExpanded() {
    return this.expanded;
  }

  getTotalCost() {
    return this.depth;
  }

  getDepth() {
    return this.depth;
  }

  getRunningTime() {
    return this.runningTime;
  }
}
