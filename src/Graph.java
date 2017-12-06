import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Graph {
    ArrayList<Node> nodes;                                                                              //List of Nodes in a Graph

    public Graph readTable(int[][] table) {                                                             //Read a graph from an adjacency matrix
        Graph graphHolder = new Graph();                                                                //Graph to be returned
        ArrayList<Node> nodeHolder = new ArrayList<Node>();                                             //Nodes that will be assigned to the graph

        for (int i = 0; i < table.length; i++) {                                                        //For each node in the graph
            nodeHolder.add(new Node(Integer.toString(i)));                                              //Initialize the nodes with i as their name
        }

        for (int i = 0; i < table.length; i++) {                                                        //For each node i in the graph
            for (int j = 0; j < table.length; j++) {                                                    //For each node j in the graph
                if (table[i][j] != 0) {                                                                 //Check if there's a Edge between them
                    nodeHolder.get(i).neighbours.add(new Edge(nodeHolder.get(j), table[i][j]));         //Assign the Edge with its end node and weight to its starting node
                }
            }
        }

        graphHolder.nodes = new ArrayList<Node>(nodeHolder);                                            //Assign nodes to the graph
        return graphHolder;
    }

    public void dijkstra(Graph graph, Node source){

    }


    public static void main(String[] args) {
        Graph graph = new Graph();
        int n = 3;
        int[][] table = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                table[i][j] = new Scanner(System.in).nextInt();
            }
        }

        graph = graph.readTable(table);
        System.out.println(Integer.toString(graph.nodes.get(2).neighbours.get(0).weight));
    }

}


