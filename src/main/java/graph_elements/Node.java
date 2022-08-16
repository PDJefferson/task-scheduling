package graph_elements;

import java.util.ArrayList;

public class Node {
    public int index;
    public String name;
    public ArrayList<Node> neighbors = new ArrayList<>();
    public boolean isVisited;
    public int inDegree = 0;

    public Node(String name, int index) {
        this.name = name;
        this.index = index;
    }

}