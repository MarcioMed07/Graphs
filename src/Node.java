import java.util.ArrayList;

public class Node {
    int id;
    ArrayList<Edge> neighbours;

    public Node(int id,ArrayList<Edge> neighbours) {
        this.neighbours = neighbours;
        this.id = id;
    }

    public Node(int id) {
        this.neighbours = new ArrayList<Edge>();
        this.id = id;
    }
}
