import java.util.Arrays;

public class DetectArbitrage {
    public static boolean detectArbitrage(double[][] graph, int s) {
        // Bellman-Ford's Algorithm
        int N = graph.length; // The Number Of Vertices In The Graph
        double[] d = new double[N]; // The Distance Of An Edge
        int[] p = new int[N]; // The Predecessor Of A Vertex
        // Initialize The Distance To All Vertices As Infinity
        Arrays.fill(d, Double.POSITIVE_INFINITY);
        Arrays.fill(p, -1);
        // Except Starting Vertex Which Is 0
        d[s] = 0.0;

        // Relax Edges Up To The Number Of Vertices Minus 1 (N - 1) Times
        for (int i = 0; i < N - 1; i++) {
            // For Each Edge (u, v) In E
            for (int u = 0; u < N; u++) {
                for (int v = 0; v < N; v++) {
                    // Checks If The Path From The Starting Vertex [v] Through
                    // Vertex [u] Is Shorter Than The Known Shortest Path To [v]
                    if (d[u] + graph[u][v] < d[v]) {
                        d[v] = d[u] + graph[u][v];
                        p[v] = u;
                    }
                }
            }
        }

        // Check For Negative Weight Cycles
        for (int u = 0; u < N; u++) {
            for (int v = 0; v < N; v++) {
                if (d[u] + graph[u][v] < d[v]) {
                    // If There Is A Negative Weight It Will Print The Arbitrage
                    System.out.println("Arbitrage Opportunity Detected:");
                    printArbSequence(p, s, d[u]);
                    return true;
                }
            }
        }
        return false;
    }

    public static void printArbSequence(int[] p, int s, double d) {
        String[] labels = CurrencyLabels.getLabels(); // Gets The Static Labels From The CurrencyLabels Object
        boolean[] visited = new boolean[p.length]; // Tracks vertices have been visited
        int current = s; // Initialises The Current Node The Source Node

        // Traverse The Sequence Of Nodes
        while (!visited[current]) {
            visited[current] = true; // Marks The Current Node As Visited
            current = p[current]; // Moves To The Next Node
        }

        // Prints The Sequence Of The Arbitrage
        System.out.println("Sequence:");
        int cycle = current; // Start Of The Cycle
        System.out.print(labels[cycle]); // Prints Label Of Starting Node

        current = p[cycle]; // Moves To Next Node
        while (current != cycle) {
            System.out.print("->" + labels[current]); // Print Label Of Current Node
            current = p[current]; // Move To Next Node
        }
        System.out.println("->" + labels[cycle]); // Print Label OF Node At End Of Cycle

        // Print Cycle and Profit/Return
        System.out.println((cycle + 1) + " Cycle Profit/Return: " + "x" + (d * -1 + 1));
    }
}
