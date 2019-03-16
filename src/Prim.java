import java.util.*;

public class Prim {

    public static int numberOfVertices = 8;

    static class Edge {
        int fromVertice;
        int toVertice;
        int weight;
        Edge(int from, int to, int weight){
            fromVertice = from;
            toVertice = to;
            this.weight = weight;

        }
    }
    /* Building the MST using a Priority Queue */
    public static ArrayList<Edge> Prims(ArrayList<ArrayList<Edge>> graph){
        ArrayList<Edge> MST = new ArrayList<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                if (o1.weight < o2.weight) return -1;
                else if (o1.weight > o2.weight) return 1;
                else return 0;
            }
        });
        for(Edge e : graph.get(0)){
            pq.add(e);
        }
        boolean[] visited = new boolean[graph.size()];
        visited[0] = true;
        while(!pq.isEmpty()){
            Edge e = pq.peek();
            pq.poll();
            //Each time a circle is closed the condition will not exist
            if((!visited[e.fromVertice] && !visited[e.toVertice]) || (visited[e.fromVertice] && !visited[e.toVertice])
                    || (!visited[e.fromVertice] && visited[e.toVertice])){
                visited[e.fromVertice] = true;
                for(Edge edge : graph.get(e.toVertice)){
                    if(!visited[edge.toVertice]){
                        pq.add(edge);
                    }
                }
                visited[e.toVertice] = true;
                MST.add(e);
            }
        }
        return MST;
    }

    /* Creating the edges of the graph so each vertex will be doubly connected, because we're dealing with
    undirected graph
    * */
    public static ArrayList<ArrayList<Edge>> createGraph(Edge[] edges){
        ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
        for(int i = 0; i < numberOfVertices; ++i){
            graph.add(new ArrayList<>());
        }
        for(Edge e : edges){
            Edge other = new Edge(e.toVertice, e.fromVertice, e.weight);
            graph.get(e.fromVertice).add(e);
            graph.get(e.toVertice).add(other);
        }
        return graph;
    }

    public static void main(String[] args){
        Edge[] edges = new Edge[13];

        //Creating the edges of the graph
        edges[0] = new Edge(0, 4, 4);
        edges[1] = new Edge(0, 1, 5);
        edges[2] = new Edge(1, 2, 6);
        edges[3] = new Edge(1, 5, 14);
        edges[4] = new Edge(1, 6, 10);
        edges[5] = new Edge(2, 6, 9);
        edges[6] = new Edge(2, 7, 17);
        edges[7] = new Edge(2, 3, 1);
        edges[8] = new Edge(4, 1, 3);
        edges[9] = new Edge(4, 5, 11);
        edges[10] = new Edge(5, 6, 7);
        edges[11] = new Edge(6, 7, 15);
        edges[12] = new Edge(3, 7, 12);

        ArrayList<ArrayList<Edge>> graph = createGraph(edges);
        ArrayList<Edge> MST = Prims(graph);

        System.out.println("MST:");
        for(Edge edge : MST){
            System.out.println((char)(edge.fromVertice + 65)+ " - " + (char)(edge.toVertice + 65) + " : " + edge.weight);
        }
    }
}