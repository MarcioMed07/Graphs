import java.util.ArrayList;

public class Node implements Comparable<Node> {
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

    public int compareTo(Node other){
        return Integer.compare(this.id, other.id);
    }


}
