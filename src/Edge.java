public class Edge implements Comparable<Edge> {
    Node end;
    Node start;
    int weight;

    public Edge(Node start, int weight, Node end) {
        this.end = end;
        this.start = start;
        this.weight = weight;
    }

    public int compareTo(Edge other){
        return Integer.compare(this.weight,other.weight);
    }
}
