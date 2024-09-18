import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.System.exit;

public class CurrencyExchange {
    public static double[][] createGraph(double[][] graph) {
        // Initialise An NxN Adjacency Matrix
        // Using 'N' The Number Of Vertices In The Graph
        int N = graph.length;
        double[][] convertGraph = new double[N][N];

        // Checks If Vertex Is 0 Then Sets It To Infinity Meaning The Path Is Non-Existent.
        // Else It Converts The Value Of The Vertex To A Negative Logarithm.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (graph[i][j] == 0) {
                    convertGraph[i][j] = Double.POSITIVE_INFINITY;
                } else {
                    convertGraph[i][j] = -Math.log(graph[i][j]);
                }
            }
        }
        return convertGraph;
    }

    public static void Options(double[][] graph){
        Scanner scanner = new Scanner(System.in);
        boolean arbitrageFound = false; // Tracks If Arbitrage Has Been Found
        System.out.println("1. Find Arbitrage");
        System.out.println("2. Find Best Exchange Rate");
        try {
            int choice = scanner.nextInt(); // Takes In The Users Choice
            scanner.nextLine(); // Consumes NewLine

            if (choice == 1) {
                for (int i = 0; i < graph.length; i++) {
                    // Checks If A Certain Currency Has An Arbitrage Opportunity
                    if (DetectArbitrage.detectArbitrage(graph, i)) {
                        arbitrageFound = true;
                        break;
                    }
                }
                // If Arbitrage Is Not Found It Will Execute FindBestExchangeRate
                FindBestExchange.arbitrageFound(arbitrageFound, true, graph);
                exit(0);
            } else if (choice == 2) {
                // Executes FindBestExchangeRate
                FindBestExchange.arbitrageFound(true, false, graph);
                exit(0);
            }
        } catch (InputMismatchException E) {
            // Handles Invalid Inputs
            System.out.println("Invalid input, select an option from the menu. ");
        }
        exit(0);
    }

    public static void main(String[] args) {
        // CLI For Start-Up
        System.out.println("Welcome To My Comp611 Assignment");
        System.out.println("1. Demo Cases");
        System.out.println("2. Real-Word Exchange Cases");
        System.out.println("3. Input Table");
        Scanner scanner = new Scanner(System.in); // Creates Scanner Object
        try {
            int choice = scanner.nextInt(); // Takes In The Users Choice
            scanner.nextLine(); // Consumes NewLine

            switch (choice) {
                case 1:
                    // Execute DemoCases
                    DemoCases.demoCases();
                    break;
                case 2:
                    // Execute Real-WorldCases
                    RealTimeExchangeRate.realTimeCases();
                case 3:
                    // Execute Input Table Cases
                    InputTableCase.inputTableCase();
                default:
                    //Handles Invalid Choice
                    System.out.println("Invalid choice. Please try again.");
                    exit(0);
            }
        } catch (InputMismatchException E) {
            // Handles Non-Integer Inputs
            System.out.println("Invalid input, select an option from the menu. ");
        } catch (IOException e) {
            // Handles Input-Output Errors
            throw new RuntimeException(e);
        }
    }
}
