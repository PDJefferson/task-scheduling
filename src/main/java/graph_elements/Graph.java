package graph_elements;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Graph {
    public ArrayList<Node> nodeList;
    public ArrayList<Node> cycle = new ArrayList<>();

    public Graph(ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public void addNode(String name) {
        int currentNodeIndex = nodeList.size();
        nodeList.add(new Node(name, currentNodeIndex));
    }

    public boolean deleteNode(String name) {
        int nodeIndex = getNodeIndex(name);
        if (nodeIndex != -1) {
            Node currentNode = nodeList.get(nodeIndex);
            removeEdgesFromNode(currentNode);
            nodeList.remove(currentNode);
            updateIndexesOfNodes();
            return true;
        }
        return false;
    }

    public boolean addEdge(String firstName, String secondName) {
        int nodeFirstIndex = getNodeIndex(firstName), nodeSecondIndex = getNodeIndex(secondName);
        if (nodeFirstIndex != -1 && nodeSecondIndex != -1) {
            Node first = nodeList.get(nodeFirstIndex), second = nodeList.get(nodeSecondIndex);
            first.neighbors.add(second);
            second.inDegree++;
            return true;
        }
        return false;
    }

    private int getNodeIndex(String name) {
        for (int i = 0; i < nodeList.size(); i++) {
            if (name.equals(nodeList.get(i).name)) {
                return i;
            }
        }
        return -1;
    }

    //uses dfs to remove the edges of those nodes tha have the currentNeighbor
    private void removeEdgesFromNode(@NotNull Node currentNode) {
        Stack<Node> stack = new Stack<>();
        for (Node neighborsOfDeletedNode : currentNode.neighbors) {
            neighborsOfDeletedNode.inDegree--;
        }

        stack.push(nodeList.get(0));
        nodeList.get(0).isVisited = true;
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            Iterator<Node> neighbors = node.neighbors.listIterator();
            while (neighbors.hasNext() ) {
                Node currentNeighbor = neighbors.next();
                if (!currentNeighbor.isVisited) {
                    if (node.neighbors.contains(currentNode)) {
                        neighbors.remove();
                        node.neighbors.remove(currentNode);
                    }
                    currentNeighbor.isVisited = true;
                    stack.push(currentNeighbor);
                }
            }
        }
        restartVisited();
    }

    private void updateIndexesOfNodes() {
        for (int i = 0; i < nodeList.size(); i++) {
            nodeList.get(i).index = i;
        }
    }

    //dfs but from end to start
    private Stack<Node> topologicalSort() {
        Stack<Node> stack = new Stack<>();
        for (Node node : nodeList) {
            if (!node.isVisited) {
                topologicalSort(node, stack);
            }
        }
        restartVisited();
        return stack;
    }

    private void topologicalSort(@NotNull Node node, Stack<Node> stack) {
        for (Node currentNeighbor : node.neighbors) {
            if (!currentNeighbor.isVisited) {
                topologicalSort(currentNeighbor, stack);
            }
        }
        stack.push(node);
        node.isVisited = true;
    }

    private void restartVisited() {
        nodeList.forEach(node -> node.isVisited = false);
    }

    /*Detect a cycle
     *   @param whiteSet to store the nodes that have not been visited yet
     *   @param graySet  to store the nodes that are currently been visited
     *   @param blackSet to store the nodes that have been fully visited meaning its neighbors have been visited as well
     *
     *   How it works:
     *       store the all the nodes on the whiteSet
     *       while there are nodes in the whiteSet call the helper dfs method to detect a cycle
     *           if the method/function returns ture there is a cycle
     *       The functions works as following:
     *           First move the vertex of the currentNode to the graySet as it has just been visited
     *           then iterate through the neighbors of the current node
     *               There are three cases:
     *                   if the blackSet contains the current neighbor we know that we have already visited the hierarchy
     *                   of all its neighbors, hence just continue and move on to the next neighbor.
     *                   if the graySet contains that current neighbor that means that we found a cycle since we have
     *                   already visited by another node in that hierarchy creating a cycle
     *                   Finally, recursively call the function if there is still nodes to be visited.
     *
     * */
    private boolean detectCycle() {
        Set<Node> blackSet = new HashSet<>();
        Set<Node> graySet = new HashSet<>();
        this.cycle = new ArrayList<>();

        //add every node to the whiteSet as it will store the nodes
        //that have not been visited yet
        Set<Node> whiteSet = new HashSet<>(nodeList);

        while (!whiteSet.isEmpty()) {
            Node currentNode = whiteSet.iterator().next();
            if (dfsToDetectCycle(currentNode, whiteSet, graySet, blackSet)) {
                return true;
            }
        }
        return false;
    }

    private boolean dfsToDetectCycle(Node currentNode, Set<Node> whiteSet, Set<Node> graySet, Set<Node> blackSet) {

        //move the currentNode to the graySet as we know that we are just visiting it
        moveVertex(currentNode, whiteSet, graySet);
        cycle.add(currentNode);
        for (Node currentNeighbor : currentNode.neighbors) {
            //if the blackSet contains the current neighbor we know that
            //we have visits its full neighbors, so we just move on to the
            //next neighbor
            if (blackSet.contains(currentNeighbor)) {
                continue;
            }
            //if the graySet contains the current neighbor
            //that means we found a cycle since we another node from
            //the hierarchy already visited it, and we are just going
            // back to that node
            if (graySet.contains(currentNeighbor)) {
                cycle.add(currentNeighbor);
                return true;
            }

            //iterate through the neighbors of the currentNeighbor until we reach the deepest node
            //and found a cycle
            if (dfsToDetectCycle(currentNeighbor, whiteSet, graySet, blackSet)) {
                return true;
            }
        }
        //add the node that has been fully visited to the black set
        moveVertex(currentNode, graySet, blackSet);
        //if we have visited every node in the graph we know that there is not a cycle
        return false;
    }

    private void moveVertex(Node currentNode, @NotNull Set<Node> source, @NotNull Set<Node> destination) {
        destination.add(currentNode);
        cycle.remove(currentNode);
        source.remove(currentNode);
    }

    private void orderByLevels(@NotNull ArrayList<Level> nodes) {
        nodes.sort(Comparator.comparingInt(task -> task.job));
    }

    private @NotNull ArrayList<Level> getOrderOfNodes() {
        ArrayList<Level> levels = new ArrayList<>();
        for (Node node : nodeList) {
            levels.add(new Level(node.name, node.inDegree, node.index));
        }
        // Find the topo sort order
        // using the indegree approach
        // Queue to store the
        // nodes while processing
        Queue<Node> queue = new LinkedList<>();

        // Pushing all the vertex in the
        // queue whose in-degree is 0
        for (Level level : levels) {
            if (level.inDegrees == 0) {
                level.job = 1;
                queue.add(nodeList.get(level.index));
            }
        }
        // Iterate until queue is empty
        while (!queue.isEmpty()) {

            Node currentNode = queue.poll();
            for (Node currentNeighbor : currentNode.neighbors) {
                // Decrease in-degree of
                // the current node
                levels.get(currentNeighbor.index).inDegrees--;

                // Push its adjacent elements
                if (levels.get(currentNeighbor.index).inDegrees == 0) {
                    levels.get(currentNeighbor.index).job = 1 + levels.get(currentNode.index).job;
                    queue.add(currentNeighbor);
                }
            }
        }
        return levels;
    }

    private void appendTopologicalSortResults(StringBuilder s) {
        Stack<Node> stack = topologicalSort();
        while (!stack.isEmpty()) {
            s.append(stack.pop().name).append(" ");
        }
        s.append("\n");
    }

    private void appendCycleFoundResults(StringBuilder s) {
        this.cycle
                .stream()
                .iterator()
                .forEachRemaining(node -> s.append(node.name).append(" "));
    }

    private void resetCycleList() {
        this.cycle = null;
    }

    private int maxInDegree(@NotNull ArrayList<Level> levels) {
        return levels
                .stream()
                .max(Comparator.comparingInt(task -> task.job))
                .get()
                .job;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(nodeList.isEmpty()) return sb.append("The graph is empty\n").toString();

        ArrayList<Level> orderOfNodes = getOrderOfNodes();
        orderByLevels(orderOfNodes);

        if (!detectCycle()) {
            sb.append("A valid ordering of tasks is as follows:\n");
            appendTopologicalSortResults(sb);
            sb.append("There are other valid ordering of tasks.\n"
                            + "The minimum number of time steps is ")
                    .append(maxInDegree(orderOfNodes))
                    .append(": \n");
            int prev = orderOfNodes.get(0).job;
            sb.append(prev).append(": ");
            for (Level orderOfNode : orderOfNodes) {
                if (prev == orderOfNode.job) {
                    sb.append(orderOfNode.name).append(" ");
                } else {
                    prev++;
                    sb.append("\n").append(prev).append(": ").append(orderOfNode.name).append(" ");
                }
            }
        } else {
            sb.append("There is NO valid ordering of task.\n" + "There is a cycle: ");
            appendCycleFoundResults(sb);
        }
        resetCycleList();
        return sb.append("\n").toString();
    }

    @Deprecated
    public void printGraph() {
        StringBuilder s = new StringBuilder();
        for (Node node : nodeList) {
            s.append(node.name).append(": ");
            for (int j = 0; j < node.neighbors.size(); j++) {
                if (j == node.neighbors.size() - 1) {
                    s.append((node.neighbors.get(j).name));
                } else {
                    s.append(node.neighbors.get(j).name)
                            .append(" -> ");
                }
            }
            s.append("\n");
        }
        System.out.println(s);
    }
}