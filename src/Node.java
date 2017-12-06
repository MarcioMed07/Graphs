import java.util.ArrayList;

public class Node {
    String name;
    ArrayList<Edge> neighbours;

    public Node(String name,ArrayList<Edge> neighbours) {
        this.neighbours = neighbours;
        this.name = name;
    }

    public Node(String name) {
        this.neighbours = new ArrayList<Edge>();
        this.name = name;
    }
}
