/*
*   College project involving Dijkstra's and Kruskal's algorithms
*   I'm sorry for the spaghetti code, I had to run this in less than 4 days
*   Author: Márcio Medeiros
*/

import java.util.*;

public class Graph {
    ArrayList<Node> nodes;                                                                              //List of Nodes in a Graph

    public Graph() {
        this.nodes = new ArrayList<Node>();
    }

    public Graph readTable(int[][] table) {                                                             //Read a graph from an adjacency matrix
        Graph graphHolder = new Graph();                                                                //Graph to be returned
        ArrayList<Node> nodeHolder = new ArrayList<Node>();                                             //Nodes that will be assigned to the graph

        for (int i = 0; i < table.length; i++) {                                                        //For each node in the graph
            nodeHolder.add(new Node(i));                                                                //Initialize the nodes with i as their id
        }

        for (int i = 0; i < table.length; i++) {                                                        //For each node i in the graph
            for (int j = 0; j < table.length; j++) {                                                    //For each node j in the graph
                if (table[i][j] != 0) {                                                                 //Check if there's a Edge between them
                    nodeHolder.get(i).neighbours.add(new Edge(nodeHolder.get(i), table[i][j],nodeHolder.get(j)));         //Assign the Edge with its end node and weight to its starting node
                }
            }
        }

        graphHolder.nodes = new ArrayList<Node>(nodeHolder);                                            //Assign nodes to the graph
        return graphHolder;
    }

    public int[][]writeTable(){                                                                         //Writes an Adjacency Table based on given graph
        int graphsize = this.nodes.size();                                                              //Gets number of nodes in the graph for convenience
        int[][] table = new int[graphsize][graphsize];                                                  //Creates an 0 filled square matrix graph sized
        for (int i = 0; i <graphsize; i++){                                                             //For each line
            for (int j = 0; j < graphsize; j++){                                                        //For each column
                for(Edge edges : this.getNodeById(i).neighbours){                                       //For each Edge in Node with 'i' id in the graph
                    if(edges.end == this.getNodeById(j)){                                               //If the node is directed to Node with 'j' id in the graph
                        table[i][j] = edges.weight;                                                     //Stores the weight of the Edge in the matrix in [i][j] position
                    }
                }
            }
        }
        return table;                                                                                   //Returns the table
    }

    public Node getNodeById(int id){                                                                    //Get a Node in the graph given an specific id
        for(Node node : nodes){                                                                         //From each Node in the graph
            if(node.id == id){                                                                          //If node's id is equal to given id
                return node;                                                                            //returns this node
            }
        }
        return null;                                                                                    //If there's no node with given id returns null
    }

    public int[][] dijkstra(int source){                                                   //Gets minimum distance from source to each other node in graph using dijkstra's algorithm
        int graphSize = this.nodes.size();                                                             //Gets number of nodes in the graph for convenience
        int dist_prev[][] = new int[graphSize][2];                                                      //Funky way to return both distance and previous node at the same time
        ArrayList<Node> unVisited = new ArrayList<Node>();                                              //Array of unvisited nodes that didn't have their minimum distance calculated yet

        for (int i = 0; i < graphSize; i++){                                                            //For each Node in the graph
            dist_prev[i][0] = Integer.MAX_VALUE / 2;                                                    //Makes the distance stupidly high
            dist_prev[i][1] = -1;                                                                       //Makes the previous Node be something impossible
            unVisited.add(this.nodes.get(i));                                                          //Make all of them unvisited
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

            for (int i = 0; i < this.nodes.get(minidx).neighbours.size(); i++){                        //For each neighbour in minidx
                Node aux = this.nodes.get(minidx).neighbours.get(i).end;                               //Did this in order to reduce typing
                if(unVisited.contains(aux)){                                                            //If the neighbour is yet to be visited
                    int maybeDist = this.nodes.get(minidx).neighbours.get(i).weight + dist_prev[minidx][0];    //calculates a possible smallest distance from source to the minidx neighbour
                    if(maybeDist < dist_prev[aux.id][0] ){                                              //If the distance is in fact smaller than the one we have
                        dist_prev[aux.id][0] = maybeDist;                                               //Change it
                        dist_prev[aux.id][1] = minidx;                                                  //And make minidx it's predecessor
                    }
                }

            }

        }

        return dist_prev;                                                                               //In the end it returns a int[a][b] were a is the id of the node you want to know the distance from the source, and b can be '0' for the distance valor or '1' for the predecessor id
    }

