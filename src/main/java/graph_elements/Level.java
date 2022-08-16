package graph_elements;

public class Level {
    public int inDegrees;
    public String name;
    public int index;
    public int job = 0;
    public Level(String name, int inDegree, int index) {
        this.name = name;
        this.inDegrees = inDegree;
        this.index = index;
    }
}
