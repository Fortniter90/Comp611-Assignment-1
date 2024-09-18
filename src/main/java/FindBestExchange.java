import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.exit;

public class FindBestExchange {
    public static void arbitrageFound(boolean found, boolean checkedArb, double[][] graph) {
        Scanner scanner = new Scanner(System.in);

        // Checks If Arbitrage Has Been Found
        if (!found) {
            System.out.println("No Arbitrage Detected");
            // Sets Checked Arbitrage To False
            checkedArb = false;
        }

        // Checks If Arbitrage Was Checked For
        if (!checkedArb) {
            System.out.println("Would You Like To Find The Best Exchange Rate For Certain Currencies (y|n)");
            // Checks If User Wants To Find The Best Exchange Rates
            if (scanner.nextLine().equals("y") || scanner.nextLine().equals("Y")) {
                for (int i = 0; i < graph.length; i++) {
                    // Prints All Currencies That Were Used In The Graph Using The getLabels Method
                    // (i + 1) So That It Makes Sense To The User As Currency 0 Becomes Currency 1
                    System.out.println((i + 1) + ". " + CurrencyLabels.getLabels()[i]);
                }
                try {
                    System.out.println("What currency would you like to exchange from:");
                    // User Picks The Currency They Want To Convert From
                    int start = scanner.nextInt() - 1; //

                    System.out.println("What currency would you like to exchange to:");
                    // User Picks The Currency They Want TO Convert To
                    int end = scanner.nextInt() - 1;

                    // Executes Find Best Exchange Rate
                    findBestExchangeRate(graph, start, end);
                } catch (InputMismatchException E) {
                    // Handles Invalid Inputs
                    System.out.println("Invalid Input: Please enter a valid integer");
                    exit(0);
                }
            } else if (scanner.nextLine().equals("n") || scanner.nextLine().equals("N")) {
                // Exits If User Doesn't Want To Find Best Exchange Rate
                System.out.println("Thanks For Using My Program");
                exit(0);
            }
        }
    }

    public static void findBestExchangeRate(double[][] graph, int s, int e) {
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

        String[] labels = CurrencyLabels.getLabels(); // Gets The Labels From CurrencyLabels With The getLabels Method
        //Prints The Starting Label And End Label
        System.out.println("Best exchange rate from " + labels[s] + " to " + labels[e]);
        printPath(p, s, e); // Prints Path Of Best Exchange
        System.out.println();
        // Prints The Currency Exchange Rate
        System.out.println("1.0000 " + labels[s] + " = " + String.format("%.4f", d[e]) + " " + labels[e]);
        System.out.println("Negative Result Means There Are Arbitrage Opportunities");
    }

    private static void printPath(int[] p, int s, int e) {
        String[] labels = CurrencyLabels.getLabels(); // Gets The Labels From CurrencyLabels With The getLabels Method

        //Base Case: Start Node Equal To End Node, Prints Label And Returns
        if (s == e) {
            System.out.print(labels[s]);
            return;
        }

        // Base Case: No Path Exists, Prints Error Message
        if (p[e] == -1) {
            System.out.println("No path exists");
            return;
        }

        // Recursively Calls The PrintPath Method To Print The Path From Start
        // To The Predecessor Of The Current End Node
        printPath(p, s, p[e]);
        System.out.print(" -> " + labels[e]);
    }
}