    public Graph kruskal(){                                                                             //Generated an minimum spanning tree from given graph using kruskal's algorithm

        int graphSize = this.nodes.size();                                                              //Gets graph size for convenience
        Graph tree = new Graph();                                                                       //Create a new graph were the tree will be stored
        ArrayList<Node> visited = new ArrayList<Node>();                                                //Create a lis to store the nodes that have already been visited
        ArrayList<Edge> orderedEdges = new ArrayList<Edge>();                                           //Create a list to store all the edges in the graph

        for(int i = 0; i < graphSize; i++){                                                             //For each node in the Graph
            for (int j = 0; j < this.nodes.get(i).neighbours.size(); j++){                             //For each edge in the node
                orderedEdges.add(this.nodes.get(i).neighbours.get(j));                                 //Add it to the list
            }
        }
        Collections.sort(orderedEdges);                                                                 //Sort the list based on edges weight

        while(!orderedEdges.isEmpty()) {                                                                //While there's still an edge in the list
            if(!visited.contains(orderedEdges.get(0).start)){                                           //If the starting node of the first edge is not on the graph yet
                tree.nodes.add(new Node(orderedEdges.get(0).start.id));                                 //Add it with the same id as the original Graph for convenience
                visited.add(orderedEdges.get(0).start);                                                 //Mark this node as already visited to avoid cycles
                if(!visited.contains(orderedEdges.get(0).end)) {                                        //If the ending node of the first edge is not on the graph yet
                    tree.nodes.add(new Node(orderedEdges.get(0).end.id));                               //Add it with the same id as the original Graph for convenience
                    visited.add(orderedEdges.get(0).end);                                               //Mark this node as already visited to avoid cycles
                }                                                                                       //Now the true spaghetti starts

                tree.getNodeById(orderedEdges.get(0).start.id).neighbours.add(new Edge(tree.getNodeById(orderedEdges.get(0).start.id),  //In the node which the id is the same as the id of the first edge starting point add an new edge which the starting point is the given node,
                        orderedEdges.get(0).weight,                                                                                     //The weight is the first edge's weight
                        tree.getNodeById(orderedEdges.get(0).end.id)));                                                                 //And the ending point is the first edge's ending point
                tree.getNodeById(orderedEdges.get(0).end.id).neighbours.add(new Edge(tree.getNodeById(orderedEdges.get(0).end.id),      //In the node which the id is the same as the id of the first edge ending point add an new edge which the starting point is the given node,
                        orderedEdges.get(0).weight,                                                                                     //The weight is the first edge's weight
                        tree.getNodeById(orderedEdges.get(0).start.id)));                                                               //And the ending point is the first edge's starting point
            }
            else if(!visited.contains(orderedEdges.get(0).end)){                                                                        //Same as above if, but it only gets here if the starting node is already in the graph and the ending node isn't
                tree.nodes.add(new Node(orderedEdges.get(0).end.id));
                visited.add(orderedEdges.get(0).end);
                tree.getNodeById(orderedEdges.get(0).start.id).neighbours.add(new Edge(tree.getNodeById(orderedEdges.get(0).start.id),
                        orderedEdges.get(0).weight,
                        tree.getNodeById(orderedEdges.get(0).end.id)));
                tree.getNodeById(orderedEdges.get(0).end.id).neighbours.add(new Edge(tree.getNodeById(orderedEdges.get(0).end.id),
                        orderedEdges.get(0).weight,
                        tree.getNodeById(orderedEdges.get(0).start.id)));
            }

            orderedEdges.remove(0);                                                                //Remove starting edge from the list as it has already been added or the edges closes a cycle
        }

        Collections.sort(tree.nodes);                                                                   //Sort the nodes based on their id's number
        return tree;                                                                                    //Return the minimum spanning graph
    }

    public void printAdjacencyTable(){                                                             //Prints the Adjacency Table
        int[][] treeTable = this.writeTable();                                                          //writes the adjacency table for the generated tree
        for (int i = 0; i < this.nodes.size(); i++) {
            for (int j = 0; j < this.nodes.size(); j++) {
                System.out.print(Integer.toString(treeTable[i][j]) + " ");                              //Print it
            }
            System.out.print("\n");
        }
    }




    public static void main(String[] args) {                                                            //Tests and presentation purpose only
        Graph graph = new Graph();
        int[][] table = new int[][]{                                                                    //Create an adjacency table here
            {0,4,0,0},
            {4,0,2,8},
            {0,2,0,1},
            {0,8,1,0}
        };
        graph = graph.readTable(table);                                                                 //Creates the graph based on given adjacency table


        Graph tree = graph.kruskal();                                                                   //Get minimum spanning tree using kruskal's algorithm and given graph
        tree.printAdjacencyTable();


        int source = 1;
        int destination = 3;
        int[][] dija = graph.dijkstra(source);                                                         //Get the array of distances from source to each node in the graph
        System.out.println("Distancia de "+source+" até "+destination+" : " + Integer.toString(dija[3][0]) + "\n" + "Antecessor da menor distancia: " + Integer.toString(dija[3][1]));
    }

}


