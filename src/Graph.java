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
            nodeHolder.add(new Node(i));                                                                //Initialize the nodes with i as their id
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

    public int[][] dijkstra(Graph graph, int source){                                                   //Dijkstra algorithm were the inputs are the graph and the id of the source Node
        int graphSize = graph.nodes.size();                                                             //Gets number of nodes in the graph for convenience
        int dist_prev[][] = new int[graphSize][2];                                                      //Funky way to return both distance and previous node at the same time
        ArrayList<Node> unVisited = new ArrayList<Node>();                                              //Array of unvisited nodes that didn't have their minimum distance calculated yet

        for (int i = 0; i < graphSize; i++){                                                            //For each Node in the graph
            dist_prev[i][0] = Integer.MAX_VALUE / 2;                                                    //Makes the distance stupidly high
            dist_prev[i][1] = -1;                                                                       //Makes the previous Node be something impossible
            unVisited.add(graph.nodes.get(i));                                                          //Make all of them unvisited
        }

        dist_prev[source][0] = 0;                                                                       //The only distance we know for sure is from the source to itself, 0 to be exact

        while (!unVisited.isEmpty()){                                                                   //While there are still Nodes distances to be calculated

            int min = 0;                                                                                //The next 8 lines are a spaghetti I used to avoid making a method that returns the smallest distance, I probably should correct it someday
            for(int i = 0; i < unVisited.size(); i++){                                                  //For each Node yet to be visited
                if(dist_prev[unVisited.get(i).id][0] <= dist_prev[unVisited.get(min).id][0]){           //Verify if this Node has the smallest distance
                    min = i;                                                                            //Of them all
                }
            }
            int minidx = unVisited.get(min).id;                                                         //Gets the real graph id from the Node we found to be the one in the unvisited to possess the smallest distance
            unVisited.remove(min);                                                                      //Remove this guys (let's call him minidx, for convenience) from the unvisited because we already found it's smallest distance

            for (int i = 0; i < graph.nodes.get(minidx).neighbours.size(); i++){                        //For each neighbour in minidx
                Node aux = graph.nodes.get(minidx).neighbours.get(i).end;                               //Did this in order to reduce typing
                if(unVisited.contains(aux)){                                                            //If the neighbour is yet to be visited
                    int maybeDist = graph.nodes.get(minidx).neighbours.get(i).weight + dist_prev[minidx][0];    //calculates a possible smallest distance from source to the minidx neighbour
                    if(maybeDist < dist_prev[aux.id][0] ){                                              //If the distance is in fact smaller than the one we have
                        dist_prev[aux.id][0] = maybeDist;                                               //Change it
                        dist_prev[aux.id][1] = minidx;                                                  //And make minidx it's predecessor
                    }
                }

            }

        }

        return dist_prev;                                                                               //In the end it returns a int[a][b] were a is the id of the node you want to know the distance from the source, and b can be '0' for the distance valor or '1' for the predecessor id
    }




    public static void main(String[] args) {
        Graph graph = new Graph();
        int n = 4;
        int[][] table = new int[][]{
            {0,4,0,0},
            {4,0,2,8},
            {0,2,0,1},
            {0,8,1,0}
        };

        /*for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                table[i][j] = new Scanner(System.in).nextInt();
            }
        }*/

        graph = graph.readTable(table);
        int[][] dija = graph.dijkstra(graph,0);
        System.out.println("Distancia de 0 até 3: " + Integer.toString(dija[3][0]) + "\n" + "Antecessor da menor distancia: " + Integer.toString(dija[3][1]));
    }

}


